package tests;

import com.senla.courses.autoservice.service.TokenService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class SecurityTest {

    @Test
    public void tokenServiceTest() {
        TokenService tokenService = new TokenService();

        String token = tokenService.generateToken("user", LocalDateTime.now().plusHours(30));
        System.out.println(token);

        String userName = tokenService.getUserNameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ1c2VyIiwiZXhwIjoxNjAzOTM0Mzk1fQ.cv_dQM8jkMQRhC1UNixd6kBCX6AzQ8KiAeeTG01sqbtqgrvF4n3iOsEX55GjLwTEtEclAq5zkYfptlBuz10S6g");
        System.out.println(userName);
    }
}
