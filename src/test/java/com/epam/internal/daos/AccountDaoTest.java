package com.epam.internal.daos;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:common-mvc-config.xml")
@Transactional(transactionManager = "transactionManager")
public class AccountDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testCreate() {

    }

    @Test
    public void testFindAllUserAccounts() throws Exception {
    }

}