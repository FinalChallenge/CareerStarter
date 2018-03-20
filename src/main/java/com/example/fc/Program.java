package com.example.fc;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String status;

    @Column
    private ArrayList<String> criteria = new ArrayList<String>();

    @Column
    private ArrayList<String> applied = new ArrayList<String>();

    @Column
    private ArrayList<String> accepted = new ArrayList<String>();

    @Column
    private ArrayList<String> attending = new ArrayList<String>();

    @ManyToMany(mappedBy = "programs", fetch = FetchType.LAZY)
    private Set<User> users;

    public Program() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCriteria() {
        return criteria;
    }

    public void setCriteria(ArrayList<String> criteria) {
        this.criteria = criteria;
    }

    public ArrayList<String> getApplied() {
        return applied;
    }

    public void setApplied(ArrayList<String> applied) {
        this.applied = applied;
    }

    public ArrayList<String> getAccepted() {
        return accepted;
    }

    public void setAccepted(ArrayList<String> accepted) {
        this.accepted = accepted;
    }

    public ArrayList<String> getAttending() {
        return attending;
    }

    public void setAttending(ArrayList<String> attending) {
        this.attending = attending;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void addCriteria(String criteria){
        this.criteria.add(criteria);
    }

    public void addAccepted(String name){
        this.accepted.add(name);
    }

    public void addAttending(String name){
        this.attending.add(name);
    }

    public void addApplied(String name){
        this.applied.add(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
