package hellomq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work",true,false,false,null);
        for (int i = 0; i < 20; i++) {
            channel.basicPublish("","work",null,(i+"hello work").getBytes());
        }
        MqUtil.close(channel,connection);
    }
}
