import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SecurityTest {
    @Test
    public void testBcry(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("123");
        System.out.println("password:"+password);
        boolean matches = encoder.matches("123", password);
        System.out.println(matches);
    }
}
