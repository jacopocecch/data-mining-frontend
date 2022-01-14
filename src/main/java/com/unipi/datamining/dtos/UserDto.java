package com.unipi.datamining.dtos;

import com.unipi.datamining.entities.User;

import java.io.Serializable;
import java.time.LocalDate;

public class UserDto implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String country;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private LocalDate registrationDate;
    private String image;
    private SurveyDto survey;
    private int cluster;

    public UserDto() {
    }

    public UserDto(String id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String country, String username, String phoneNumber, String email, String password, LocalDate registrationDate, String image, SurveyDto survey, int cluster) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.country = country;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.image = image;
        this.survey = survey;
        this.cluster = cluster;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.country = user.getCountry();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.registrationDate = user.getRegistrationDate();
        this.image = user.getImage();
        this.survey = new SurveyDto(user.getSurvey());
        this.cluster = user.getCluster();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SurveyDto getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDto survey) {
        this.survey = survey;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", image='" + image + '\'' +
                ", survey=" + survey +
                ", cluster=" + cluster +
                '}';
    }
}
