package org.groupware.ilchin.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SignUp {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private Long department;
    private String phone;
    private String fullName;

}
