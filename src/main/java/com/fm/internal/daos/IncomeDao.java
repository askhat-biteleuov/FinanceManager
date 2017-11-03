package com.fm.internal.daos;

import com.fm.internal.models.Account;
import com.fm.internal.models.Account_;
import com.fm.internal.models.HashTag;
import com.fm.internal.models.HashTag_;
import com.fm.internal.models.Income;
import com.fm.internal.models.Income_;
import com.fm.internal.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class IncomeDao extends GenericDao<Income> {

    public IncomeDao() {
        super(Income.class);
    }

    @Transactional
    public List<Income> getAccountIncomes(Account account) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getAccountIncomesByDate(Account account, LocalDate start, LocalDate end) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getIncomesPage(Account account, int offset, int limit) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Income> getUserIncomes(User user){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Income_.date)));
        query.where(builder.equal(accountJoin.get(Account_.user), user));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public Long getUserIncomesNumberByDate(User user, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.select(builder.count(incomeRoot));
        Predicate equalDate = builder.between(incomeRoot.get(Income_.date), start, end);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        query.where(builder.and(equalDate,equalUser));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Transactional
    public Long getAccountIncomeNumberByDate(Account account, LocalDate start, LocalDate end) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Income> incomeRoot = query.from(Income.class);
        query.select(criteriaBuilder.count(incomeRoot));
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).getSingleResult();
    }

    @Transactional
    public List<Income> getUserIncomesPageByDate(User user, int offset, int limit, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        query.orderBy(builder.asc(accountJoin.getParent().get(Income_.date)));
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Predicate equalDate = builder.between(incomeRoot.get(Income_.date), start, end);
        query.where(builder.and(equalUser,equalDate));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Income> getAccountIncomesPageByDate(Account account, int offset, int limit, LocalDate start, LocalDate end) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = criteriaBuilder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = criteriaBuilder.equal(incomeRoot.get(Income_.account), account);
        Predicate equalDate = criteriaBuilder.between(incomeRoot.get(Income_.date), start, end);
        query.where(criteriaBuilder.and(equalAccount, equalDate));
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public BigDecimal getSumOfIncomesInAccount(Account account) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Income> root = query.from(Income.class);
        query.select(criteriaBuilder.sum(root.get(Income_.amount)));
        query.where(criteriaBuilder.equal(root.get(Income_.account), account));
        try {
            return getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return BigDecimal.valueOf(0);
        }
    }

    @Transactional
    public List<Income> getIncomesByAccountAndHashTag(Account account, HashTag hashTag, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate equalAccount = builder.equal(incomeRoot.get(Income_.account), account);
        Join<Income, HashTag> hashTagJoin = incomeRoot.join(Income_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate betweenDate = builder.between(incomeRoot.get(Income_.date), start, end);
        query.where(matchingHashTag, equalAccount, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getAccountIncomesPageByHashTagAndDate(Account account, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, HashTag> hashTagJoin = incomeRoot.join(Income_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate equalAccount = builder.equal(incomeRoot.get(Income_.account), account);
        Predicate betweenDate = builder.between(incomeRoot.get(Income_.date), start, end);
        query.where(matchingHashTag, equalAccount, betweenDate);
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    public List<Income> getIncomesByUserAndHashTag(User user, HashTag hashTag, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Join<Income, HashTag> hashTagJoin = incomeRoot.join(Income_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        Predicate betweenDate = builder.between(incomeRoot.get(Income_.date), start, end);
        query.where(matchingHashTag, equalUser, betweenDate);
        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public List<Income> getUserIncomesPageByHashTagAndDate(User user, HashTag hashTag, int offset, int limit, LocalDate start, LocalDate end){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> incomeRoot = query.from(Income.class);
        Predicate betweenDate = builder.between(incomeRoot.get(Income_.date), start, end);
        Join<Income, Account> accountJoin = incomeRoot.join(Income_.account);
        Predicate equalUser = builder.equal(accountJoin.get(Account_.user), user);
        Join<Income, HashTag> hashTagJoin = incomeRoot.join(Income_.hashTags);
        Predicate matchingHashTag = builder.equal(hashTagJoin.get(HashTag_.text), hashTag.getText());
        query.where(matchingHashTag, betweenDate, equalUser);
        return getEntityManager().createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
