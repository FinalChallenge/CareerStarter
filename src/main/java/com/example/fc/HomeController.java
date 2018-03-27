package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private JavaMailSender emailSender;

    ///////////EVERYONE CAN SEE//////////////////////////////////////////////////

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("classActiveSettings","current_page_item");
        return "Home";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("classActiveSettings1","current_page_item");
        return "Login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("classActiveSettings2","current_page_item");
        return "Registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "Registration";
        }
        if(userRepository.findByUsername(user.getUsername()) == null){
            user.addRole(roleRepository.findByRoleName("USER"));
            System.out.println("Username: "+user.getUsername());
            System.out.println("Password: "+user.getPassword());
            userRepository.save(user);
            System.out.println("UserRole: "+user.getRoles().toString());
        }
//        ArrayList<String> thisCriterias =  new ArrayList<String>();
//        String [] temp = (user.getCriteria().split("&"));
//        for(String i : temp){
//            thisCriterias.add(i.substring(9));
//        }
//        user.setCriterias(thisCriterias);
//        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/allprograms")
    public String allPrograms(Model model){
        model.addAttribute("programs", programRepository.findAll());
        model.addAttribute("classActiveSettings4","current_page_item");
        return "All";
    }

    ////////////////USER/////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
    //matched based on criteria
    @GetMapping("/myprograms")
    public String myPrograms(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        Set<Program> programs = new HashSet<>();
        ////check if user matches any program criteria
        for (Program program : programRepository.findAll()) {
            for (String criteria : program.getCriteria()) {
                if(user.getCriterias().contains(criteria)){
                    programs.add(program);
                    user.addProgram(program);
                }
            }
        }
        model.addAttribute("programs", programs);
        model.addAttribute("classActiveSettings5","current_page_item");
        return "MyPrograms";
    }

    public Set<User> meetRequirements(Set<User> users, Program thisProgram){
        Set<User> rUsers = new HashSet<>();
        for(User user : users){
            for (String criteria : thisProgram.getCriteria()) {
                if(user.getCriterias().contains(criteria)){
                    rUsers.add(user);
                }
            }
        }
        return rUsers;
    }
    public Set<User> notMeetRequirements(Set<User> users, Set<User> reqUsers){
        Set<User> nUsers = new HashSet<>();
        for(User user : users){
            if(! reqUsers.contains(user)){
                nUsers.add(user);
            }
        }
        return nUsers;
    }

/////////////////////////////////////////////////////////////////////////////////////////////

    //edit user info
    @GetMapping("/user/edit")
    public String editUser(Model model, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);
        ArrayList<String> criteria=user.getCriterias();
//        ArrayList<String> thisUserCriterias=new ArrayList<>();
        for(String cr:criteria){
            System.out.println(cr);
//            model.addAttribute("c", c);
//            thisUserCriterias.add(c);
        }
        model.addAttribute("c", criteria);
//        model.addAttribute("thisUserCriterias", thisUserCriterias);
        model.addAttribute("classActiveSettings6","current_page_item");
        return "Edit"; //Use Registration.html until Edit is fixed
    }
    @PostMapping("/user/edit")
    public String processUserChanges(@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "Edit";
        }
        userRepository.save(user);
        return "redirect:/";
    }
