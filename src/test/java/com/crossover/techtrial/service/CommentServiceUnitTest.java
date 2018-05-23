package com.crossover.techtrial.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.model.Comment;
import com.crossover.techtrial.repository.CommentRepository;

@RunWith(SpringRunner.class)

public class CommentServiceUnitTest {

	@TestConfiguration
    static class CommmentServiceImplTestContextConfiguration {
  
        @Bean
        public CommentService commentService() {
            return new CommentServiceImpl();
        }
    }
	
	 @Autowired
	 private CommentService commentService;
	 
	 @MockBean
	 CommentRepository commentRepository;
	 
	 Comment comment;
	 Article article;
	 
	 @Before
	 public void setUp() {
		 article = new Article();
		 article.setId(1L);
		 article.setTitle("Luxempart assurance");
		 
		 comment = new Comment();
		 comment.setId(4L);
		 comment.setEmail("Luxempart@ass.com");
		 comment.setArticle(article);
		 
	     Mockito.when(commentRepository.save(comment))
	       .thenReturn(comment);
	 }
	 
	 @Test
	 public void whenCommentIsSavedThenItIsReturned() {
		 Comment savedComment = commentRepository.save(comment);
		 assertThat(comment.equals(savedComment)).isTrue();      
	 }
	 
	
	 @Test
	 public void findAllCommentByArticleIdShouldReturnRelatedExistingComments() {
		 
		 List<Comment> comments = new ArrayList<>();		 
		 comments.add(comment);
		 
		 Mockito.when(commentRepository.findByArticleIdOrderByDate(comment.getArticle().getId()))
		 .thenReturn(comments);
		 
		 List<Comment> foundComments = commentService.findAll(article.getId());
		 assertThat(foundComments).containsOnly(comment);
	 }
	 
	
 
}
