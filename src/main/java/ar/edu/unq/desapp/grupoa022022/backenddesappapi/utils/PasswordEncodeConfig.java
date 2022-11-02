package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/******/
@Configuration
public class PasswordEncodeConfig {

    @Bean
    public PasswordEncoder passwordEncode(){
         return new BCryptPasswordEncoder();


    }

}
/******/