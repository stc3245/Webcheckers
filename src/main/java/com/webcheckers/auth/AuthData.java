package com.webcheckers.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AuthData {
    private static HashMap<String,String> usernamePasswords;
    private static HashMap<String,Boolean> signedInUsers;

    /**
     * synchronize with database, which right now just initializes a new one in memory if non existent
     */
    private static void sync(){
        if (usernamePasswords == null){
            usernamePasswords = new HashMap<>();
        }

        if (signedInUsers == null){
            signedInUsers = new HashMap<>();
        }
    }

    /**
     * check if a string is clean. right now it doesnt do anything
     * @return false if it's clean
     */
    private static boolean containsInvalidCharacter(Collection<String> deezStrings){
        return true;
    }

    /**
     * method for signing off a user
     * @param username username
     * @param password password
     * @param clientUID unique identifier for the client
     * @throws AuthException will throw exception if failed
     */
    static synchronized void signIn(String username, String password, String clientUID) throws AuthException{
        sync();

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }
        if (signedInUsers.get(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDIN);
        }
        signedInUsers.put(username, true);
    }

    /**
     * method for signing off a user
     * @param username username
     * @param password password
     * @param clientUID unique identifier for the client
     * @throws AuthException will throw exception if failed
     */
    static synchronized void signOff(String username, String password, String clientUID) throws AuthException{
        sync();

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }
        if (!signedInUsers.get(username)){
            throw new AuthException(AuthException.ExceptionMessage.ALREADY_SIGNEDOFF);
        }
        signedInUsers.put(username, false);
    }

    static synchronized void signUp(String username, String password) throws AuthException{
        sync();
        Collection<String> deezStrings = new ArrayList<String>();
        deezStrings.add(username);
        deezStrings.add(password);
        if (containsInvalidCharacter(deezStrings)){
            throw new AuthException(AuthException.ExceptionMessage.INVALID_CHARACTER);
        }
        if (usernamePasswords.containsKey(username)){
            throw new AuthException(AuthException.ExceptionMessage.USERNAME_TAKEN);
        }
        usernamePasswords.put(username, password);
    }

    static synchronized void changePassword(String username, String password, String newpassword) throws AuthException{
        sync();

        Collection<String> deezStrings = new ArrayList<String>();
        deezStrings.add(password);
        if (containsInvalidCharacter(deezStrings)){
            throw new AuthException(AuthException.ExceptionMessage.INVALID_CHARACTER);
        }

        String expected = usernamePasswords.get(username);
        if (expected == null || !expected.equals(password)){
            throw new AuthException(AuthException.ExceptionMessage.WRONG_CREDENTIALS);
        }

        usernamePasswords.put(username, newpassword);
    }
}
