package Main;

public class Like {
    private UserAccount user;
    private boolean isLike;

    public Like(UserAccount user, boolean isLike) {
        this.user = user;
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "Like{" +
                "user=" + user +
                ", isLike=" + isLike +
                '}';
    }

    // Getters and Setters ================================================

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
