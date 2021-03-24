package site.kexing.Annotation;

import java.lang.annotation.*;

/**
 * 登录校验注解
 * 如果参数前使用该注解则调用自定义的解析器
 * 否则使用springMvc默认的解析器
 */
@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserParameter {
}
