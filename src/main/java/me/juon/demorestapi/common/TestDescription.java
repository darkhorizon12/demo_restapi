package me.juon.demorestapi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kim Juon
 */
@Target(ElementType.METHOD) // 어느 레벨에 적용할 것인가?
@Retention(RetentionPolicy.SOURCE) // 실행시킨 후 얼마동안 유지할 것인가?
public @interface TestDescription {
    String value();
}
