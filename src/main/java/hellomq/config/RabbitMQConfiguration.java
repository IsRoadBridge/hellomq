package hellomq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    //创建队列,注意创建的队列，交换机，绑定都是org.springframework.amqp.core下的类
    @Bean
    public Queue queue2(){
        return new Queue("queue2");
    }

    @Bean
    public Queue queue3(){
        return new Queue("queue3");
    }

    //创建订阅类型的交换机
    @Bean
    public FanoutExchange ex1(){
        return  new FanoutExchange("ex1");
    }

    //创建路由类型的交换机
    @Bean
    public DirectExchange ex2(){
        return  new DirectExchange("ex2");
    }


    //绑定队列
    @Bean
    public Binding bindingQueue2(Queue queue2,DirectExchange ex2){
        return BindingBuilder.bind(queue2).to(ex2).with("k1");
    }

    @Bean
    public Binding bindingQueue3(Queue queue3,DirectExchange ex2){
        return BindingBuilder.bind(queue3).to(ex2).with("k1");
    }
}
