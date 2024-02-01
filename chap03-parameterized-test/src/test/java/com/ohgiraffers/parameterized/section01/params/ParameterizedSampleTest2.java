package com.ohgiraffers.parameterized.section01.params;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParameterizedSampleTest2 {

    @DisplayName("샘플 테스트")
    @ParameterizedTest // 여러개의 테스트를 한꺼번에
    @ValueSource(ints = {16,17,18})
    void testString(int age){

        System.out.println("number : " + age);

    }


}
