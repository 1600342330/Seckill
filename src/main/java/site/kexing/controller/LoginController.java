package site.kexing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.kexing.dao.UserDao;
import site.kexing.pojo.User;
import site.kexing.redis.key.MiaoShaUserKey;
import site.kexing.redis.RedisService;
import site.kexing.result.CodeMsg;
import site.kexing.result.Result;
import site.kexing.utils.Md5Utils;
import site.kexing.utils.UUIDUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class LoginController {
    public static final String COOKIE_NAME = "token";

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    /**
     * 用户登录
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/dologin")
    public synchronized Result<String> doLogin(String nickname,String password,HttpServletResponse response) throws IOException {
        User loginUser = userDao.selectUserByNickName(nickname);
        if(loginUser == null){
            return Result.error(CodeMsg.NICKNAME_NOT_EXIST);
        }
        String salt = loginUser.getSalt();
        //将表单提交的密码二次md5
        String s = Md5Utils.formPassToDb(password, salt);
        System.out.println(nickname+":"+password);
        //验证密码
        if(s.equals(loginUser.getPassword())){
            //生成随机的token
            String token = UUIDUtil.uuid();

            File file = new File("/usr/local/loginData.csv");
            if(!file.getParentFile().exists()){   //文件不存在
                file.getParentFile().mkdirs();   //创建父目录
            }
            OutputStream os = new FileOutputStream(file,true);
            os.write((nickname+',').getBytes());
            os.write((token+"\r\n").getBytes());

            //将token和用户作为key与value存入缓存
            redisService.set(MiaoShaUserKey.userKeyToken,token,loginUser);
            //封装cookie
            Cookie cookie = new Cookie(COOKIE_NAME,token);
            //设置cookie过期时间
            cookie.setMaxAge(MiaoShaUserKey.userKeyToken.getExpireSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.success(token);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
