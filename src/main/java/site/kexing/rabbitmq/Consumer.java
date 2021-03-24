package site.kexing.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.pojo.Miaosha_order;
import site.kexing.rabbitmq.msg.MiaoshaMsg;
import site.kexing.redis.key.MiaoShaOrderKey;
import site.kexing.redis.RedisService;
import site.kexing.service.*;
import site.kexing.vo.GoodsList;

@Service
public class Consumer {
    @Autowired
    private MiaoShaGoodsService miaoSha_goodsService;
    @Autowired
    private MiaoShaService miaoShaService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;

    /**
     * 秒杀商品消费者
     * 根据生产者传递过来的msg进行订单生产
     * @param msg
     */
    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void miaoshaConsumer(String msg){
        MiaoshaMsg message = redisService.StringToBean(msg, MiaoshaMsg.class);
        GoodsList good = goodsService.getGoodsListById(message.getGood_id());
        //判断该商品是否还有库存
        int stock_count = miaoSha_goodsService.getMiaosha_goodsByid(good.getId());
        if(stock_count <= 0){
            return;
        }

        //判断该用户是第一次秒杀，不可重复秒杀
        //查找redis缓存，绕开数据库
        Miaosha_order miaosha_order = redisService.get(MiaoShaOrderKey.orderKey,message.getUser().getId()+":"+good.getId(),Miaosha_order.class);
        if(miaosha_order != null){
            return;
        }

        //允许秒杀
        //库存-1 生成order_info订单 生成秒杀订单
        miaoShaService.miaoSha(message.getUser(), good);
    }

    /**
     * hello world消息模型消费者
     * @param msg
     */
    @RabbitListener(queues = MqConfig.QUEUE)
    public void consumer(String msg){
        System.out.println("Hello world模型消费："+msg);
    }

    /**
     * topic消息模型消费者 1
     * @param msg
     */
    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void topicConsumer1(String msg){
        System.out.println("topic模型消费1："+msg);
    }

    /**
     * topic消息模型消费者 2
     * @param msg
     */
    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void topicConsumer2(String msg){
        System.out.println("topic模型消费2："+msg);
    }

    /**
     * fanout消息模型消费者 1
     * @param msg
     */
    @RabbitListener(queues = MqConfig.FANOUT_QUEUE1)
    public void fanoutConsumer1(String msg){
        System.out.println("fanout模型消费1："+msg);
    }

    /**
     * fanout消息模型消费者 2
     * @param msg
     */
    @RabbitListener(queues = MqConfig.FANOUT_QUEUE2)
    public void fanoutConsumer2(String msg){
        System.out.println("fanout模型消费2："+msg);
    }
}
