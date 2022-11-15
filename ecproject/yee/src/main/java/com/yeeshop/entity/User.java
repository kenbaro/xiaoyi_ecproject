package com.yeeshop.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="Users")
@Getter @Setter
public class User {
    @Id
    @NotEmpty
    String id;

    @NotEmpty
    @Length(min=6)
    String password;

    @NotEmpty
    String fullname;
    @NotEmpty
    String telephone;

    @NotEmpty
    @Email
    String email;

    String photo;
    Boolean activated;
    Boolean admin;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Order> orders;
}
