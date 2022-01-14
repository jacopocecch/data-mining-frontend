package com.unipi.datamining.entities;

import com.unipi.datamining.beans.UserBean;
import com.unipi.datamining.dtos.Neo4jUserDto;
import com.unipi.datamining.dtos.UserDto;

import java.time.LocalDate;

public class User {
    private String id;
    private String image;
    private String fullName;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private String country;
    private Survey survey;
    private int cluster;
    private FriendRequest friendRequest;

    public User(String id) {
        this.id = id;
    }

    public User(String firstName, String lastName, String username, String email, String phoneNumber, String password, String gender, LocalDate dateOfBirth, LocalDate registrationDate, String country, String url){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.image = url;
        this.registrationDate = registrationDate;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(Neo4jUserDto neo4jUser){
        this.id = neo4jUser.getId();
        this.fullName = neo4jUser.getFullName();
        this.firstName = neo4jUser.getFullName().split(" ")[0];
        this.lastName = neo4jUser.getFullName().split(" ")[1];
        this.image = neo4jUser.getImage();
        this.country = neo4jUser.getCountry();
        if (neo4jUser.getFriendRequest() != null) {
            this.friendRequest = new FriendRequest(neo4jUser.getFriendRequest());
        }
    }

    public User(UserDto userDto){
        this.id = userDto.getId();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.phoneNumber = userDto.getPhoneNumber();
        this.password = userDto.getPassword();
        this.gender = userDto.getGender();
        this.dateOfBirth = userDto.getDateOfBirth();
        this.country = userDto.getCountry();
        this.image = userDto.getImage();
        this.registrationDate = userDto.getRegistrationDate();
        this.fullName = this.firstName + " " + this.lastName;
        this.cluster = userDto.getCluster();
        if (userDto.getSurvey() == null) {
            this.survey = null;
        } else {
            this.survey = new Survey(userDto.getSurvey());
        }
    }

    public User(UserBean userBean){
        this.id = userBean.getId();
        this.firstName = userBean.getFirstName();
        this.lastName = userBean.getLastName();
        this.username = userBean.getUsername();
        this.email = userBean.getEmail();
        this.phoneNumber = userBean.getPhoneNumber();
        this.password = userBean.getPassword();
        this.gender = userBean.getGender();
        this.dateOfBirth = userBean.getDateOfBirth();
        this.country = userBean.getCountry();
        this.fullName = this.firstName + " " + this.lastName;
        this.cluster = userBean.getCluster();
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public void setImage(String url) {
        this.image = url;
    }

    public String getImage() {
        return image;
    }

    public double getAgreeableness(){
        double agr = 0;
        for(int i = 0; i < 10; ++i) {
            agr += survey.getAgr().get(i).getValue();
        }
        agr /= 10;
        return agr;
    }

    public double getOpenness(){
        double opn = 0;
        for(int i = 0; i < 10; ++i) {
            opn += survey.getOpn().get(i).getValue();
        }
        opn /= 10;
        return opn;
    }

    public double getExtroversion(){
        double ext = 0;
        for(int i = 0; i < 10; ++i) {
            ext += survey.getExt().get(i).getValue();
        }
        ext /= 10;
        return ext;
    }

    public double getTimeSpent(){
        double time = 0;
        for(int i = 0; i < 10; ++i) {
            time += survey.getOpn().get(i).getTime();
            time += survey.getCsn().get(i).getTime();
            time += survey.getAgr().get(i).getTime();
            time += survey.getExt().get(i).getTime();
            time += survey.getEst().get(i).getTime();
        }
        time /= 50;
        return time;
    }

    public double getNeurotic(){
        double est = 0;
        for(int i = 0; i < 10; ++i) {
            est += survey.getEst().get(i).getValue();
        }
        est /= 10;
        return est;
    }

    public double getConscientiousness(){
        double csn = 0;
        for(int i = 0; i < 10; ++i) {
            csn += survey.getCsn().get(i).getValue();
        }
        csn /= 10;
        return csn;
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }


}


