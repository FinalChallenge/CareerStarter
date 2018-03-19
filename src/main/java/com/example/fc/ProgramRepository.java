package com.example.fc;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.Set;

public interface ProgramRepository extends CrudRepository<Program, Long> {

    Program findByName(String name);
}
