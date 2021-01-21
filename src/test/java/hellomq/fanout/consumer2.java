package hellomq.fanout;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class consumer2 {

    public static void main(String[] args) throws IOException {
        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机与生产者一致
        channel.exchangeDeclare("logs",BuiltinExchangeType.FANOUT);
        //获取临时队列名
        String queueName = channel.queueDeclare().getQueue();
        //通道绑定临时队列 参数一：临时队列名  参数二：交换机名 参数三：路由key
        channel.queueBind(queueName,"logs","");
        //消费者通过队列接收消息 参数一：临时队列名  参数二：消息自动确认
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2222"+ new String(body));
            }
        });

    }
}
