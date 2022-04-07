package edu.z_ivt_18.bugtracker;

public final class User {
    private final int id;
    private final String username;
    private final String fullName;
    private final char[] password;

    public User(int id, String username, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.password = null;
    }

    public User(String username, String fullName, char[] password) {
        this.id = -1;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public char[] getPassword() {
        return password;
    }
}
