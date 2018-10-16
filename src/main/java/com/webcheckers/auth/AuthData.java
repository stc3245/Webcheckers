package com.webcheckers.auth;

import com.webcheckers.appl.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class AuthData 
{
    private static final Logger LOG = Logger.getLogger(AuthData.class.getName());

    private static HashMap<String,Player> userData;
    private static HashSet<String> signedInUsers;

    /**
     * synchronize with database, which right now just initializes a new one in memory if non existent
     */
    private static void sync(){

        if (userData == null){
            userData = new HashMap<>();
        }

        if (signedInUsers == null){
            signedInUsers = new HashSet<>();
        }
    }


    /**
     * get users that are currently online
     * @return a collection of usernames
     */
    static Collection<String> getSignedInUsers(){
        sync();
        return signedInUsers;
    }

    /**
     * method for signing off a user
     * @param username username
     * @param password password
     * @param clientUID unique identifier for the client
     * @throws AuthException will throw exception if failed
     */
    static synchronized void signIn(String username) throws AuthException{
        sync();
        //String expected = usernamePasswords.get(username);
        // if (expected == null){
        //     throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        // }
        if (signedInUsers.contains(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDIN);
        }
        signedInUsers.add(username);
        LOG.config(username + " signed in.");
    }

    /**
     * method for signing off a user
     * @param username username
     * @param password password
     * @param clientUID unique identifier for the client
     * @throws AuthException will throw exception if failed
     */
    static synchronized void signOff(String username, String password, String clientUID, Player player) throws AuthException{
        sync();

        if (!signedInUsers.contains(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDOFF);
        }
        signedInUsers.remove(username);
        //userData.get(username).copiesValuesFrom(player);
        LOG.config(username + " signed off.");
    }

    /**
     * class for signing up
     * @param username username
     * @param p player
     */
    public static synchronized void signUp(String username, Player p)
    {
        sync();
        signedInUsers.add(username);
        LOG.config(username + " signed up.");
        userData.put(username, p);
    }
}
