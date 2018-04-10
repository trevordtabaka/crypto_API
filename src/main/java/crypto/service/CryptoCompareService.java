package crypto.service;


import crypto.mappers.CryptoCompareMapper;
import crypto.model.histohour.external.Data;
import crypto.model.histohour.external.HistoHourRoot;
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


    public HistoHourRoot getHistoHour(String fsym, String tsym, String e, String extraParams, boolean sign, int limit, boolean persist) throws SQLIntegrityConstraintViolationException {

        // Query
        String fQuery = "https://min-api.cryptocompare.com/data/histohour?fsym="+fsym+"&tsym="+tsym+"&e="+e+"&extraParams="+extraParams+"&sign="+sign+"&limit="+limit+"&persist="+persist;
        System.out.println(fQuery);
        HistoHourRoot hourResponse = restTemplate.getForObject(
                fQuery, HistoHourRoot.class);
        HistoHourRoot histoHourRoot = new HistoHourRoot();

        if(persist){
            histoHourRoot.setResponse(hourResponse.getResponse());

            for (Data element : hourResponse.getData()) {
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


                    insertHourSummary(dataSummary);
                    System.out.println("Added not unique data " + dataSummary.getTime());
                }catch(Exception dupE){
                    System.out.println("Caught a duplicate entry");
                }



            }


        }

        return hourResponse;
    }
    public void insertHourSummary(DataHourSummary result) throws Exception
    {

        cryptoCompareMapper.insertHourSummary(result);

    }


    public ArrayList<SqlDataSummary> getHourDataByFsym(String fsym) {

        return cryptoCompareMapper.getHourDataByFsym(fsym);

    }

//    public HistoDailyRoot getHistoDaily(String fsym, String tsym, String e, String extraParams, boolean sign, int limit, boolean persist) {
//    }
}
