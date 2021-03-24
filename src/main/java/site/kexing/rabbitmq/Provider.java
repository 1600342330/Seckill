package site.kexing.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.rabbitmq.msg.MiaoshaMsg;
import site.kexing.redis.RedisService;

@Service
public class Provider {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RedisService redisService;

    public void miaoshaProvider(MiaoshaMsg msg){
        String message = redisService.beanToString(msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE,message);
    }

    /**
     * hello world消息模型
     * @param msg
     */
    public void provider(Object msg){
        String message = redisService.beanToString(msg);
        System.out.println("生产一条消息"+message);
        amqpTemplate.convertAndSend(MqConfig.QUEUE,message);
    }

    /**
     * topic 消息模型
     * @param msg
     */
    public void topicProvider(Object msg){
        String message = redisService.beanToString(msg);
        System.out.println("生产一条消息："+message);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"site.kexing",message);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,"site.kexing.blog.cn",message);
    }

    /**
     * fanout 消息模型
     * @param msg
     */
    public void fanoutProvider(Object msg){
        String message = redisService.beanToString(msg);
        System.out.println("生产一条消息："+message);
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,"",message);
    }

}
