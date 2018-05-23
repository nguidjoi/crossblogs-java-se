package com.crossover.techtrial.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.crossover.techtrial.model.Comment;

@RepositoryRestResource(exported = false)
public interface CommentRepository extends CrudRepository<Comment, Long> {

	@Override
	List<Comment> findAll();

	List<Comment> findByArticleIdOrderByDate(Long articleId);
}
