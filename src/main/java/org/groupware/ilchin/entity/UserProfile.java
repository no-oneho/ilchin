package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.groupware.ilchin.dto.user.request.SignUp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", length = 50)
    private String phone;

    @Builder
    public static UserProfile SignUpToUserProfile(Long userId, SignUp signUp) {
        return UserProfile.builder()
                .userId(userId)
                .departmentId(signUp.getDepartment())
                .fullName(signUp.getFullName())
                .phone(signUp.getPhone())
                .build();
    }

}