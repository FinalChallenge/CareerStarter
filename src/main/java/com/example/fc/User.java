package com.example.fc;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String englishLL;

    @Column
    private String workInUs;

    @Column
    private String understandOOP;

    @Column
    private String expOOP;

    @Column
    private String major;

    @Column
    private int gradYear;

    @Column
    private String salaryUnderCutoff;

    @Column
    private String hsGed;

    @Column
    private String interestIT;

    @Column
    private String comfComp;

    @Column
    private String unemployed;

    @Column
    private String underemployed;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id"))
    private Set<Program> programs;

    public User() {
        this.programs = new HashSet<>();
        this.roles = new HashSet<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkInUs() {
        return workInUs;
    }

    public void setWorkInUs(String workInUs) {
        this.workInUs = workInUs;
    }

    public String getUnderstandOOP() {
        return understandOOP;
    }

    public void setUnderstandOOP(String understandOOP) {
        this.understandOOP = understandOOP;
    }

    public String getExpOOP() {
        return expOOP;
    }

    public void setExpOOP(String expOOP) {
        this.expOOP = expOOP;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGradYear() {
        return gradYear;
    }

    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }

    public String getSalaryUnderCutoff() {
        return salaryUnderCutoff;
    }

    public void setSalaryUnderCutoff(String salaryUnderCutoff) {
        this.salaryUnderCutoff = salaryUnderCutoff;
    }

    public String getHsGed() {
        return hsGed;
    }

    public void setHsGed(String hsGed) {
        this.hsGed = hsGed;
    }

    public String getInterestIT() {
        return interestIT;
    }

    public void setInterestIT(String interestIT) {
        this.interestIT = interestIT;
    }

    public String getComfComp() {
        return comfComp;
    }

    public void setComfComp(String comfComp) {
        this.comfComp = comfComp;
    }

    public String getUnemployed() {
        return unemployed;
    }

    public void setUnemployed(String unemployed) {
        this.unemployed = unemployed;
    }

    public String getUnderemployed() {
        return underemployed;
    }

    public void setUnderemployed(String underemployed) {
        this.underemployed = underemployed;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }


    public Set<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }
    public void addProgram(Program program){
        this.programs.add(program);
    }

    public String getEnglishLL() {
        return englishLL;
    }

    public void setEnglishLL(String englishLL) {
        this.englishLL = englishLL;
    }
}







