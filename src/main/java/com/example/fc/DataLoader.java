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

//User One
        ArrayList<String> criterias =  new ArrayList<String>();
        criterias.add("workInUs");
        User user1=new User("admin1@wdce.com", "Dave", "password", "Dave Wolf");
        user1.addRole(adminRole);
        user1.setCriterias(criterias);
        userRepository.save(user1);

//User Two
        User user2=new User("admin2@wdce.com", "Afua", "password", "Afua Ankomah");
        user2.addRole(adminRole);
        userRepository.save(user2);

//User Three
        ArrayList<String> criterias1 =  new ArrayList<String>();
        criterias1.add("workInUs");
        criterias1.add("unemployed");
        criterias1.add("underemployed");
        criterias1.add("comfComp");
        criterias1.add("interestIT");
        criterias1.add("hsGed");
        User user3=new User("student1@wdce.com", "Saniya", "password", "Saniya Godil");
        user3.addRole(userRole);
        user3.setCriterias(criterias1);
        userRepository.save(user3);

//User Four
        User user4=new User("student2@wdce.com", "Addis", "password", "Addisalem Wondie");
        user4.addRole(userRole);
        user4.setCriterias(criterias1);
        userRepository.save(user4);

//User Five
        ArrayList<String> criterias2 =  new ArrayList<String>();
        criterias2.add("understandOOP");
        criterias2.add("expOOP");
        criterias2.add("correctMajor");
        criterias2.add("recentGrad");
        criterias2.add("salaryUnderCutoff");
        criterias2.add("workInUs");
        User user5=new User("student3@wdce.com", "John", "password", "John Doe");
        user5.addRole(userRole);
        user5.setCriterias(criterias2);
        userRepository.save(user5);

//User Six
        User user6=new User("student4@wdce.com", "Sam", "password", "Sam Brown");
        user6.addRole(userRole);
        user6.setCriterias(criterias2);
        userRepository.save(user6);

//User Seven
        ArrayList<String> criterias3 =  new ArrayList<String>();
        criterias3.add("workInUs");
        criterias3.add("unemployed");
        criterias3.add("underemployed");
        criterias3.add("understandOOP");
        criterias3.add("expOOP");
        criterias3.add("correctMajor");
        User user7=new User("student5@wdce.com", "Anne", "password", "Anne Frank");
        user7.addRole(userRole);
        user7.setCriterias(criterias3);
        userRepository.save(user7);

//User Eight
        User user8=new User("student5@wdce.com", "Emmy", "password", "Emmy Noether");
        user8.addRole(userRole);
        user8.setCriterias(criterias3);
        userRepository.save(user8);

//Programs
//Hiring in Tech
        Program techHire=new Program();
        programRepository.save(techHire);
        techHire.setName("Hiring in Tech");

        techHire.setDescription("TechHire is a new U.S. Department of Labor grant-funded training" +
                " program for those interested in careers in computers and Information Technology.");

        techHire.addCriteria("englishLL");
        techHire.addCriteria("unemployed");
        techHire.addCriteria("underemployed");
        techHire.addCriteria("comfComp");
        techHire.addCriteria("interestIT");
        techHire.addCriteria("hsGed");
        techHire.addCriteria("workInUs");
        ArrayList<String> techHireList = new ArrayList<String>();
        techHireList.add("English Language Learner");
        techHireList.add("Unemployed");
        techHireList.add("Underemployed");
        techHireList.add("Comfortable using computers");
        techHireList.add("Strong Interest in IT");
        techHireList.add("Received High School Diploma or GED");
        techHireList.add("Able to work legally in the United States");
        techHire.setCriterias(techHireList);
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
        promisingTheFuture.addCriteria("understandOOP");
        promisingTheFuture.addCriteria("expOOP");
        promisingTheFuture.addCriteria("correctMajor");
        promisingTheFuture.addCriteria("recentGrad");
        promisingTheFuture.addCriteria("salaryUnderCutoff");
        promisingTheFuture.addCriteria("workInUs");

        ArrayList<String> promisingTheFutureList = new ArrayList<String>();
        promisingTheFutureList.add("Basic understanding of Object Oriented Programming");
        promisingTheFutureList.add("Experience with Object Oriented Programming");
        promisingTheFutureList.add("If employed, make less than $42,000 a year");
        promisingTheFutureList.add("Graduated within the last 6 years");
        promisingTheFutureList.add("Majored in Computer Science or Information Systems");
        promisingTheFutureList.add("Able to work legally in the United States");
        promisingTheFuture.setCriterias(promisingTheFutureList);
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