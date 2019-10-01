package com.kee.stlcodecafe.models.data;

import com.kee.stlcodecafe.models.Post;
import com.kee.stlcodecafe.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SessionDao extends CrudRepository<User, Integer> {
}

