package hellomq.work;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1); //每次只消费一条消息
        channel.queueDeclare("work",true,false,false,null);
        //关闭自动确认消息
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1111" + new String(body));
                //手动确认消息 参数一：确认的是哪个消息  参数二：是否开启多条消息同时确认
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
