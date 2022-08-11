package com.enigma.loan_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tbl_role")
@Getter @AllArgsConstructor @NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "role_id")
    private String id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role(ERole role) {
        this.role = role;
    }
}
