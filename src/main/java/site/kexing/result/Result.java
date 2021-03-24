package site.kexing.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg msg){
        if(msg == null){
           return;
        }
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    /**
     * 成功的时候调用 返回数据
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
}

    /**
     * 失败的时候调用 返回状态码和消息
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

}
