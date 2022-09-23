package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

@GetMapping
    public String pub(){
    return "You are on main";
}
}
