package com.webcheckers.auth;

import com.webcheckers.appl.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class AuthData {
    private static final Logger LOG = Logger.getLogger(AuthData.class.getName());

    private static HashMap<String,String> usernamePasswords;
    private static HashMap<String,Player> userData;
    private static HashSet<String> signedInUsers;

    /**
     * synchronize with database, which right now just initializes a new one in memory if non existent
     */
    private static void sync(){
        if (usernamePasswords == null){
            usernamePasswords = new HashMap<>();
        }

        if (userData == null){
            userData = new HashMap<>();
        }

        if (signedInUsers == null){
            signedInUsers = new HashSet<>();
        }
    }

    /**
     * check if a string is clean. right now it doesnt do anything
     * @return false if it's clean
     */
    private static boolean containsInvalidCharacter(Collection<String> deezStrings){
        return false;
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
    static synchronized Player signIn(String username, String password, String clientUID) throws AuthException{
        sync();

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }
        if (signedInUsers.contains(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDIN);
        }
        signedInUsers.add(username);
        LOG.config(username + " signed in.");
        return userData.get(username);
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

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }
        if (!signedInUsers.contains(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDOFF);
        }
        signedInUsers.remove(username);
        userData.get(username).copiesValuesFrom(player);
        LOG.config(username + " signed off.");
    }

    static synchronized void signUp(String username, String password) throws AuthException{
        sync();
        Collection<String> deezStrings = new ArrayList<>();
        deezStrings.add(username);
        deezStrings.add(password);
        if (containsInvalidCharacter(deezStrings)){
            throw new AuthException(AuthException.ExceptionMessage.INVALID_CHARACTER);
        }
        if (usernamePasswords.containsKey(username)){
            throw new AuthException(AuthException.ExceptionMessage.USERNAME_TAKEN);
        }
        usernamePasswords.put(username, password);
        LOG.config(username + " signed up.");
        userData.put(username, new Player(username));
    }

    static synchronized void changePassword(String username, String password, String newpassword) throws AuthException{
        sync();

        Collection<String> deezStrings = new ArrayList<>();
        deezStrings.add(password);
        if (containsInvalidCharacter(deezStrings)){
            throw new AuthException(AuthException.ExceptionMessage.INVALID_CHARACTER);
        }

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }

        usernamePasswords.put(username, newpassword);
        LOG.config(username + " changed password");
    }
}
