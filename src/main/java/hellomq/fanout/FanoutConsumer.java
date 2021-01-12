package hellomq.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//绑定临时队列
                          exchange = @Exchange(value = "logs",type = "fanout")//绑定具体的交换机和类型
            )
    })
    public void receive1(String message){
        System.out.println("消费者1：" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//绑定临时队列
                    exchange = @Exchange(value = "logs",type = "fanout")//绑定具体的交换机和类型
            )
    })
    public void receive2(String message){
        System.out.println("消费者2：" + message);
    }
}
