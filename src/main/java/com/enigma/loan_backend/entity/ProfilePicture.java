package com.enigma.loan_backend.entity;


import com.enigma.loan_backend.model.response.FileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_profile_picture")
@Getter @AllArgsConstructor @NoArgsConstructor
public class ProfilePicture {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String contentType;

    private String url;

    private long size;

    public ProfilePicture(String name, String contentType, String url, long size) {
        this.name = name;
        this.contentType = contentType;
        this.url = url;
        this.size = size;
    }
}
