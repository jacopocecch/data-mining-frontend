package com.unipi.datamining.gui;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.unipi.datamining.beans.UserBean;
import com.unipi.datamining.PersonalityClustering;
import com.unipi.datamining.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import static com.unipi.datamining.gui.ValidationForm.*;
import static com.unipi.datamining.PersonalityClustering.*;
import static com.unipi.datamining.util.UtilGUI.*;

public class FXMLHomeDocumentController implements Initializable{
    private boolean initialized;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane layoutPane;
    @FXML
    private AnchorPane friendsPane;
    @FXML
    private TableView<UserBean> tableView;
    @FXML
    private TableColumn<UserBean, ImageView> imageColumn;
    @FXML
    private TableColumn<UserBean, String> firstNameColumn;
    @FXML
    private TableColumn<UserBean, String> lastNameColumn;
    @FXML
    private TableColumn<UserBean, String> infoColumn;
    @FXML
    private TableColumn<UserBean, String> sendRequestColumn;
    @FXML
    private TableColumn<UserBean, String> declineRequestColumn;
    @FXML
    private TableColumn<UserBean, String> acceptRequestColumn;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private PasswordField password;
    @FXML
    private TextField dateOfBirthInput;
    @FXML
    private TextField genderInput;
    @FXML
    private TextField countryInput;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<String> country;
    @FXML
    private ImageView image;
    @FXML
    private Button saveSettings;
    @FXML
    private BarChart personalityBarChart;
    @FXML
    private BarChart clusterBarChart;
    @FXML
    private Text deviation;
    @FXML
    private Text deviationNN;
    @FXML
    private Text personalityDescription;
    private static String userInfoID;

    @FXML
    private void showRecommendedUsers(ActionEvent event){
        System.out.println("Showing recommended users to the user");
        List<User> similarUsers = getSimilarUsers();
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        if(similarUsers != null) {
            for (User similarUser : similarUsers) {
                userBeans.add(new UserBean(similarUser));
            }
        }
        tableView.setItems(userBeans);
    }

    @FXML
    private void showNearbyUsers(ActionEvent event){
        System.out.println("Showing nearby users to the user");
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        List<User> similarUsers = getNearbyUsers();
        if(similarUsers != null) {
            for (User similarUser : similarUsers) {
                userBeans.add(new UserBean(similarUser));
            }
        }
        tableView.setItems(userBeans);
    }

