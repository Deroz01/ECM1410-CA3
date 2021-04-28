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
    public int getIndent() {
		return indent;
	}
    public void setIndent(int indent) {
		this.indent = indent;
	}
	public int indent(int level) {
		return level++;
	}
    public String getHandle() {
        return handle;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
		this.message = message;
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
    	if (commentNumber>0) {
    		commentNumber--;
    	}
    }
    public static void resetIdCounter() {
    	idCount = 0;
    }
    
    private static int idCount=0;

    public Post (String handle, String message) {
        this.handle = handle;
        this.message = message;
        this.id = idCount;
        idCount++;
    }
    public static int getIdCount() {
		return idCount;
	}
    @Override
	public String toString() {
		return "ID: " + id + "\nAccount: " + handle + "\nNo. endorsements: " + endorseNumber + " | No. comments: "+ commentNumber + "\n" + message;
	}
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
    
	public static void main(String[] args) {
        Post a = new Post("a", "welcome");
        //System.out.println(a);
        Post b = new Post("b", "hi");
        //System.out.println(b);
        Post c = new Post("c", "hello");
        System.out.println(c.commentString(1));
        System.out.println(c.commentString(2));
        //System.out.println(c);
    }
}
