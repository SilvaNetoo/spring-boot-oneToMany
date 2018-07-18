package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
