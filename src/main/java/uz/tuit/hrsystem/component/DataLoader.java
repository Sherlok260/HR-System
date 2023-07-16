package uz.tuit.hrsystem.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.tuit.hrsystem.entity.Employee;
import uz.tuit.hrsystem.entity.EmployeeInfo;
import uz.tuit.hrsystem.entity.Token;
import uz.tuit.hrsystem.entity.enums.Position;
import uz.tuit.hrsystem.entity.enums.ProjectLevel;
import uz.tuit.hrsystem.entity.enums.Roles;
import uz.tuit.hrsystem.entity.enums.Status;
import uz.tuit.hrsystem.jwt.JwtProvider;
import uz.tuit.hrsystem.payload.EmployeeDto;
import uz.tuit.hrsystem.payload.InternshipDto;
import uz.tuit.hrsystem.repository.EmployeeInfoRepository;
import uz.tuit.hrsystem.repository.EmployeeRepository;
import uz.tuit.hrsystem.repository.TokenRepository;
import uz.tuit.hrsystem.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    public void addHR(String hr_email) {
        EmployeeInfo hrInfo = new EmployeeInfo();
        hrInfo.setFirstName("Qobiljon");
        hrInfo.setLastName("Jumaboyev");
        hrInfo.setAddress("Tashkent");
        hrInfo.setBirthday(LocalDate.of(2000, 12, 12));
        hrInfo.setPhoneNumber("+998909991122");
        hrInfo.setSalary(700);
        hrInfo.setStatus(Status.ACTIVE);
        hrInfo.setProjectLevel(ProjectLevel.NONE);
        hrInfo.setJoinProject(false);
        hrInfo.setPosition(Position.NONE);
        hrInfo.setEmail(hr_email);

        Token hr_token = new Token();
        hr_token.setToken(jwtProvider.generateToken(hr_email));
        hr_token.setEmail(hr_email);
        hr_token.setLevel("valid");

        Employee hr = new Employee();
        hr.setInfo(employeeInfoRepository.save(hrInfo));
        hr.setUsername(hr_email);
        hr.setToken(tokenRepository.save(hr_token));
        hr.setPassword(passwordEncoder.encode("hr000000"));
        hr.setRoles(Roles.HR);
        hr.setEnabled(true);
        employeeRepository.save(hr);

    }
    
    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {

            String hr_email = "hr_qobiljon@gmail.com";

            addHR(hr_email);
            userService.addEmployee(new EmployeeDto("Shaxzod", "Murtozaqulov", "+998939320618", LocalDate.of(2000, 12, 12), "Tashkent", 400, "emp_shaxzod@gmail.com", "00000000", Position.BACKEND), true);
            userService.addEmployee(new EmployeeDto("Nodir", "Jabborov", "+998335667899", LocalDate.of(2000, 12, 12), "Tashkent", 410, "emp_nodir@gmail.com", "00000000", Position.FRONTEND), true);
            userService.addEmployee(new EmployeeDto("Abror", "Tursunov", "+998904552525", LocalDate.of(2000, 12, 12), "Tashkent", 409, "emp_abror@gmail.com", "00000000", Position.BACKEND), true);
            userService.addEmployee(new EmployeeDto("Kamol", "Bozorov", "+998998552678", LocalDate.of(2000, 12, 12), "Tashkent", 390, "emp_kamol@gmail.com", "00000000", Position.FRONTEND), true);

            userService.addInternship(new InternshipDto("Baxodir", "Choriyev", LocalDate.of(2000, 10, 12), 1, "PDP", "", "+998955661232", 300, Position.BACKEND, "HR system", false));
            userService.addInternship(new InternshipDto("Feruz", "Maqsudov", LocalDate.of(2001, 01,23), 1, "Najot ta`lim", "", "+998937415989", 200, Position.FRONTEND, "HR system", false));
        }
    }
}
