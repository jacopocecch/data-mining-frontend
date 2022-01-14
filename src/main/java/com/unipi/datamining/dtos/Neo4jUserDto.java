package com.unipi.datamining.dtos;

import java.io.Serializable;

public class Neo4jUserDto implements Serializable {

    private String id;
    private String fullName;
    private String country;
    private String image;
    private FriendRequestDto friendRequest;

    public Neo4jUserDto() {
    }

    public Neo4jUserDto(String id, String fullName, String image, String country, FriendRequestDto friendRequest){
        this.id = id;
        this.fullName = fullName;
        this.image = image;
        this.country = country;
        this.friendRequest = friendRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFriendRequest(FriendRequestDto friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestDto getFriendRequest() {
        return friendRequest;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
