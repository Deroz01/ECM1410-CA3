package socialmedia;
/**
 * This class extends the Post Class and inherits the attributes of a post but also has a reference to the post which is being commented on
 * @author Luca de Rozairo and Tommy Mack
 *
 */
public class Endorsement extends Post {
	/**
	 * Endorsed handle
	 */
	private String endorsedHandle;
	/**
	 * Creates Endorsement object
	 * @param endorserHandle Handle of account that is endorsing
	 * @param message Message that is being endorsed
	 * @param endorsedHandle Handle of account that is being endorsed
	 */
	public Endorsement(String endorserHandle, String message, String endorsedHandle) {
		super(endorserHandle, message);
		// TODO Auto-generated constructor stub
		this.endorsedHandle = endorsedHandle;
	}
	/**
	 * Endorsed Handle
	 * @return String representing the handle of the endorsed account
	 */
	public String getEndorsedHandle() {
		return endorsedHandle;
	}
	/**
	 * Returns Endorsement in the correct format
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EP@" + endorsedHandle + ": "+ getMessage();
	}
	/**
	 * Overrides post string format
	 */
	@Override
	public String commentString(int a) {
		return toString();
	}
}
