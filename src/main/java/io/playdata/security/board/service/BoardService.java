package io.playdata.security.board.service;

import io.playdata.security.board.model.BoardDTO;
import io.playdata.security.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BoardDTO createPost(BoardDTO boardDTO){
        boardDTO.setUsername(getCurrentUsername());
        boardDTO.setCreatedAt(LocalDateTime.now());
        return boardRepository.save(boardDTO);
    }

    public BoardDTO getPostByUserId(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public List<BoardDTO> getAllPost() {
        return boardRepository.findAll();
    }

    public BoardDTO updatePost(Long id, BoardDTO boardDTO) {
        BoardDTO post = boardRepository.findById(id).orElse(null);
        if (post != null && post.getUsername().equals(getCurrentUsername())) {
            post.setTitle(boardDTO.getTitle());
            post.setContent(boardDTO.getContent());
            post.setCreatedAt(LocalDateTime.now());
            return boardRepository.save(post);
        }
        return null; // 수정할 게시물이 없거나 권한이 없는 경우 null 반환
    }

    public void deletePost(Long id) {
        BoardDTO post = boardRepository.findById(id).orElse(null);
        if (post != null && post.getUsername().equals(getCurrentUsername())) {
            boardRepository.deleteById(id);
        }
    }

    // findByUserId
    public List<BoardDTO> findByUsername(String username) {
        return boardRepository.findByUsername(username);
    }

    // 현재 로그인한 사용자의 이름(아이디)를 반환하는 메서드
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public void deleteAllByUsername(String username) {
        boardRepository.deleteAllByUsername(username);
    }
}
