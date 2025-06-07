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

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "department_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", length = 50)
    private String phone;

    @Builder
    public static UserProfile SignUpToUserProfile(User user, SignUp signUp, Department department) {
        return UserProfile.builder()
                .user(user)
                .department(department)
                .fullName(signUp.getFullName())
                .phone(signUp.getPhone())
                .build();
    }

    public void update(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

}