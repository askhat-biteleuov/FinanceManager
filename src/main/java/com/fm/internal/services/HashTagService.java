package com.fm.internal.services;

import com.fm.internal.models.HashTag;
import com.fm.internal.models.User;

import java.util.List;

public interface HashTagService {
    void addHashTag(HashTag hashTag);

    void updateHashTag(HashTag hashTag);

    void deleteHashTag(HashTag hashTag);

    HashTag getHashTagById(long id);

    List<HashTag> getHashTagsByUser(User user);

    List<HashTag> getMatchingHashTags(User user, String hashTagPiece);
}
