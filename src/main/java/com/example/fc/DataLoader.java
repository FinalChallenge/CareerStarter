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
    public void run(String... strings) throws Exception {

        System.out.println("Loading data . . .");
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRoleName("ADMIN");
        Role userRole = roleRepository.findByRoleName("USER");

////User One
//        User user1=new User("admin1@wdce.com", "Dave", "password", "Dave Wolf", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user1.addRole(adminRole);
//        userRepository.save(user1);
//
////User One
//        User user2=new User("admin2@wdce.com", "Afua", "password", "Afua Ankomah", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user2.addRole(adminRole);
//        userRepository.save(user2);
//
////User Three
//        User user3=new User("student1@wdce.com", "Saniya", "password", "Saniya Godil", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user3.addRole(userRole);
//        userRepository.save(user3);
//
////User Four
//        User user4=new User("student2@wdce.com", "Addis", "password", "Addisalem Wondie", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user4.addRole(userRole);
//        userRepository.save(user4);
//
////User Five
//        User user5=new User("student3@wdce.com", "John", "password", "John Doe", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user5.addRole(userRole);
//        userRepository.save(user5);
//
////User Six
//        User user6=new User("student4@wdce.com", "Sam", "password", "Sam Brown", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user5.addRole(userRole);
//        userRepository.save(user6);
//
////User Seven
//        User user7=new User("student5@wdce.com", "Anne", "password", "Anne Frank", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user7.addRole(userRole);
//        userRepository.save(user7);
//
////User Eight
//        User user8=new User("student5@wdce.com", "Emmy", "password", "Emmy Noether", "no", "yes", "yes", "yes", "yes", "no", "yes", "no", "yes", "no", "yes", "no" );
//        user8.addRole(userRole);
//        userRepository.save(user8);

//Programs
//Hiring in Tech
        Program techHire=new Program();
        programRepository.save(techHire);
        techHire.setName("Hiring in Tech");

        techHire.setDescription("TechHire is a new U.S. Department of Labor grant-funded training" +
                " program for those interested in careers in computers and Information Technology.");

        techHire.addCriteria("English Language Learner");
        techHire.addCriteria("Unemployed");
        techHire.addCriteria("Underemployed");
        techHire.addCriteria("Comfortable with Computers");
        techHire.addCriteria("Interest in IT");
        techHire.addCriteria("HS diploma/GED");
        techHire.addCriteria("Can work in US");
        programRepository.save(techHire);
//        techHire.addApplied(user2.getUsername());
//        techHire.addApplied(user3.getUsername());
//        techHire.addApplied(user4.getUsername());
//        techHire.addApplied(user5.getUsername());
//
//        techHire.addAccepted(user2.getUsername());
//        techHire.addAccepted(user3.getUsername());
//        techHire.addAccepted(user5.getUsername());
//
//        techHire.addAttending(user5.getName());
//
//        techHire.addUser(user2);
//        techHire.addUser(user3);
//        techHire.addUser(user4);
//        techHire.addUser(user5);
//        programRepository.save(techHire);

//Promising the Future
        Program promisingTheFuture=new Program();
        programRepository.save(promisingTheFuture);
        promisingTheFuture.setName("Promising the Future");
        promisingTheFuture.setDescription("The Java Web Developer Boot Camp is an 8 hour a day" +
                " 8-week, immersive software engineering program funded by the Department of Labor. This course " +
                "aims to increase the number of skilled software developers in this country.");
        promisingTheFuture.addCriteria("Understanding OOP");
        promisingTheFuture.addCriteria("Experience with OOP");
        promisingTheFuture.addCriteria("Major in CS/IS");
        promisingTheFuture.addCriteria("Recent Grad");
        promisingTheFuture.addCriteria("Salary Under Cutoff");
        promisingTheFuture.addCriteria("Can work in US");

        programRepository.save(promisingTheFuture);
//        promisingTheFuture.addApplied(user2.getUsername());
//        promisingTheFuture.addApplied(user3.getUsername());
//        promisingTheFuture.addApplied(user4.getUsername());
//
//        promisingTheFuture.addAccepted(user2.getUsername());
//        promisingTheFuture.addAccepted(user3.getUsername());
//        promisingTheFuture.addAccepted(user4.getUsername());
//
//        promisingTheFuture.addAttending(user2.getUsername());
//        promisingTheFuture.addAttending(user3.getUsername());
//
//        promisingTheFuture.addUser(user2);
//        promisingTheFuture.addUser(user3);
//        promisingTheFuture.addUser(user4);
//        programRepository.save(promisingTheFuture);

    }
}