/////////////////////////////////////////////////////////////////////////////////////////////

    //view program details
    @GetMapping("/program/{id}")
    public String course(Model model, @PathVariable("id") long id) {
        Program program = programRepository.findOne(id);
        model.addAttribute("jobid", id);
        model.addAttribute("program", program);
        return "Program";
    }

    ///Look at the programs user applied to, status of those applications
    @Transactional
    @GetMapping("/user/programs")
    public String applied(Authentication auth, Model model) {
        User user = userRepository.findByUsername(auth.getName());
        Set<Program> programs = user.getPrograms();
        for(Program thisProgram : programs){
            if (thisProgram.getAttending().contains(user.getUsername())){
                thisProgram.setStatus("Attending");
            } else if (thisProgram.getAccepted().contains(user.getUsername())){
                thisProgram.setStatus("Accepted");
            } else if (thisProgram.getApplied().contains(user.getUsername())){
                thisProgram.setStatus("Applied");
            } else {
                thisProgram.setStatus("Error");
            }
        }
        model.addAttribute("programs", programs);
        model.addAttribute("classActiveSettings8","current_page_item");
        return "Applied";
    }

    //////////////////USER Apply or Attend program/////////////////////////////////////////////////////////////////////////////
    @Transactional
    @PostMapping("/apply/{id}")
    public String applyToProgram(@PathVariable("id") long id, Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Program program = programRepository.findOne(id);
        program.addUser(user);
        if(program.getApplied().contains(user.getUsername())){
            return "redirect:/user/programs";
        }
        program.addApplied(user.getUsername());
        programRepository.save(program);
        user.addProgram(program);
        userRepository.save(user);
        return "redirect:/user/programs";
    }

    @PostMapping("/attending/{id}")
    public String attendToProgram(@PathVariable("id") long id, Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        Program program = programRepository.findOne(id);
        if(program.getAttending().contains(user.getUsername())){
            return "redirect:/user/programs";
        }
        program.addAttending(user.getUsername());
        programRepository.save(program);
        return "redirect:/user/programs";
    }


    /////////////////ADMIN ONLY/////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

    /////////Accept student into program///////////////////////////////////////
    @PostMapping("/accept/{programid}/{id}")
    public String acceptToProgram(@PathVariable("id") long id, @PathVariable("programid") long programid, Model model) {
        User user = userRepository.findOne(id);
        Program program = programRepository.findOne(programid);
        if(program.getAccepted().contains(user.getUsername())){
            return "redirect:/";
        }
        program.addAccepted(user.getUsername());
        programRepository.save(program);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Admission");
        message.setText("Congratulation! " +
                "You have been admitted for " + program.getName() + " course");
        message.setTo(user.getEmail());
        message.setFrom("mcjbc2018@gmail.com");
        emailSender.send(message);

        return "redirect:/";
    }
    ////////////////////////////////////////////////////////////////////////////
    /////////View Applicants, Accepted, Attending///////////////////
    @RequestMapping("/applicants/{id}")
    public String viewApplicants(Model model, @PathVariable("id") long id){
        Program program = programRepository.findOne(id);
        Set<User> users = new HashSet<>();
        for(String name: program.getApplied()){
            users.add(userRepository.findByUsername(name));
        }


        Set<User> reqUsers = meetRequirements(users, program);
        model.addAttribute("users", users);
        model.addAttribute("reqUsers", reqUsers);
        model.addAttribute("nUsers", notMeetRequirements(users, reqUsers));
        model.addAttribute("program", program);
        return "Applicants";
    }

    @RequestMapping("/accepted/{id}")
    public String viewAccepted(Model model, @PathVariable("id") long id){
        Program program = programRepository.findOne(id);
        Set<User> users = new HashSet<>();
        for(String name: program.getAccepted()){
            User thisUser = userRepository.findByUsername(name);
            thisUser.getCriteria();
            users.add(thisUser);

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

    ////////////////////////////////////////////////////////////////////////////
    @GetMapping("/registeradmin")
    public String registerAdmin(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("classActiveSettings3","current_page_item");
        return "AdminRegistration";
    }

    @PostMapping("/registeradmin")
    public String registerAdmin(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "AdminRegistration";
        }
        user.addRole(roleRepository.findByRoleName("ADMIN"));
        userRepository.save(user);
        return "redirect:/logout";
    }

    @RequestMapping ("/programssummary")
    public String viewSummary(Model model){
        Set<Program> programs = new HashSet<>();
        for(Program program : programRepository.findAll()){
            program.setNumApplicants(program.getApplied().size());
            program.setNumAccepted(program.getAccepted().size());
            program.setNumAttending(program.getAttending().size());
            programs.add(program);
        }
        model.addAttribute("programs", programs);
        model.addAttribute("classActiveSettings7","current_page_item");
        return "ProgramsSummary";
    }



}
