package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.tuit.hrsystem.entity.Attendance;

import java.time.LocalDateTime;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    
    @Query(value = "update attendance(come_time) value (?2) where id=?1", nativeQuery = true)
    public void setComeTime(Integer id, LocalDateTime time);

    @Query(value = "update attendance(leave_time) value (?2) where id=?1", nativeQuery = true)
    public void setLeaveTime(Integer id, LocalDateTime time);
}
