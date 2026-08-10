package com.lazrproductions.lazrslib.common.math;

import java.time.Instant;
import java.util.Date;

public class TimeUtilities {
    /**
     * Returns the seconds until the given timestamp will occur.
     *
     * @param timestamp The timestamp to test to.
     * @return The seconds until the given timestamp will occur.
     *         <br/>
     *         WARN: will return a negative number if the timestamp has already
     *         occurred.
     */
    public static long getSecondsUntil(long timestamp) {
        return getSecondsUntil(timestamp, getCurrentTimestamp());
    }

    /**
     * Returns the seconds until the given timestamp will occur from the given
     * timestamp.
     *
     * @param timestamp     The timestamp to test to.
     * @param fromTimestamp The timestamp to test from.
     * @return The seconds until the given timestamp will occur from the given
     *         timestamp.
     *         <br/>
     *         WARN: will return a negative number if the fromTimestamp will occur
     *         after the given timestamp.
     */
    public static long getSecondsUntil(long timestamp, long fromTimestamp) {
        return timestamp - fromTimestamp;
    }

    /**
     * Gets the current timestamp
     *
     * @return the current timestamp.
     *         <br/>
     *         This is equal to the number of seconds from the Java epoch of
     *         1970-01-01T00:00:00Z.
     */
    public static long getCurrentTimestamp() {
        return Instant.now().getEpochSecond();
    }

    /**
     * Get the current date as a Date object.
     *
     * @return The current date as a Date object.
     */
    public static Date getDate() {
        return getDate(getCurrentTimestamp());
    }

    /**
     * Get the date of the given timestamp as a Date object.
     *
     * @return The date of the given timestamp as a Date object.
     */
    public static Date getDate(long timestamp) {
        return Date.from(Instant.ofEpochSecond(timestamp));
    }

    /**
     * Convert the current timestamp to a string of the form:
     * <blockquote>
     *
     * <pre>
     * dow mon dd hh:mm:ss zzz yyyy
     * </pre>
     *
     * </blockquote>
     * where:
     * <ul>
     * <li>{@code dow} is the day of the week ({@code Sun, Mon, Tue, Wed,
     *     Thu, Fri, Sat}).
     * <li>{@code mon} is the month ({@code Jan, Feb, Mar, Apr, May, Jun,
     *     Jul, Aug, Sep, Oct, Nov, Dec}).
     * <li>{@code dd} is the day of the month ({@code 01} through
     * {@code 31}), as two decimal digits.
     * <li>{@code hh} is the hour of the day ({@code 00} through
     * {@code 23}), as two decimal digits.
     * <li>{@code mm} is the minute within the hour ({@code 00} through
     * {@code 59}), as two decimal digits.
     * <li>{@code ss} is the second within the minute ({@code 00} through
     * {@code 61}, as two decimal digits.
     * <li>{@code zzz} is the time zone (and may reflect daylight saving
     * time). Standard time zone abbreviations include those
     * recognized by the method {@code parse}. If time zone
     * information is not available, then {@code zzz} is empty -
     * that is, it consists of no characters at all.
     * <li>{@code yyyy} is the year, as four decimal digits.
     * </ul>
     *
     * @return a string representation of the current timestamp.
     */
    public static String stringifyTime() {
        return stringifyTime(getCurrentTimestamp());
    }

    /**
     * Convert the given timestamp to a string of the form:
     * <blockquote>
     *
     * <pre>
     * dow mon dd hh:mm:ss zzz yyyy
     * </pre>
     *
     * </blockquote>
     * where:
     * <ul>
     * <li>{@code dow} is the day of the week ({@code Sun, Mon, Tue, Wed,
     *     Thu, Fri, Sat}).
     * <li>{@code mon} is the month ({@code Jan, Feb, Mar, Apr, May, Jun,
     *     Jul, Aug, Sep, Oct, Nov, Dec}).
     * <li>{@code dd} is the day of the month ({@code 01} through
     * {@code 31}), as two decimal digits.
     * <li>{@code hh} is the hour of the day ({@code 00} through
     * {@code 23}), as two decimal digits.
     * <li>{@code mm} is the minute within the hour ({@code 00} through
     * {@code 59}), as two decimal digits.
     * <li>{@code ss} is the second within the minute ({@code 00} through
     * {@code 61}, as two decimal digits.
     * <li>{@code zzz} is the time zone (and may reflect daylight saving
     * time). Standard time zone abbreviations include those
     * recognized by the method {@code parse}. If time zone
     * information is not available, then {@code zzz} is empty -
     * that is, it consists of no characters at all.
     * <li>{@code yyyy} is the year, as four decimal digits.
     * </ul>
     *
     * @return a string representation of the given timestamp.
     */
    public static String stringifyTime(long timestamp) {
        return stringifyTime(getDate(timestamp));
    }

    /**
     * Convert the given date to a string of the form:
     * <blockquote>
     *
     * <pre>
     * dow mon dd hh:mm:ss zzz yyyy
     * </pre>
     *
     * </blockquote>
     * where:
     * <ul>
     * <li>{@code dow} is the day of the week ({@code Sun, Mon, Tue, Wed,
     *     Thu, Fri, Sat}).
     * <li>{@code mon} is the month ({@code Jan, Feb, Mar, Apr, May, Jun,
     *     Jul, Aug, Sep, Oct, Nov, Dec}).
     * <li>{@code dd} is the day of the month ({@code 01} through
     * {@code 31}), as two decimal digits.
     * <li>{@code hh} is the hour of the day ({@code 00} through
     * {@code 23}), as two decimal digits.
     * <li>{@code mm} is the minute within the hour ({@code 00} through
     * {@code 59}), as two decimal digits.
     * <li>{@code ss} is the second within the minute ({@code 00} through
     * {@code 61}, as two decimal digits.
     * <li>{@code zzz} is the time zone (and may reflect daylight saving
     * time). Standard time zone abbreviations include those
     * recognized by the method {@code parse}. If time zone
     * information is not available, then {@code zzz} is empty -
     * that is, it consists of no characters at all.
     * <li>{@code yyyy} is the year, as four decimal digits.
     * </ul>
     *
     * @return a string representation of the given date.
     */
    public static String stringifyTime(Date date) {
        return date.toString();
    }
}