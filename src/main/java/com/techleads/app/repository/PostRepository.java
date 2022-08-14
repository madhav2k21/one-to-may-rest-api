package com.techleads.app.repository;

import com.techleads.app.model.Post;
import com.techleads.app.model.PostKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, PostKey> {
}
