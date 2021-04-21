package socialmedia;

import java.io.Serializable;

public class Post implements Serializable{
    private String handle;
    private String message;
    private int id;
    private int endorseNumber;
    private int commentNumber;
    
    public String getHandle() {
        return handle;
    }
    public String getMessage() {
        return message;
    }
    public int getId() {
        return id;
    }
    public int getCommentNumber() {
		return commentNumber;
	}
    public int getEndorseNumber() {
		return endorseNumber;
	}
    public void increaseEndorseNumber (){
    	endorseNumber++;
    }
    public void decreaseEndorseNumber (){
    	endorseNumber--;
    }
    public void increaseCommentNumber (){
    	commentNumber++;
    }
    public void decreaseCommentNumber (){
    	commentNumber--;
    }

    private static int idCount=0;

    public Post (String handle, String message) {
        this.handle = handle;
        this.message = message;
        this.id = idCount;
        idCount++;
    }
    
    @Override
	public String toString() {
		return "ID: " + id + "\nAccount: " + handle + "\nNo. endorsements: " + endorseNumber + " | No. comments: "+ commentNumber + "\n" + message;
	}
    
	public static void main(String[] args) {
        Post a = new Post("a", "welcome");
        System.out.println(a);
        Post b = new Post("b", "hi");
        System.out.println(b);
        Post c = new Post("c", "hello");
        System.out.println(c);
    }
}
