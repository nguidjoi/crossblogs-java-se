package com.crossover.techtrial.service;

import java.util.List;
import com.crossover.techtrial.model.Comment;

/**
 * This interface provides all methods to access the functionality of an Article.
 * See CommentServiceImpl for implementation.
 * @author crossover
 *
 */
public interface CommentService {

	/**
	 * Returns all Comments related to article.
	 * @param articleId the corresponding article Id
	 * @return the comment related to article by Id
	 */
	List<Comment> findAll(Long articleId);

	/**
	 * Save the comment.
	 * @param comment the comment to save
	 * @return saved comment
	 */
	Comment save(Comment comment);
}
