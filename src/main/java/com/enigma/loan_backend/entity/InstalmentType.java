package com.enigma.loan_backend.entity;

import com.enigma.loan_backend.entity.my_enum.EInstalmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_instalment_type")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InstalmentType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Enumerated(EnumType.STRING)
    private EInstalmentType instalmentType;

}
