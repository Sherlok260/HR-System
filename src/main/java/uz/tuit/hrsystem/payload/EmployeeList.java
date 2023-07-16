package uz.tuit.hrsystem.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.hrsystem.entity.enums.Position;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeList {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
    private String address;
    private Integer salary;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Position position;

}
