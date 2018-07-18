package com.example.jpa.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.model.Post;
import com.example.jpa.repository.CommentRepository;
import com.example.jpa.repository.PostRepository;

@RequestMapping("/posts")
@RestController
public class CommentController {

	@Autowired
	private PostRepository postRep;

	@Autowired
	private CommentRepository commentRep;

	@GetMapping("/{id}/comments/")
	public Page<Comment> getAllCommentByPostId(@PathVariable(value = "id") Long postId, Pageable pageable) {
		return commentRep.findByPostId(postId, pageable);
	}

	@PostMapping("/{id}/comments/")
	public Comment createComment(@PathVariable(value = "id") Long postId, @Valid Comment comment) {
		Post post = getPostById(postId);
		comment.setPost(post);
		return commentRep.save(comment);
	}
	
	@PutMapping("/{id}/comments/{id}")
	public Comment updateComment(@PathVariable(value = "id") Long postId, @PathVariable(value = "id") Long commentId, @Valid Comment comment) {
		if(!postRep.existsById(postId)) {
			throw new ResourceNotFoundException("Post", "postId", postId);
		}
		
		Comment commentWithId = getCommentById(commentId);
		commentWithId.setText(comment.getText());
		return commentRep.save(commentWithId);
	}
	
	@DeleteMapping("/{id}/comments/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long postId, @PathVariable("id") Long commentId) {
		if(!postRep.existsById(postId)) {
			throw new ResourceNotFoundException("Post ", "postId", postId);
		}
		
		Comment comment = getCommentById(commentId);
		commentRep.delete(comment);
		return ResponseEntity.ok().build();
	}

	private Post getPostById(@PathVariable Long postId) {
		return postRep.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ", "postId", postId));
	}
	
	private Comment getCommentById(@PathVariable Long commentId) {
		return commentRep.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
	}
}