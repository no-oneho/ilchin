package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.groupware.ilchin.dto.department.request.CreateReq;
import org.groupware.ilchin.dto.department.request.PatchReq;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "tel", length = 50)
    private String tel;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public static Department create(CreateReq createReq, User user) {
        return Department.builder()
                .name(createReq.name())
                .description(createReq.description())
                .user(user)
                .tel(createReq.tel())
                .isDeleted(false)
                .build();
    }

    public void updateDepartment(PatchReq patchReq) {
        if (patchReq.name() != null && !patchReq.name().isBlank()) {
            this.name = patchReq.name();
        }
        if (patchReq.description() != null && !patchReq.description().isBlank()) {
            this.description = patchReq.description();
        }
    }

    public void updateManager(User user) {
        this.user = user;
    }

    public void deleteDepartment() {
        this.isDeleted = true;
    }

}