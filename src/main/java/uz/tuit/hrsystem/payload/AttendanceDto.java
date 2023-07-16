package uz.tuit.hrsystem.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.hrsystem.entity.enums.AttendanceEnum;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceDto {

    @Enumerated(EnumType.STRING)
    private AttendanceEnum anEnum;

    private LocalDate time;

}
