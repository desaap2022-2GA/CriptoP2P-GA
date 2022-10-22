package ar.edu.unq.desapp.grupoa022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoImpl {
    @Autowired
    private IUserRepo userRepo;

/*
    public List listUsers() throws ExceptionsUser {

        List userList = null;
        List<User> users = userRepo.findAll();
        for (User us:users){
            List user = null;
            user.add(us.getName());
            user.add(us.getLastname());
            user.add(us.getNumberOperations());
            user.add(us.getReputation());

            userList.add(user);

        }



        return userList;


    }

 */
}
