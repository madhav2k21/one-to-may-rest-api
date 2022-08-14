package com.techleads.app.controller;

import com.techleads.app.excepton.ResourceNotFoundException;
import com.techleads.app.model.Post;
import com.techleads.app.model.PostKey;
import com.techleads.app.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getAllPosts() {


        return postRepository.findAll();
    }
//    public Page<Post> getAllPosts(Pageable pageable) {
//
//
//        return postRepository.findAll(pageable);
//    }

    @GetMapping(value = {"/posts/{postId}/{postType}"})
    public Post findPostBykey(
            @PathVariable("postId") String postId,
            @PathVariable("postType") String postType
    ) {

        PostKey key=new PostKey(postId,postType);
        return postRepository.findById(key).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));

    }




    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/posts/{postId}/{postType}")
    public Post updatePost( @PathVariable String postId,
                            @PathVariable String postType, @Valid @RequestBody Post postRequest) {
        PostKey key=new PostKey(postId,postType);

        return postRepository.findById(key).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }


    @DeleteMapping("/posts/{postId}/{postType}")
    public ResponseEntity<?> deletePost(
            @PathVariable String postId,
            @PathVariable String postType
    ) {

        PostKey key=new PostKey(postId,postType);
        return postRepository.findById(key).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
