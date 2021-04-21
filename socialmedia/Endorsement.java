package socialmedia;

import java.util.ArrayList;

public class Endorsement extends Post {

	public Endorsement(String handle, String message) {
		super(handle, message);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EP@" + getHandle() + ": "+ getMessage();
	}
	public static void main(String[] args) {
		Post endorsement = new Endorsement("handle", "message");
		System.out.println(endorsement);
		
		ArrayList<Post> posts = new ArrayList<>();
		
		posts.add(endorsement);
	}
}
