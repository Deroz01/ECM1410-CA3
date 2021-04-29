package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
public class Post implements Serializable{
    private String handle;
    private String message;
    private int id;
    private int commentNumber;
    private int endorseNumber;
    private int indent;

    /**
     * Method to get the indent of a post
     * @return The int with the level of indent for the post
     */
    public int getIndent() {
        return indent;
    }

    /**
     * Method to set the indent allowing for ease of printing
     * @param indent The int which shows what level of indentation the post requires
     */
    public void setIndent(int indent) {
        this.indent = indent;
    }

    /**
     * Method to create indent for the post
     * @param level The int which specifies the level of indentation
     * @return An int which specifies the level of indentation
     */
    public int indent(int level) {
        return level++;
    }

    /**
     * Method to get the handle of the user who posted
     * @return A String with the user's handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Method to get the post message
     * @return A String with the post message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method to set the post message
     * @param message The message that will be set for the post
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method to get the post id
     * @return The int with the post id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the number of comments on the post
     * @return The int with the number of comments on the post
     */
    public int getCommentNumber() {
        return commentNumber;
    }

    /**
     * Method to get the number of endorsements
     * @return The int with the number of endorsements on the post
     */
    public int getEndorseNumber() {
        return endorseNumber;
    }

    /**
     * Method to increase the number of endorsements on the post
     */
    public void increaseEndorseNumber (){
        endorseNumber++;
    }

    /**
     * Method to decrease the number of endorsements on the post
     */
    public void decreaseEndorseNumber (){
        endorseNumber--;
    }

    /**
     * Method to increase the number of comments on the post
     */
    public void increaseCommentNumber (){
        commentNumber++;
    }

    /**
     * Method to decrease the number of comments on the post
     */
    public void decreaseCommentNumber (){
        if (commentNumber>0) {
            commentNumber--;
        }
    }

    /**
     * Method to reset the number of posts and set id to 0
     */
    public static void resetIdCounter() {
        idCount = 0;
    }

    private static int idCount=0;

    /**
     * Constructor to initialize the post
     * @param handle The handle of the user who is posting
     * @param message The message of the post
     */
    public Post (String handle, String message) {
        this.handle = handle;
        this.message = message;
        this.id = idCount;
        idCount++;
    }

    /**
     * Method to get the number of IDs
     * @return An int containing the number of IDs
     */
    public static int getIdCount() {
        return idCount;
    }
    @Override
    public String toString() {
        return "ID: " + id + "\nAccount: " + handle + "\nNo. endorsements: " + endorseNumber + " | No. comments: "+ commentNumber + "\n" + message;
    }

    /**
     * Method to get a correctly formatted post
     * @param a the level of indent that the post will have
     * @return A String containing the correctly formatted
     */
    public String commentString(int a) {
        String firstLine = "";
        String indent = "";
        for (int i=0; i<a; i++) {
            indent += "    ";
            if (i>0) {
                firstLine += "    ";
            }
        }
        return firstLine+"| > ID: " + getId() + "\n"+ indent+ "Account: " + getHandle() + "\n"+ indent +"No. endorsements: " + getEndorseNumber() + " | No. comments: "+ getCommentNumber() + "\n" + indent + getMessage();
    }
}
