package com.kee.stlcodecafe.models.data;

import com.kee.stlcodecafe.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface CommentDao extends CrudRepository<Comment, Integer> {
}
