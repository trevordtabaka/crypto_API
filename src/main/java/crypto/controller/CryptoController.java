package crypto.controller;

import crypto.model.histohour.external.HistoRoot;
import crypto.model.histohour.internal.SqlDataSummary;
import crypto.service.CryptoCompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@RestController
@RequestMapping("cryptocompare")
public class CryptoController {

    @Autowired
    CryptoCompareService cryptoCompareService;

    /**
     *
     * @param fsym - You can specify the cryptocurrencY you want to query separated by commas. i.e. BTC,ETH,...
     * @param tsym- You can specify the fiatcurrencies you want to compare against separated by commas. i.e. USD,EUR,...
     * @param persist - Persist set to true will notify the service that you wish to store the data into your database
     * @return- Returns a call to the method getMultiData() in the service package with the specified variables
     */

    /**
     *
     * @param fsym - You can specify the cryptocurrency you want to query  i.e. BTC
     * @param tsym - You can specify the currency you want to compare against i.e. USD
     * @param e -
     * @param extraParams -
     * @param sign -
     * @param limit - This sets how many hours back you want to retrieve data from cryptocompare. Max 1000. Default 100
     * @param persist - When persist is true, it triggers the saving the response into the mySQL database
     * @return
     * @throws SQLIntegrityConstraintViolationException
     */
    @RequestMapping("/")
    public HistoRoot getHistoHour(@RequestParam(value="timeFrame", defaultValue="minute") String timeFrame,
                                  @RequestParam(value="fsym", defaultValue="BTC") String fsym,
                                  @RequestParam(value="tsym", defaultValue="USD") String tsym,
                                  @RequestParam(value="e", defaultValue="CCCAGG") String e,
                                  @RequestParam(value="extraParams", defaultValue="NotAvailable") String extraParams,
                                  @RequestParam(value="sign", defaultValue="false") boolean sign,
                                  @RequestParam(value="limit", defaultValue="100") int limit,
                                  @RequestParam(value="persist", defaultValue="true") boolean persist) throws SQLIntegrityConstraintViolationException {
        return cryptoCompareService.getHistoHour(timeFrame, fsym, tsym, e, extraParams, sign, limit, persist);
    }

//    @RequestMapping("/")
//    public HistoDailyRoot getHistoHour(@RequestParam(value="fsym", defaultValue="BTC") String fsym,
//                                      @RequestParam(value="tsym", defaultValue="USD") String tsym,
//                                      @RequestParam(value="e", defaultValue="CCCAGG") String e,
//                                      @RequestParam(value="extraParams", defaultValue="NotAvailable") String extraParams,
//                                      @RequestParam(value="sign", defaultValue="false") boolean sign,
//                                      @RequestParam(value="limit", defaultValue="168") int limit,
//                                      @RequestParam(value="persist", defaultValue="true") boolean persist) throws SQLIntegrityConstraintViolationException {
//        return cryptoCompareService.getHistoDaily(fsym, tsym, e, extraParams, sign, limit, persist);
//    }
    @RequestMapping("/{fsym}")
    public ArrayList<SqlDataSummary> getHourDataByFsym(@PathVariable(value= "fsym")String fsym) {
        return cryptoCompareService.getHourDataByFsym(fsym);
    }

}
