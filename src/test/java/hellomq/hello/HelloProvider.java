package hellomq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import hellomq.utils.MqUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
public class HelloProvider {

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //创建一个rabbitmq工厂
        Connection connection = MqUtil.getConnection();
        //通过连接创建通道
        Channel channel = connection.createChannel();
        //通道绑定具体的队列
        //参数一:队列名  如果不存在队列就会自动创建
        //注意！！！如果队列名已经存在，你定义的绑定队列的参数与原队列不一致就会报错，
        // 解决办法删除原队列或者把参数改成与原队列一致

        //参数二：定义队列是否要持久化，持久化后当mq重启后队列还在，否则不存在
        //注意！！！队列持久化只是持久化了队列，消息重启后还是会消失

        //参数三：是否独占队列，独占队列就只允许自己使用该队列

        //参数四：消费完成后是否自动删除队列，
        //注意！！！自动删除队列的条件是消费者消费完消息且连接和通道均关闭，队列才会被删除

        //参数五：额外附加参数
        channel.queueDeclare("hello",true,false,true,null);
        //发布消息 参数一：交换机名称 参数二：队列名 参数三：额外设置 参数四：具体消息内容
        //参数三设置成MessageProperties.PERSISTENT_TEXT_PLAIN后，消息就会被持久化，mq后消息还在
        channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,"hello rabbitmq".getBytes());
        //完成后关闭通道关闭连接
        MqUtil.close(channel,connection);
        /*channel.close();
        connection.close();*/
    }
}
