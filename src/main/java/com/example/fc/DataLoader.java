package com.example.fc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception{

        System.out.println("Loading data . . .");
        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("STUDENT"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole=roleRepository.findByRole("ADMIN");
        Role userRole=roleRepository.findByRole("USER");
//        Role studentRole=roleRepository.findByRole("STUDENT");

        User user1=new User("admin@wdce.com","password","Dave","Wolf",true,"Dave");
        user1.setRoles(Arrays.asList(adminRole));
        userRepository.save(user1);

        User user2=new User("student1@wdce.com","password","Addis","Wondie",true,"Addis");
        user2.setRoles(Arrays.asList(userRole));
        userRepository.save(user2);

        User user3 = new User("student2@wdce.com", "password", "Saniya", "Godil", true, "Saniya");
        user3.setRoles(Arrays.asList(userRole));
        userRepository.save(user3);

        User user4 = new User("student3@wdce.com", "password", "Ashenafi", "Mulat", true, "Ashenafi");
        user4.setRoles(Arrays.asList(userRole));
        userRepository.save(user4);
    }
}
