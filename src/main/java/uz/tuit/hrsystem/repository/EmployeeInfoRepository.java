package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.tuit.hrsystem.entity.EmployeeInfo;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Integer> {

    @Query(value = "select exists (select 1 from employee_info where phone_number=?1);", nativeQuery = true)
    public boolean checkEmployeePhoneNumber(String phoneNumber);

    EmployeeInfo findEmployeeInfoByEmail(String email);

}
