package com.ohgiraffers.assertions.section02.assertj;


import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AssertJTests {

    /*
    * AssertJ는 메소드 체이닝을 지원하기 때문에 좀 더 깔끔하고 읽기 쉬운 테스트 코드를 작성할 수 있다.
    * 개발자 테스트를 하면서 필요하다고 생각하는 거의 모든 기능을 메소드로 제공하고 있다.
    * 참고 hamncrest, truth 등도 사용하는 용법이나 컨셉이 비슷하지만 보통 assertj를 많이 사용 한다.
    * 가장 직관적이고 편하며, junit5 팀에서도 공식적으로 추천하고 있는 third party lib이다.
    * */

    // 목차 1 문자열 테스트
    // 문자열의 패턴들로 검증을 하기 때문에 일치성 여부만 판단하는 것 보다 다양한 테스트를 할 수 있다
    @Test
    @DisplayName("문자열 테스트 하기")
    void testStringValidation(){
        // given
        String expected = "hello world";

        // when
        String actual = new String("hello world");

        // then
        Assertions.assertThat(actual)
                .isNotEmpty()
                .isNotBlank()
                .contains("hello")
                .doesNotContain("haha")
                .startsWith("h")
                .endsWith("d")
                .isEqualTo("hello world");
    }

    @Test
    @DisplayName("숫자 테스트 하기")
    void testIntegerValidation(){

        //given
        double pi = Math.PI;

        //when : 테스트를 실행할 메소드
        Double actual = Double.valueOf(pi);

        Assertions.assertThat(actual)
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(4)
                .isEqualTo(Math.PI);
    }

    // 날짜 객체가 가지는 년,월,일,시,분,초,밀리초,나노초 까지도 검증이 가능하며, 특정 날짜 범위, 이전, 이후 내용도 검증이 가능하다.
    @Test
    @DisplayName("날짜 테스트 하기")
    void testLocalDateTimeValidation(){

        //given
        String birthDay = "2014-09-18T16:42:00.000";

        //when
        LocalDateTime thatDay = LocalDateTime.parse(birthDay);

        //then
        Assertions.assertThat(thatDay)
                .hasYear(2014)
                .hasMonthValue(9)
                .hasDayOfMonth(18)
                .isBetween("2014-09-18T16:42:00.000","2014-12-31T23:59:59.999")
                .isBefore(LocalDateTime.now());
    }

    // 예외 내용이 검증하는 것 또한 예외 타입, 예외 메세지 등을 검증할 수 있다.
    @Test
    @DisplayName("예외 테스트 하기")
    void testExceptionValidation(){

        //given
        //when
        Throwable thrown = AssertionsForClassTypes.catchThrowable(()->{throw new IllegalArgumentException("잘못된 어쩌구");});

        Assertions.assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된");

    }


    @Test
    @DisplayName("예외 테스트2")
    void testExceptionValidation2(){

        //given

        // when & then
        Assertions.assertThatThrownBy(()-> {throw new IllegalArgumentException("잘못된 파라미터를 입력");})
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("파라미터");

        /*
         * 필기
         * 1. assertThatNullPointerException
         * 2. assertThatIllegalArgumentException
         * 3. assertThatIllegalStateException
         * 4. assertThatIOException
         * */

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(()->{
                    throw new IllegalArgumentException("잘못된 파리미터를 입력하셨습니다");
        }).withMessageContaining("파라미터");


    }

    /*
    * 목차. 6. filter를 이용한 단정문 테스트 하기
    * 필기.
    * */
    @Test
    @DisplayName("filtering assertions 테스트하기")
    void testFilteringAssertions(){

        //given
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 16);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 43);
        Member member5 = new Member(5, "user05", "임꺽정", 19);

        //when
        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);

        //then
        /* 회원 3과 4가 성인인지 확인 */
        Assertions.assertThat(members) // member 에서 하나씩 꺼내온다
                .filteredOn(member -> member.getAge() > 20) //member age가 20 이상이면 true
                .containsOnly(member3, member4); // 반환된 녀석들이 멤버 3,4가 맞는지 확인
    }

    /* 목차. 7. 객체 프로퍼티 검증 하기 */
    /* 필기. 객체의 프로퍼티를 추출하여 filter를 할 수 있다. 이 때 getter함수가 존재하지 않아도 프로프터 필터링이 가능하다. */
    @Test
    @DisplayName("객체의 프로퍼티 검증 테스트하기")
    void testPropertyValidation() {

        //given
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 16);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 43);
        Member member5 = new Member(5, "user05", "임꺽정", 19);

        //when
        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);

        //then
        /* 설명. property를 확인하는 것은 getter 메소드가 필요하지 않다. */
        Assertions.assertThat(members)
                .filteredOn("age", 20) // propertyof~: 해당 요소(age)가 있는지 , value = 20인 값이 있는지
                .containsOnly(member1);
    }

    /* 목차. 8. 객체 프로퍼티 추출 하기 */
    /* 필기. 객체의 특정 프로퍼티를 추출하여 List를 이용한 검증을 할 수 있다. */
    @Test
    @DisplayName("프로퍼티 추출 테스트하기")
    void testExtractProperty() {

        //given
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 16);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 43);
        Member member5 = new Member(5, "user05", "임꺽정", 19);

        List<String> expected = Arrays.asList("홍길동", "유관순", "이순신", "신사임당", "임꺽정");

        //when
        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);

        //then
        Assertions.assertThat(members)
                .extracting("name", String.class)
                // extraction - 적출하다 , name을 String 타입을 적출하다
                .containsAll(expected);
        // Arrays.asList("홍길동", "유관순", "이순신", "신사임당", "임꺽정"); 제대로 포함되어 있으면 됨
    }

    /* 목차. 9. 객체의 프로퍼티를 여러 개 튜플로 추출하기 */
    /* 필기. 객체의 여러 프로퍼티들을 추출하여 튜플로 생성하고, 튜플을 이용하여 검증할 수 있는 기능을 제공하고 있다. */
    @Test
    @DisplayName("튜플로 추출하여 테스트하기😂")
    void testExtractPropertyTuple() {

        //given
        Member member1 = new Member(1, "user01", "홍길동", 20);
        Member member2 = new Member(2, "user02", "유관순", 16);
        Member member3 = new Member(3, "user03", "이순신", 40);
        Member member4 = new Member(4, "user04", "신사임당", 43);
        Member member5 = new Member(5, "user05", "임꺽정", 19);

        //when
        List<Member> members = Arrays.asList(member1, member2, member3, member4, member5);
        //셀수있는 수량의 순서있는 열거 또는 어떤 순서를 따르는 요소들의 모음을 튜플(tuple)이라고 합니다

        //then
        Assertions.assertThat(members)
                .extracting("name", "age")
                .contains(
                        Tuple.tuple("홍길동", 20),
                        Tuple.tuple("유관순", 16)
                );
    }
}
