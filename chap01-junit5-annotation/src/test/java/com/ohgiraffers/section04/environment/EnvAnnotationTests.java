package com.ohgiraffers.section04.environment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class EnvAnnotationTests {

    /*
    * 테스트 메소드는 특정 OS 환경에서만 테스트를 수행할 수 있다.
    * 이 때, 특정 OS에서 무시되는 사유를 disabledReason에 기술하여
    * 테스트 코드가 무시되는 사유를 다른 사람도 함께 알아볼 수 있도록 작성해 준다.
    * */
    @Test
    @EnabledOnOs(value = OS.MAC, disabledReason = "맥에서만 테스트 합니다")
    public void testMAC(){}

    @Test
    @EnabledOnOs(value = {OS.WINDOWS,OS.LINUX}, disabledReason = "윈도우와 리눅스에서만 테스트를 합니다")
    public void testWindowsAndLinux(){}

    @Test
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "윈도우 환경에서는 테스트를 무시합니다.")
    public void testDisabledWindow(){}

    /*
    * @EnabledOnJre를 이용하는 특정 JRE 버전에서만 테스트 하는 것도 가능하다.
    * @DisableOnJre를 이용하여 특정 JRE 버전에서만 테스트를 Disabled 하는 것도 가능하다.
    * @EnabledForJreRange를 이용하면 min과 max 속성 사이의 버전에서 테스트 하는 것도 가능하다.
    * (min만 작성 시 min~최신버전까지, Max만 작성 시 이전 버젼부터 max까지만 테스트한다.)
    * @DisableForJreRange를 이용하면 특정 범위 상이의 JRE환경에서의 테스트를 disabled 할 수 있다.
    * */
    @Test
    @EnabledOnJre(value = JRE.JAVA_8, disabledReason = "jre 1.8 환경에서만 테스트합니다.")
    public void testJRE8(){}

    @Test
    @EnabledOnJre(value = {JRE.JAVA_8, JRE.JAVA_17})
    public void testJRE8AndJRE17(){}

    @Test
    @DisabledOnJre(value = JRE.JAVA_8)
    public void testDisabledJRE8(){}

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_17)
    public void testFromJRE8ToJRE17(){}

    @Test
    @DisabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_17)
    public void testDisabledFromJRE8ToJRE17(){}

    /*
    * 운영체제의 시스템 환경변수에 따라 테스트를 활성화 또는 비활성화 할 수 있다.
    * cmd에서 set명령으로 시스템 환경 변수값을 확인해 볼 수 있다.
    * 시스템 변수의 이름은 대소문자를 구분하지 않지만 값은 대소문자를 구분한다.
    * */
    @Test
    @EnabledIfEnvironmentVariable(named = "username", matches = "jinho")
    public void testEnvironmentVariable(){}

    @Test
    @DisabledIfEnvironmentVariable(named = "username", matches = "gildong")
    public void testDisableEnvironmentVariable(){}
}
