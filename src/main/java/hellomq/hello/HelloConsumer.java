package hellomq.hello;


import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //将消费者注册为组件
//指定消费者绑定哪个队列 队列属性默认是持久化，不独占队列，不自动删除队列
//@Queue(value = "hello",declare = "true",autoDelete = "true") 可以通过declare和autoDelete进行修改
@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
//常用的简便写法
//@RabbitListener(queues = "hello")
public class HelloConsumer {

    //通过RabbitHandler注解直接回调接口，message就是接收到的消息
    @RabbitHandler
    public void receive1(String message){
        System.out.println("接收到的信息:" + message);
    }
}
