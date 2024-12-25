package vttp5b.ssf.miniProject1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vttp5b.ssf.miniProject1.models.User;
import vttp5b.ssf.miniProject1.repositories.PlannerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {
    
    @Autowired
    private PlannerRepository plannerRepo;

    //if loginIsSucessful pull data from redis to put in sess
    public User populateLoginSess(User loginForm) {
        List<User> userList = plannerRepo.getUserList();

        Optional<User> foundUserOpt = userList.stream()
        .filter(existingUser -> existingUser.getUsername().equals(loginForm.getUsername()))
        .findFirst();

        User foundUser = foundUserOpt.get();
        
        return foundUser;
    }

    public Boolean isLoginFilledIn(User user, BindingResult bind) {
        
        if (user.getUsername()==null) {
            FieldError usernameErr = new FieldError("loginForm", "username", "please input your username");
            bind.addError(usernameErr);
            return false;
        } else if (user.getPassword()==null) {
            FieldError pwErr = new FieldError("loginForm", "password", "please input password");
            bind.addError(pwErr);
            return false;
        }
        return true;
    }
    
    public Boolean isLoginSuccessful(User user, BindingResult bind) {
        
        List<User> userList = plannerRepo.getUserList();

        Optional<User> foundUserOpt = userList.stream()
        .filter(existingUser -> existingUser.getUsername().equals(user.getUsername()))
        .findFirst();

        User foundUser = foundUserOpt.get();

        if (foundUserOpt.isEmpty()) {
            FieldError usernameErr = new FieldError("loginForm", "username", "account does not exist");
            bind.addError(usernameErr);
            return false;
        } 

        if (!foundUser.getUsername().equals(user.getUsername()) 
                    && !foundUser.getPassword().equals(user.getPassword())) {
            
            FieldError pwErr = new FieldError("loginForm", "password", "wrong password");
            bind.addError(pwErr);
            return false;

        } else if (!foundUser.getUsername().equals(user.getUsername()) 
                    || !foundUser.getPassword().equals(user.getPassword())) {
            FieldError pwErr = new FieldError("loginForm", "password", "wrong password");
            bind.addError(pwErr);
            return false;
        }
       return true;
    }

    public void saveUser(User user) {
        plannerRepo.saveUserToRedis(user);
    }

    public Boolean isCreateSuccessful(User user, BindingResult bind) {
        
        if (isUsernameExist(user)) {
            FieldError usernameErr = new FieldError("createForm", "username", "username already registered");
            bind.addError(usernameErr);
            return false;
        }

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            System.out.println("passwords are not the same");
            FieldError pwErr = new FieldError("createForm", "password", "password does not match");
            bind.addError(pwErr);

            FieldError rpwErr = new FieldError("createForm", "repeatPassword", "password does not match");
            bind.addError(rpwErr);
            return false;
        }
        return true;
    }

    public Boolean isUsernameExist(User user) {
        Boolean isExist = false;

        List<User> userList = plannerRepo.getUserList();
        Optional<User> foundUserOpt = userList.stream()
                        .filter(existingUser -> existingUser.getUsername().equals(user.getUsername()))
                        .findFirst();

        if (foundUserOpt.isPresent()) {
            isExist = true;
        }
        return isExist;
    }
}
