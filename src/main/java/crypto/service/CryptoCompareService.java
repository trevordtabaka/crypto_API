package crypto.service;


import crypto.mappers.CryptoCompareMapper;
import crypto.model.Data;
import crypto.model.histohour.external.HistoRoot;
import crypto.model.histohour.internal.DataHourSummary;
import crypto.model.histohour.internal.SqlDataSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@Service
public class CryptoCompareService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CryptoCompareMapper cryptoCompareMapper;


    public HistoRoot getHistoHour(String timeFrame, String fsym, String tsym, String e, String extraParams, boolean sign, int limit, boolean persist) throws SQLIntegrityConstraintViolationException {

        // Query
        String fQuery = "https://min-api.cryptocompare.com/data/histo"+ timeFrame + "?fsym="+fsym+"&tsym="+tsym+"&e="+e+"&extraParams="+extraParams+"&sign="+sign+"&limit="+limit+"&persist="+persist;
        System.out.println(fQuery);
        HistoRoot response = restTemplate.getForObject(
                fQuery, HistoRoot.class);
        HistoRoot histoRoot = new HistoRoot();

        if(persist){
            histoRoot.setResponse(response.getResponse());

            for (Data element : response.getData()) {
                DataHourSummary dataSummary = new DataHourSummary();
                try{

                    dataSummary.setTime(element.getTime());
                    dataSummary.setDateTime(dataSummary.getTime());
                    dataSummary.setFsym(fsym);
                    dataSummary.setTsym(tsym);
                    dataSummary.setClose(element.getClose());
                    dataSummary.setOpen(element.getOpen());
                    dataSummary.setHigh(element.getHigh());
                    dataSummary.setLow(element.getLow());


                    insertHourSummary(timeFrame,dataSummary);
                    System.out.println("Added not unique data " + dataSummary.getTime());
                }catch(Exception dupE){
                    System.out.println("Caught a duplicate entry");
                }



            }


        }

        return response;
    }
    public void insertHourSummary(String timeFrame, DataHourSummary result) throws Exception
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
