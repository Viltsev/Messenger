package example.demo.JWT;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
