package com.unipi.datamining.API;
import com.unipi.datamining.dtos.*;
import com.unipi.datamining.entities.ClusterValues;
import com.unipi.datamining.entities.User;
import com.unipi.datamining.util.ConfigurationParameters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class API {
    private static String uri;

    public static void setConfiguration(ConfigurationParameters confParameters){
        if(confParameters.serverIP != null && confParameters.serverPort != 0)
            uri = "http://" + confParameters.serverIP + ":" + confParameters.serverPort;
        else uri = "http://localhost:8080"; //default parameters
    }
    private static final RestTemplate restTemplate = new RestTemplate();

    public static User registerUser(User user) throws Exception{
        UserDto userDto = new UserDto(user);
        try {
            UserDto response = restTemplate.postForObject(uri + "/users/register", userDto, UserDto.class);
            assert response != null;
            user.setId(response.getId());
            user.setCluster(response.getCluster());
        } catch(Exception e){
            throw new Exception(e.getMessage().split("\"")[10]);
        }
        return user;
    }


    public static User loginUser(User user) throws Exception{
        LoginDto loginDto = new LoginDto(user);
        UserDto response;
        try {
            response = restTemplate.postForObject(uri + "/users/login", loginDto, UserDto.class);
        } catch(Exception e){
            if(e.getMessage().split("\"").length == 3)
                throw new Exception("Server not connected....");
            else throw new Exception(e.getMessage().split("\"")[10]);
        }
        if(response != null)
            return new User(response);
        else return null;
    }

    public static User getUserInfo(String id){
        ResponseEntity<UserDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/" + id, UserDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return new User(response.getBody());
        } else return null;
    }


    public static List<User> getSimilarUsers(User user){
        ResponseEntity<Neo4jUserDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/similarities/" + user.getId(), Neo4jUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).map(User::new).toList();
        } else return null;
    }

    public static List<User> getNearbyUsers(User user){
        ResponseEntity<Neo4jUserDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/similar_nearby/" + user.getId(), Neo4jUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static ClusterValues getClusterValues(User user){
        ResponseEntity<ClusterValuesDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/cluster_cluster_values/" + user.getId(), ClusterValuesDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return new ClusterValues(response.getBody());
        else return null;
    }

    public static User getMostSimilarUser(User user){
        ResponseEntity<UserDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/most_similar/" + user.getId(), UserDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return new User(response.getBody());
        else return null;
    }

    public static List<User> getFriendRequests(User user){
        ResponseEntity<Neo4jUserDto[]> response = null;
        try {
            response = restTemplate.getForEntity(uri + "/users/friend_requests/" + user.getId(), Neo4jUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        assert response != null;
        if(response.getBody() != null)
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static List<User> getFriendships(User user){
        ResponseEntity<Neo4jUserDto[]> response = null;
        try {
            response = restTemplate.getForEntity(uri + "/users/friends/" + user.getId(), Neo4jUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        assert response != null;
        if(response.getBody() != null )
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static void updateFriendRequest(User user, User receiver, int status){
        try {
           restTemplate.exchange(uri + "/users/friend_requests/?from=" + user.getId() + "&to=" + receiver.getId() + "&status=" + status, HttpMethod.PUT, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void updateUserInfo(User user){
        HttpEntity<UserDto> entity = new HttpEntity<>(new UserDto(user));
        restTemplate.exchange(uri + "/users/update/" ,
                    HttpMethod.PUT,
                    entity, Void.class);
    }

     public static void addFriendRequest(User sender, String receiverId){
         try {
             restTemplate.postForEntity(uri + "/users/friend_requests/?from=" + sender.getId() + "&to=" + receiverId, null, Object.class);
         } catch(Exception e){
             System.out.println(e.getMessage());
         }
     }

     public static void checkForUpdates(User user){
         try {
             restTemplate.postForEntity(uri + "/users/similarities/" + user.getId(), null, Object.class);
         } catch(Exception e){
             System.out.println(e.getMessage());
         }
     }
}
