package socialmedia;

public class Endorsement extends Post {

	private String endorsedHandle;
	public Endorsement(String endorserHandle, String message, String endorsedHandle) {
		super(endorserHandle, message);
		// TODO Auto-generated constructor stub
		this.endorsedHandle = endorsedHandle;
	}
	public String getEndorsedHandle() {
		return endorsedHandle;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EP@" + endorsedHandle + ": "+ getMessage();
	}
	@Override
	public String commentString(int a) {
		return toString();
	}
	public static void main(String[] args) {
		Post endorsement = new Endorsement("endorserHandle", "endorsedMessage", "endorsedAccountHandle");
		System.out.println(endorsement instanceof Endorsement);
	}
}
