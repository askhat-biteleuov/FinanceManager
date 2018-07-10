package com.fm.internal.daos;

import com.fm.internal.models.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OutcomeDao extends GenericDao<Outcome> {

    public OutcomeDao() {
        super(Outcome.class);
    }

    @Transactional
    public List<Outcome> getAccountOutcomes(Account account) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getUserOutcomes(User user){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Outcome_.date)));
        query.where(builder.equal(accountJoin.get(Account_.user), user));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getAccountOutcomesPageByDate(Account account, int offset, int limit, LocalDate start, LocalDate end) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Outcome> getUserOutcomesPageByDate(User user, int offset, int limit, LocalDate start, LocalDate end) {
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Outcome_.date)));
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Predicate equalDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(builder.and(equalUser,equalDate));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Outcome> getAccountOutcomesByDate(Account account, LocalDate start, LocalDate end) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public Long getUserOutcomesNumberByDate(User user, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        query.select(builder.count(outcomeRoot));
        Predicate equalDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        query.where(builder.and(equalDate,equalUser));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Transactional
    public Long getAccountOutcomeNumberByDate(Account account, LocalDate start, LocalDate end) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        query.select(criteriaBuilder.count(outcomeRoot));
        Predicate equalDate = criteriaBuilder.between(outcomeRoot.get(Outcome_.date), start, end);
        Predicate equalAccount = criteriaBuilder.equal(outcomeRoot.get(Outcome_.account), account);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Transactional
    public List<Outcome> getOutcomesPage(Account account, int offset, int limit) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = criteriaBuilder.createQuery(Outcome.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public void deleteOutcomeByType(OutcomeType outcomeType) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<Outcome> delete = criteriaBuilder.createCriteriaDelete(Outcome.class);
        Root<Outcome> root = delete.from(Outcome.class);
        delete.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), outcomeType));
        getEntityManager().createQuery(delete).executeUpdate();
    }

    @Transactional
    public void updateOutcomeByType(OutcomeType oldOutcomeType, OutcomeType newOutcomeType) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Outcome> update = criteriaBuilder.createCriteriaUpdate(Outcome.class);
        Root<Outcome> root = update.from(Outcome.class);
        update.set(Outcome_.outcomeType, newOutcomeType);
        update.where(criteriaBuilder.equal(root.get(Outcome_.outcomeType), oldOutcomeType));
        getEntityManager().createQuery(update).executeUpdate();
    }

    @Transactional
    public BigDecimal getSumOfAllOutcomesForMonthForUser(User user) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.sum(root.get(Outcome_.defaultAmount)));
        Expression month = criteriaBuilder.function("month", Integer.class, root.get(Outcome_.date));
        Expression year = criteriaBuilder.function("year", Integer.class, root.get(Outcome_.date));
        Predicate equalMonth = criteriaBuilder.equal(month, LocalDate.now().getMonthValue());
        Predicate equalYear = criteriaBuilder.equal(year, LocalDate.now().getYear());
        Join<Outcome, Account> accountJoin = root.join(Outcome_.account);
        query.orderBy(criteriaBuilder.asc(accountJoin.getParent().get(Outcome_.date)));
        Predicate equalUser = criteriaBuilder.equal(accountJoin.get(Account_.user), user);
        query.where(equalUser, equalMonth, equalYear);
        query.groupBy(accountJoin.get(Account_.user));
        try {
            return getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return BigDecimal.valueOf(0);
        }
    }

    @Transactional
    public List<Outcome> getOutcomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = builder.equal(outcomeRoot.get(Outcome_.account), account);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTag, equalAccount, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getOutcomesByAccountAndHashTags(Account account, List<HashTag> hashTags, LocalDate start, LocalDate end){
        List<String> hashTagsText = hashTags.stream().map(hashTag -> hashTag.getText()).collect(Collectors.toList());
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalAccount = builder.equal(outcomeRoot.get(Outcome_.account), account);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTags = hashTagJoin.get(HashTag_.text).in(hashTagsText);
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTags, equalAccount, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getAccountOutcomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate equalAccount = builder.equal(outcomeRoot.get(Outcome_.account), account);
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTag, equalAccount, betweenDate);
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Outcome> getOutcomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTag, equalUser, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getUserOutcomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        Join<Outcome, Account> accountJoin = outcomeRoot.join(Outcome_.account);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        query.where(matchingHashTag, betweenDate, equalUser);
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Outcome> getOutcomesByTypeAndHashTag(OutcomeType type, HashTag hashTag, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Predicate equalType = builder.equal(outcomeRoot.get(Outcome_.outcomeType), type);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTag, equalType, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Outcome> getTypeOutcomesPageByHashTagAndDate(OutcomeType type, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end){
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Outcome> query = builder.createQuery(Outcome.class);
        Root<Outcome> outcomeRoot = query.from(Outcome.class);
        Join<Outcome, HashTag> hashTagJoin = outcomeRoot.join(Outcome_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate equalType = builder.equal(outcomeRoot.get(Outcome_.outcomeType), type);
        Predicate betweenDate = builder.between(outcomeRoot.get(Outcome_.date), start, end);
        query.where(matchingHashTag, equalType, betweenDate);
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public BigDecimal getSumOfOutcomesInAccount(Account account) {
        
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Outcome> root = query.from(Outcome.class);
        query.select(criteriaBuilder.sum(root.get(Outcome_.amount)));
        query.where(criteriaBuilder.equal(root.get(Outcome_.account), account));
        try {
            return getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return BigDecimal.valueOf(0);
        }
    }
}