package com.zb.daily;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void Test1() {
        BigDecimal d = new BigDecimal("3.1465926");
        BigDecimal i = d.setScale(2, RoundingMode.DOWN);
        System.out.println("i是："+i);
    }
}