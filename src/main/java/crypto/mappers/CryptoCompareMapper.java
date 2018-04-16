package crypto.mappers;

import crypto.model.histohour.internal.DataSummary;
import crypto.model.histohour.internal.SqlDataSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface CryptoCompareMapper {

//////////// SELECT statement for getting hourly historical data back from the mySQL DB ///////////////////////

    String GET_FSYM_MINUTE = ("SELECT * FROM `mybatis-test`.historical_minute where fsym = #{fsym}");
    String GET_FSYM_HOUR = ("SELECT * FROM `mybatis-test`.historical_hour where fsym = #{fsym}");

    String GET_FSYM_DAY = ("SELECT * FROM `mybatis-test`.historical_day where fsym = #{fsym}");

    /////////// INSERT statements for inserting DataSummary model objects to the mySQL database ////////////////
    String INSERT_HOURSUMMARY = ("INSERT INTO `mybatis-test`.historical_minute (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");
    String INSERT_MINUTESUMMARY = ("INSERT INTO `mybatis-test`.historical_hour (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");
    String INSERT_DAYSUMMARY = ("INSERT INTO `mybatis-test`.historical_day (fsym,tsym,time,dateTime, close, open, high, low) " +
            "VALUES (#{fsym}, #{tsym},#{time},#{dateTime},#{close}, #{open}, #{high},#{low})");

    /////////// INSERT methods for connecting to the DB INSERT statements ////////////////
    @Insert(INSERT_HOURSUMMARY)
    public long insertMinuteSummary(DataSummary result);
    @Insert(INSERT_MINUTESUMMARY)
    public long insertHourSummary(DataSummary result);
    @Insert(INSERT_DAYSUMMARY)
    public long insertDaySummary(DataSummary result);

    /////////// SELECT method for hourly query of the mySQL DB INSERT statement ////////////////
    @Select(GET_FSYM_HOUR)
    ArrayList<SqlDataSummary> getHourDataByFsym(String fsym);

    @Select(GET_FSYM_MINUTE)
    ArrayList<SqlDataSummary> getMinuteDataByFsym(String fsym);

    @Select(GET_FSYM_DAY)
    ArrayList<SqlDataSummary> getDayDataByFsym(String fsym);
}
