package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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

    //matched based on critera
    @GetMapping("/myprograms")
    public String myPrograms(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Set<Program> programs = findPrograms(user);
        model.addAttribute("programs", programs);
        return "MyPrograms";
    }

    @GetMapping("/allprograms")
    public String allPrograms(Model model){
        model.addAttribute("programs", programRepository.findAll());
        return "All";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
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

//edit user info
    @GetMapping("/user/edit")
    public String editUser(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "Register"; //See if should use Edit.html instead
    }

    //view program details
    @GetMapping("/program/{id}")
    public String course(Model model, @PathVariable("id") long id) {
        Program program = programRepository.findOne(id);
        model.addAttribute("program", program);
        return "Program";

    }

    @GetMapping("/admin/course/{id}")
    public String adminTools(@PathVariable("id") long id, Model model) {
        Program program = programRepository.findOne(id);
        model.addAttribute("program", program);
        return "AdminTools";
    }


    @GetMapping("/user/programs")
    public String applied(Authentication auth, Model model) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("programs", user.getPrograms());
        return "Applied";
    }

///////////////************///////////////////////////////
    public Set<Program> findPrograms(User user){
        Set<Program> programs = new HashSet<>();
        ////check if user matches any program criteria
        for(Program program : programRepository.findAll()){
            for(String crit : program.getCriteria()){
               // if()
            }
            ///add to programs
        }
        return programs;
    }
}
