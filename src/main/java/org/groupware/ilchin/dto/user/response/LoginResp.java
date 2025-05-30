package org.groupware.ilchin.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.groupware.ilchin.entity.User;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginResp {

    private Long id;
    private String username;
    private String token;

    public static LoginResp from(User user, String token) {
        return LoginResp.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
    }

}
