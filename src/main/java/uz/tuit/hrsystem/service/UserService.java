package uz.tuit.hrsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.tuit.hrsystem.entity.*;
import uz.tuit.hrsystem.entity.enums.*;
import uz.tuit.hrsystem.jwt.JwtFilter;
import uz.tuit.hrsystem.jwt.JwtProvider;
import uz.tuit.hrsystem.payload.*;
import uz.tuit.hrsystem.repository.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    InternshipRepository internshipRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    LeaveRequestHistoryRepository leaveRequestHistoryRepository;

    public ApiResponse login(LoginDto dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()));
            Token token = employeeRepository.findByUsername(dto.getEmail()).get().getToken();
            if (token.getLevel().equals("valid")) {
                return new ApiResponse("success", true, token.getToken());
            }
            return new ApiResponse("User name or password incorrect", false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("User name or password incorrect", false);
        }
    }

    public ApiResponse addEmployee(EmployeeDto dto, boolean isStart) {
        try {

            if(employeeInfoRepository.checkEmployeePhoneNumber(dto.getPhoneNumber())) {
                return new ApiResponse("This phone number is already used", false);
            } else if (employeeRepository.checkEmployeeUserName(dto.getEmail())) {
                return new ApiResponse("This email is already used", false);
            }

            EmployeeInfo empInfo = new EmployeeInfo();
            empInfo.setFirstName(dto.getFirstName());
            empInfo.setLastName(dto.getLastName());
            empInfo.setAddress(dto.getAddress());
            empInfo.setBirthday(dto.getBirthday());
            empInfo.setPhoneNumber(dto.getPhoneNumber());
            empInfo.setSalary(dto.getSalary());
            empInfo.setStatus(Status.ACTIVE);
            empInfo.setJoinProject(false);
            empInfo.setPosition(dto.getPosition());
            empInfo.setProjectLevel(ProjectLevel.NONE);
            empInfo.setEmail(dto.getEmail());

            Token emp_token = new Token();
            emp_token.setToken(jwtProvider.generateToken(dto.getEmail()));
            emp_token.setEmail(dto.getEmail());
            emp_token.setLevel("valid");

            Employee emp = new Employee();

            emp.setUsername(dto.getEmail());
            emp.setToken(tokenRepository.save(emp_token));
            emp.setPassword(passwordEncoder.encode(dto.getPassword()));
            emp.setRoles(Roles.EMPLOYEE);
            emp.setEnabled(true);
            if(isStart && new Random().nextInt(2) == 1) {
                empInfo.setJoinProject(true);
                int x = new Random().nextInt(4);
                if(x == 3) empInfo.setProjectLevel(ProjectLevel.HARD);
                else if(x == 2) empInfo.setProjectLevel(ProjectLevel.MEDIUM);
                else if(x == 1) empInfo.setProjectLevel(ProjectLevel.SIMPLE);
                else empInfo.setProjectLevel(ProjectLevel.NONE);
            }
            emp.setInfo(employeeInfoRepository.save(empInfo));
            employeeRepository.save(emp);
            return new ApiResponse("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse addInternship(InternshipDto dto) {
        try {

            if(internshipRepository.checkInternshipPhoneNumber(dto.getPhoneNumber())) {
                return new ApiResponse("This phone number is already used", false);
            }

            Internship internship = new Internship();
            internship.setBirthday(dto.getBirthday());
            internship.setExperience(dto.getExperience());
            internship.setPhoneNumber(dto.getPhoneNumber());
            internship.setFirstName(dto.getFirstName());
            internship.setLastName(dto.getLastName());
            internship.setCourseName(dto.getCourseName());
            internship.setPrice(dto.getPrice());
            internship.setRealProjects(dto.getRealProjects());
            internship.setPosition(dto.getPosition());
            internship.setRoles(Roles.INTERNSHIP);
            internship.setTask(dto.getTask());
            internship.setComplete(false);

            internshipRepository.save(internship);

            return new ApiResponse("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse attendance(AttendanceEnum attendanceEnum) {
        try {
            String emp_email = JwtFilter.getEmail;
            Employee employee = employeeRepository.findByUsername(emp_email).get();

            if(employee.getInfo().getAttendance() == null) {
                Attendance attendance = new Attendance();
                if(attendanceEnum.name() == AttendanceEnum.COME.name()) {
                    attendance.setCome_time(LocalDateTime.now());
                    employee.getInfo().setStatus(Status.ACTIVE);
                } else if(attendanceEnum.name() == AttendanceEnum.LEAVE.name()) {
                    return new ApiResponse("please set first come time", false);
                }
                Attendance attendance1 = attendanceRepository.save(attendance);
                employee.getInfo().setAttendance(attendance1);
                employeeRepository.save(employee);
            } else {
                Attendance attendance2 = employee.getInfo().getAttendance();
                if(attendanceEnum.name().equals(AttendanceEnum.COME.name())) {
                    attendance2.setCome_time(LocalDateTime.now());
                    employee.getInfo().setStatus(Status.ACTIVE);
                } else if(attendanceEnum.name().equals(AttendanceEnum.LEAVE.name())) {
                    attendance2.setLeave_time(LocalDateTime.now());
                }
                employee.getInfo().setAttendance(attendance2);
                employeeRepository.save(employee);
            }
            return new ApiResponse("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse giveProjectToEmp(GiveProject project) {
        try {

            String emp_email = project.getEmail();
            EmployeeInfo employeeInfo = employeeInfoRepository.findEmployeeInfoByEmail(emp_email);
            employeeInfo.setProjectLevel(project.getProjectLevel());
            employeeInfo.setJoinProject(true);
            employeeInfo.setProjectName(project.getProjectName());

            employeeInfoRepository.save(employeeInfo);

            return new ApiResponse("success", true);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse leaveRequest(LeaveRequestDto dto) {
        try {

            String emp_email = JwtFilter.getEmail;
            EmployeeInfo employeeInfo = employeeInfoRepository.findEmployeeInfoByEmail(emp_email);
            LocalDateTime now_date = LocalDateTime.now();

            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setFirstName(employeeInfo.getFirstName());
            leaveRequest.setLastName(employeeInfo.getLastName());
            leaveRequest.setComment(dto.getComment());
            leaveRequest.setPosition(employeeInfo.getPosition());
            leaveRequest.setEmail(emp_email);
            leaveRequest.setProjectLevel(employeeInfo.getProjectLevel());
            leaveRequest.setProjectName(employeeInfo.getProjectName());
            leaveRequest.setReqDate(now_date);

            employeeInfo.setLeaveRequest(leaveRequestRepository.save(leaveRequest));

            LeaveRequestHistory history = new LeaveRequestHistory();
            history.setFirstName(leaveRequest.getFirstName());
            history.setLastName(leaveRequest.getLastName());
            history.setComment(leaveRequest.getComment());
            history.setPosition(leaveRequest.getPosition());
            history.setProjectLevel(leaveRequest.getProjectLevel());
            history.setProjectName(leaveRequest.getProjectName());
            history.setStatus(LeaveRequestStatus.PROGRESS);
            history.setReqDate(now_date);

            employeeInfo.addLeaveRequestHistories(leaveRequestHistoryRepository.save(history));
//            Employee employee = employeeRepository.findByUsername(emp_email).get();
//            employee.setInfo(employeeInfo);
//            employeeRepository.save(employee);
            employeeInfoRepository.save(employeeInfo);
            return new ApiResponse("success", true);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }

    public ApiResponse allowLeaveReq(LeaveRequest leaveRequest, boolean isAllow) {
        try {
            String emp_email = leaveRequest.getEmail();
            EmployeeInfo employeeInfo = employeeInfoRepository.findEmployeeInfoByEmail(emp_email);
            List<LeaveRequestHistory> leaveRequestHistories = employeeInfo.getLeaveRequestHistories();
            LeaveRequestHistory history = new LeaveRequestHistory();
            for (LeaveRequestHistory l: leaveRequestHistories) {
                System.out.println("---------------------------------------------------------");
                System.out.println(l.getReqDate() + " ___________ " + leaveRequest.getReqDate());
                if(l.getReqDate().equals(leaveRequest.getReqDate())) System.out.println("yes");
                else System.out.println("no");
                System.out.println("---------------------------------------------------------");
                if(l.getReqDate().equals(leaveRequest.getReqDate())) {

//                    l.setId(l.getId());
//                    history.setReqDate(l.getReqDate());
//                    history.setPosition(l.getPosition());
//                    history.setComment(l.getComment());
//                    history.setStatus(l.getStatus());
//                    history.setProjectName(l.getProjectName());
//                    history.setProjectLevel(l.getProjectLevel());
//                    history.setFirstName(l.getFirstName());
//                    history.setLastName(l.getLastName());

                    if (isAllow) {
                        l.setStatus(LeaveRequestStatus.ALLOWED);
                    } else {
                        l.setStatus(LeaveRequestStatus.CANCELLED);
                    }
                    break;
               }
            }

            leaveRequestRepository.deleteById(leaveRequest.getId());
            employeeInfo.setLeaveRequestHistories(leaveRequestHistories);
            Employee employee = employeeRepository.findByUsername(emp_email).get();
            employee.setInfo(employeeInfo);
            employeeRepository.save(employee);
            return new ApiResponse("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(e.getMessage(), false);
        }
    }
}
