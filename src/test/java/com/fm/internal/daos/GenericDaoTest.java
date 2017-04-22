package com.fm.internal.daos;

import com.fm.internal.models.User;
import com.fm.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:spring-utils.xml")
public class GenericDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GenericDao<User> genericDao;
    private User user;

    @BeforeMethod
    public void setUp() throws Exception {
        user = new User("email1", "pass", new UserInfo("firstName", "lastName"));
        genericDao.create(user);
    }

    @AfterMethod
    public void cleanUp() throws Exception {
        User reload = genericDao.findById(user.getId());
        if (reload != null) {
            genericDao.delete(user);
        }
    }

    @Test
    public void testCreate() throws Exception {
        Assert.assertNotNull(user.getId());
        User reloaded = genericDao.findById(user.getId());
        Assert.assertEquals(user.getId(), reloaded.getId());
        Assert.assertEquals(user.getEmail(), reloaded.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {
        UserInfo newUserInfo = new UserInfo("newName", "newLastName");
        user.setInfo(newUserInfo);
        genericDao.update(user);
        Assert.assertNotNull(user.getId());
        User reloaded = genericDao.findById(user.getId());
        Assert.assertNotNull(reloaded);
        Assert.assertEquals(user.getInfo().getFirstName(), reloaded.getInfo().getFirstName());
        Assert.assertEquals(user.getInfo().getLastName(), reloaded.getInfo().getLastName());
    }

    @Test
    public void testDelete() throws Exception {
        genericDao.delete(user);
        User reload = genericDao.findById(user.getId());
        Assert.assertNull(reload);
    }

}