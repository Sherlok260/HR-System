package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.tuit.hrsystem.entity.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    @Query(value = "select exists (select 1 from internship where phone_number=?1);", nativeQuery = true)
    public boolean checkInternshipPhoneNumber(String phoneNumber);
}
