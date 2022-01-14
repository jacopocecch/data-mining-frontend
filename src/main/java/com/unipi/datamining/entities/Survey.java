package com.unipi.datamining.entities;

import com.unipi.datamining.dtos.QuestionDto;
import com.unipi.datamining.dtos.SurveyDto;
import java.util.ArrayList;

public class Survey {

    private ArrayList<Question> ext;
    private ArrayList<Question> est;
    private ArrayList<Question> agr;
    private ArrayList<Question> csn;
    private ArrayList<Question> opn;

    public Survey(ArrayList<Question> ext, ArrayList<Question> est, ArrayList<Question> agr, ArrayList<Question> csn, ArrayList<Question> opn) {
        this.ext = ext;
        this.est = est;
        this.agr = agr;
        this.csn = csn;
        this.opn = opn;
    }

    public Survey(SurveyDto survey){
        ext = new ArrayList<>();
        Question questionDto;
        for(QuestionDto question: survey.getExt()){
            questionDto = new Question(question);
            ext.add(questionDto);
        }
        est = new ArrayList<>();
        for(QuestionDto question: survey.getEst()){
            questionDto = new Question(question);
            est.add(questionDto);
        }
        agr = new ArrayList<>();
        for(QuestionDto question: survey.getAgr()){
            questionDto = new Question(question);
            agr.add(questionDto);
        }
        opn = new ArrayList<>();
        for(QuestionDto question: survey.getOpn()){
            questionDto = new Question(question);
            opn.add(questionDto);
        }
        csn = new ArrayList<>();
        for(QuestionDto question: survey.getCsn()){
            questionDto = new Question(question);
            csn.add(questionDto);
        }
    }

    public ArrayList<Question> getExt() {
        return ext;
    }

    public void setExt(ArrayList<Question> ext) {
        this.ext = ext;
    }

    public ArrayList<Question> getEst() {
        return est;
    }

    public void setEst(ArrayList<Question> est) {
        this.est = est;
    }

    public ArrayList<Question> getAgr() {
        return agr;
    }

    public void setAgr(ArrayList<Question> agr) {
        this.agr = agr;
    }

    public ArrayList<Question> getCsn() {
        return csn;
    }

    public void setCsn(ArrayList<Question> csn) {
        this.csn = csn;
    }

    public ArrayList<Question> getOpn() {
        return opn;
    }

    public void setOpn(ArrayList<Question> opn) {
        this.opn = opn;
    }
}
