package com.crossover.techtrial.service;

import java.util.List;

import com.crossover.techtrial.model.Article;


/**
 * This interface provides all methods to access the functionality of an Article.
 * see ArticleServiceImpl for implementation.
 * @author crossover
 *
 */
public interface ArticleService {
	
	/**
	 * Save an article.
	 * @param article article to save
	 * @return saved article
	 */
	Article save(Article article);

	/**
	 * Find the specific article by his Id.
	 * @param id the Id of article to find
	 * @return finded article 
	 */
	Article findById(Long id);

	/**
	 * Delete a particular article with id.
	 * @param id the id of article to delete
	 */
	void delete(Long id);

	/**
	 * Search Articles Table matching the title and return result.
	 * @param title the title of article
	 * @return list of article found
	 */
	List<Article> search(String title);

}
