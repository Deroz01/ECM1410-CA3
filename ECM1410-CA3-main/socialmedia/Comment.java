package socialmedia;

public class Comment extends Post{

    private String commentHandle;
    private String message;
    private int commentId;
    private static int idCount=0;

    public Comment(String commentHandle, int id, String message) {
        super(id);
        this.commentHandle = commentHandle;
        this.message = message;
        this.commentId = idCount;
        idCount ++;
    }

    public String getCommentHandle() {
        return commentHandle;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentHandle='" + commentHandle + '\'' +
                ", message='" + message + '\'' +
                ", commentId=" + commentId +
                '}';
    }

    /*
    public static void main(String[] args) {
        Post aPost = new Post("old", "hello");
        System.out.println(aPost.getCommentNumber());
        Post comment = new Comment(0, "new", "comment");
        System.out.println(comment.getMessage());
        aPost.increaseCommentNumber();
        System.out.println(aPost.getCommentNumber());
    }*/
}