package hellomq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

//注册成组件，实现ConfirmCallback和ReturnsCallback接口
@Component
public class MsgConfirmAndReturn implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    Logger logger = LoggerFactory.getLogger(MsgConfirmAndReturn.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void  init(){
        //回调自身的消息确认和return
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }



    //此方法用于监听消息发送结果，消息是否成功发送到交换机
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //如果ack为true则消息成功发送到交换机
        if(ack){
            logger.info("消息成功发送到交换机");
        }else {
            logger.warn("消息发送到交换机失败");
        }
    }

    //此方法用于return监听，只有消息发送到队列失败才会调用
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        logger.warn("消息" + returned.getMessage().getBody() + "发送到交换机" +returned.getExchange() +"对应的key" + returned.getRoutingKey() +"失败");
    }
}
