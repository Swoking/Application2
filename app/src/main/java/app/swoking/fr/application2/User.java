package app.swoking.fr.application2;

import java.io.Serializable;

/**
 * Created by rgab7 on 14/08/2016.
 */
public class User implements Serializable {

    private static int      DefaultId       = 0;
    private static String   DefaultName     = "#ERROR";
    private static String   DefaultUsername = "#ERROR";
    private static int      DefaultAge      = 0;
    private static String   DefaultBio      = "#ERROR";
    private static String[] DefaultUrls     = new String[] {"http://swoking.scopegames.fr/getImage.php?id=0"};

    private int      id;
    private String   name;
    private String   username;
    private int      age;
    private String   bio;
    private String[] urls;

    public User(int id, String name, String username, int age, String bio, String[] urls) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.age = age;
        this.bio = bio;
        this.urls = urls;
    }

    public static User DefaultUser() {
        return new User(DefaultId, DefaultName, DefaultUsername, DefaultAge, DefaultBio, DefaultUrls);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
