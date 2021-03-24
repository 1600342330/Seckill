package site.kexing.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsList{
    private int id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private double goods_price;
    private int goods_stock;
    private double miaosha_price;
    private int stock_count;
    private Date start_date;
    private Date end_date;
}
