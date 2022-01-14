package com.unipi.datamining.entities;

import com.unipi.datamining.dtos.ClusterValuesDto;

public class ClusterValues {
    private double extraversion;
    private double agreeableness;
    private double conscientiousness;
    private double neuroticism;
    private double openness;
    private double timeSpent;

    public ClusterValues(ClusterValuesDto clusterValuesDto) {
        this.extraversion = clusterValuesDto.getExtraversion();
        this.agreeableness = clusterValuesDto.getAgreeableness();
        this.conscientiousness = clusterValuesDto.getConscientiousness();
        this.neuroticism = clusterValuesDto.getNeuroticism();
        this.openness = clusterValuesDto.getOpenness();
        this.timeSpent = clusterValuesDto.getTimeSpent();
    }

    public double getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(double extraversion){ this.extraversion=extraversion; }

    public double getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(double agreeableness){ this.agreeableness=agreeableness; }

    public double getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public double getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(double neuroticism) {
        this.neuroticism = neuroticism;
    }

    public double getOpenness() {
        return openness;
    }

    public void setOpenness(double openness) {
        this.openness = openness;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public String toString() {
        return "ClusterValues{" +
                "extraversion=" + extraversion +
                ", agreeableness=" + agreeableness +
                ", conscientiousness=" + conscientiousness +
                ", neuroticism=" + neuroticism +
                ", openness=" + openness +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
