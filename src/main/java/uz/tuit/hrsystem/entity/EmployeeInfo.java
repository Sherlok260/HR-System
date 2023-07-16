package uz.tuit.hrsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.tuit.hrsystem.entity.enums.Position;
import uz.tuit.hrsystem.entity.enums.ProjectLevel;
import uz.tuit.hrsystem.entity.enums.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    private String address;
    private Integer salary;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Position position;

    @OneToOne
    private Attendance attendance;

    private boolean isJoinProject;
    private String projectName;

    @Enumerated(EnumType.STRING)
    private ProjectLevel projectLevel;

    @OneToOne(cascade = CascadeType.ALL)
    private LeaveRequest leaveRequest;

    @OneToMany(orphanRemoval = true)
    private List<LeaveRequestHistory> leaveRequestHistories;

    public void addLeaveRequestHistories(LeaveRequestHistory leaveRequestHistories) {
        this.leaveRequestHistories.add(leaveRequestHistories);
    }
}