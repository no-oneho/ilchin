package org.groupware.ilchin.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tel", length = 50)
    private String tel;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}