package com.webcheckers.appl;

/**
 * Player data type
 * author: Perry Deng, Bryce Murphy
 */
public class Player {

    private String name;
    private boolean signedIn;

    public Player(String username) {
        name = username;
        signedIn = true;
    }

    public String getName(){
        return name;
    }

    public boolean signedIn() {
        return signedIn;
    }

}
