package com.unipi.datamining.entities;

import com.unipi.datamining.dtos.QuestionDto;

public class Question {

    private String name;
    private int value;
    private double time;

    public Question(String name, int value, double time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public Question(QuestionDto questionDto) {
        this.name = questionDto.getName();
        this.value = questionDto.getValue();
        this.time = questionDto.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){ this.value = value; }

    public double getTime() {
        return time;
    }

    public void setTime(double time){ this.time = time;}
}
