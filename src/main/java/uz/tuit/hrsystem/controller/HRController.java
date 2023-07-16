package uz.tuit.hrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.tuit.hrsystem.entity.Employee;
import uz.tuit.hrsystem.entity.LeaveRequest;
import uz.tuit.hrsystem.payload.*;
import uz.tuit.hrsystem.repository.EmployeeInfoRepository;
import uz.tuit.hrsystem.repository.EmployeeRepository;
import uz.tuit.hrsystem.repository.InternshipRepository;
import uz.tuit.hrsystem.repository.LeaveRequestRepository;
import uz.tuit.hrsystem.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HRController {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    InternshipRepository internshipRepository;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto dto) {
        ApiResponse apiResponse = userService.login(dto);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/add/employee")
    @PreAuthorize("hasRole('HR')")
    public HttpEntity<?> addEmployee(@RequestBody EmployeeDto dto) {
        ApiResponse apiResponse = userService.addEmployee(dto, false);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/add/internship")
    @PreAuthorize("hasRole('HR')")
    public HttpEntity<?> addInternship(@RequestBody InternshipDto dto) {
        ApiResponse apiResponse = userService.addInternship(dto);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/hello2")
    public ResponseEntity<?> hello2() {
        return ResponseEntity.ok("hello2");
    }

    @GetMapping("/getAllEmp")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> getAllEmp() {
        return ResponseEntity.ok(employeeInfoRepository.findAll());
    }

    @GetMapping("/getAllInt")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> getAllInt() {
        return ResponseEntity.ok(internshipRepository.findAll());
    }

    @PostMapping("/giveProject")
    public ResponseEntity<?> giveProject(@RequestBody GiveProject giveProject) {
        return ResponseEntity.ok(userService.giveProjectToEmp(giveProject));
    }

    @GetMapping("/leaveReqList")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestRepository.findAll());
    }

    @PostMapping("/allowLeaveReq")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<?> allowLeaveRequests(@RequestBody LeaveRequest leaveRequest, @RequestParam boolean isAllow) {
        return ResponseEntity.ok(userService.allowLeaveReq(leaveRequest, isAllow));
    }
}
