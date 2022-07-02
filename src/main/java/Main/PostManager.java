package Main;

import java.util.ArrayList;
import java.util.List;

public class PostManager {

    public static List<Post> allPosts = new ArrayList<>();

    // kimia -----------------------------------------------------------------------
    public static void unpopularPosts() {

        double aveDislikeNum = 0;

        for (Post allPost : UserManager.userLoggedIn.getPosts()){
            aveDislikeNum += allPost.getDislikeNumber();
        }
        aveDislikeNum /= UserManager.userLoggedIn.getPosts().size();

        for (Post allPost : UserManager.userLoggedIn.getPosts()){
            if (allPost.getDislikeNumber() > aveDislikeNum)
                System.out.println(allPost);
        }
    }

    public static void explore() {

        for (Post allPost : allPosts){
            if (allPost.getUser_Post().getType().equals(AccountType.PUBLIC))
                System.out.println(allPost);
        }
    }

    public static boolean checkToFollow(int post_id, String ID) {

        boolean found = false;

        for (Post allPost : allPosts){
            if (allPost.getPost_id() == post_id){
                for (UserAccount user : allPost.getUser_Post().getFollowers()){
                    if (user.getID().equals(ID))
                        found = true;
                }
            }
        }

        return found;
    }

    public static Post search(int post_id) {

        for (Post allPost : allPosts){
            if (allPost.getPost_id() == post_id)
                return allPost;
        }

        return null;
    }

    public static boolean comment(int post_id, String text, UserAccount user) {

        boolean found = false;

        for (Post allPost : allPosts){
            if (allPost.getPost_id() == post_id){

                String sqlCom = String.format("INSERT INTO comment (text, user id, post id) VALUES ('%s', '%s', '%s') ", text, user.getID(), post_id);
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                allPost.setComments(new Comment(text, user));
                found = true;
                break;
            }
        }

        return found;
    }
}
