package site.kexing.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    public static final String MIAOSHA_QUEUE =  "miaoshaQueue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topicQueue1";
    public static final String TOPIC_QUEUE2 = "topicQueue2";
    public static final String FANOUT_QUEUE1 = "fanoutQueue1";
    public static final String FANOUT_QUEUE2 = "fanoutQueue2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";

    @Bean
    public Queue miaoshaQueue(){
        return new Queue(MqConfig.MIAOSHA_QUEUE);
    }

    /**
     * hello world队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(MqConfig.QUEUE);
    }

    /**
     * Topic临时队列
     * @return
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(MqConfig.TOPIC_QUEUE1);
    }

    /**
     * Topic临时队列
     * @return
     */
    @Bean
    public Queue topicQueue2(){
        return new Queue(MqConfig.TOPIC_QUEUE2);
    }

    /**
     * Topic交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 队列交换机绑定 1
     * @return
     */
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("site.kexing");
    }

    /**
     * 队列交换机绑定 2
     * @return
     */
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("site.kexing.#");
    }

    /**
     * 声明 Fanout交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(MqConfig.FANOUT_EXCHANGE);
    }

    @Bean
    public Queue fanoutQueue1(){
        return new Queue(MqConfig.FANOUT_QUEUE1);
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue(MqConfig.FANOUT_QUEUE2);
    }

    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
}
