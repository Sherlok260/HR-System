package uz.tuit.hrsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.hrsystem.entity.enums.LeaveRequestStatus;
import uz.tuit.hrsystem.entity.enums.Position;
import uz.tuit.hrsystem.entity.enums.ProjectLevel;
import uz.tuit.hrsystem.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;

    private LocalDateTime reqDate;

    private String comment;
    private String projectName;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private ProjectLevel projectLevel;

    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus status;

}
