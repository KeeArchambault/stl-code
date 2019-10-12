package com.kee.stlcode.models.data;



import com.kee.stlcode.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostDao extends CrudRepository<Post, Integer> {
}