package uz.tuit.hrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.hrsystem.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
