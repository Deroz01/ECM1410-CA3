package socialmedia;

public class Comment extends Post {
	private int orginalPostId;
	
	public Comment(int id, String handle, String message) {
		super(handle, message);
		// TODO Auto-generated constructor stub
		this.orginalPostId = id;
	}

	public int getOrginalPostId() {
		return orginalPostId;
	}
	public void setOrginalPostId(int orginalPostId) {
		this.orginalPostId = orginalPostId;
	}
}
