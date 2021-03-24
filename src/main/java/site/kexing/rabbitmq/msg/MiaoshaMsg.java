package site.kexing.rabbitmq.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kexing.pojo.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaMsg {
    private User user;
    private int good_id;
}
