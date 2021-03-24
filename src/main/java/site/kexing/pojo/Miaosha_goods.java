package site.kexing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miaosha_goods {
    private int id;
    private int goods_id;
    private double miaosha_price;
    private int stock_count;
    private Date start_date;
    private Date end_date;
}
