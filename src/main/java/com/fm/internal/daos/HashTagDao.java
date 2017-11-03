package com.fm.internal.daos;

import com.fm.internal.models.HashTag;
import com.fm.internal.models.HashTag_;
import com.fm.internal.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class HashTagDao extends GenericDao<HashTag>{
    public HashTagDao() {
        super(HashTag.class);
    }

    @Transactional
    public HashTag getHashTagByUserAndText(User user, String hashTagText){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HashTag> query = builder.createQuery(HashTag.class);
        Root<HashTag> root = query.from(HashTag.class);
        Predicate userEquals = builder.equal(root.get(HashTag_.user), user);
        Predicate textEquals = builder.equal(root.get(HashTag_.text), hashTagText.toLowerCase());
        query.where(userEquals, textEquals);
        try {
            return getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<HashTag> getHashTagsByUser(User user){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HashTag> query = builder.createQuery(HashTag.class);
        Root<HashTag> root = query.from(HashTag.class);
        Predicate userEquals = builder.equal(root.get(HashTag_.user), user);
        query.where(userEquals);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<HashTag> getMatchingHashTags(User user, String hashTagPiece){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HashTag> query = builder.createQuery(HashTag.class);
        Root<HashTag> root = query.from(HashTag.class);
        Predicate userEquals = builder.equal(root.get(HashTag_.user), user);
        Predicate matchingPiece = builder.like(root.get(HashTag_.text), hashTagPiece+"%");
        query.where(userEquals, matchingPiece);
        return getEntityManager().createQuery(query).getResultList();
    }
}
