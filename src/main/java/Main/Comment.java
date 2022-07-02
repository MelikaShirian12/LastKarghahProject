package Main;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    private String text;
    private UserAccount user;
    private boolean delete;
    private List<Comment> replies;

    public Comment(String text, UserAccount user) {
        this.text = text;
        this.user = user;
        replies = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", user=" + user +
                ", delete=" + delete +
                ", replies=" + replies +
                '}';
    }

    // Getters and Setters ================================================


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(Comment reply) {
        this.replies.add(reply);
    }
}
