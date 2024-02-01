package com.ohgiraffers.parameterized.section01.params;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParameterizedTests {

    /*
    * 테스트 시 여러 값들을 검증을 해야 하는 모든 경우의 수 만큼 테스트 메소드를 작성해야 한다.
    * parameterized-test는 @ParameterizedTest 어노테이션을 @test 어노테이션을 대체하여 사용하며,
    * 이 경우 테스트 메소드에 배개변수를 선언할 수 있다. (일반적으로 테스트 메소드 매개변수 사용 불가)
    * 매개변수로 전달하여 테스트 코드를 실행하게 된다.
    * given when then 패턴에서 사전에 테스트를 위해 준비하던 given 부분을 대체할 수 있게 된다.
    * */

    // @ValueSource를 이용한 parameter Value 목록 지정
    /*
    * @ValueSource를 이용하여 한 개의 파라미터로 전달할 값들의 목록을 지정할 수 있다.
    * 이 때 지원하는 타입은 다음과 같다.
    * short, byte, int, long, float, double, char, java.lang.String, java.lang.Class
    * */
    @DisplayName("홀수 짝수 판별 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,3,-1,15,123}) //사실상 given 단계
    void testIsOdd(int number){// 하나씩 꺼내서 요소 대입

        //when
        boolean result = NumberValidator.isOdd(number);

        //then
        Assertions.assertTrue(result);
    }

    // @NullSource와 @EmptySource
    /*
    * 기본 자료형 타입은 null 값을 가질 수 없다.
    * 문자열이나 클래스 타입인 경우 null이나 빈 값을 파라미터로 전달하기 위해 사용되는 어노테이션 이다.
    * null값과 빈 값을 모두 전달하기 위해 구성된 @NullAndEmptySource도 이용할 수 있다.
    * */
    @DisplayName("null값 테스트")
    @ParameterizedTest
    @NullSource
    void testIsNull(String input){ // 매개변수 선언 - given 단계

        boolean result = StringValidator.isNull(input); // isNull = boolean type
        Assertions.assertTrue(result);

    }

    @DisplayName("empty값 테스트")
    @ParameterizedTest
    @EmptySource
    void testIsEmpty(String input){
        //when
        boolean result = StringValidator.isEmpty(input);

        //then
        Assertions.assertTrue(result);
    }

    //Blank 테스트
    @DisplayName("blank 값 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void testIsBlank(String input){

        //when
        boolean result = StringValidator.isBlank(input);

        //then
        Assertions.assertTrue(result);
    }

    //목차 3. 열거형을 이용한 @EnumSource 활용하기
    // 열거형에 작성된 타입들을 파라미터로 전달하여 테스트에 활용할 수 있다.
    @DisplayName("Month에 정의된 타입들이 1~12월 사이의 범위인지 테스트")
    @ParameterizedTest
    @EnumSource(Month.class)
    void testMonthValueIsCollect(Month month){

        //when
        boolean result = DataValidator.isCollect(month);

        //then
        Assertions.assertTrue(result);
    }

    @DisplayName("2월, 4월, 6월, 9월, 11월을 제외한 달이 31일인지 확인")
    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = {"FEBRUARY", "APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"},
            mode = EnumSource.Mode.EXCLUDE
    )
    void testHasThirtyOneDaysLong(Month month){ // given 1

        //given 2  기븐이 두 개 필요함
        int verifyValue = 31;

        int actual = DataValidator.getLastDayOf(month);

        Assertions.assertEquals(verifyValue, actual); // 얘네를 제외한 결과가 31일이ㅑㄴ 라는 ㅡㄴ깜
    }

    // 4. @CsvSource 를 이용한 Csv리터럴
    /*
    * 입력값과 예상값을 테스트 메소드에 전달하기 위해 사용할 수 있다.
    * 이 경우 여러 매개변수 값을 전달할 여러 인자 묶음이 필요하게 된다.
    * 이 때 @CsvSource를 사용할 수 있다.
    * */
    @DisplayName("영문자를 대문자로 변경하는지 확인")
    @ParameterizedTest
    @CsvSource(
            value = {"test:TEST", "tEst:TEST", "JavaScript: JAVASCRIPT"},
            delimiter = ':' // 특정지어 주는 것  : ->구분자
    )
    void testToUpperCase(String input, String verifyValue){ // test = input, TEST = verifyValue
        //when
        String actual = input.toUpperCase();

        //then
        Assertions.assertEquals(verifyValue, actual);
    }



    @DisplayName("CSV 파일을 읽은 테스트 데이터를 테스트에 활용하는지 확인")
    @ParameterizedTest
    /* 설명. csv 파일의 헤더 부분을 무시하고 테스트 하기 위해 nulLinesToSkip을 활용할 수 있다. */
    @CsvFileSource(resources = "/parameter-test-data.csv", numLinesToSkip = 1)
    void testToUpperCaseWithCSVFileData(String input, String verifyValue) {

        //when
        String actual = input.toUpperCase();

        //then
        Assertions.assertEquals(verifyValue, actual);
    }

    /* 목차. 5. @MethodSource를 활용한 메소드 인수 활용하기 */
    /* 필기. Stream을 반환하는 메소드를 만들어서 이를 테스트에 활용할 수 있다. */

    private static Stream<Arguments> providerStringSource() {

        return Stream.of(
                Arguments.of("hello world", "HELLO WORLD"),
                Arguments.of("JavaScript", "JAVASCRIPT"),
                Arguments.of("tEsT", "TEST")
        );
    }

    @DisplayName("메소드 소스를 활용한 대문자 변환 테스트")
    @ParameterizedTest
    /* 설명.
     *   메소드 소스의 메소드 이름과 테스트 메소드의 이름이 일치하면 메소드 소스의 이름을 생략할 수 있다.
     *   커스텀 어노테이션을 만들어서 이를 활용할 수 도 있다.
     * */
//    @MethodSource("providerStringSource")
    @MethodSourceStringSource
    void testToUpperCaseWithMethodSource(String input, String verifyValue) {

        //when
        String actual = input.toUpperCase();

        //then
        Assertions.assertEquals(verifyValue, actual);
    }

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MethodSource("providerStringSource")
    private @interface MethodSourceStringSource {}

    /* 목차. 6. ArgumentsProvider를 이용한 메소드 소스 사용 */
    /* 필기.
     *   ArgumentProvider 구현체를 만들어서 메소드로 테스트용 데이터를 반환하면,
     *   테스트 코드와 데이터를 분리해서 관리할 수 있다.
     *   이 때 @ArgumentsSource 어노테이션을 이용할 수 있다.
     *  */
    @DisplayName("두 수를 더한 결과를 정상적으로 반환하는지 테스트")
    @ParameterizedTest(name = "[{index}] {0} + {1} = {2} (이)가 맞는지 확인")
    @ArgumentsSource(SumTwoNumbersArgumentsProvider.class)
    void testSumTwoNumbers(int num1, int num2, int verifyValue) {

        //when
        int actual = Calculator.sumTwoNumbers(num1, num2);

        //then
        Assertions.assertEquals(verifyValue, actual);
    }

    /* 목차. 7. 인수 변환기 (암시적 변환과 명시적 변환) */
    /* 필기.
     *   JUnit5는 인수로 지정된 String을 Enum으로 변환한다. 이처럼 기본적으로 제공하는 변환을 암시적 변환이라 한다.
     *   UUID, LocalDate, LocalTime, LocalDateTime, Year, Month, 파일 및 경로, URL, 열거형 서브클래스 등을 암시적으로 변환해준다.
     * */
    @DisplayName("암시적 변환 테스트")
    @ParameterizedTest(name = "[{0}] is 30 days long")
    @CsvSource({"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void testAutoConverting(Month month) {

        //given
        int verifyValue = 30;

        //when
        int actual = DateValidator.getLastDayOf(month);

        //then
        Assertions.assertEquals(verifyValue, actual);
    }

    /* 설명.
     *    자동으로 변환되지 않는 경우 명시적 변환 설정을 해 주어야 한다.
     *    2020/01/01 형식의 문자열을 LocalDate로 구현하기 위해
     *    SlashyDateConverter 클래스를 생성하고 ArgumentConverter 인터페이스를 구현한다.
     * */
    @DisplayName("argument converter를 이용한 명시적 변환 테스트")
    @ParameterizedTest(name = "{0}은 {1}년 입니다.")
    @CsvSource({"2014/09/18,2014", "2021/10/13,2021"})
    void testCustomConverter(@ConvertWith(SlashyDateConverter.class) LocalDate date, int year) {

        //when
        int actual = date.getYear();

        //then
        Assertions.assertEquals(year, actual);
    }

}