    @FXML
    private void saveSettings(ActionEvent event) {
        String newName = name.getText();
        String newSurname = surname.getText();
        String newUsername = username.getText();
        String newEmail = email.getText();
        String newPhoneNumber = phoneNumber.getText();
        String newPassword = password.getText();
        String newCountry = country.getValue();
        String newGender = gender.getValue();
        LocalDate newDateOfBirth = null;
        String newImage = image.getImage().getUrl();
        name.setStyle("-fx-border-color: null;");
        surname.setStyle("-fx-border-color: null;");
        username.setStyle("-fx-border-color: null;");
        country.setStyle("-fx-border-color: null;-fx-background-color:#CCE5FF");
        gender.setStyle("-fx-border-color: null;-fx-background-color: #CCE5FF");
        dateOfBirth.setStyle("-fx-border-color: null;");
        password.setStyle("-fx-border-color: null;");
        email.setStyle("-fx-border-color: null;");
        phoneNumber.setStyle("-fx-border-color: null;");
        if(newName.equals("")) {
            name.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newSurname.equals("")) {
            surname.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newUsername.equals("")) {
            username.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validEmailAddress(newEmail)) {
            email.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newGender == null) {
            gender.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        if(dateOfBirth.getValue() != null)
            newDateOfBirth = dateOfBirth.getValue();
        if(newDateOfBirth == null || !validDate(newDateOfBirth)){
            dateOfBirth.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newCountry == null) {
            country.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        try {
            updateUserInfo(new User(newName, newSurname, newUsername, newEmail, newPhoneNumber, newPassword, newGender, newDateOfBirth, null, newCountry, newImage));
            System.out.println("New settings saved!");
        } catch(Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Error in saving settings: " + e.getMessage());
            errorAlert.setHeaderText("Information not valid!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            name.setText(user.getFirstName());
            surname.setText(user.getLastName());
            username.setText(user.getUsername());
            email.setText(user.getEmail());
            phoneNumber.setText(user.getPhoneNumber());
            country.getItems().addAll(readListOfCountries());
            country.setValue(user.getCountry());
            gender.getItems().add("M");
            gender.getItems().add("F");
            gender.getItems().add("Not Specified");
            gender.setValue(user.getGender());
            image.setImage(new Image(user.getImage()));
            dateOfBirth.setValue(user.getDateOfBirth());
        }
    }

    @FXML
    private void showHome(ActionEvent event){
        System.out.println("Loading the home page");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("home");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFriendships(ActionEvent event){
        System.out.println("Showing the friendships of the user");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("friends");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event){
        System.out.println("The user is logging out");
        LoaderFXML object = new LoaderFXML();
        Pane loginPane = object.getPage("login");
        logoutUser();
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(loginPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkForUpdates(ActionEvent event){
        System.out.println("Checking for updates...");
        checkForNewRecommended();
        showRecommendedUsers(new ActionEvent());
    }

    @FXML
    private void showSettings(ActionEvent event){
        System.out.println("Loading the settings page");
        LoaderFXML object = new LoaderFXML();
        Pane settingsPane = object.getPage("settings");
        try {
            layoutPane.getChildren().clear();
            layoutPane.getChildren().add(settingsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStats(ActionEvent event){
        System.out.println("Loading the stats page");
        LoaderFXML object = new LoaderFXML();
        Pane statsPane = object.getPage("stats");
        try {
            layoutPane.getChildren().clear();
            layoutPane.getChildren().add(statsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFriendRequests(ActionEvent event){
        System.out.println("Loading the friend requests of the user");
        LoaderFXML object = new LoaderFXML();
        Pane friendPane = object.getPage("friendRequests");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(friendPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showUserInfo(ActionEvent event){
        System.out.println("Showing user information");
        LoaderFXML object = new LoaderFXML();
        Pane userPane = object.getPage("userInfo");
        try {
            if(friendsPane != null)
                friendsPane.getChildren().clear();
            assert friendsPane != null;
            friendsPane.getChildren().add(userPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void uploadPic(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(PersonalityClustering.stage);
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dslbv6rz5",
                "api_key", "628347138896939",
                "api_secret", "k9zyTI4iM2nbYvQWLDDAn-18KBs",
                "secure", true));
        Map uploadedImage = cloudinary.uploader().upload(selectedFile, Collections.emptyMap());
        String imageUrl = uploadedImage.get("secure_url").toString();
        image.setImage(new Image(imageUrl));
        System.out.println("User uploaded a new profile picture");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!initialized && layoutPane != null){
            showHome(new ActionEvent());
            initialized = true;
        }
        // Home page
        if(sendRequestColumn != null) {
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            Callback<TableColumn<UserBean, String>, TableCell<UserBean, String>> cellFactory
                    = //
                    new Callback<>() {
                        @Override
                        public TableCell call(final TableColumn<UserBean, String> param) {
                            return new TableCell<UserBean, String>() {
                                final Button btn = new Button("Send Request");
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                    } else {
                                        btn.setStyle("-fx-background-color: #CCE5FF");
                                        btn.setOnAction(event -> {
                                            UserBean person = getTableView().getItems().get(getIndex());
                                            // if request not sent already
                                            if(!person.getRequested()) {
                                                addFriendRequest(person.getId());
                                                System.out.println("User sent a friend request");
                                                btn.setDisable(true);
                                                btn.setText("Request sent");
                                            } else {
                                                //request already sent
                                                System.out.println("User requested a request already sent");
                                                btn.setDisable(true);
                                                btn.setText("Request already sent");
                                            }
                                        });
                                        setGraphic(btn);
                                    }
                                    setText(null);
                                }
                            };
                        }
                    };
            sendRequestColumn.setCellFactory(cellFactory);
            showRecommendedUsers(new ActionEvent());
        }
        // Friendships page
        if(infoColumn != null) {
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            Callback<TableColumn<UserBean, String>, TableCell<UserBean, String>> cellFactory
                    = //
                    new Callback<>() {
                        @Override
                        public TableCell call(final TableColumn<UserBean, String> param) {
                            return new TableCell<UserBean, String>() {

                                final Button btn = new Button();

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                    } else {
                                        UserBean person = getTableView().getItems().get(getIndex());
                                        btn.setStyle("-fx-background-color: #CCE5FF");
                                        btn.setText("Show information");
                                        btn.setOnAction(event -> {
                                            userInfoID = person.getId();
                                            showUserInfo(event);
                                        });
                                        setGraphic(btn);
                                    }
                                    setText(null);
                                }
                            };
                        }
                    };
            infoColumn.setCellFactory(cellFactory);
            ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
            List<User> friends = getFriendships();
            if(friends != null) {
                for (User user: friends) {
                    userBeans.add(new UserBean(user));
                }
            }
            tableView.setItems(userBeans);
        }
        // Friendships Requests Page
        if(acceptRequestColumn != null) {
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            Callback<TableColumn<UserBean, String>, TableCell<UserBean, String>> cellFactory
                    = //
                    new Callback<>() {
                        @Override
                        public TableCell call(final TableColumn<UserBean, String> param) {
                            return new TableCell<UserBean, String>() {
                                final Button btn = new Button("Accept");
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                    } else {
                                        btn.setStyle("-fx-background-color: #CCE5FF");
                                        btn.setOnAction(event -> {
                                            UserBean person = getTableView().getItems().get(getIndex());
                                            acceptFriendRequest(new User(person));
                                            System.out.println("User accepted a friendship request");
                                            List<User> friendRequests = getFriendshipRequests();
                                            ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
                                            for(User friendRequesting: friendRequests)
                                                userBeans.add(new UserBean(friendRequesting));
                                            tableView.setItems(userBeans);
                                        });
                                        setGraphic(btn);
                                    }
                                    setText(null);
                                }
                            };
                        }
                    };
            acceptRequestColumn.setCellFactory(cellFactory);
            cellFactory = new Callback<>() {
                @Override
                public TableCell call(final TableColumn<UserBean, String> param) {
                    return new TableCell<UserBean, String>() {
                        final Button btn = new Button("Decline");
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                btn.setStyle("-fx-background-color: #CCE5FF");
                                btn.setOnAction(event -> {
                                    UserBean person = getTableView().getItems().get(getIndex());
                                    deleteFriendRequest(new User(person));
                                    System.out.println("User declined a friendship request");
                                    List<User> friendRequests = getFriendshipRequests();
                                    ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
                                    for(User friendRequesting: friendRequests)
                                        userBeans.add(new UserBean(friendRequesting));
                                    tableView.setItems(userBeans);
                                });
                                setGraphic(btn);
                            }
                            setText(null);
                        }
                    };
                }
            };
            declineRequestColumn.setCellFactory(cellFactory);
            List<User> friendRequests = getFriendshipRequests();
            ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
            for(User friendRequesting: friendRequests)
                userBeans.add(new UserBean(friendRequesting));
            tableView.setItems(userBeans);
        }
        // Settings Page
        if(saveSettings != null){
            name.setText(user.getFirstName());
            surname.setText(user.getLastName());
            username.setText(user.getUsername());
            email.setText(user.getEmail());
            phoneNumber.setText(user.getPhoneNumber());
            country.getItems().addAll(readListOfCountries());
            country.setValue(user.getCountry());
            gender.getItems().add("M");
            gender.getItems().add("F");
            gender.getItems().add("Not Specified");
            gender.setValue(user.getGender());
            image.setImage(new Image(user.getImage()));
            dateOfBirth.setValue(user.getDateOfBirth());
        }
        // Stats page
        if(personalityBarChart != null){
            personalityBarChart.setStyle("-fx-bar-fill: blue;");
            // personal bar chart
            XYChart.Series<String, Number> values = new XYChart.Series<>();
            double[] personalityValues = getPersonalityValues();
            double[] clusterPersonalityValues = getClusterPersonalityValues();
            values.getData().add(new XYChart.Data<>("OPN", personalityValues[0]));
            values.getData().add(new XYChart.Data<>("AGR", personalityValues[1]));
            values.getData().add(new XYChart.Data<>("NSM", personalityValues[2]));
            values.getData().add(new XYChart.Data<>("EXT", personalityValues[3]));
            values.getData().add(new XYChart.Data<>("CNS", personalityValues[4]));
            values.getData().add(new XYChart.Data<>("Time", personalityValues[5]));
            personalityBarChart.getData().addAll(values);
            // cluster bar chart
            XYChart.Series<String, Number> valuesCluster = new XYChart.Series<>();
            if(clusterPersonalityValues != null) {
                valuesCluster.getData().add(new XYChart.Data<>("OPN", clusterPersonalityValues[0]));
                valuesCluster.getData().add(new XYChart.Data<>("AGR", clusterPersonalityValues[1]));
                valuesCluster.getData().add(new XYChart.Data<>("NSM", clusterPersonalityValues[2]));
                valuesCluster.getData().add(new XYChart.Data<>("EXT", clusterPersonalityValues[3]));
                valuesCluster.getData().add(new XYChart.Data<>("CNS", clusterPersonalityValues[4]));
                valuesCluster.getData().add(new XYChart.Data<>("Time", clusterPersonalityValues[5]));
                clusterBarChart.getData().addAll(valuesCluster);
            }
            deviation.setText("Your behavior deviates "+ getDeviation() + "% with respect to others of your cluster.");
            String deviationNNValue = getDeviationNN();
            if(deviationNNValue != null)
                deviationNN.setText("Your behavior deviates "+ deviationNNValue + "% with respect to your nearest neighbor.");
            else deviationNN.setText("");
            personalityDescription.setText(getPersonalityDescription(user));
        }
        // User Information Page
        if(dateOfBirthInput != null){
            User user = getUserInfo(userInfoID);
            userInfoID = null;
            image.setImage(new Image(user.getImage()));
            name.setText(user.getFirstName());
            name.setEditable(false);
            surname.setText(user.getLastName());
            surname.setEditable(false);
            username.setText(user.getUsername());
            username.setEditable(false);
            email.setText(user.getEmail());
            email.setEditable(false);
            phoneNumber.setText(user.getPhoneNumber());
            phoneNumber.setEditable(false);
            genderInput.setText(user.getGender());
            genderInput.setEditable(false);
            dateOfBirthInput.setText(user.getDateOfBirth().toString());
            dateOfBirthInput.setEditable(false);
            countryInput.setText(user.getCountry());
            countryInput.setEditable(false);
        }
    }
}
