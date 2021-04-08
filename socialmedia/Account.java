package socialmedia;

import java.io.Serializable;

public class Account implements Serializable{
	private int id;
    private String handle;
    private String description;
    private int postCount;
    private int endorseCount;

    private static int idNumber = 0;

    public String getHandle() {
        return handle;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public int getPostCount() {
		return postCount;
	}
    public int getEndorseCount() {
		return endorseCount;
	}
    public void setHandle(String handle) {
        this.handle = handle;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Account (String handle) {
        this.handle = handle;
        this.id = idNumber;
        this.description = "";
        idNumber++;
    }
    public Account (String handle, String description) {
        this(handle);
        this.description = description;
    }
	@Override
	public String toString() {
		return "ID: " + id + "\nHandle: " + handle + "\nDescription: " + description + "\nPost count: " + postCount + "\nEndorse count: " + endorseCount;
	}
	public static void main(String[] args) {
		Account user1 = new Account("user1");
		System.out.println(user1);
	}
	
    
}