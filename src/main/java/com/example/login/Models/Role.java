// src/main/java/com/example/login/Models/Role.java
package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idRole"
)
public class Role {

    @Id
    @Column(name = "idRole")
    private String idRole;

    @Column(name = "nomRole")
    private String nomRole;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    private List<EmployeSimple> employes;
}
