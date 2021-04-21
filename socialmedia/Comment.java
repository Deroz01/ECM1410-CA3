package socialmedia;

public class Comment extends Post {
	private int orginialPostId;
	
	public Comment(int id, String handle, String message) {
		super(handle, message);
		// TODO Auto-generated constructor stub
		this.orginialPostId = id;
	}

	public int getOrginalPostId() {
		return orginialPostId;
	}
	public static void main(String[] args) {
		Post aPost = new Post("old", "hello");
		System.out.println(aPost.getCommentNumber());
		Post comment = new Comment(0, "new", "comment");
		System.out.println(comment.getMessage());
		aPost.increaseCommentNumber();
		System.out.println(aPost.getCommentNumber());
	}
}
