package site.kexing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("xm")
@SpringBootTest(classes = {MiaoshaApplication.class, FileAndExcelTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileAndExcelTest {

    @Test
    public void test() throws Exception{

    }
}
