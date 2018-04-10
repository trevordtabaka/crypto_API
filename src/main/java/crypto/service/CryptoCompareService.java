package crypto.service;


import crypto.mappers.CryptoCompareMapper;
import crypto.model.Data;
import crypto.model.histohour.external.HistoRoot;
import crypto.model.histohour.internal.DataSummary;
import crypto.model.histohour.internal.SqlDataSummary;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CryptoCompareService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CryptoCompareMapper cryptoCompareMapper;


    public HistoRoot getHistoData(String timeFrame, String fsym, String tsym, String e, String extraParams, boolean sign, int limit, boolean persist) throws SQLIntegrityConstraintViolationException {

     //////// Query sent to the cryptocompare api ////////
        String fQuery = "https://min-api.cryptocompare.com/data/histo"+ timeFrame + "?fsym="+fsym+"&tsym="+tsym+"&e="+e+"&extraParams="+extraParams+"&sign="+sign+"&limit="+limit+"&persist="+persist;
        /// Just a log of the request being sent /////
        logger.info(fQuery);
        // Create a response object of model type HistoRoot //
        HistoRoot response = restTemplate.getForObject(fQuery, HistoRoot.class);
        HistoRoot histoRoot = new HistoRoot();

        /// If the persist parameter is true, take the HistoRoot response object and map it to a DataSummary object
        // that contains the information we want to map and send to the mySql DB
        if(persist){
            histoRoot.setResponse(response.getResponse());

            for (Data element : response.getData()) {
                DataSummary dataSummary = new DataSummary();

                // If the data element has a time, fsym, and tsym combination that has not been entered into the associated timeFrame table
                // insert that entry to the database. If it isnt unique then catch the failed try and notify that it was a duplicate
                try{

                    dataSummary.setTime(element.getTime());
                    dataSummary.setDateTime(dataSummary.getTime());
                    dataSummary.setFsym(fsym);
                    dataSummary.setTsym(tsym);
                    dataSummary.setClose(element.getClose());
                    dataSummary.setOpen(element.getOpen());
                    dataSummary.setHigh(element.getHigh());
                    dataSummary.setLow(element.getLow());

                    insertDateSummary(timeFrame,dataSummary);
                    logger.info("Added not unique data " + dataSummary.getTime());
                }catch(Exception dupE){
                    logger.info("Caught a duplicate entry");
                }



            }


        }

        return response;
    }

    /**
     *
     * @param timeFrame - Can be minute, hour, day which decides what mapper is called and
     *                  therefore what table in the mySQL the data is sent to
     * @param result - the DataSummary object with the cryptocurrency information
     * @throws Exception - Throws exception if the DataSummary object is unique in its time, fsym and tsym.
     */
    public void insertDateSummary(String timeFrame, DataSummary result) throws Exception
    {
        switch (timeFrame){

            case "minute":
                cryptoCompareMapper.insertMinuteSummary(result);
            case "hour":
                cryptoCompareMapper.insertHourSummary(result);
            case "day":
                cryptoCompareMapper.insertDaySummary(result);
        }

    }


    public ArrayList<SqlDataSummary> getHourDataByFsym(String fsym) {

        return cryptoCompareMapper.getHourDataByFsym(fsym);

    }

}
