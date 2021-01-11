package hellomq.topic;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机
        channel.exchangeDeclare("topics","topic");
        String queue = channel.queueDeclare().getQueue();
        //通道绑定临时队列，交换机，路由key
        //通过两个通配符指定路由
        //例 user.* 可以匹配 user.save/user.delete  user.# 可以匹配 user/user.save.all
        channel.queueBind(queue,"topics","*.user.#");
        //消费者通过通道接收消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("这是消费者1111"+ new String(body));
            }
        });
    }
}
