package hellomq.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "queue4")
@Component
public class Consumer {

    @RabbitHandler
    public void reivece1(Goods goods){
        System.out.println("Goods-----" + goods);
    }

    @RabbitHandler
    public void reivece2(byte[] goods){
        //将序列化的数组强转回对象
        Goods goods1 = (Goods) SerializationUtils.deserialize(goods);
        System.out.println("Goods []-----" + goods1);
    }

    @RabbitHandler
    public void reivece3(String goods) throws JsonProcessingException {
        //将序列化的字符串转回对象
        ObjectMapper mapper = new ObjectMapper();
        Goods goods1 = mapper.readValue(goods, Goods.class);
        System.out.println("Goods string-----" + goods1);
    }
}
