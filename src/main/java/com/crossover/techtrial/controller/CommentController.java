package com.crossover.techtrial.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import com.crossover.techtrial.service.ArticleService;
import com.crossover.techtrial.service.CommentService;

@RestController
public class CommentController {
	
	@Autowired
	CommentService commentService;

	@Autowired
	ArticleService articleService;

	@PostMapping(path = "articles/{articleId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable(value = "articleId") Long articleId,
			@RequestBody Comment comment) {	
		Article article = articleService.findById(articleId);
		comment.setArticle(article);
		Comment savedComment = commentService.save(comment);
		ResponseEntity<Comment> commentsResponse = new ResponseEntity<Comment>(savedComment, HttpStatus.CREATED);
		return commentsResponse;
	}

	@GetMapping(path = "articles/{articleId}/comments")
	public ResponseEntity<List<Comment>> getComments(@PathVariable("articleId") Long articleId) {
		ResponseEntity<List<Comment>> commentsResponse;
		List<Comment> comments = commentService.findAll(articleId);
		if(Objects.nonNull(comments) && !comments.isEmpty()) {
			commentsResponse = new ResponseEntity<>(comments , HttpStatus.OK);
		}
		else {
			commentsResponse= new ResponseEntity<>(comments, HttpStatus.NOT_FOUND);
		}
		return commentsResponse;
	}
}
