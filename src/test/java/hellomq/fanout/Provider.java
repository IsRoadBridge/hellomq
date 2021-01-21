package hellomq.fanout;

import com.rabbitmq.client.*;
import hellomq.utils.MqUtil;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException, InterruptedException {

        Connection connection = MqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机 参数一：交换机名  参数二:交换机类型 fanout为广播类型
        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        //通过交换机发布消息
        //参数一：交换机名字
        //参数二：路由key fanout类型的交换机路由key为空
        //参数三：消息属性
        //参数四：具体消息
        //开启消息确认，消息确认是确认消息是否成功发送到交换机
        channel.confirmSelect();
        channel.basicPublish("logs","",null,"fanout type message".getBytes());
        channel.addConfirmListener(new ConfirmListener() {
            // 参数一：消息标识 参数二: 是否为发送批量消息
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息成功发送到交换机");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息发送到交换机失败");
            }
        });
        //注意如果要进行return监听，参数三必须为true，表示开启return监听
        channel.basicPublish("logs","a",true,null,"message".getBytes());
        //开启异步return机制，return用来确认消息是否成功发送到队列
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("参数一：消息标识" + replyCode);
                System.out.println("参数二：回复文本" + replyText);
                System.out.println("参数三：交换机名" + exchange);
                System.out.println("参数四：路由key" + routingKey);
                System.out.println("参数一：消息具体内容" + new String(body));
            }
        });
        //关闭连接和通道
        MqUtil.close(channel,connection);
    }
}
