package site.kexing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.kexing.rabbitmq.Provider;
import site.kexing.result.CodeMsg;
import site.kexing.result.Result;

@Controller
@RequestMapping("/demo")
public class TestController {
    @Autowired
    private Provider provider;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/success")
    @ResponseBody
    public Result<String> success(){
        return Result.success("成功");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 测试 helloWorld消息模型
     * @return
     */
    @RequestMapping("/helloWorld")
    @ResponseBody
    public Result<String> helloWorld(){
        String msg = "hello rabbitmq";
        provider.provider(msg);
        return Result.success(msg);
    }

    /**
     * 测试topic消息模型
     * @return
     */
    @RequestMapping("/topic")
    @ResponseBody
    public Result<String> topic(){
        String msg = "topic消息";
        provider.topicProvider(msg);
        return Result.success(msg);
    }

    /**
     * 测试fanout消息模型
     * @return
     */
    @RequestMapping("/fanout")
    @ResponseBody
    public Result<String> fanout(){
        String msg = "fanout消息";
        provider.fanoutProvider(msg);
        return Result.success(msg);
    }

}
