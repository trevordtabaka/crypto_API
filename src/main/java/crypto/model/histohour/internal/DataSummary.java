package crypto.model.histohour.internal;

import java.util.Date;

public class DataSummary {

    int id;
    String fsym;
    String tsym;
    long time;
    Date dateTime;
    double close;
    double high;
    double low;
    double open;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFsym() {
        return fsym;
    }

    public void setFsym(String fsym) {
        this.fsym = fsym;
    }

    public String getTsym() {
        return tsym;
    }

    public void setTsym(String tsym) {
        this.tsym = tsym;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        long millis = time *1000;
        setDateTime(millis);
        this.time = millis;
    }

    public Date getDateTime() {
        return dateTime;
    }


    public void setDateTime(long millis) {
        Date date = new Date(millis);
        this.dateTime = date;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }
}


