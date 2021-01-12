package hellomq.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RouteConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//创建临时队列
                          exchange = @Exchange(value = "direct",type = "direct"),//指定交换机名字和类型
                           key = {"info","error","warn"} //绑定具体的key
            )
    })
    public void receive1(String message){
        System.out.println("绑定info,error,warn的消费者1：" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "direct",type = "direct"),
                    key = {"warn"}
            )
    })
    public void receive2(String message){
        System.out.println("绑定warn的消费者2：" + message);
    }
}
