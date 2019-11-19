package com.bigboss.compiler.check.name;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: CheckName
 * @Description: 名称检查注解，只有存在该注解会执行自定义的名称检查(该注解只能在类上使用且只有编译时生效)
 * @author: BigBoss
 * @date: 2019年11月19日 21:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface CheckName {
}
