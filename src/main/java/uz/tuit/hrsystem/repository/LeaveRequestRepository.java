package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.hrsystem.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
}
