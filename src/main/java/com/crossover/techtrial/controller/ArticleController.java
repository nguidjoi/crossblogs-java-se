package com.crossover.techtrial.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.techtrial.model.Article;
import com.crossover.techtrial.service.ArticleService;

@RestController
public class ArticleController {
	
	@Autowired
	ArticleService articleService;

	@PostMapping(path = "articles")
	public ResponseEntity<Article> createArticle(@RequestBody Article article) {
		return new ResponseEntity<>(articleService.save(article), HttpStatus.CREATED);
	}

	@GetMapping(path = "articles/{articleId}")
	public ResponseEntity<Article> getArticleById(@PathVariable("articleId") Long id) {
		Article article = articleService.findById(id);
		if (Objects.nonNull(article)) {
			return new ResponseEntity<Article>(article, HttpStatus.OK);		
		}	
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(path = "articles/{articleId}")
	public ResponseEntity<Article> updateArticle(@PathVariable("articleId") Long id, @RequestBody Article article) {				
		Article existingArticle = articleService.findById(id);
		if(Objects.isNull(existingArticle)) {
			return new ResponseEntity<Article>( HttpStatus.NOT_FOUND);
		}
		existingArticle.setEmail(article.getEmail());
		existingArticle.setDate(article.getDate());
		existingArticle.setTitle(article.getTitle());
		existingArticle.setContent(article.getContent());
		existingArticle.setPublished(article.getPublished());
		return new ResponseEntity<Article>(articleService.save(existingArticle), HttpStatus.OK);
	}

	@DeleteMapping(path = "articles/{articleId}")
	public ResponseEntity<Article> deleteArticleById(@PathVariable("articleId") Long id) {
		articleService.delete(id);
		return new ResponseEntity<Article>(HttpStatus.OK);
	}

	
	@GetMapping(path = "articles/search")
	public ResponseEntity<List<Article>> searchArticles(@RequestParam(value = "text") String text) {
		return new ResponseEntity<>(articleService.search(text), HttpStatus.OK);
	}

}
