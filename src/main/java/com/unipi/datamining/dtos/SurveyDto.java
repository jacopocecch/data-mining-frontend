package com.unipi.datamining.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unipi.datamining.entities.Question;
import com.unipi.datamining.entities.Survey;

import java.io.Serializable;
import java.util.ArrayList;

public class SurveyDto implements Serializable {
    @JsonProperty(value="EXT")
    private ArrayList<QuestionDto> ext;
    @JsonProperty(value="EST")
    private ArrayList<QuestionDto> est;
    @JsonProperty(value="AGR")
    private ArrayList<QuestionDto> agr;
    @JsonProperty(value="CSN")
    private ArrayList<QuestionDto> csn;
    @JsonProperty(value="OPN")
    private ArrayList<QuestionDto> opn;

    public SurveyDto() {
    }

    public SurveyDto(ArrayList<QuestionDto> ext, ArrayList<QuestionDto> est, ArrayList<QuestionDto> agr, ArrayList<QuestionDto> csn, ArrayList<QuestionDto> opn) {
        this.ext = ext;
        this.est = est;
        this.agr = agr;
        this.csn = csn;
        this.opn = opn;
    }

    public SurveyDto(Survey survey){
        ext = new ArrayList<>();
        QuestionDto questionDto;
        for(Question question: survey.getExt()){
            questionDto = new QuestionDto(question);
            ext.add(questionDto);
        }
        est = new ArrayList<>();
        for(Question question: survey.getEst()){
            questionDto = new QuestionDto(question);
            est.add(questionDto);
        }
        agr = new ArrayList<>();
        for(Question question: survey.getAgr()){
            questionDto = new QuestionDto(question);
            agr.add(questionDto);
        }
        opn = new ArrayList<>();
        for(Question question: survey.getOpn()){
            questionDto = new QuestionDto(question);
            opn.add(questionDto);
        }
        csn = new ArrayList<>();
        for(Question question: survey.getCsn()){
            questionDto = new QuestionDto(question);
            csn.add(questionDto);
        }
    }

    public ArrayList<QuestionDto> getExt() {
        return ext;
    }

    public void setExt(ArrayList<QuestionDto> ext) {
        this.ext = ext;
    }

    public ArrayList<QuestionDto> getEst() {
        return est;
    }

    public void setEst(ArrayList<QuestionDto> est) {
        this.est = est;
    }

    public ArrayList<QuestionDto> getAgr() {
        return agr;
    }

    public void setAgr(ArrayList<QuestionDto> agr) {
        this.agr = agr;
    }

    public ArrayList<QuestionDto> getCsn() {
        return csn;
    }

    public void setCsn(ArrayList<QuestionDto> csn) {
        this.csn = csn;
    }

    public ArrayList<QuestionDto> getOpn() {
        return opn;
    }

    public void setOpn(ArrayList<QuestionDto> opn) {
        this.opn = opn;
    }

    @Override
    public String toString() {
        return "SurveyDto{" +
                "ext=" + ext +
                ", est=" + est +
                ", agr=" + agr +
                ", csn=" + csn +
                ", opn=" + opn +
                '}';
    }
}
