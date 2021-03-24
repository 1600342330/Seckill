package site.kexing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miaosha_order {
    private int id;
    private int user_id;
    private int order_id;
    private int goods_id;
}
