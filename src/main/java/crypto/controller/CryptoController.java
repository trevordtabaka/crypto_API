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
     * @param fsym - You can specify the cryptocurrency you want to query  i.e. BTC
     * @param tsym - You can specify the currency you want to compare against i.e. USD
     * @param e - Name of exchange
     * @param extraParams - Name of your application
     * @param sign - If set to true, the server will sign the requests.
     * @param limit - This sets how many hours back you want to retrieve data from cryptocompare. Max 1000 for days and hours and 2000 for minutes. Default set to 100 here
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
        return cryptoCompareService.getHistoData(timeFrame, fsym, tsym, e, extraParams, sign, limit, persist);
    }

    /**
     *
     * @param fsym - Enter the cryptocurrency you wasnt to get hourly data back from mySQL db.
     *            Returns all data of a certain symbol, not distinguishing between the compared currencies.
     * @return calls the service method
     */
    @RequestMapping("/minute/{fsym}")
    public ArrayList<SqlDataSummary> getMinuteDataByFsym(@PathVariable(value= "fsym")String fsym) {
        return cryptoCompareService.getMinuteDataByFsym(fsym);
    }
    @RequestMapping("/hour/{fsym}")
    public ArrayList<SqlDataSummary> getHourDataByFsym(@PathVariable(value= "fsym")String fsym) {
        return cryptoCompareService.getHourDataByFsym(fsym);
    }
    @RequestMapping("/day/{fsym}")
    public ArrayList<SqlDataSummary> getDayDataByFsym(@PathVariable(value= "fsym")String fsym) {
        return cryptoCompareService.getDayDataByFsym(fsym);
    }

}
