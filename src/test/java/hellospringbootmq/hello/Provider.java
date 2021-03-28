package hellospringbootmq.hello;

import hellomq.HellomqApplication;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = HellomqApplication.class)
public class Provider {

    //直接注入springboot提供给我们的模板类操作mq
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //AmqpTemplate也可以用来操作mq
    @Autowired
    private AmqpTemplate amqpTemplate;

    //直连模式
    @Test
    void hello(){
        //通过模板类发送消息，参数一：队列名 参数二：具体的消息
        rabbitTemplate.convertAndSend("ex6","k1","hello spring boot mq");
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

    @Test
    void  level(){
        String level = "一级";
        Map<String,String> levelMap = new HashMap<>();
        levelMap.put("一级","1");
        levelMap.put("二级","2");
        levelMap.put("三级","3");
        levelMap.put("四级","4");
        level = levelMap.get(level) == null ? level.substring(0,1) : levelMap.get(level);
        System.out.println(level);
    }
}
