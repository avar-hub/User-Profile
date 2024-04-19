package com.socials.UserProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"email","imagePath"})
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    @NotNull
    private String name;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private Date dob;
    @NotNull
    private String gender;
    @NotNull
    private String sexualOrientation;
    @NotNull
    private String city;
    @Size(min = 2)
    private List<String> interests;
    private String imagePath;
}
