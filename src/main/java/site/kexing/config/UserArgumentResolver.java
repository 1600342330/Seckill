package site.kexing.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.kexing.Annotation.UserParameter;
import site.kexing.pojo.User;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    //先判断参数类型和是否贴了自定义注解，满足则进行下一步
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
       return parameter.getParameterType() == User.class && parameter.hasParameterAnnotation(UserParameter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        return UserContext.getUser();
    }
}
