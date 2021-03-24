package site.kexing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kexing.pojo.Order_info;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailStatic {
    private Order_info order_info;
    private GoodsList order_good;
}
