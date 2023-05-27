package io.playdata.security.comment.controller;

import io.playdata.security.comment.model.CommentDTO;
import io.playdata.security.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment) {
        CommentDTO createdComment = commentService.createComment(comment.getPostId(), comment.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId){
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);
    }

}

