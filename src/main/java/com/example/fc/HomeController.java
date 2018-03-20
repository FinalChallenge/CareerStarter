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

    ///////////EVERYONE CAN SEE//////////////////////////////////////////////////

    @GetMapping("/")
    public String home(){
        return "Home";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
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

    @GetMapping("/allprograms")
    public String allPrograms(Model model){
        model.addAttribute("programs", programRepository.findAll());
        return "All";
    }

////////////////USER/////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
    //matched based on criteria
    @GetMapping("/myprograms")
    public String myPrograms(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Set<Program> programs = findPrograms(user);
        model.addAttribute("programs", programs);
        return "MyPrograms";
    }

    //edit user info
    @GetMapping("/user/edit")
    public String editUser(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "Registration"; //See if should use Edit.html instead
    }

    //view program details
    @GetMapping("/program/{id}")
    public String course(Model model, @PathVariable("id") long id) {
        Program program = programRepository.findOne(id);
        model.addAttribute("jobid", id);
        model.addAttribute("program", program);
        return "Program";
    }

    ///Look at the programs user applied to, status of those applications
    @GetMapping("/user/programs")
    public String applied(Authentication auth, Model model) {
        User user = userRepository.findByUsername(auth.getName());
        Set<Program> programs = user.getPrograms();
        for(Program thisProgram : programs){
            if (thisProgram.getAttending().contains(user.getName())){
                thisProgram.setStatus("Attending");
            } else if (thisProgram.getAccepted().contains(user.getName())){
                thisProgram.setStatus("Accepted");
            } else if (thisProgram.getApplied().contains(user.getName())){
                thisProgram.setStatus("Applied");
            } else {
                thisProgram.setStatus("Error");
            }
        }
        model.addAttribute("programs", user.getPrograms());
        return "Applied";
    }

    //////[possibly merge this with the above method and have the admin tools hidden to users]
//    @GetMapping("/admin/course/{id}")
//    public String adminTools(@PathVariable("id") long id, Model model) {
//        Program program = programRepository.findOne(id);
//        model.addAttribute("program", program);
//        return "AdminTools";
//    }
//////////////////USER Apply or Attend program/////////////////////////////////////////////////////////////////////////////
    @PostMapping("/apply/{id}")
    public String applyToProgram(@PathVariable("id") long id, Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Program program = programRepository.findOne(id);
        program.addUser(user);
        program.addApplied(user.getUsername());
        programRepository.save(program);
        user.addProgram(program);
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/attending/{id}")
    public String attendToProgram(@PathVariable("id") long id, Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Program program = programRepository.findOne(id);
        program.addAttending(user.getUsername());
        programRepository.save(program);
        return "redirect:/";
    }
/////////////////ADMIN ONLY/////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

    /////////Accept student into program///////////////////////////////////////
    @PostMapping("/accept/{programid}/{id}")
    public String acceptToProgram(@PathVariable("id") long id, @RequestParam("programid") long programid, Model model) {
        User user = userRepository.findOne(id);
        Program program = programRepository.findOne(programid);
        program.addAccepted(user.getUsername());
        programRepository.save(program);
        return "redirect:/";
    }
    ////////////////////////////////////////////////////////////////////////////
    /////////View Applicants, Accepted, Attending///////////////////
    @RequestMapping("/applicants/{id}")
    public String viewApplicants(Model model, @PathVariable("id") long id){
        Program program = programRepository.findOne(id);
        model.addAttribute("users", program.getUsers());
        return "Applicants";
    }

    @RequestMapping("/accepted/{id}")
    public String viewAccepted(Model model, @PathVariable("id") long id){
        Program program = programRepository.findOne(id);
        Set<User> users = new HashSet<>();
        for(String name: program.getAccepted()){
            users.add(userRepository.findByUsername(name));
        }
        model.addAttribute("users", users);
        return "Accepted";
    }

    @RequestMapping("/attending/{id}")
    public String viewAttending(Model model, @PathVariable("id") long id){
        Program program = programRepository.findOne(id);
        Set<User> users = new HashSet<>();
        for(String name: program.getAttending()){
            users.add(userRepository.findByUsername(name));
        }
        model.addAttribute("users", users);
        return "Attending";
    }

///////////////************//////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
    public Set<Program> findPrograms(User user){
        Set<Program> programs = new HashSet<>();
        ////check if user matches any program criteria
        for(Program program : programRepository.findAll()){
            if(compareArrays(user, program.getCriteria()) == true){
                programs.add(program);
                user.addProgram(program);
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
