
public class Endorsement {
 private String message;
 
 private String handle;
 
 public public Endorsement(String message, String handle) {
	// TODO Auto-generated constructor stub
	this.message = message;
	this.handle = handle;
 }
 
 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EP@" + handle + ": " + message;
	}
}
