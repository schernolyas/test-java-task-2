package ru.schernolyas.testtask.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static BigDecimal build(double d) {
        return new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);

    }
}
