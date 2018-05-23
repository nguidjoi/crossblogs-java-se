package com.crossover.techtrial.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommentControllerIntegrationTest {
	
	 @Autowired
	 private TestRestTemplate restTemplate;
	
	 private Comment comment;
	
	 private HttpHeaders headers;
	
	 @Before
	 public void setup() throws Exception {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		comment = new Comment();
		comment.setEmail("arnutb001@cross.com");
		comment.setMessage("Je suis heureux");
	 }

	
	 @Test
	 public void WhenGetCommentOfExistingArticleResponseIsOk() {
		 ResponseEntity<String> response = this.restTemplate.getForEntity("/articles/1/comments", String.class);
		 assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();  	
	 }
	
	 @Test
	 public void WhenGetCommentOfInexistingArticleResponseIsNotFound() {
		 ResponseEntity<String> response = this.restTemplate.getForEntity("/articles/90/comments", String.class);
		 assertThat(response.getStatusCode().equals(HttpStatus.NOT_FOUND)).isTrue();  	
	 }
	
	 	
	 @Test
	 public void WhenCreateCommentWithIdOfArticleThenCommentIsCreated() {
		 HttpEntity<Comment> entity = new HttpEntity<Comment>(comment, headers);
		 ResponseEntity<Comment> response = this.restTemplate.postForEntity("/articles/3/comments", entity, Comment.class);
		 
		 assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
	 }
	 
	 @Test
	 public void WhenCreateCommentWithIdOfNonExistingArticleCommentWasCreatedWithoutArticle() {
		 HttpEntity<Comment> entity = new HttpEntity<Comment>(comment, headers);
		 
		 ResponseEntity<Comment> response = this.restTemplate.postForEntity("/articles/0/comments", entity, Comment.class);
		 
		 assertThat(response.getStatusCode().equals(HttpStatus.CREATED)).isTrue();
		 assertThat(response.getBody().getArticle()).isEqualTo(null);	  
	 }
	 
	 
	 
	 
	 
	
}
