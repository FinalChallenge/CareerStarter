package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProgramRepository programRepository;

    @Override
    public void run(String... strings) throws Exception{

        System.out.println("Loading data . . .");
        roleRepository.save(new Role("USER"));
// roleRepository.save(new Role("STUDENT"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole=roleRepository.findByRoleName("ADMIN");
        Role userRole=roleRepository.findByRoleName("USER");
// Role studentRole=roleRepository.findByRole("STUDENT");
        Set<Role> role=new HashSet<>();
        role.add(userRole);

        Set<Role> admin=new HashSet<>();
        admin.add(adminRole);

//User One
        User user1=new User();
        user1.setEmail("admin@wdce.com");
        user1.setName("Dave Wolf");
        user1.setUsername("Dave");
        user1.setPassword("password");
        user1.setRoles(admin);
//user1.setRoles(Arrays.asList(adminRole));
        userRepository.save(user1);

//User Two
        User user2=new User();
        user2.setEmail("student1@wdce.com");
        user2.setName("Saniya Godil");
        user2.setUsername("Saniya");
        user2.setPassword("password");
        user2.setRoles(role);
        userRepository.save(user2);

//User Three
        User user3=new User();
        user3.setEmail("student2@wdce.com");
        user3.setName("Saniya Godil");
        user3.setUsername("Saniya");
        user3.setPassword("password");
        user3.setRoles(role);
        userRepository.save(user3);

//User Four
        User user4=new User();
        user4.setEmail("student3@wdce.com");
        user4.setName("Addisalem Wondie");
        user4.setUsername("Addis");
        user4.setPassword("password");
        user4.setRoles(role);
        userRepository.save(user4);

//User Four
        User user5=new User();
        user5.setEmail("student4@wdce.com");
        user5.setName("Bob");
        user5.setUsername("Bob");
        user5.setPassword("password");
        user5.setRoles(role);
        userRepository.save(user5);
//Programs
//Hiring in Tech
        Program techHire=new Program();

        techHire.setName("Hiring in Tech");

        techHire.setDescription("TechHire is a new U.S. Department of Labor grant-funded training" +
                " program for those interested in careers in computers and Information Technology (IT). " +
                "Based on your skills, experience, and English level, you may be able to enter one or more" +
                " tracks that will help you further your education and training and find a job.");

        techHire.addCriteria("English Language Learner");
        techHire.addCriteria("Unemployed");
        techHire.addCriteria("Underemployed");
        techHire.addCriteria("Comfortable with Computers");
        techHire.addCriteria("Strong Interest in IT");
        techHire.addCriteria("Have HS diploma or GED");
        techHire.addCriteria("Legally authorized to work in US");

        techHire.addApplied(user2.getUsername());
        techHire.addApplied(user3.getUsername());
        techHire.addApplied(user4.getUsername());
        techHire.addApplied(user5.getUsername());

        techHire.addAccepted(user2.getUsername());
        techHire.addAccepted(user3.getUsername());
        techHire.addAccepted(user5.getUsername());

        techHire.addAttending(user5.getName());

        techHire.addUser(user2);
        techHire.addUser(user3);
        techHire.addUser(user4);
        techHire.addUser(user5);
        programRepository.save(techHire);

//Promising the Future
        Program promisingTheFuture=new Program();
        promisingTheFuture.setName("Promising the Future");
        promisingTheFuture.setDescription("The Java Web Developer Boot Camp is an 8 hour a day (9 am - 5 pm, M-F)" +
                " 8-week, immersive software engineering program funded by the Department of Labor. This course " +
                "aims to increase the number of skilled software developers in this country. We do this by " +
                "accepting qualified candidates who cannot afford the cost of a coding boot camp. If accepted into" +
                " the program all fees will be covered by the program.");
        promisingTheFuture.addCriteria("Basic understanding of Object Oriented Programming");
        promisingTheFuture.addCriteria("Previous experience with Object Oriented Programming");
        promisingTheFuture.addCriteria("Major in Computer Science or Information Systems");
        promisingTheFuture.addCriteria("Graduated within last 6 years");
        promisingTheFuture.addCriteria("Salary Under 42k Cutoff");
        promisingTheFuture.addCriteria("Legally authorized to work in US");

        promisingTheFuture.addApplied(user2.getUsername());
        promisingTheFuture.addApplied(user3.getUsername());
        promisingTheFuture.addApplied(user4.getUsername());

        promisingTheFuture.addAccepted(user2.getUsername());
        promisingTheFuture.addAccepted(user3.getUsername());
        promisingTheFuture.addAccepted(user4.getUsername());

        promisingTheFuture.addAttending(user2.getUsername());
        promisingTheFuture.addAttending(user3.getUsername());

        promisingTheFuture.addUser(user2);
        promisingTheFuture.addUser(user3);
        promisingTheFuture.addUser(user4);
        programRepository.save(promisingTheFuture);

    }
}