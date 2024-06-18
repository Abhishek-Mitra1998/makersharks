package com.makersharks.identity.util;

import com.makersharks.identity.dao.LoginDao;
import com.makersharks.identity.dao.UserDao;
import com.makersharks.identity.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceHelper {

    public static UserDao getUserDaoMock(){

        return UserDao.builder()
                .username("MockUserName")
                .email("MockEmail@mock.com")
                .password("MockPass")
                .confirmPassword("MockPass")
                .firstName("MockFirst")
                .middleName("MockMiddle")
                .lastName("MockLast")
                .build();

    }

    public static Optional<User> getUserEntityMock() {
        return Optional.of(
             User.builder()
                     .id("1")
                     .username("MockUserName")
                     .email("MockEmail@mock.com")
                     .password("MockPass")
                     .firstName("MockFirst")
                     .middleName("MockMiddle")
                     .lastName("MockLast")
                     .dateCreated("12345")
                     .build()
        );
    }

    public static User getUserEntityResponseMock() {
        return User.builder()
                        .id("1")
                        .username("MockUserName")
                        .email("MockEmail@mock.com")
                        .password("MockPass")
                        .firstName("MockFirst")
                        .middleName("MockMiddle")
                        .lastName("MockLast")
                        .dateCreated("12345")
                        .build();
    }

    public static LoginDao getLoginDaoMock() {
        return LoginDao.builder()
                .username("MockUserName")
                .password("MockPass")
                .build();
    }


}
