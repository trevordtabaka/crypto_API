package crypto.mappers;

import crypto.model.histohour.internal.DataHourSummary;
import crypto.model.histohour.internal.SqlDataSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface CryptoCompareMapper {


    String GET_FSYM_HOUR = ("SELECT * FROM `mybatis-test`.historical_hour where fsym = #{fsym}");
    String INSERT_HOURSUMMARY = ("INSERT INTO `mybatis-test`.historical_minute (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");
    String INSERT_MINUTESUMMARY = ("INSERT INTO `mybatis-test`.historical_hour (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");
    String INSERT_DAYSUMMARY = ("INSERT INTO `mybatis-test`.historical_day (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");

    @Insert(INSERT_HOURSUMMARY)
    public long insertMinuteSummary(DataHourSummary result);
    @Insert(INSERT_MINUTESUMMARY)
    public long insertHourSummary(DataHourSummary result);
    @Insert(INSERT_DAYSUMMARY)
    public long insertDaySummary(DataHourSummary result);
    @Select(GET_FSYM_HOUR)
    ArrayList<SqlDataSummary> getHourDataByFsym(String fsym);


}
