package com.crossover.techtrial.service;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.repository.ArticleRepository;

@RunWith(SpringRunner.class)
public class ArticleServiceUnittest {

	@TestConfiguration
    static class ArticleServiceImplTestContextConfiguration {
  
        @Bean
        public ArticleService articleService() {
            return new ArticleServiceImpl();
        }
    }
	
	 @Autowired
	 private ArticleService articleService;
	 
	 @MockBean
	 ArticleRepository articleRepository;
	 
	 Article article;
	 
	 @Before
	 public void setUp() {
		 article = new Article();
		 article.setId(1L);
		 article.setTitle("Luxempart assurance");
	     Mockito.when(articleRepository.save(article))
	       .thenReturn(article);
	 }
	 
	 @Test
	 public void whenArticleIsSavedThenItIsReturned() {
		 Article savedArticle = articleService.save(article);
		 assertThat(article.equals(savedArticle)).isTrue();      
	 }
	
	 @Test
	 public void findArticleByIdShouldReturnCorrespondingExistingArticle() {
		 Mockito.when(articleRepository.findById(article.getId()))
		 .thenReturn(Optional.of(article));
		 
		 Article foundArticle = articleService.findById(article.getId());
		 assertThat(article.equals(foundArticle)).isTrue();
	 }
	 
	 @Test
	 public void findArticleByTitleShouldReturnCorrespondingExistingArticle() {
		 
		 List<Article> articles = new ArrayList<>();		 
		 articles.add(article);
		 
		 Mockito.when(articleRepository
				 .findTop10ByTitleContainingIgnoreCase(article.getTitle()))
		 .thenReturn(articles);
		 
		 List<Article> foundArticles = articleService.search(article.getTitle());
		 assertThat(foundArticles).containsOnly(article);
	 }
 
}
