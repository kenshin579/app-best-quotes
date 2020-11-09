package kr.pe.advenoh.quote.repository;

import kr.pe.advenoh.quote.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    Optional<User> findByUsername(String username);
}