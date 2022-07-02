package Main;

import java.util.ArrayList;
import java.util.List;

public class Post implements Comparable{

    private static int postIDMaker=1000;
    private int post_id;
    private String text;
    private List<Like> likes;
    private int likeNumber;
    private int dislikeNumber;
    private List<Comment> comments;
    private UserAccount user_Post;

    public Post(String text , UserAccount user_Post) {
        this.text = text;
        this.likeNumber = 0;
        this.dislikeNumber = 0;
        this.user_Post=user_Post;
        this.post_id= ++postIDMaker;
        likes = new ArrayList<>();
        comments = new ArrayList<>();

        String sqlCom = String.format("INSERT INTO post (text, like num, dislike num, post id, user id) VALUES ('%s', '%s', '%s', '%s', '%s') ", this.text, this.likeNumber, this.dislikeNumber, this.post_id, this.user_Post.getID());
        MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", likeNumber=" + likeNumber +
                ", dislikeNumber=" + dislikeNumber +
                ", comments=" + comments +
                ", user_Post=" + user_Post +
                '}';
    }

    //comparing

    @Override
    public int compareTo(Object post) {
        if (this.likeNumber - this.dislikeNumber > ((Post)post).likeNumber - ((Post) post).dislikeNumber)
            return 1;
        else if (this.likeNumber - this.dislikeNumber < ((Post)post).likeNumber - ((Post) post).dislikeNumber)
            return -1;
        else
        {
            if (this.comments.size()  > ((Post) post).comments.size())
                return 1;
            else if (this.comments.size()  < ((Post) post).comments.size())
                return -1;
            else return 0;
        }
    }


    // Getters and Setters ================================================


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(UserAccount user, boolean isLike) {
        this.likes.add(new Like(user, isLike));
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getDislikeNumber() {
        return dislikeNumber;
    }

    public void setDislikeNumber(int dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
    }

    public UserAccount getUser_Post() {
        return user_Post;
    }

    public void setUser_Post(UserAccount user_Post) {
        this.user_Post = user_Post;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
