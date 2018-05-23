package com.crossover.techtrial.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
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

import com.crossover.techtrial.model.Article;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArticleControllerIntegrationTest {

	 @Autowired
	 private TestRestTemplate restTemplate;
	
	 private Article article;
	 private HttpHeaders headers;

	 @Before
	 public void setup() throws Exception {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		article = new Article();
		article.setTitle("Les seigneur des agneaux");
		article.setEmail("al22018@cross.com");
		article.setDate(LocalDateTime.now());
	 }

	 @Test
	 public void whenCreateArticleThenArticleShouldBeCreated() {
		HttpEntity<Object> article = getHttpEntity("{\"email\": \"user2@gmail.com\", \"title\": \"hello user 2\" }");
		ResponseEntity<Article> resultAsset = restTemplate.postForEntity("/articles", article, Article.class);
		Assert.assertNotNull(resultAsset.getBody().getId());
	 }

	 private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	 }
	
	 @Test
	 public void WhenGetExistingArticleByIsIdRelatedArticleIsFound() {	
		getExistingArticleById(1);	 
	 }
	
	 
	 @Test
	 public void WhenGetNonExistingArticleByIdRelatedArticleIsNotFound() {
		 getNonExistingArticleById(0);	 
	 }
	 
	 
	 @Test
	 public void WhenUpdateNonExistingArticleByIdRelatedArticleIsNotFound() {		
		 this.restTemplate.put("/articles/0", article);	 
		 getNonExistingArticleById(0);
	 }
	
	 
	 @Test
	 public void WhenUpdateExistingArticleByIdWithNewArticlePreviuosOneIsUpdated() {	
		 this.restTemplate.put("/articles/1", article);	 
		 Article response = getExistingArticleById(1);
	
		 assertThat(response.getDate().isEqual(article.getDate()));
	 }
	 
	
	 @Test
	 public void WhenSearchArticleByTitleGetAllArticleWithPrefixedTitle() {	
		 ResponseEntity<Article[]> response= this.restTemplate.getForEntity("/articles/search?text=he", Article[].class );	 
		 assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
	 }
	 
	 @Test
	 public void WhenDeleteExistingArticleByIdarticleDoesNotExistAlready() {
		 ResponseEntity<Article[]> response= this.restTemplate.getForEntity("/articles/search?text=he", Article[].class ); 
		 Arrays.asList(response.getBody())
		 	   .stream()
		 	   .forEach(article -> this.restTemplate.delete("/articles/"+article.getId().toString()));	 
		 ResponseEntity<Article[]> emptyResponse= this.restTemplate.getForEntity("/articles/search?text=he", Article[].class ); 
		 assertThat(emptyResponse.getBody().length == 0);	
	 }
	 
	 private Article getExistingArticleById(Integer id) {
			ResponseEntity<Article> response = this.restTemplate.getForEntity("/articles/"+id.toString(), Article.class);		 
			assertThat(response.getStatusCode().equals(HttpStatus.OK)).isTrue();
			assertThat(response.getBody().getId()).isNotNull();
			return response.getBody();
	 }
	 
	 private void getNonExistingArticleById(Integer id) {
			ResponseEntity<Article> response = this.restTemplate.getForEntity("/articles/"+id.toString(), Article.class);	 
			 assertThat(response.getStatusCode().equals(HttpStatus.NOT_FOUND)).isTrue();
			 assertThat(response.getBody()).isNull();
	 }
	 
}
