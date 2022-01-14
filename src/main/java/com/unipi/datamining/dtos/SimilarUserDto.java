package com.unipi.datamining.dtos;

import java.io.Serializable;

public class SimilarUserDto implements Serializable {

    private Long id;
    private Neo4jUserDto user;
    private double weight;

    public SimilarUserDto() {
    }

    public SimilarUserDto(Long id, Neo4jUserDto userDto, double weight) {
        this.id = id;
        this.user = userDto;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Neo4jUserDto getUser() {
        return user;
    }

    public void setUser(Neo4jUserDto user) {
        this.user = user;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
