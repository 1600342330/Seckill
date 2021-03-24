package site.kexing.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeMsg {
    private int code;
    private String msg;

    //普通异常
    public static final CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static final CodeMsg NICKNAME_NOT_EXIST = new CodeMsg(500110,"用户名不存在");


    //登录异常 5002xx
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500201,"用户未登录");

    //商品异常 5003xx

    //订单异常 5004xx

    //秒杀异常 5005xx
    public static final CodeMsg OUT_OF_STOCK = new CodeMsg(500501,"秒杀库存不足");
    public static final CodeMsg NO_REPEAT_MIAOSHA = new CodeMsg(500502,"不可重复秒杀");
    public static final CodeMsg MIAOSHA_FREQUENTLY= new CodeMsg(500503,"操作太过频繁");

}
