package hellomq.hello;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class HelloConsumer {

    @Test
    void  testReceiveMessage() throws IOException, TimeoutException {
       //创建rabbit工厂
        Connection connection = MqUtil.getConnection();
        //通过连接创建通道
        Channel channel = connection.createChannel();
        //通道绑定队列，队列名和参数要和生产者完全一致
        channel.queueDeclare("hello",true,false,true,null);
        //接收消息 参数一：队列名 参数二：是否开启消息自动确认机制 参数三: 消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel){

            @Override//最后一个参数就是消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("========="+new String(body));
            }
        });
        //消费者连接和通道不能关闭，这样才能够回调接口输出取出的消息
    }
}
