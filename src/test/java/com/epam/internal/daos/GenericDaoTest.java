package com.epam.internal.daos;

import com.epam.internal.models.User;
import com.epam.internal.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@ContextConfiguration("classpath:common-mvc-config.xml")
public class GenericDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GenericDao<User> genericDao;
    private User user;

    @BeforeClass
    public void setUp() throws Exception {
        user = new User("email1", "pass", new UserInfo("firstName", "lastName"));
    }

    @Test
    public void testCreate() throws Exception {
        genericDao.create(user);
        Assert.assertNotNull(user.getId());
        User reloaded = genericDao.findById(1);
        Assert.assertEquals(user.getId(), reloaded.getId());
        Assert.assertEquals(user.getEmail(), reloaded.getEmail());
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindById() throws Exception {
        User loadedUser = genericDao.findById(1);
        Assert.assertEquals(user.getEmail(), loadedUser.getEmail());
    }

    @Test(dependsOnMethods = "testCreate")
    public void testUpdate() throws Exception {
        UserInfo newUserInfo = new UserInfo("newName", "newLastName");
        user.setInfo(newUserInfo);
        genericDao.update(user);

        User reload = genericDao.findById(1);
        Assert.assertEquals(user.getInfo().getFirstName(), reload.getInfo().getFirstName());
        Assert.assertEquals(user.getInfo().getLastName(), reload.getInfo().getLastName());
    }

    @Test(dependsOnMethods = "testUpdate")
    public void testDelete() throws Exception {
        genericDao.delete(user);

        User reload = genericDao.findById(1);
        Assert.assertNull(reload);
    }

}