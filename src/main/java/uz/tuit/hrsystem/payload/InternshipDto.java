package uz.tuit.hrsystem.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.hrsystem.entity.enums.Position;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternshipDto {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Integer experience;
    private String courseName;
    private String realProjects;
    private String phoneNumber;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String task;
    private boolean isComplete = false;
}
