package socialmedia;

import java.io.Serializable;
/**
 * This class contains a post id, handle, description, number of posts and number of endorsements
 * @author Luca de Rozairo and Tommy Mack
 *
 */
public class Account implements Serializable{
    private int id;
    private String handle;
    private String description;
    private int postCount;
    private int endorseCount;

    private static int idNumber = 0;

    /**
     * Method to get the user's handle
     * @return A String of the user's handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Method to get the user's description
     * @return A String of the user's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get the user's ID
     * @return A String of the user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the number of user's posts
     * @return An int of how many posts the user has
     */
    public int getPostCount() {
        return postCount;
    }

    /**
     * Method to add a post to the user's post count
     */
    public void increasePostCount() {
        postCount++;
    }

    /**
     *  Method to get the number of endorsements the user has received
     * @return An int of how many endorsements the user has received
     */
    public int getEndorseCount() {
        return endorseCount;
    }

    /**
     * Method to increase the number of endorsements the user has recieved
     */
    public void increaseEndorseCount() {
        endorseCount++;
    }

    /**
     * Method to set the user's handle
     * @param handle the handle that will be set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Method to set the user's description
     * @param description the description that will be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to set the id number of the account to 0
     */
    public static void resetIdCounter() {
        idNumber = 0;
    }

    /**
     * Constructor for the account
     * @param handle The String that will be the user's account handle
     */
    public Account (String handle) {
        this.handle = handle;
        this.id = idNumber;
        this.description = "";
        idNumber++;
    }

    /**
     * Constructor for the account
     * @param handle The String that will be the user's account handle
     * @param description The String that will be the user's account description
     */
    public Account (String handle, String description) {
        this(handle);
        this.description = description;
    }

    /**
     * Method to output the correct string format for an account
     * @return The String that describes the the account
     */
    @Override
    public String toString() {
        return "ID: " + id + "\nHandle: " + handle + "\nDescription: " + description + "\nPost count: " + postCount + "\nEndorse count: " + endorseCount;
    }
}
