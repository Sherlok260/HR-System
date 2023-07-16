package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.tuit.hrsystem.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username);

    @Query(value = "select exists (select 1 from employee where username=?1);", nativeQuery = true)
    public boolean checkEmployeeUserName(String email);

}
