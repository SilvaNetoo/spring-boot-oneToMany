package com.example.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Post;
import com.example.jpa.repository.PostRepository;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostRepository postRepository;

	@GetMapping("/")
	public List<Post> getAllPosts(Pageable pageable) {
		return postRepository.findAll();
	}

	@PostMapping("/")
	public Post createPost(@Valid Post post) {
		return postRepository.save(post);
	}

	@GetMapping("/{id}")
	public Post getPostById(@PathVariable(value = "id") Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
	}

	@PutMapping("/{id}")
	public Post updatePost(@PathVariable(value = "id") Long postId, @Valid Post postRequest) {
		Post post = getPostById(postId);
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		post.setContent(postRequest.getContent());
		return postRepository.save(post);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long postId) {
		Post post = getPostById(postId);
		postRepository.delete(post);
		return ResponseEntity.ok().build();
	}

}
