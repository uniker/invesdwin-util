package de.invesdwin.util.time.fdate;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.Immutable;

import org.joda.time.DurationFieldType;

import de.invesdwin.util.error.UnknownArgumentException;

@Immutable
public enum FTimeUnit {

    YEARS {
        @Override
        public int calendarValue() {
            return Calendar.YEAR;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.YEARS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.years();
        }

        @Override
        public TimeUnit timeUnitValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toYears(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return TimeUnit.DAYS.toNanos(toDays(duration));
        }

        @Override
        public long toMicros(final long duration) {
            return TimeUnit.DAYS.toMicros(toDays(duration));
        }

        @Override
        public long toMillis(final long duration) {
            return TimeUnit.DAYS.toMillis(toDays(duration));
        }

        @Override
        public long toSeconds(final long duration) {
            return TimeUnit.DAYS.toSeconds(toDays(duration));
        }

        @Override
        public long toMinutes(final long duration) {
            return TimeUnit.DAYS.toMinutes(toDays(duration));
        }

        @Override
        public long toHours(final long duration) {
            return TimeUnit.DAYS.toHours(toDays(duration));
        }

        @Override
        public long toDays(final long duration) {
            return duration * DAYS_IN_YEAR;
        }

        @Override
        public long toWeeks(final long duration) {
            return duration * WEEKS_IN_YEAR;
        }

        @Override
        public long toMonths(final long duration) {
            return duration * MONTHS_IN_YEAR;
        }

        @Override
        public long toYears(final long duration) {
            return duration;
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            TimeUnit.DAYS.sleep(toDays(timeout));
        }

    },
    MONTHS {
        @Override
        public int calendarValue() {
            return Calendar.MONTH;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.MONTHS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.months();
        }

        @Override
        public TimeUnit timeUnitValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toMonths(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return TimeUnit.DAYS.toNanos(toDays(duration));
        }

        @Override
        public long toMicros(final long duration) {
            return TimeUnit.DAYS.toMicros(toDays(duration));
        }

        @Override
        public long toMillis(final long duration) {
            return TimeUnit.DAYS.toMillis(toDays(duration));
        }

        @Override
        public long toSeconds(final long duration) {
            return TimeUnit.DAYS.toSeconds(toDays(duration));
        }

        @Override
        public long toMinutes(final long duration) {
            return TimeUnit.DAYS.toMinutes(toDays(duration));
        }

        @Override
        public long toHours(final long duration) {
            return TimeUnit.DAYS.toHours(toDays(duration));
        }

        @Override
        public long toDays(final long duration) {
            return duration * DAYS_IN_MONTH;
        }

        @Override
        public long toWeeks(final long duration) {
            return duration * WEEKS_IN_MONTH;
        }

        @Override
        public long toMonths(final long duration) {
            return duration;
        }

        @Override
        public long toYears(final long duration) {
            return duration / MONTHS_IN_YEAR;
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            TimeUnit.DAYS.sleep(toDays(timeout));
        }

    },
    WEEKS {

        @Override
        public int calendarValue() {
            return Calendar.WEEK_OF_YEAR;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.WEEKS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.weeks();
        }

        @Override
        public TimeUnit timeUnitValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toWeeks(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return TimeUnit.DAYS.toNanos(toDays(duration));
        }

        @Override
        public long toMicros(final long duration) {
            return TimeUnit.DAYS.toMicros(toDays(duration));
        }

        @Override
        public long toMillis(final long duration) {
            return TimeUnit.DAYS.toMillis(toDays(duration));
        }

        @Override
        public long toSeconds(final long duration) {
            return TimeUnit.DAYS.toSeconds(toDays(duration));
        }

        @Override
        public long toMinutes(final long duration) {
            return TimeUnit.DAYS.toMinutes(toDays(duration));
        }

        @Override
        public long toHours(final long duration) {
            return TimeUnit.DAYS.toHours(toDays(duration));
        }

        @Override
        public long toDays(final long duration) {
            return duration * DAYS_IN_WEEK;
        }

        @Override
        public long toWeeks(final long duration) {
            return duration;
        }

        @Override
        public long toMonths(final long duration) {
            return duration / WEEKS_IN_MONTH;
        }

        @Override
        public long toYears(final long duration) {
            return duration / WEEKS_IN_YEAR;
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            TimeUnit.DAYS.sleep(toDays(timeout));
        }

    },
    DAYS {
        @Override
        public int calendarValue() {
            return Calendar.DAY_OF_MONTH;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.DAYS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.days();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.DAYS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toDays(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return duration;
        }

        @Override
        public long toWeeks(final long duration) {
            return duration / DAYS_IN_WEEK;
        }

        @Override
        public long toMonths(final long duration) {
            return duration / DAYS_IN_MONTH;
        }

        @Override
        public long toYears(final long duration) {
            return duration / DAYS_IN_YEAR;
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    HOURS {
        @Override
        public int calendarValue() {
            return Calendar.HOUR_OF_DAY;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.HOURS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.hours();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.HOURS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toHours(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return duration;
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    MINUTES {
        @Override
        public int calendarValue() {
            return Calendar.MINUTE;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.MINUTES;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.minutes();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.MINUTES;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toMinutes(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return duration;
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    SECONDS {
        @Override
        public int calendarValue() {
            return Calendar.SECOND;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.SECONDS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.seconds();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.SECONDS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toSeconds(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return duration;
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    MILLISECONDS {
        @Override
        public int calendarValue() {
            return Calendar.MILLISECOND;
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.MILLIS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            return DurationFieldType.millis();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.MILLISECONDS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toMillis(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return duration;
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    MICROSECONDS {
        @Override
        public int calendarValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.MICROS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.MICROSECONDS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toMicros(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return timeUnitValue().toNanos(duration);
        }

        @Override
        public long toMicros(final long duration) {
            return duration;
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    },
    NANOSECONDS {
        @Override
        public int calendarValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChronoUnit javaTimeValue() {
            return ChronoUnit.NANOS;
        }

        @Override
        public DurationFieldType jodaTimeValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public TimeUnit timeUnitValue() {
            return TimeUnit.NANOSECONDS;
        }

        @Override
        public long convert(final long duration, final FTimeUnit timeUnit) {
            return timeUnit.toNanos(duration);
        }

        @Override
        public long toNanos(final long duration) {
            return duration;
        }

        @Override
        public long toMicros(final long duration) {
            return timeUnitValue().toMicros(duration);
        }

        @Override
        public long toMillis(final long duration) {
            return timeUnitValue().toMillis(duration);
        }

        @Override
        public long toSeconds(final long duration) {
            return timeUnitValue().toSeconds(duration);
        }

        @Override
        public long toMinutes(final long duration) {
            return timeUnitValue().toMinutes(duration);
        }

        @Override
        public long toHours(final long duration) {
            return timeUnitValue().toHours(duration);
        }

        @Override
        public long toDays(final long duration) {
            return timeUnitValue().toDays(duration);
        }

        @Override
        public long toWeeks(final long duration) {
            return DAYS.toWeeks(toDays(duration));
        }

        @Override
        public long toMonths(final long duration) {
            return DAYS.toMonths(toDays(duration));
        }

        @Override
        public long toYears(final long duration) {
            return DAYS.toYears(toDays(duration));
        }

        @Override
        public void sleep(final long timeout) throws InterruptedException {
            timeUnitValue().sleep(timeout);
        }

    };

    public static final int DAYS_IN_YEAR = 365;
    public static final int MONTHS_IN_YEAR = 12;
    public static final int DAYS_IN_WEEK = 7;
    public static final int DAYS_IN_MONTH = DAYS_IN_YEAR / MONTHS_IN_YEAR;
    public static final int WEEKS_IN_MONTH = DAYS_IN_MONTH / DAYS_IN_WEEK;
    public static final int WEEKS_IN_YEAR = DAYS_IN_YEAR / DAYS_IN_WEEK;
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MILLISECONDS_IN_SECOND = 1000;
    public static final int MICROSECONDS_IN_MILLISECOND = 1000;
    public static final int NANOSECONDS_IN_MICROSECOND = 1000;

    private static final Map<TimeUnit, FTimeUnit> TIME_UNIT_LOOKUP = new HashMap<TimeUnit, FTimeUnit>();
    private static final Map<Integer, FTimeUnit> CALENDAR_LOOKUP = new HashMap<Integer, FTimeUnit>();
    private static final Map<ChronoUnit, FTimeUnit> JAVA_TIME_LOOKUP = new HashMap<ChronoUnit, FTimeUnit>();
    private static final Map<DurationFieldType, FTimeUnit> JODA_TIME_LOOKUP = new HashMap<DurationFieldType, FTimeUnit>();

    static {
        for (final FTimeUnit f : values()) {
            try {
                TIME_UNIT_LOOKUP.put(f.timeUnitValue(), f);
            } catch (final UnsupportedOperationException e) { //SUPPRESS CHECKSTYLE empty block
                //ignore
            }
            try {
                CALENDAR_LOOKUP.put(f.calendarValue(), f);
            } catch (final UnsupportedOperationException e) {//SUPPRESS CHECKSTYLE empty block
                //ignore
            }
            try {
                JAVA_TIME_LOOKUP.put(f.javaTimeValue(), f);
            } catch (final UnsupportedOperationException e) {//SUPPRESS CHECKSTYLE empty block
                //ignore
            }
            try {
                JODA_TIME_LOOKUP.put(f.jodaTimeValue(), f);
            } catch (final UnsupportedOperationException e) {//SUPPRESS CHECKSTYLE empty block
                //ignore
            }
        }
    }

    public abstract int calendarValue();

    public abstract TimeUnit timeUnitValue();

    public abstract ChronoUnit javaTimeValue();

    public abstract DurationFieldType jodaTimeValue();

    public static FTimeUnit valueOfTimeUnit(final TimeUnit timeUnit) {
        return lookup(TIME_UNIT_LOOKUP, timeUnit);
    }

    public static FTimeUnit valueOfCalendar(final int timeUnit) {
        return lookup(CALENDAR_LOOKUP, timeUnit);
    }

    public static FTimeUnit valueOfJavaTime(final ChronoUnit timeUnit) {
        return lookup(JAVA_TIME_LOOKUP, timeUnit);
    }

    public static FTimeUnit valueOfJodaTime(final DurationFieldType timeUnit) {
        return lookup(JODA_TIME_LOOKUP, timeUnit);
    }

    @SuppressWarnings("unchecked")
    private static <T> FTimeUnit lookup(final Map<T, FTimeUnit> map, final T timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("parameter field should not be null");
        }
        final FTimeUnit value = map.get(timeUnit);
        if (value == null) {
            throw UnknownArgumentException.newInstance((Class<T>) timeUnit.getClass(), timeUnit);
        } else {
            return value;
        }
    }

    public abstract void sleep(final long timeout) throws InterruptedException;

    public abstract long convert(final long duration, final FTimeUnit timeUnit);

    public abstract long toNanos(final long duration);

    public abstract long toMicros(final long duration);

    public abstract long toMillis(final long duration);

    public abstract long toSeconds(final long duration);

    public abstract long toMinutes(final long duration);

    public abstract long toHours(final long duration);

    public abstract long toDays(final long duration);

    public abstract long toWeeks(final long duration);

    public abstract long toMonths(final long duration);

    public abstract long toYears(final long duration);

}
