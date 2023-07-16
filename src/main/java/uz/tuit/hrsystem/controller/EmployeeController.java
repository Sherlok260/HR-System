package uz.tuit.hrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.tuit.hrsystem.entity.LeaveRequestHistory;
import uz.tuit.hrsystem.entity.enums.AttendanceEnum;
import uz.tuit.hrsystem.jwt.JwtFilter;
import uz.tuit.hrsystem.payload.ApiResponse;
import uz.tuit.hrsystem.payload.LeaveRequestDto;
import uz.tuit.hrsystem.repository.EmployeeInfoRepository;
import uz.tuit.hrsystem.repository.LeaveRequestHistoryRepository;
import uz.tuit.hrsystem.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    UserService userService;

    @Autowired
    LeaveRequestHistoryRepository leaveRequestHistoryRepository;

    @Autowired
    EmployeeInfoRepository employeeInfoRepository;

    @PostMapping("/attendance")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> attendance(@RequestParam AttendanceEnum attendanceEnum) {
        ApiResponse apiResponse = userService.attendance(attendanceEnum);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/hello3")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/leaveReq")
    public ResponseEntity<?> leaveRequest(@RequestBody LeaveRequestDto dto) {
        ApiResponse apiResponse = userService.leaveRequest(dto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/leaveReqHistory")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> leaveReqHistory() {
        return ResponseEntity.ok(employeeInfoRepository.findEmployeeInfoByEmail(JwtFilter.getEmail).getLeaveRequestHistories());
    }

}