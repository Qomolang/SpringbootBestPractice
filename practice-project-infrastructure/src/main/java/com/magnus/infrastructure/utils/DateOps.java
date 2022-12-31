package com.magnus.infrastructure.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author gaosong
 */
public class DateOps {

    public static Long getNextDayIntervalInMilliseconds() {
        Date now = new Date();

        //算到下一天 2022/02/02 22:22 -> 2022/02/03 00:00
        Date nextDayMidnight = DateUtils.ceiling(now, Calendar.DATE);

        Long interval = nextDayMidnight.getTime() - now.getTime();
        return interval;
    }

}
