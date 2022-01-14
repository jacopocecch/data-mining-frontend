package com.unipi.datamining;
import com.unipi.datamining.API.API;
import com.unipi.datamining.entities.*;
import com.unipi.datamining.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static com.unipi.datamining.API.API.*;
import static com.unipi.datamining.util.UtilGUI.*;

public class PersonalityClustering extends Application {
    public static Stage stage;
    public static User user;
    public static double timeStart;
    private static final int[] answers = new int[50];
    private static final double[] times = new double[50];

    public void start(Stage stage){
        ConfigurationParameters configurationParameters = new ConfigurationParameters("src/main/resources/configurationParameters.xml");
        API.setConfiguration(configurationParameters);
        Properties props = System.getProperties();
        props.setProperty("javafx.platform", "Desktop");
        System.out.println("Loading the login page");
        FXMLLoader loader = new FXMLLoader(PersonalityClustering.class.getResource("/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        PersonalityClustering.stage = stage;
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    // method called each time a new question is answered;
    public static void saveAnswer(int index, int answer, double time) throws Exception {
        if(index < 50){
            answers[index] = answer;
            times[index] = time;
        }
        // last question
        if(index == 49){
            // flipping questions negatively correlated
            for(int i = 0; i < 50; i++){
                if(isNegativelyCorrelated(i+1)) {
                    switch(answers[i]) {
                        case 1 -> answers[i] = 5;
                        case 2 -> answers[i] = 4;
                        case 4 -> answers[i] = 2;
                        case 5 -> answers[i] = 1;
                    }
                }
            }
            ArrayList<Question> ext = new ArrayList<>();
            for(int i = 0; i < 10; ++i){
                ext.add(new Question(getQuestions().get(i), answers[i], times[i]));
            }
            ArrayList<Question> est = new ArrayList<>();
            for(int i = 10; i < 20; ++i){
                est.add(new Question(getQuestions().get(i), answers[i], times[i]));
            }
            ArrayList<Question> agr = new ArrayList<>();
            for(int i = 20; i < 30; ++i){
                agr.add(new Question(getQuestions().get(i), answers[i], times[i]));
            }
            ArrayList<Question> csn = new ArrayList<>();
            for(int i = 30; i < 40; ++i){
                csn.add(new Question(getQuestions().get(i), answers[i], times[i]));
            }
            ArrayList<Question> opn = new ArrayList<>();
            for(int i = 40; i < 50; ++i){
                opn.add(new Question(getQuestions().get(i), answers[i], times[i]));
            }
            Survey survey = new Survey(ext, est, agr, csn, opn);
            user.setSurvey(survey);
            System.out.println("Sending answers to the server");
            user = API.registerUser(user);
        }
    }

    public static int getClusterID(){
        return user.getCluster();
    }

    public static void logoutUser(){
        user = null;
    }

    public static void updateUserInfo(User newUser){
        newUser.setSurvey(user.getSurvey());
        newUser.setCluster(user.getCluster());
        newUser.setId(user.getId());
        API.updateUserInfo(newUser);
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setFullName(newUser.getFullName());
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setCountry(newUser.getCountry());
        user.setGender(newUser.getGender());
        user.setDateOfBirth(newUser.getDateOfBirth());
        user.setImage(newUser.getImage());
    }

    public static double[] getClusterPersonalityValues(){
        ClusterValues clusterValues = API.getClusterValues(user);
        if(clusterValues != null)
            return new double[]{clusterValues.getOpenness(),clusterValues.getAgreeableness(),clusterValues.getNeuroticism(),clusterValues.getExtraversion(),clusterValues.getConscientiousness(),clusterValues.getTimeSpent()};
        else return null;
    }

    public static String getDeviation(){
        ClusterValues clusterValues= API.getClusterValues(user);
        if(clusterValues != null) {
            double diffC = Math.abs(user.getConscientiousness() - clusterValues.getConscientiousness());
            double diffN = Math.abs(user.getNeurotic() - clusterValues.getNeuroticism());
            double diffE = Math.abs(user.getExtroversion() - clusterValues.getExtraversion());
            double diffA = Math.abs(user.getAgreeableness() - clusterValues.getAgreeableness());
            double diffO = Math.abs(user.getOpenness() - clusterValues.getOpenness());
            double deviation = diffC + diffN + diffE + diffA + diffO;
            return String.format("%.02f", (deviation / 5) * 100);
        } else return "0";
    }

    public static String getDeviationNN(){
        User NN = getMostSimilarUser(user);
        if(NN == null)
            return null;
        double diffC = (user.getConscientiousness() - NN.getConscientiousness());
        double diffN = (user.getNeurotic() - NN.getNeurotic());
        double diffE = (user.getExtroversion() - NN.getExtroversion());
        double diffA = (user.getAgreeableness() - NN.getAgreeableness());
        double diffO = (user.getOpenness() - NN.getOpenness());
        double deviation = Math.sqrt(diffC*diffC + diffN*diffN + diffE*diffE + diffA*diffA + diffO*diffO);
        return String.format("%.02f",(deviation/5)*100);
    }

    public static List<User> getFriendshipRequests(){
        return API.getFriendRequests(user);
    }

    public static double[] getPersonalityValues(){
        return new double[]{user.getOpenness(),user.getAgreeableness(),user.getNeurotic(),user.getExtroversion(),user.getConscientiousness(),user.getTimeSpent()};
    }

    public static List<User> getSimilarUsers(){
        return API.getSimilarUsers(user);
    }

    public static List<User> getFriendships(){
        return API.getFriendships(user);
    }

    public static User getUserInfo(String id){
        return API.getUserInfo(id);
    }

    public static void loginUser(String loginEmail, String loginPassword) throws Exception {
        User userLogin = new User(loginEmail, loginPassword);
        user = API.loginUser(userLogin);
    }

    public static void addFriendRequest(String receiverId){
        API.addFriendRequest(user, receiverId);
    }

    public static void deleteFriendRequest(User receiver){
        API.updateFriendRequest(receiver, user, 0);
    }

    public static void acceptFriendRequest(User receiver){
        API.updateFriendRequest(user, receiver, 1);
    }

    public static List<User> getNearbyUsers(){
        return API.getNearbyUsers(user);
    }

    public static void checkForNewRecommended(){
        API.checkForUpdates(user);
    }
}
