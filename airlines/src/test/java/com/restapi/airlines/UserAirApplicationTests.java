package com.restapi.airlines;

import com.restapi.airlines.controller.UserController;
import com.restapi.airlines.exception.AirlineNotFoundException;
import com.restapi.airlines.exception.UserNotFoundException;
import com.restapi.airlines.model.request.UserDetailsRequestModel;
import com.restapi.airlines.model.response.ErrorMessage;
import com.restapi.airlines.model.response.UserResponseModel;
import com.restapi.airlines.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserAirApplicationTests {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    UserService userService;

    @Autowired
    UserController userController;

    @Test
    public void createUser(){
        UserDetailsRequestModel user = new UserDetailsRequestModel();
        user.setIdUser("1");
        user.setFirstNameUser("Mutiara");
        user.setLastNameUser("Bela");
        user.setPhoneNumUser("082111711170");
        user.setUserNameUser("mutiarabela");
        user.setEmailUser("bela@gmail.com");
        user.setPasswordUser("12345678");

        log.info("-------- Parameter that we POST [User] --------");
        log.info("idUser="+user.getIdUser());
        log.info("firstNameUser="+user.getFirstNameUser());
        log.info("lastNameUser="+user.getLastNameUser());
        log.info("phoneNumUser="+user.getPhoneNumUser());
        log.info("userNameUser="+user.getUserNameUser());
        log.info("emailUser="+user.getEmailUser());
        log.info("passwordUser="+user.getPasswordUser());

        UserResponseModel create = userService.createUser(user);

        assertThat(create.getIdUser()).isEqualTo(user.getIdUser());
        assertThat(create.getFirstNameUser()).isEqualTo(user.getFirstNameUser());
        assertThat(create.getLastNameUser()).isEqualTo(user.getLastNameUser());
        assertThat(create.getPhoneNumUser()).isEqualTo(user.getPhoneNumUser());
        assertThat(create.getUserNameUser()).isEqualTo(user.getUserNameUser());
        assertThat(create.getEmailUser()).isEqualTo(user.getEmailUser());
        assertThat(create.getPasswordUser()).isEqualTo(user.getPasswordUser());

        log.info("----------- Posted Parameter [User] -----------");
        log.info(create.toString());



        ResponseEntity createUser = userController.createUser(user);
        assertThat(createUser.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getUser() {

        createUser();
        log.info("Create idUser=1");

        String idUser  = "1";
        log.info("GET idUser=1");

        UserResponseModel result = userService.getUser(idUser);
        assertThat(result.getFirstNameUser()).isEqualTo("Mutiara");
        assertThat(result.getLastNameUser()).isEqualTo("Bela");
        assertThat(result.getPhoneNumUser()).isEqualTo("082111711170");
        assertThat(result.getUserNameUser()).isEqualTo("mutiarabela");
        assertThat(result.getEmailUser()).isEqualTo("bela@gmail.com");
        assertThat(result.getPasswordUser()).isEqualTo("12345678");

        log.info("-------- Parameter that we GET --------");
        log.info(result.toString());

        ResponseEntity getUser = userController.getUser(idUser);
        assertThat(getUser.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getAllUser(){
        createUser();

        Collection getCreatedUsers = userService.getAllUser();
        assertThat(getCreatedUsers.size()).isEqualTo(3);

        log.info("------ Get All Users Success ------");

        ResponseEntity getAllUser = userController.getAllUser();
        assertThat(getAllUser.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void updatePhoneNumUser(){
        createUser();

        log.info("Update phoneNumUser=082111711170 for idUser=1");

        String idUser = "1";
        UserResponseModel userResponseModel = userService.getUser(idUser);
        String updatedIdUser = userResponseModel.getIdUser();
        String updatedPhoneNumUser  = userResponseModel.getPhoneNumUser();
        String updatedFirstNameUser  = userResponseModel.getFirstNameUser();
        String updatedLastNameUser  = userResponseModel.getLastNameUser();
        String updatedUserNameUser  = userResponseModel.getUserNameUser();
        String updatedEmailUser  = userResponseModel.getEmailUser();
        String updatedPasswordUser  = userResponseModel.getPasswordUser();

        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        UserResponseModel updatedUser = userService.updatePhoneNumUser(idUser, userDetailsRequestModel);

        updatedUser.setPhoneNumUser("081222722270");

        log.info("------------- Posted Parameter------------- ");
        log.info(updatedUser.toString());

        log.info("------------- Compared Parameter----------- ");
        log.info(" idUser="       + updatedIdUser       + " firstNameUser=" + updatedFirstNameUser +
                 " lastNameUser=" + updatedLastNameUser + " phoneNumUser="  + updatedPhoneNumUser +
                 " userNameUser=" + updatedUserNameUser + " emailUser="     + updatedEmailUser +
                 " passwordUser=" + updatedPasswordUser);

        assertThat(updatedUser.getIdUser()).isEqualTo(updatedIdUser);
        assertThat(updatedUser.getPhoneNumUser()).isNotEqualTo(updatedPhoneNumUser);

        ResponseEntity updatePhoneNumUser = userController.updatePhoneNumUser(idUser, userDetailsRequestModel);
        assertThat(updatePhoneNumUser.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void updatePhoneNumUserErr(){
        exceptionRule.expect(UserNotFoundException.class);
        exceptionRule.expectMessage("Update Failed, User Not Found");
        createUser();

        String idUser = "1";
        UserResponseModel userResponseModel = userService.getUser(idUser);
        String updatedIdUser = userResponseModel.getIdUser();
        String updatedPhoneNumUser  = userResponseModel.getPhoneNumUser();
        String updatedFirstNameUser  = userResponseModel.getFirstNameUser();
        String updatedLastNameUser  = userResponseModel.getLastNameUser();
        String updatedUserNameUser  = userResponseModel.getUserNameUser();
        String updatedEmailUser  = userResponseModel.getEmailUser();
        String updatedPasswordUser  = userResponseModel.getPasswordUser();

        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        UserResponseModel updatedUser = userService.updatePhoneNumUser(idUser, userDetailsRequestModel);

        String idUserErr = "10";
        updatedUser.setPhoneNumUser("081222722270");

        ResponseEntity updatePhoneNumUser = userController.updatePhoneNumUser(idUserErr, userDetailsRequestModel);
    }

    @Test
    public void deleteUser(){
        createUser();

        log.info("---------- Delete User for idUser=1 ----------");

        userService.deleteUser("1");
        assertThat(userService.getUser("1")).isNull();

        log.info("------------ Delete User Success -------------");
    }

    @Test
    public void deleteUserCont(){
        createUser();

        log.info("---------- Delete User for idUser=1 ----------");

        ResponseEntity deleteUser = userController.deleteUser("1");
        assertThat(deleteUser.getStatusCode().is2xxSuccessful());

        log.info("------------ Delete User Success -------------");
    }

    @Test(expected = UserNotFoundException.class)
    public  void userNotFoundException(){
        createUser();
        String idUser = "5";
        exceptionRule.equals(ErrorMessage.ErrorMessageBuilder.class);
        ResponseEntity create = userController.getUser(idUser);
        log.info(create.toString());
    }

    @Test
    public void deleteNotFound(){
        exceptionRule.expect(UserNotFoundException.class);
        exceptionRule.expectMessage("Delete Failed, User Not Found");

        String idUser = "9";

        ResponseEntity deleteUser = userController.deleteUser(idUser);
        assertThat(deleteUser.getStatusCode().is2xxSuccessful());
    }

}
