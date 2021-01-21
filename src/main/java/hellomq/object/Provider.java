package hellomq.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class Provider {

    @Autowired
    private static AmqpTemplate amqpTemplate;

    public static void main(String[] args) throws JsonProcessingException {
        goodsTest(new Goods());
        goodsByteTest(new Goods());
        goodsStringTest(new Goods());
    }

     static void  goodsTest(Goods goods){
        //注意goods必须是序列化对象，必须实现Serializable接口，不然传输对象就会报错
        goods.setName("1111");
        goods.setPrice(1);
        amqpTemplate.convertAndSend("","queue1",goods);
    }

    static void goodsByteTest(Goods goods){
        //将序列化的对象使用amqp的序列化工具转成字节数组进行传输
        goods.setName("2222");
        goods.setPrice(1);
        byte[] goodsBtye = SerializationUtils.serialize(goods);
        amqpTemplate.convertAndSend("","queue1",goodsBtye);
    }


    static void goodsStringTest(Goods goods) throws JsonProcessingException {
        //使用json的方法将对象序列化成String类型
        goods.setName("3333");
        goods.setPrice(3);
        ObjectMapper mapper = new ObjectMapper();
        String goodsString = mapper.writeValueAsString(goods);
        amqpTemplate.convertAndSend("","queue1",goodsString);
    }
}
