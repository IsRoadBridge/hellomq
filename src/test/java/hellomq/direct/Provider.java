package hellomq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        //通道声明交换机 参数一：交换机名  参数二：交换机类型 direct 路由模式
        channel.exchangeDeclare(exchangeName,"direct");
        String routeKey = "error";
        //发布消息  参数二：路由key 发布消息到指定的路由中，只有绑定了该路由的消费者才能接收到消息
        channel.basicPublish(exchangeName,routeKey,null,("这是基于路由模式发送的"+routeKey+"消息").getBytes());
        //关闭连接和通道
        MqUtil.close(channel,connection);
    }
}
