package hellospringbootmq.hello;

import hellomq.HellomqApplication;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HellomqApplication.class)
public class Provider {

    //直接注入springboot提供给我们的模板类操作mq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //直连模式
    @Test
    void hello(){
        //通过模板类发送消息，参数一：队列名 参数二：具体的消息
        rabbitTemplate.convertAndSend("hello","hello spring boot mq");
    }

    //工作队列模式，多个消费者轮询接收到消息
    @Test
    void work(){
        for (int i = 0; i < 10; i++) {
            //通过模板类发送消息，参数一：队列名 参数二：具体的消息
            rabbitTemplate.convertAndSend("work","work send number:" + i);
        }
    }

    //广播模式 fanout 每个消费者都能接收到生产者发送的消息
    @Test
    void fanout(){
        //参数一：交换机类型  参数二：路由key fanout模式默认为空 参数三：具体消息
        rabbitTemplate.convertAndSend("logs","","fanout模式发送的消息");
    }

    //路由模式 只有绑定了对应的交换机和路由的消费者才能接收到对应生产者发送的消息
    @Test
    void route(){
        //参数一：交换机类型  参数二：路由key  参数三：具体消息
        rabbitTemplate.convertAndSend("direct","info","发送给info的消息");
    }

    //动态路由模式 通过通配符指定路由key进行匹配
    @Test
    void topic(){
        rabbitTemplate.convertAndSend("topic","user.save.add","user.save.add 路由发送的消息");
    }

}
