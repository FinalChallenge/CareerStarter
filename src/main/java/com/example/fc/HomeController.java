package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProgramRepository programRepository;

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
        model.addAttribute("hello");

        return "All";
    }


    @GetMapping("/register")
    public String register(Model model){

        //presents the user with the form to register , it will be tied to the AppUser class

        return "Registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "Registration";
        }
        user.addRole(roleRepository.findByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/";
    }


    @GetMapping("/user/edit")
    public String editUser(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "Register"; //See if should use Edit.html instead
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
