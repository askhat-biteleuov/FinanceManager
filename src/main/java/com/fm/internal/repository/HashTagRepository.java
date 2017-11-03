package com.fm.internal.repository;

import com.fm.internal.models.HashTag;
import com.fm.internal.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HashTagRepository extends CrudRepository<HashTag, Long> {

    HashTag findByUserAndText(User user, String text);

    List<HashTag> findAllByUser(User user);

    List<HashTag> findAllByUserAndTextContaining(User user, String pieceOfText);

}
