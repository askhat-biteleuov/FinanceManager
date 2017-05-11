package com.fm.internal.services.implementation;

import com.fm.internal.daos.HashTagDao;
import com.fm.internal.models.HashTag;
import com.fm.internal.models.User;
import com.fm.internal.services.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HashTagServiceImplementation implements HashTagService {
    @Autowired
    private HashTagDao hashTagDao;

    @Override
    public void addHashTag(HashTag hashTag) {
        hashTagDao.add(hashTag);
    }

    @Override
    public void updateHashTag(HashTag hashTag) {
        hashTagDao.update(hashTag);
    }

    @Override
    public void deleteHashTag(HashTag hashTag) {
        hashTagDao.delete(hashTag);
    }

    @Override
    public HashTag getHashTagById(long id) {
        return hashTagDao.getById(id);
    }

    @Override
    public HashTag getHashTagByUserAndText(User user, String hashTagText) {
        return hashTagDao.getHashTagByUserAndText(user, hashTagText);
    }

    @Override
    public List<HashTag> getHashTagsByUser(User user) {
        return hashTagDao.getHashTagsByUser(user);
    }

    @Override
    public List<HashTag> getMatchingHashTags(User user, String hashTagPiece) {
        return hashTagDao.getMatchingHashTags(user, hashTagPiece);
    }
}
