package com.techleads.app.controller;

import com.techleads.app.excepton.ResourceNotFoundException;
import com.techleads.app.model.Comment;
import com.techleads.app.model.CommentKey;
import com.techleads.app.model.PostKey;
import com.techleads.app.repository.CommentRepository;
import com.techleads.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/{postType}/comments")
//    public Page<Comment> getAllCommentsByPostId(Pageable pageable
            public List<Comment> getAllCommentsByPostId(
            @PathVariable("postId") String postId,
            @PathVariable("postType") String postType
                                                ) {
        PostKey key=new PostKey(postId,postType);

        return commentRepository.findByPostPostKey(key);
    }



    @GetMapping("/pc")
    public Comment getComment(){
        CommentKey key=new CommentKey("key", "type",10 );
            Comment cmt=new Comment();
        cmt.setCommentKey(key);
        cmt.setText("test text");


          return cmt;
    }

    @PostMapping("/posts/{postId}/{postType}/comments")
    public Comment createComment(
            @PathVariable(value = "postId") String postId,
            @PathVariable(value = "postType") String postType,
            @Valid @RequestBody Comment comment) {

        PostKey postKey = new PostKey(postId, postType);

//        CommentKey cKey=new CommentKey();
        return postRepository.findById(postKey).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

//    @PutMapping("/posts/{postId}/comments/{commentId}")
//    public Comment updateComment(@PathVariable (value = "postId") Long postId,
//                                 @PathVariable (value = "commentId") Long commentId,
//                                 @Valid @RequestBody Comment commentRequest) {
//        if(!postRepository.existsById(postId)) {
//            throw new ResourceNotFoundException("PostId " + postId + " not found");
//        }
//
//        return commentRepository.findById(commentId).map(comment -> {
//            comment.setText(commentRequest.getText());
//            return commentRepository.save(comment);
//        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
//    }

    @DeleteMapping("/posts/{postId}/{postType}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable(value = "postId") String postId,
            @PathVariable(value = "postType") String postType,
            @PathVariable (value = "commentId") Integer commentId
    ) {

        PostKey postKey=new PostKey(postId, postType);
        CommentKey commentKey=new CommentKey(postId, postType, commentId);
        return commentRepository.findByCommentKeyAndPostPostKey(commentKey, postKey).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }
}
