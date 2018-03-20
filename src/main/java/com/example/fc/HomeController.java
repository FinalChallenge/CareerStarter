package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
        return "Home";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }

    //matched based on criteria
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
        model.addAttribute("jobid", id);
        model.addAttribute("program", program);
        return "Program";

    }
////[possibly merge this with the above method and have the admin tools hidden to users]
    @GetMapping("/admin/course/{id}")
    public String adminTools(@PathVariable("id") long id, Model model) {
        Program program = programRepository.findOne(id);
        model.addAttribute("program", program);
        return "AdminTools";
    }

///Look at the programs user applied to, status of those applications
    @GetMapping("/user/programs")
    public String applied(Authentication auth, Model model) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("programs", user.getPrograms());
        return "Applied";
    }


    @PostMapping("/apply/{programid}/{id}")
    public String applyToProgram(@PathVariable("id") long id, @RequestParam("programid") long programid, Model model){
        User user = userRepository.findOne(id);
        Program program = programRepository.findOne(programid);
        program.addUser(user);
        program.addApplied(user.getName());
        programRepository.save(program);
        return "redirect:/";
    }

    @PostMapping("/accept/{programid}/{id}")
    public String acceptToProgram(@PathVariable("id") long id, @RequestParam("programid") long programid, Model model){
        User user = userRepository.findOne(id);
        Program program = programRepository.findOne(programid);
        program.addAccepted(user.getName());
        programRepository.save(program);
        return "redirect:/";
    }

    @PostMapping("/attending/{programid}/{id}")
    public String attendToProgram(@PathVariable("id") long id, @RequestParam("programid") long programid, Model model){
        User user = userRepository.findOne(id);
        Program program = programRepository.findOne(programid);
        program.addAttending(user.getName());
        programRepository.save(program);
        return "redirect:/";
    }


///////////////************///////////////////////////////
    public Set<Program> findPrograms(User user){
        Set<Program> programs = new HashSet<>();
        ////check if user matches any program criteria
        for(Program program : programRepository.findAll()){
            if(compareArrays(user, program.getCriteria()) == true){
                programs.add(program);
            }
        }
        return programs;
    }

    public Boolean compareArrays(User user, ArrayList<String> programCriteria){
        Boolean flag = false;
        ArrayList<String> userCriteria = user.getCriteria();
        if(userCriteria.isEmpty() || programCriteria.isEmpty()){
            return false;
        }
        for(String criteria : programCriteria){
            if (userCriteria.contains(criteria)){
               flag = true;
            }
        }
        return flag;
    }

}
