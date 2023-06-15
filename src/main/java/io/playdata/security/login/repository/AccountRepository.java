package io.playdata.security.login.repository;

import io.playdata.security.login.model.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Long> {
    public AccountDTO findByUsername(String username);  // Sprint Security가 알아서
    Optional<AccountDTO> findByEmail(String email);
}
