package Main;

import Exceptions.DuplicateID;
import Exceptions.WrongIDException;
import Exceptions.WrongPasswordException;
import Exceptions.wrongPostID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UserManager {

    public static List<UserAccount> allUsers = new ArrayList<>();
    public static UserAccount userLoggedIn;

    // zahra -----------------------------------------------------------------------
    public static void login(String ID, String password)
            throws WrongPasswordException, WrongIDException {

        boolean foundID = false;
        for (UserAccount user : allUsers) {
            if (user.getID().equals(ID)) {

                if (user.getPassword().equals(password)) {
                    userLoggedIn = user;
                    foundID = true;
                }

                else throw new WrongPasswordException();
            }
        }

        if (!foundID)
            throw new WrongIDException();
    }

    //-----------------------------------------------------------------------------
    public static boolean follow(String userId) {
        for (UserAccount u : allUsers)
            if (userId.equals(u.getID())) {
                if (u.getType().equals(AccountType.PRIVATE)) {
                    followReq(userId);
                    System.out.println("Your request is sent to this user");
                } else {

                    String sqlCom = String.format("INSERT INTO followers (user id, follower id) VALUES ('%s', '%s') ", u.getID(), userLoggedIn.getID());
                    MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                    u.setFollowers(userLoggedIn);
                    userLoggedIn.setFollowing(u);
                    System.out.println("You are following this user from now");
                }
                return true;
            }
        // if user id could not be found
        return false;
    }

    // -----------------------------------------------------------------------------
    public static boolean unfollow(String userId) {
        for (UserAccount u : allUsers)
            if (userId.equals(u.getID())) {
                userLoggedIn.getFollowers().remove(u);
                userLoggedIn.getUnfollowers().add(u);
                return true;
            }
        // if user id could not be found
        return false;
    }

    // -----------------------------------------------------------------------------
    public static ArrayList<UserAccount> didntFollow() {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        for (UserAccount u1 : userLoggedIn.getFollowing()) {
            boolean found = false;
            for (UserAccount u2 : u1.getFollowing())
                if (u2.equals(userLoggedIn)) {
                    found = true;
                    break;
                }
            if (!found)
                accounts.add(u1);
        }

        return accounts;
    }

    // -----------------------------------------------------------------------------
    public static ArrayList<UserAccount> tarafdarHa() {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        for (UserAccount u1 : userLoggedIn.getFollowers()) {
            boolean found = false;
            for (UserAccount u2 : userLoggedIn.getFollowing())
                if (u2.equals(u1)) {
                    found = true;
                    break;
                }
            if (!found)
                accounts.add(u1);
        }

        return accounts;
    }

    // -----------------------------------------------------------------------------
    public static ArrayList<UserAccount> followBack() {
        ArrayList<UserAccount> accounts = new ArrayList<>();

        for (UserAccount u1 : userLoggedIn.getFollowers()) {
            boolean found = false;
            for (UserAccount u2 : userLoggedIn.getFollowing())
                if (u2.equals(u1)) {
                    found = true;
                    break;
                }
            if (found)
                accounts.add(u1);
        }

        return accounts;
    }

    // -----------------------------------------------------------------------------
    public static UserAccount theMostLikes() {
        // element i -> follower i likes
        int[] likesNumber = new int[userLoggedIn.getFollowers().size()];

        for (Post p : userLoggedIn.getPosts())
            for (Like like : p.getLikes())
                for (int i = 0; i < likesNumber.length; i++)
                    if (userLoggedIn.getFollowers().get(i).equals(like.getUser())) {
                        likesNumber[i]++;
                        break;
                    }

        int maxIndex = 0;
        for (int i : likesNumber)
            if (likesNumber[maxIndex] < likesNumber[i])
                maxIndex = i;


        return userLoggedIn.getFollowers().get(maxIndex);
    }

    // -----------------------------------------------------------------------------
    public static UserAccount theMostComments() {
        // element i -> follower i comments
        int[] commentsNumber = new int[userLoggedIn.getFollowers().size()];

        for (Post p : userLoggedIn.getPosts())
            for (Comment c : p.getComments())
                for (int i = 0; i < commentsNumber.length; i++)
                    if (userLoggedIn.getFollowers().get(i).equals(c.getUser())) {
                        commentsNumber[i]++;
                        break;
                    }

        int maxIndex = 0;
        for (int i : commentsNumber)
            if (commentsNumber[maxIndex] < commentsNumber[i])
                maxIndex = i;


        return userLoggedIn.getFollowers().get(maxIndex);
    }

    // -----------------------------------------------------------------------------
    public static boolean deleteComment(int postId, String userId, String text) throws wrongPostID {
        Post post = findPost(postId);
        for (Comment c : post.getComments())
            if (c.getText().equals(text) && c.getUser().getID().equals(userId)) {
                post.getComments().remove(c);
                return true;
            }

        // if comment doesnt exist
        return false;
    }

    //-----------------------------------------------------------------------------
    public static boolean replyComment(String userId, int postId, String comment, String replyText) {
        for (UserAccount user : allUsers)
            if (user.getID().equals(userId))
                for (Post p : user.getPosts())
                    if (p.getPost_id() == postId) for (Comment c : p.getComments())
                        if (c.getText().equals(comment)) {
                            c.setReplies(new Comment(replyText, userLoggedIn));
                            return true;
                        }
        return false;
    }

    // -----------------------------------------------------------------------------
    public static ArrayList<UserAccount> suggestions() {
        ArrayList<UserAccount> accounts = new ArrayList<>();
        ArrayList<UserAccount> followingsOfFollowers = new ArrayList<>();

        for (UserAccount u1 : UserManager.followBack())
            followingsOfFollowers.addAll(u1.getFollowing());

        for (int i = 0; i < followingsOfFollowers.size() - 1; i++)
            for (int j = i + 1; j < followingsOfFollowers.size(); j++)
                if (followingsOfFollowers.get(i).equals(followingsOfFollowers.get(j))) {
                    accounts.add(followingsOfFollowers.get(j));
                    break;
                }

        if (accounts != null)
            return accounts;
        else
            return followingsOfFollowers;
    }

    // kimia -----------------------------------------------------------------------
    public static boolean register(String ID, String name, String birthDay,
                                   String phoneNumber, String password, String bio) {

        boolean registered = false;

        for (UserAccount allUser : allUsers) {
            if (allUser.getID().equals(ID)) {
                registered = true;
                break;
            }
        }

        if (!registered) {
            allUsers.add(new UserAccount(name, birthDay, phoneNumber, ID, password, bio));
            String sqlCom = String.format("INSERT INTO account (type, name, birth date, phone num, id, pass, bio) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s') ", "PUBLIC", name, birthDay, phoneNumber, ID, password, bio);
            MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);
            registered = true;
        } else
            registered = false;

        return registered;
    }

    public static void likeCmUnfollowers() {

        boolean found = false;


        for (Post post : userLoggedIn.getPosts()) {
            for (Like like : post.getLikes()) {
                for (UserAccount follower : userLoggedIn.getFollowers())
                    if (!follower.getID().equals(like.getUser().getID())) {
                        found = true;
                        break;
                    }
                if (!found)
                    System.out.println(like.getUser().getID());
                else
                    found = false;
            }
        }


        for (Post post : userLoggedIn.getPosts()) {
            for (Comment cm : post.getComments()) {
                for (UserAccount follower : userLoggedIn.getFollowers())
                    if (!follower.getID().equals(cm.getUser().getID())) {
                        found = true;
                        break;
                    }
                if (!found)
                    System.out.println(cm.getUser().getID());
                else
                    found = false;
            }
        }
    }

    public static boolean followReq(String ID) {

        boolean found = false;

        for (UserAccount allUser : allUsers) {
            if (allUser.getID().equals(ID)) {
                allUser.setRequests(userLoggedIn);

                String sqlCom = String.format("INSERT INTO follow req (user id, follower id) VALUES ('%s', '%s') ", ID, userLoggedIn.getID());
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                found = true;
                break;
            }
        }
        return found;
    }

    public static boolean addSavedPost(int post_id) {

        boolean found = false;

        for (Post allPost : PostManager.allPosts) {
            if (allPost.getPost_id() == post_id) {

                String sqlCom = String.format("INSERT INTO saved posts (post id, user id) VALUES ('%s', '%s') ", post_id, userLoggedIn.getID());
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                userLoggedIn.setSavedPost(allPost);
                found = true;
                break;
            }
        }

        return found;
    }

    public static boolean like(int post_id) {

        boolean found = false;

        for (Post allPost : PostManager.allPosts) {
            if (allPost.getPost_id() == post_id) {

                String sqlCom = String.format("INSERT INTO like (isliked, user id, post id) VALUES ('%s', '%s', '%s') ", true, userLoggedIn.getID(), post_id);
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                allPost.setLikeNumber(allPost.getLikeNumber() + 1);
                allPost.setLikes(userLoggedIn, true);
                found = true;
                break;
            }
        }

        return found;
    }

    public static boolean disLike(int post_id) {

        boolean found = false;

        for (Post allPost : PostManager.allPosts) {
            if (allPost.getPost_id() == post_id) {

                String sqlCom = String.format("INSERT INTO like (isliked, user id, post id) VALUES ('%s', '%s', '%s') ", false, userLoggedIn.getID(), post_id);
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                allPost.setDislikeNumber(allPost.getDislikeNumber() + 1);
                allPost.setLikes(userLoggedIn, false);
                found = true;
                break;
            }
        }

        return found;
    }

    public static void acceptReq(String userID) {

        for (UserAccount allUser : allUsers) {
            if (allUser.getID().equals(userID)){

                String sqlCom = String.format("INSERT INTO followers (user id, follower id) VALUES ('%s', '%s') ", userLoggedIn.getID(), userID);
                MySQLConnection.mySQLConnection.ExecuteSQL(sqlCom);

                userLoggedIn.setFollowers(allUser);
                allUser.setFollowing(userLoggedIn);

                break;
            }
        }

    }

    //====================================================melika===========================
    public static void print(Object o) {
        System.out.println(o);
    }

    public static List<UserAccount> userNotLiked() {
        List<UserAccount> FoundUsers = new ArrayList<>();
        boolean hasEverLiked = false;

        for (UserAccount user : userLoggedIn.getFollowers())
            for (Post eachPost : userLoggedIn.getPosts()) {
                for (Like like : eachPost.getLikes()) {
                    if (like.getUser() == user)
                        hasEverLiked = true;
                }
                if (hasEverLiked)
                    FoundUsers.add(user);
                hasEverLiked = false;
            }

        return FoundUsers;
    }

    public static List<UserAccount> userNotCommented() {

        List<UserAccount> FoundUsers = new ArrayList<>();
        boolean hasEverCommented = false;

        for (UserAccount user : userLoggedIn.getFollowers())
            for (Post eachPost : userLoggedIn.getPosts()) {
                for (Comment comment : eachPost.getComments()) {
                    if (comment.getUser() == user)
                        hasEverCommented = true;
                }
                if (hasEverCommented)
                    FoundUsers.add(user);
                hasEverCommented = false;
            }

        return FoundUsers;
    }

    public static void deleteUser(UserAccount user) {
        //delete from all users
        int index = 0;
        for (UserAccount eachUser : allUsers) {
            if (user == eachUser) {
                allUsers.remove(index);
                break;
            }
            ++index;
        }

        //delete all the user comments and likes
        for (UserAccount other_user : allUsers) {
            for (Post post : other_user.getPosts()) {
                //deleting the user comments
                index = 0;
                for (Comment comment : post.getComments()) {
                    if (comment.getUser() == other_user)
                        post.getComments().remove(index);
                    ++index;
                }
                //deleting likes
                index = 0;
                for (Like like : post.getLikes()) {
                    if (like.getUser() == other_user) {
                        post.getLikes().remove(index);
                        if (like.isLike())
                            post.setLikeNumber(post.getLikeNumber() - 1);
                        else
                            post.setDislikeNumber(post.getDislikeNumber() - 1);
                    }
                    ++index;
                }
            }
        }

    }

    public static void UserEditProfile() throws DuplicateID {
        //order and change always be the same sizes

        Scanner sc = new Scanner(System.in);
        print("please enter NAME/BIO/PHONE NUMBER/BIRTH DATE/PASSWORD/TYPE/ID then the new change afterwards ");

        String takeOrder = sc.nextLine();

        String[] orders = takeOrder.split("\\s");


        switch (orders[0]) {
            case "NAME":
                userLoggedIn.setName(orders[1]);
                break;
            case "BIO":
                userLoggedIn.setBio(orders[1]);
                break;
            case "PHONE NUMBER":
                userLoggedIn.setPhoneNumber(orders[1]);
                break;
            case "BIRTH DATE":
                userLoggedIn.setBirthDate(orders[1]);
                break;
            case "PASSWORD":
                userLoggedIn.setPassword(orders[1]);
                break;
            case "TYPE":
                userLoggedIn.setType(AccountType.valueOf(orders[1]));
                break;
            case "ID":
                checkID(orders[1]);
                userLoggedIn.setID(orders[1]);
                break;
        }

        print("changed successfully !");

    }

    public static void checkID(String ID) throws DuplicateID {
        for (UserAccount user : allUsers) {
            if (user.getID().equals(ID))
                throw new DuplicateID();
        }
    }

    public static void postOrganizing(UserAccount user) {
        //organizing from the most liked to the least
        Collections.sort(user.getPosts());

        for (Post post : user.getPosts())
            print(post.toString());

    }

    public static void addPost(String Text) {
        Post new_post = new Post(Text, userLoggedIn);
        PostManager.allPosts.add(new_post);
        userLoggedIn.setPosts(new_post);
        print("added successfully !");

        print("post id: " + new_post.getPost_id());
    }

    public static void editPost(String Text, int id) throws wrongPostID {
        //we take the string from the graphic

        Post post = findPost(id);
        if (post == null)
            return;

        post.setText(Text);

        Notification coomentNotif = new Notification(userLoggedIn, post, "one post from " + userLoggedIn.getID() +
                "that you put a comment on ,has been edited");
        for (Comment comment : post.getComments())
            comment.getUser().setNotifications(coomentNotif);

        Notification LikeNotif = new Notification(userLoggedIn, post, "one post from " + userLoggedIn.getID() +
                "that you Liked/disliked ,has been edited");

        for (Like like : post.getLikes())
            like.getUser().setNotifications(LikeNotif);

    }

    public static void deletePost(int id) throws wrongPostID {
        Post post = findPost(id);
        PostManager.allPosts.remove(post);
        userLoggedIn.getPosts().remove(post);

        print("post removed !");
    }

    public static Post findPost(int id) throws wrongPostID {


        for (Post post1 : userLoggedIn.getPosts()) {
            if (post1.getPost_id() == id) {
                return post1;
            }
        }
        throw new wrongPostID();
    }

    public static void lostFollowers() {
        print("people who unfollowed you since the last check are as below: ");
        if (userLoggedIn.getUnfollowed_you().size() == 0) {
            print("nobody unfollowed you ");
            return;
        }

        for (UserAccount user : userLoggedIn.getUnfollowed_you())
            print(user.getID());

        print("======================================");

        userLoggedIn.getUnfollowed_you().removeAll(userLoggedIn.getUnfollowed_you());

    }

    public static void unfollowings() {
        print("people you unfollowed recently: ");
        if (userLoggedIn.getUnfollowed_you().size() == 0) {
            print("nobody ");
            return;
        }

        for (UserAccount user : userLoggedIn.getUnfollowers())
            print(user.getID());

        print("======================================");

        userLoggedIn.getUnfollowers().removeAll(userLoggedIn.getUnfollowers());

    }


    //=============================================melika============================================
}
