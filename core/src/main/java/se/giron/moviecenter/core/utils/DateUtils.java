package se.giron.moviecenter.core.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.io.File.separatorChar;

public class DateUtils {

    public static Date getStartOfToday() {
        return Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS));
    }

    public static Date getStartOfTodayMidnight() {
        return Date.from(Instant.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toInstant());
    }

    public static Date getStartOfYesterday() {
        return Date.from(Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS));
    }

    public static Date getStartOfDay(Date date) {
        return Date.from(date.toInstant().truncatedTo(ChronoUnit.DAYS));
    }

    public static Date getStartOfNextDay(Date date) {
        return Date.from(date.toInstant().truncatedTo(ChronoUnit.DAYS).plus(1, ChronoUnit.DAYS));
    }

    public static Date getStartOfDayInThreeMonths() {
        return getStartOfDay(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)));
    }

    public static String getYearAndMonthForFilePath() {
        return separatorChar + YearMonth.now().toString().replace("-", Character.toString(separatorChar)) + separatorChar;
    }

    public static String getStringFromScheduleDate(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String getStringFromDate(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getStringFromDateAlt(final Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDateForThePast() {
        return asDate(LocalDate.of(1700, Month.JANUARY, 1));
    }

    public static Date getDateForTheFuture() {
        return asDate(LocalDate.of(3000, Month.DECEMBER, 31));
    }
}
