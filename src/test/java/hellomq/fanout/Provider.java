package hellomq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机 参数一：交换机名  参数二:交换机类型 fanout为广播类型
        channel.exchangeDeclare("logs","fanout");
        //通过交换机发布消息
        //参数一：交换机名字
        //参数二：路由key fanout类型的交换机路由key为空
        //参数三：额外参数
        //参数四：具体消息
        channel.basicPublish("logs","",null,"fanout type message".getBytes());
        //关闭连接和通道
        MqUtil.close(channel,connection);
    }
}
