package hellomq.object;

import java.io.Serializable;

public class Goods  implements  Serializable {
    private Integer price = 1;
    private  String name = "wq";

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
