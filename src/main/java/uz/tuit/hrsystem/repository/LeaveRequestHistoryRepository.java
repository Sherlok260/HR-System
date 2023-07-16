package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.hrsystem.entity.LeaveRequestHistory;

import java.util.List;

public interface LeaveRequestHistoryRepository extends JpaRepository<LeaveRequestHistory, Integer> {

}
