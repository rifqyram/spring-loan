package com.enigma.loan_backend.entity;

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
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "customer_id")
    private String id;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String phone;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = ProfilePicture.class, orphanRemoval = true)
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
    private ProfilePicture profilePicture;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = LoanDocument.class, mappedBy = "customer")
    private List<LoanDocument> loanDocuments;

    public Customer(User user) {
        this.user = user;
    }
}
