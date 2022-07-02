package Main;

import Exceptions.DuplicateID;
import Exceptions.wrongPostID;

import java.util.Scanner;

public class UserMenu {

    static Scanner sc = new Scanner(System.in);

    public static void menu() {

        boolean exit = true;

        while (exit) {
            System.out.println("--- User Menu ---");
            System.out.println("[1] Add Request");
            System.out.println("[2] Edit Info");
            System.out.println("[3] New Post");
            System.out.println("[4] Remove Post");
            System.out.println("[5] Edit Post");
            System.out.println("[6] Reply Comment");
            System.out.println("[7] Delete Comment");
            System.out.println("[8] Your Saved Post");
            System.out.println("[9] Follow Nakonandegan");
            System.out.println("[10] Tarafdarha");
            System.out.println("[11] Follow backs");
            System.out.println("[12] Recently Unfollowers");
            System.out.println("[13] Your Recently Unfollows");
            System.out.println("[14] Sort Posts (by likes)");
            System.out.println("[15] Sort Posts (by comments)");
            System.out.println("[16] The Most Hated Post");
            System.out.println("[17] The Most Popular Post");
            System.out.println("[18] Follower With The Most Like");
            System.out.println("[19] Follower With The Most Comment");
            System.out.println("[20] Followers With No Likes");
            System.out.println("[21] Followers With No Comments");
            System.out.println("[22] Unfollowers"); // 16
            System.out.println("[23] Your Requests");
            System.out.println("[24] Explore");
            System.out.println("[25] Post Menu");
            System.out.println("[26] Follow");
            System.out.println("[27] Unfollow");
            System.out.println("[28] EXIT");

            int n = sc.nextInt();

            switch (n) {
                case 1:
                    System.out.println("Enter the user's id:");
                    if (UserManager.followReq(sc.next()))
                        System.out.println("Your request is sent to user");
                    else
                        System.out.println("This user id is wrong");
                    break;

                case 2:
                    try {
                        UserManager.UserEditProfile();
                    } catch (DuplicateID e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 3:
                    System.out.println("Enter text of post:");
                    sc.next();
                    UserManager.addPost(sc.nextLine());
                    break;

                case 4:
                    try {
                        System.out.println("Please enter post's id:");
                        UserManager.deletePost(sc.nextInt());
                    } catch (wrongPostID e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.println("Enter new text for the post and post id:");
                        sc.next();
                        UserManager.editPost(sc.nextLine(), sc.nextInt());
                    } catch (wrongPostID e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Reply Comment");
                    System.out.println("Enter user id, post id, the comment you want to reply and reply text");
                    if (UserManager.replyComment(sc.next(), sc.nextInt(), sc.next(), sc.next()))
                        System.out.println("ok");
                    else
                        System.out.println("WRONG INFORMATION");
                    break;

                case 7:
                    try {
                        System.out.println("Enter your post id, user id and comment text");
                        int postId = sc.nextInt();
                        sc.next();
                        String userid = sc.next();
                        String text = sc.nextLine();

                        if (UserManager.deleteComment(postId, userid, text))
                            System.out.println("Comment deleted successfully!");
                        else
                            System.out.println("This comment with this information doesnt exist");

                    } catch (wrongPostID e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    System.out.println("Your Saved Posts");
                    for (Post p : UserManager.userLoggedIn.getSavedPost())
                        System.out.println(p);
                    break;

                case 9:
                    System.out.println("people you followed but they didnt follow you:");
                    for (UserAccount u : UserManager.didntFollow())
                        System.out.println(u);
                    break;

                case 10:
                    System.out.println("Your tarafdars:");
                    for (UserAccount u : UserManager.tarafdarHa())
                        System.out.println(u);
                    break;

                case 11:
                    System.out.println("both followed each other:");
                    for (UserAccount u : UserManager.followBack())
                        System.out.println(u);
                    break;

                case 12:
                    System.out.println("Recently unfollows: ");

                    break;

                case 13:

                    break;

                case 14:
                    System.out.println("Sort Posts (by likes)");
                    UserManager.postOrganizing(UserManager.userLoggedIn);
                    break;

                case 15:
                    // todo
                    System.out.println("Sort Posts (by comments)");
                    UserManager.postOrganizing(UserManager.userLoggedIn);
                    break;

                case 16:
                    System.out.println("The Most Hated Post:");
                    PostManager.unpopularPosts();
                    break;

                case 17:
                    System.out.println("The Most Popular Post:");
                    // ???
                    break;

                case 18:
                    System.out.println("Follower With The Most Like:");
                    System.out.println(UserManager.theMostLikes());
                    break;

                case 19:
                    System.out.println("Follower With The Most Comments:");
                    System.out.println(UserManager.theMostComments());
                    break;

                case 20:
                    System.out.println("Followers With No Likes");
                    for (UserAccount u : UserManager.userNotLiked())
                        System.out.println(u);
                    break;

                case 21:
                    System.out.println("Followers With No Comments");
                    for (UserAccount u : UserManager.userNotCommented())
                        System.out.println(u);
                    break;

                case 22:
                    for (UserAccount user : UserManager.userLoggedIn.getUnfollowers())
                        System.out.println(user.getID());
                    break;

                case 23:
                    System.out.println("Requests:");
                    for (UserAccount request : UserManager.userLoggedIn.getRequests())
                        System.out.println(request);
                    // todo : accept or remove
                    break;

                case 24:
                    PostManager.explore();
                    break;

                case 25:
                    PostMenu.menu();
                    break;

                case 26:
                    System.out.println("Enter id");
                    UserManager.follow(sc.next());
                    break;

                case 27:
                    System.out.println("Enter id");
                    if(UserManager.unfollow(sc.next()))
                    System.out.println("unfollowed successfully");
                    break;

                case 28:
                    exit = false;
                    break;

                default:
                    System.out.println("Error");
                    break;
            }
        }
    }
}
