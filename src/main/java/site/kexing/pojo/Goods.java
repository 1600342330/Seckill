package site.kexing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private double goods_price;
    private int goods_stock;
}
