package hellomq.direct;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "ex6";
        //channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT);
        //String queue = channel.queueDeclare().getQueue();
        //channel.queueBind(queue,exchangeName,"error");
        //channel.queueBind(queue,exchangeName,"info");
        //channel.queueBind(queue,exchangeName,"warning");
        channel.basicConsume("queue4",false,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("这是绑定所有类型路由的消费者2222" + new String(body));
            }

        });
    }
}
