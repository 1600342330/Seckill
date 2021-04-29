package site.kexing.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import site.kexing.Annotation.AccessLimit;
import site.kexing.controller.LoginController;
import site.kexing.pojo.User;
import site.kexing.redis.key.AccessLimitKey;
import site.kexing.redis.RedisService;
import site.kexing.result.CodeMsg;
import site.kexing.result.Result;
import site.kexing.service.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class AccessInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            User user = getMiaoshaUser(request);
            UserContext.setUser(user);

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit methodAnnotation = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (methodAnnotation == null) {
                return true;
            }

            int second = methodAnnotation.second();
            int maxCount = methodAnnotation.maxCount();
            boolean needLogin = methodAnnotation.needLogin();

            if (needLogin) {
                if (user == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
            }

            /**
             * 根据注解填的参数进行限流，接口的uri+"_"+userID为Key，最大请求数maxCount为Value
             */
            String requestURI = request.getRequestURI();
            String key = requestURI + "_" + user.getId();

            AccessLimitKey accessLimitKey = AccessLimitKey.withExpire(second);

            Integer rMaxCount = redisService.get(accessLimitKey, key, Integer.class);
            if (rMaxCount == null) {
                redisService.set(accessLimitKey, key, 1);
            } else if (rMaxCount < maxCount) {
                redisService.incr(accessLimitKey, key);
            } else {
                System.out.println(CodeMsg.MIAOSHA_FREQUENTLY);
                render(response, CodeMsg.MIAOSHA_FREQUENTLY);
                return false;
            }
        }
            return true;
    }

    public void render(HttpServletResponse response, CodeMsg msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(Result.error(msg));
        OutputStream os = response.getOutputStream();
        os.write(s.getBytes("UTF-8"));
        os.flush();
        os.close();
    }

    public User getMiaoshaUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies == null || cookies.length <=0){
            return null;
        }
        String token = null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(LoginController.COOKIE_NAME)){
                //拿到token
                token = cookie.getValue();
                break;
            }
        }
        User user = userService.getUserByToken(token);
        return user;
    }
}
