package com.enigma.loan_backend.entity;

import com.enigma.loan_backend.entity.my_enum.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String phone;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "profile_picture_id")
    private ProfilePicture profilePicture;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customers")
    private List<LoanDocument> loanDocuments;

    public Customer(User user) {
        this.user = user;
    }
}
