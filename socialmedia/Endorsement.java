package socialmedia;

import java.util.ArrayList;

public class Endorsement extends Post{

    private String endorsementHandle;
    private int postId;
    private static int idCount=0;
    private int id;
    private String message;

    public Endorsement(String endorsementHandle, int postId) {
        super(postId);
        this.endorsementHandle = endorsementHandle;
        this.id = idCount;
        idCount ++;
    }

    @Override
    public String toString() {
        return "Endorsement{" +
                "endorsementHandle='" + endorsementHandle + '\'' +
                ", endorsementId=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    /*
    public static void main(String[] args) {
        Post endorsement = new Endorsement("handle", "message");
        System.out.println(endorsement);

        ArrayList<Post> posts = new ArrayList<>();

        posts.add(endorsement);
    }
     */
}