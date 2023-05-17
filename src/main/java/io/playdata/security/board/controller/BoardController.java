package io.playdata.security.board.controller;

import io.playdata.security.board.model.BoardDTO;
import io.playdata.security.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BoardController {
    // http://localhost:8080/
    @Autowired
    private BoardService boardService;

    @GetMapping("/home")
    public String index(Model model,
                        RedirectAttributes redirectAttributes) {
        List<BoardDTO> posts = boardService.getAllPost(); //포스트 데이터를 가져오는 서비스 메소드 호출
        model.addAttribute("posts", posts); // 모델에 포스트 데이터 추가
        return "blog/home";
    }

    @GetMapping("/posting")
    public String showPostForm(Model model) {
        model.addAttribute("blogpost",new BoardDTO());
        return "blog/posting";
    }

    @PostMapping("/posting")
    public String blogpost(@ModelAttribute("blogpost") BoardDTO blogpost,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        boardService.createPost(blogpost);
        return "redirect:/blog/home";
    }

    @GetMapping("/detailview/{id}")
    public String showDetailView(@PathVariable Long id, Model model) {
        BoardDTO post = boardService.getPostByUserId(id);
        if (post == null) {
            return "redirect:/blog/home";
        }
        model.addAttribute("post", post);
        return "blog/detailview";
    }


    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("post") BoardDTO newPost) {
        BoardDTO post = boardService.getPostByUserId(id);
        String currentUsername = boardService.getCurrentUsername();
        if (currentUsername != null && currentUsername.equals(post.getUsername())) {
            post.setTitle(newPost.getTitle());
            post.setContent(newPost.getContent());
            boardService.updatePost(id, post);
        }
        return "redirect:/blog/home";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        BoardDTO post = boardService.getPostByUserId(id);
        if (post == null || !post.getUsername().equals(boardService.getCurrentUsername())) {
            return "redirect:/blog/home";
        }
        model.addAttribute("post", post);
        return "blog/update";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        BoardDTO post = boardService.getPostByUserId(id);
        if (post == null || !post.getUsername().equals(boardService.getCurrentUsername())) {
            redirectAttributes.addFlashAttribute("message", "삭제할 권한이 없습니다");
            return "redirect:/blog/detailview/" + id;
        }
        boardService.deletePost(id);
        return "redirect:/blog/home";
    }

    @GetMapping("/user/{username}")
    public String showUserPost(@PathVariable("username") String username, Model model) {
        List<BoardDTO> posts = boardService.findByUsername(username);
        model.addAttribute("posts", posts);
        model.addAttribute("username", username);
        return "blog/user";
    }
}

