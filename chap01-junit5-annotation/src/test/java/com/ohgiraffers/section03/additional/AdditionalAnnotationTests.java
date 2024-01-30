package com.ohgiraffers.section03.additional;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdditionalAnnotationTests {

    /*
    * 해당 테스트를 무시할 때 사용하는 어노테이션이다.
    * Junit4에서 @Ignore와 동일한 기능을 제공한다.
    * */

    @Disabled
    @Test
    public void testIgnore(){
        // 목록에는 있지만 실행은 안된거다.
    }

    /*
    * 주어진 시간 안에 테스트가 끝나지 않으면 테스트가 실패한다.
    * Value에는 시간을 정수형으로, unit에는 사용할 시간 단위를 기술한다.
    * */

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    public void testTimeout() throws InterruptedException{
        Thread.sleep(900);
    }
}
