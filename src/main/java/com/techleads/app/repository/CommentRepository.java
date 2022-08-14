package com.techleads.app.repository;

import com.techleads.app.model.Comment;
import com.techleads.app.model.CommentKey;
import com.techleads.app.model.PostKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
//    Page<Comment> findByPostId(CommentKey commentKey, Pageable pageable);
    List<Comment> findByPostPostKey(PostKey postKey);
//    Optional<Comment> findByIdAndPostId(Long id, Long postId);
    // Page<Comment> findByPostId(Long postId, Pageable pageable);

    Optional<Comment> findByCommentKeyAndPostPostKey(CommentKey commentKey, PostKey postKey);
}
