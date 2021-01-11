package hellomq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机   参数一：交换机名 参数二： 交换机类型 topic动态路由类型
        channel.exchangeDeclare("topics","topic");
        String routeKey = "save.user";
        channel.basicPublish("topics",routeKey,null,("这是topic动态路由模式的消息，routekey:["+routeKey+"]").getBytes());
        MqUtil.close(channel,connection);
    }
}
