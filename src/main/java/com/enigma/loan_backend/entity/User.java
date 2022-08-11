package com.enigma.loan_backend.entity;

import com.enigma.loan_backend.model.response.SignInResponse;
import com.enigma.loan_backend.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mst_user")
@Getter @AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id")
    private String id;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserResponse toUserResponse() {
        return new UserResponse(email, roles.get(0).getRole().name());
    }

    public SignInResponse toSignInResponse(String token) {
        return new SignInResponse(email, roles.get(0).getRole().name(), token);
    }
}
