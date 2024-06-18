package com.makersharks.identity.service;

import com.makersharks.identity.constants.ApiResponse;
import com.makersharks.identity.dao.LoginDao;
import com.makersharks.identity.dao.UserDao;
import com.makersharks.identity.entity.UserDetails;
import com.makersharks.identity.exception.CustomException;
import com.makersharks.identity.repo.UserRepo;
import com.makersharks.identity.util.UserServiceHelper;
import com.makersharks.identity.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserServiceHelper userServiceHelper;

    @Test
    public void testRegister() throws CustomException {

        UserDao request = UserServiceHelper.getUserDaoMock();

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = new UserUtil();

        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());

        UserService userService = new UserService(userRepo,userUtil);

        ApiResponse<String> response = userService.register(request);

        Assertions.assertEquals("USER CREATED "+UserServiceHelper.getUserEntityResponseMock().getId(),response.getData());
    }

    @Test
    public void testRegisterWithException() throws CustomException {

        UserDao request = UserServiceHelper.getUserDaoMock();

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = new UserUtil();

        when(userRepo.findByUsername(anyString())).thenReturn(UserServiceHelper.getUserEntityMock());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());

        UserService userService = new UserService(userRepo,userUtil);

        Assertions.assertThrows(CustomException.class,()->userService.register(request));
    }

    @Test
    public void testRegisterWithMismatchedPassword() throws CustomException {

        UserDao request = UserServiceHelper.getUserDaoMock();
        request.setConfirmPassword("12345");

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = new UserUtil();

        when(userRepo.findByUsername(anyString())).thenReturn(UserServiceHelper.getUserEntityMock());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());

        UserService userService = new UserService(userRepo,userUtil);

        Assertions.assertThrows(CustomException.class,()->userService.register(request));
    }

    @Test
    public void testRegisterWithBlankUserName() throws CustomException {

        UserDao request = UserServiceHelper.getUserDaoMock();
        request.setUsername(null);

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = new UserUtil();

        when(userRepo.findByUsername(anyString())).thenReturn(UserServiceHelper.getUserEntityMock());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());

        UserService userService = new UserService(userRepo,userUtil);

        Assertions.assertThrows(CustomException.class,()->userService.register(request));
    }

    @Test
    public void testLogin() throws CustomException {

        LoginDao loginDao = UserServiceHelper.getLoginDaoMock();

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = Mockito.mock(UserUtil.class);

        when(userRepo.findByUsername(anyString())).thenReturn(UserServiceHelper.getUserEntityMock());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());
        when(userUtil.generatePasswordHash(anyString())).thenReturn(loginDao.getPassword());
        when(userUtil.generateToken(any())).thenReturn("MockToken");

        UserService userService = new UserService(userRepo,userUtil);

        ApiResponse<String> response = userService.login(loginDao);

        Assertions.assertEquals("MockToken",response.getData());
    }

    @Test
    public void testLoginUserNotFound() throws CustomException {

        LoginDao loginDao = UserServiceHelper.getLoginDaoMock();

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = Mockito.mock(UserUtil.class);

        when(userRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(any())).thenReturn(UserServiceHelper.getUserEntityResponseMock());
        when(userUtil.generatePasswordHash(anyString())).thenReturn(loginDao.getPassword());
        when(userUtil.generateToken(any())).thenReturn("MockToken");

        UserService userService = new UserService(userRepo,userUtil);

        Assertions.assertThrows(CustomException.class,()->userService.login(loginDao));
    }

    @Test
    public void testFetchUser() throws CustomException {

        UserRepo userRepo = Mockito.mock(UserRepo.class);
        UserUtil userUtil = Mockito.mock(UserUtil.class);

        when(userRepo.findByUsername(anyString())).thenReturn(UserServiceHelper.getUserEntityMock());
        UserService userService = new UserService(userRepo,userUtil);

        ApiResponse<UserDetails> response = userService.fetchUser("MockUserName");

        Assertions.assertEquals(UserServiceHelper.getUserEntityResponseMock().getId(),response.getData().getId());

    }

}
