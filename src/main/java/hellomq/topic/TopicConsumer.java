package hellomq.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//绑定临时队列
                    exchange = @Exchange(value = "topic",type = "topic"),//绑定交换机，指定类型
                    key = {"user.*"} //配置路由key *表示匹配一个
            )
    })
    public void receive1(String message){
        System.out.println("消费者1，可以接收user后带一个单词的路由：" + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "topic",type = "topic"),
                    key = {"user.#"} //#表示匹配零个或者多个
            )
    })
    public void receive2(String message){
        System.out.println("消费者2，可以接收user后带零个或者多个单词的路由：" + message);
    }
}
