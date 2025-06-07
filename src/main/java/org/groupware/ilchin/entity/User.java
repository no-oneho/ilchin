package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.groupware.ilchin.dto.user.request.SignUp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;


    @Builder
    public static User SignUpToUser(SignUp signUp, String passwordEnc) {
        return User.builder()
                .username(signUp.getUsername())
                .email(signUp.getEmail())
                .password(passwordEnc)
                .role(signUp.getRole())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void deleteUser() {
        this.isDeleted = true;
    }

}