package hellomq.direct;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Consumer1 {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        //通道绑定交换机
        channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT);
        //获取临时队列
        String queue = channel.queueDeclare().getQueue();
        //通道绑定指定队列，交换机，路由
        channel.queueBind(queue,exchangeName,"info");
        //消费者消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("这是绑定error路由的消费者11"+new String(body));
            }
        });

    }
}
