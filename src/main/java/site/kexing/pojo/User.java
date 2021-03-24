package site.kexing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date register_date;
    private Date last_login_date;
    private int login_count;
}
