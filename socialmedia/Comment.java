package socialmedia;
/**
 * This class extends the Post Class and inherits the attributes of a post but also has a reference to the post which is being commented on
 * @author Luca de Rozairo and Tommy Mack
 *
 */
public class Comment extends Post {
	/**
	 * Referenced post id
	 */
	private int orginalPostId;
	/**
	 * Creates a new Comment object
	 * @param id The id of the referenced post
	 * @param handle The handle that is commenting
	 * @param message The comment's message
	 */
	public Comment(int id, String handle, String message) {
		super(handle, message);
		// TODO Auto-generated constructor stub
		this.orginalPostId = id;
	}
	/**
	 * Creates a new Comment object
	 * @param id The id of the referenced post
	 * @param handle The handle that is commenting
	 * @param message The comment's message
	 * @param indent The indent of the referenced post level
	 */
	public Comment(int id, String handle, String message, int indent) {
		this(id, handle, message);
		this.setIndent(indent+1);
	}
	/**
	 * This method retrieves the original post id
	 * @return An integer representing the referenced post id
	 */
	public int getOrginalPostId() {
		return orginalPostId;
	}
	/**
	 * This methods sets a new id for the referenced post
	 * @param orginalPostId An integer representing the referenced post id
	 */
	public void setOrginalPostId(int orginalPostId) {
		this.orginalPostId = orginalPostId;
	}
	
}
