package shop.mtcoding.blog1.dto.user;

import lombok.Getter;
import lombok.Setter;

public class UserReq {
    @Setter
    @Getter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String email;
    }

    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

}
