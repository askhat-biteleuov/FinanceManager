package com.fm.internal.services.implementation;

import com.fm.internal.models.HashTag;
import com.fm.internal.models.User;
import com.fm.internal.repository.HashTagRepository;
import com.fm.internal.services.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagServiceImplementation implements HashTagService {
    @Autowired
    private HashTagRepository hashTagRepository;

    @Override
    public void addHashTag(HashTag hashTag) {
        hashTagRepository.save(hashTag);
    }

    @Override
    public void updateHashTag(HashTag hashTag) {
        hashTagRepository.save(hashTag);
    }

    @Override
    public void deleteHashTag(HashTag hashTag) {
        hashTagRepository.delete(hashTag);
    }

    @Override
    public HashTag getHashTagById(long id) {
        return hashTagRepository.findOne(id);
    }

    @Override
    public HashTag getHashTagByUserAndText(User user, String hashTagText) {
        return hashTagRepository.findByUserAndText(user, hashTagText);
    }

    @Override
    public List<HashTag> getHashTagsByUser(User user) {
        return hashTagRepository.findAllByUser(user);
    }

    @Override
    public List<HashTag> getMatchingHashTags(User user, String hashTagPiece) {
        return hashTagRepository.findAllByUserAndTextContaining(user, hashTagPiece);
    }
}
