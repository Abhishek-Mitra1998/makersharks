package com.makersharks.identity.service;

import com.makersharks.identity.constants.ApiResponse;
import com.makersharks.identity.dao.LoginDao;
import com.makersharks.identity.dao.UserDao;
import com.makersharks.identity.entity.User;
import com.makersharks.identity.entity.UserDetails;
import com.makersharks.identity.exception.CustomException;
import com.makersharks.identity.repo.UserRepo;
import com.makersharks.identity.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class UserService {

    /**
     *     TODO : Create an admin api to fetch all users based on an Admin Role
     */

    private final UserRepo userRepo;
    private final UserUtil userUtil;

    public UserService(UserRepo userRepo, UserUtil userUtil) {
        this.userRepo = userRepo;
        this.userUtil = userUtil;
    }

    /**
     * Function to register a user onto the database and return a response
     * @param userDao
     * @return
     */
    public ApiResponse<String> register(UserDao userDao) throws CustomException {

        //Check if password is equal to confirmPassword field - Validation
        if(!userDao.getPassword().equals(userDao.getConfirmPassword())){
            throw new CustomException("E-001", "Password Does Not Match");
        }

        //Check if the username is not null or empty
        if(null != userDao.getUsername() && !userDao.getUsername().isEmpty()){
            Optional<User> checkUser = userRepo.findByUsername(userDao.getUsername());
            if(checkUser.isEmpty()){

                log.info("Registering User ==>{}",userDao.getUsername());

                User newUser = User.builder()
                        .id(UUID.randomUUID().toString())
                        .email(userDao.getEmail())
                        .username(userDao.getUsername())
                        .password(userUtil.generatePasswordHash(userDao.getPassword()))
                        .firstName(userDao.getFirstName())
                        .middleName(userDao.getMiddleName())
                        .lastName(userDao.getLastName())
                        .dateCreated(String.valueOf(Instant.now().toEpochMilli()))
                        .build();

                User response = userRepo.save(newUser);

                return ApiResponse.<String>builder()
                        .success(true)
                        .data("USER CREATED "+response.getId())
                        .build();

            }

            throw new CustomException("E-002", "User Already Exists");
        }

         throw new CustomException("E-003","Username Cannot Be Empty");


    }

    public ApiResponse<String> login(LoginDao loginDao) throws CustomException {

        Optional<User> checkUser = userRepo.findByUsername(loginDao.getUsername());
        if(checkUser.isPresent()){
            String hashed = userUtil.generatePasswordHash(loginDao.getPassword());
            User existing = checkUser.get();

            if(existing.getPassword().equals(hashed)){
                String token = userUtil.generateToken(existing);

                log.info("Logging In User ==>{}",loginDao.getUsername());

                return ApiResponse.<String>builder()
                        .success(true)
                        .data(token)
                        .build();
            }
            throw new CustomException("E-004","Incorrect Password");

        }

        throw new CustomException("E-005","User Not Found");

    }

    /**
     * Function to fetch user from database for a specific user
     * @param username
     * @return
     * @throws CustomException
     */
    public ApiResponse<UserDetails> fetchUser(String username) throws CustomException {
        Optional<User> existing = userRepo.findByUsername(username);
        if(existing.isPresent()){
            User thisUser = existing.get();

            log.info("Fetching Details for User ==>{}",username);

            return ApiResponse.<UserDetails>builder()
                    .success(true)
                    .data(
                            UserDetails.builder()
                                    .id(thisUser.getId())
                                    .email(thisUser.getEmail())
                                    .username(thisUser.getUsername())
                                    .firstName(thisUser.getFirstName())
                                    .middleName(thisUser.getMiddleName())
                                    .lastName(thisUser.getLastName())
                                    .dateCreated(thisUser.getDateCreated())
                                    .build()
                    ).build();


        }
        throw new CustomException("E-005","USER NOT FOUND");
    }


}
