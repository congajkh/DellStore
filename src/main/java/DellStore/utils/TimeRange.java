package DellStore.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeRange {

    private final Date begin;
    private final Date end;

    public TimeRange(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    // Hôm nay
    public static TimeRange today() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date end = cal.getTime();

        return new TimeRange(start, end);
    }

    // Tuần này (thứ Hai đầu tuần, Chủ nhật cuối tuần - chuẩn VN)
    public static TimeRange thisWeek() {
        Calendar cal = Calendar.getInstance();
        // Đặt ngày đầu tuần là thứ Hai
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        // Tính toán lùi về thứ Hai của tuần hiện tại
        int currentDay = cal.get(Calendar.DAY_OF_WEEK);
        int diffToMonday;
        if (currentDay == Calendar.SUNDAY) {
            diffToMonday = -6;
        } else {
            diffToMonday = Calendar.MONDAY - currentDay;
        }
        cal.add(Calendar.DATE, diffToMonday);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        // Tiến tới Chủ nhật
        cal.add(Calendar.DATE, 6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date end = cal.getTime();

        return new TimeRange(start, end);
    }

    // Tháng này
    public static TimeRange thisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MILLISECOND, -1);
        Date end = cal.getTime();

        return new TimeRange(start, end);
    }

    // Quý này
    public static TimeRange thisQuarter() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH); // tháng hiện tại (0-11)
        int quarterStartMonth = (month / 3) * 3; // 0, 3, 6, 9
        cal.set(Calendar.MONTH, quarterStartMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        // Sang quý sau, trừ đi 1ms
        cal.add(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MILLISECOND, -1);
        Date end = cal.getTime();

        return new TimeRange(start, end);
    }

    // Năm nay
    public static TimeRange thisYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();

        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date end = cal.getTime();

        return new TimeRange(start, end);
    }
}
