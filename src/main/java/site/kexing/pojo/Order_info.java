package site.kexing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order_info {
    private int id;
    private int user_id;
    private int goods_id;
    private int delivery_addr_id;
    private String goods_name;
    private int goods_count;
    private double goods_price;
    private int order_channel;
    private int status;
    private Date create_date;
    private Date pay_date;
}
