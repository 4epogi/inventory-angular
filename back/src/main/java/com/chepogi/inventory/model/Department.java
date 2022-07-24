package com.chepogi.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "departments")
@Builder
public class Department {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department name cannot be empty")
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", targetEntity=DeviceUser.class,
            cascade = CascadeType.ALL)
    private List<DeviceUser> deviceUsers = new ArrayList<>();
}
