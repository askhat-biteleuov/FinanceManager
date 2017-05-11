package com.fm.internal.daos;

import com.fm.internal.models.HashTag;
import com.fm.internal.models.HashTag_;
import com.fm.internal.models.User;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class HashTagDao extends GenericDao<HashTag>{
    public HashTagDao() {
        super(HashTag.class);
    }

    @Transactional
    public List<HashTag> getHashTagsByUser(User user){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<HashTag> query = builder.createQuery(HashTag.class);
        Root<HashTag> root = query.from(HashTag.class);
        Predicate userEquals = builder.equal(root.get(HashTag_.user), user);
        query.where(userEquals);
        return currentSession.createQuery(query).getResultList();
    }

    @Transactional
    public List<HashTag> getMatchingHashTags(User user, String hashTagPiece){
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<HashTag> query = builder.createQuery(HashTag.class);
        Root<HashTag> root = query.from(HashTag.class);
        Predicate userEquals = builder.equal(root.get(HashTag_.user), user);
        Predicate matchingPiece = builder.like(root.get(HashTag_.text), hashTagPiece+"%");
        query.where(userEquals, matchingPiece);
        return currentSession.createQuery(query).getResultList();
    }
}
