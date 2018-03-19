package com.example.fc;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
//displays the navbar has home login and register
        return "Home";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }

    @GetMapping("/programs")
    public String allPrograms(Model model){
// displays brief desc.
        return "All";
    }


    @GetMapping("/register")
    public String register(Model model){

        //presents the user with the form to register , it will be tied to the AppUser class

        return "Registration";
    }

    @PostMapping("/register")
    public String register(Model model, Authentication auth, BindingResult result) {
        if (result.hasErrors()) {
            return "Registration";
        }
        //write the logic to save the registration information


    }





    @GetMapping("/user/edit/{id}")
    public String edituser() {

        return "Edit";

    }

    @GetMapping("/course/{id}")
    public String course() {

        return "Course";

    }

    @GetMapping("/admin/course/{id}")
    public String admintools(){


        return "AdminTools";
    }


   @GetMapping("/user/programs/{id}")
    public String applied(){


       return "Applied";
    }



}
