package io.playdata.security.board.repository;

import io.playdata.security.board.model.BoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardDTO,Long> {
    List<BoardDTO> findByUsername(String username);

    void deleteAllByUsername(String username);
}
