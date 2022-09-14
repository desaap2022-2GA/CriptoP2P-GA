package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.service;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.User;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.exceptions.ResourceNotFoundException;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IUserRepo;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.el.LambdaExpression;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    public MappingJacksonValue register(User user) {
        return filterViewModel(userRepo.save(user));
    }

    public MappingJacksonValue modify(User user) {
        return filterViewModel(userRepo.save(user));
    }

    public MappingJacksonValue getAllUsers() {
        return filterViewModel(userRepo.findAll());
    }

    public void delete(int id) throws ResourceNotFoundException {
        try {
            userRepo.deleteById(id);
        } catch (Exception err) {
            throw new ResourceNotFoundException("user not found with id : " + id);
        }
    }

    public MappingJacksonValue getUser(Integer id) throws NoSuchElementException {
        User userFinded = userRepo.findById(id).get();
        if (userFinded != null) {
            return filterViewModel(userFinded);
        } else {
            throw new NoSuchElementException("user not found with id : " + id);
        }
    }

    /*
        public User getUser(Integer id) throws ResourceNotFoundException {
            return userRepo.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User not found with userId " + id)
            );
        }
    */
    private MappingJacksonValue filterViewModel(Object object) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name",
                "lastname", "email", "adress", "CVUMercadoPago", "adressWalletActiveCripto", "points", "numberOperations");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserDetails", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(object);
        mapping.setFilters(filters);
        return mapping;
    }
}