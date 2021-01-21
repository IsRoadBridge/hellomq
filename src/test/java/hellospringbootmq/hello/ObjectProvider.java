package hellospringbootmq.hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hellomq.HellomqApplication;
import hellomq.object.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(classes = HellomqApplication.class)
public class ObjectProvider {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void goodsTest(){
        //消息队列只能发送字符串，字节数组，序列化对象三种类型的消息
        //注意goods必须是序列化对象，必须实现Serializable接口，不然传输对象就会报错
         Goods goods = new Goods();
        amqpTemplate.convertAndSend("ex6","k1",goods);
    }

    @Test
    void goodsByteTest(){
        //将序列化的对象使用amqp的序列化工具转成字节数组进行传输
        Goods goods = new Goods();
        byte[] goodsBtye = SerializationUtils.serialize(goods);
        amqpTemplate.convertAndSend("","queue1",goodsBtye);
    }

    @Test
    void goodsStringTest() throws JsonProcessingException {
        //使用json的方法将对象序列化成String类型
        Goods goods = new Goods();
        ObjectMapper mapper = new ObjectMapper();
        String goodsString = mapper.writeValueAsString(goods);
        amqpTemplate.convertAndSend("","queue1",goodsString);
    }

}
