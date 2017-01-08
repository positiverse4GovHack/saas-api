package com.positiverse.govhack.saas.util;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by gha on 04.01.17.
 */
public class DateUtils {

    public static Date convertToDate(BigInteger blockchainSeconds) {
        return new Date(blockchainSeconds.longValue() * 1000L);
    }
}
