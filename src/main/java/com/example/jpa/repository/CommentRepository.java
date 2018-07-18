package com.example.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findByPostId(Long postId, Pageable pageable);
	
}
