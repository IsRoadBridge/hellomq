package hellomq.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkConsumer {

    //@RabbitListener注解可以直接注解在方法上
    //注意！！！！消费者方法内的参数类型要与生产者发送的类型一致，不一定为String类型
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void  receive1(String message){
        System.out.println("消费者1："+ message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void  receive2(String message){
        System.out.println("消费者2："+ message);
    }
}
