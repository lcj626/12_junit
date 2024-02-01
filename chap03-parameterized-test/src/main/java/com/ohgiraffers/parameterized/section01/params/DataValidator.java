package com.ohgiraffers.parameterized.section01.params;

import java.time.Month;

public class DataValidator {
    public static boolean isCollect(Month month){
        int monthValue = month.getValue();
        return monthValue >=1 && monthValue <=12; // 1월부터 12월 사이에 속하는지
    }

    public static int getLastDayOf(Month month){

        return month.maxLength();
    }
}
