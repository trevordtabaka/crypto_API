package crypto.model.histohour.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import crypto.model.ConversionType;
import crypto.model.Data;

public class HistoRoot {

    @JsonProperty("Response")
    String response;
    @JsonProperty("Type")
    int type;
    @JsonProperty("Aggregated")
    boolean aggregated;
    @JsonProperty("Data")
    Data[] data;
    @JsonProperty("TimeTo")
    long timeTo;
    @JsonProperty("TimeFrom")
    long timeFrom;
    @JsonProperty("FirstValueInArray")
    boolean firstValueInArray;
    @JsonProperty("ConversionType")
    ConversionType conversionType;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAggregated() {
        return aggregated;
    }

    public void setAggregated(boolean aggregated) {
        this.aggregated = aggregated;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public boolean isFirstValueInArray() {
        return firstValueInArray;
    }

    public void setFirstValueInArray(boolean firstValueInArray) {
        this.firstValueInArray = firstValueInArray;
    }

    public ConversionType getConversionType() {
        return conversionType;
    }

    public void setConversionType(ConversionType conversionType) {
        this.conversionType = conversionType;
    }
}
