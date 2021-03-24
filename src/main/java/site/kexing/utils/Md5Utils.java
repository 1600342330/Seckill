package site.kexing.utils;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {
    public static final String salt = "1a2b3c4d";
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    /**
     * 第一次对表单输入的密码进行md5+salt
     * @param password 表单输入的密码
     * @return
     */
    public static String passToForm(String password){
        password = ""+salt.charAt(0)+salt.charAt(2)+password+salt.charAt(4)+salt.charAt(5);
        return Md5Utils.md5(password);
    }

    /**
     * 第二次对第一次加密的密码进行第二次加密
     * @param formPass 第一次加密后的密码
     * @param salt 随机生成
     * @return
     */
    public static String formPassToDb(String formPass,String salt){
        formPass = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(4)+salt.charAt(5);
        return Md5Utils.md5(formPass);
    }

    /**
     * 将用户输入的密码经过两次md5加密
     * @param pass
     * @param dbSalt
     * @return
     */
    public static String passToDb(String pass,String dbSalt){
        return Md5Utils.formPassToDb(Md5Utils.passToForm(pass),dbSalt);
    }

    public static void main(String[] args) {
        System.out.println(Md5Utils.passToForm("123456"));
    }
}
