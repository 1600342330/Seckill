package site.kexing.config;

import site.kexing.pojo.User;

public class UserContext {
    public static ThreadLocal<User> threadLocal = new ThreadLocal();

    public static void setUser(User user){
        threadLocal.set(user);
    }

    public static User getUser(){
        return threadLocal.get();
    }
}
