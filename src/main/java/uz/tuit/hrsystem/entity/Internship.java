package uz.tuit.hrsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.tuit.hrsystem.entity.enums.Position;
import uz.tuit.hrsystem.entity.enums.Roles;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;
    private Integer experience;
    private String courseName;
    private String realProjects;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String task;
    private boolean isComplete = false;

}