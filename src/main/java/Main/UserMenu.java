package Main;

import Exceptions.DuplicateID;
import Exceptions.wrongPostID;

import java.util.Scanner;

public class UserMenu {

    static Scanner sc = new Scanner(System.in);

    public static void menu() {
        while (true) {
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
            System.out.println("[12] Recently Unfollowed");
            System.out.println("[13] lost followers");
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

                    break;

                case 7:
                    try {
                        System.out.println("Enter your post id, user id and comment text");
                        int postId= sc.nextInt();
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
                    for (Post p: UserManager.userLoggedIn.getSavedPost())
                        System.out.println(p);
                    break;

                case 9:
                    System.out.println("people you followed but they didnt follow you:");
                    for (UserAccount u: UserManager.didntFollow())
                        System.out.println(u);
                    break;

                case 10:
                    System.out.println("Your tarafdars:");
                    for (UserAccount u: UserManager.tarafdarHa())
                        System.out.println(u);
                    break;

                case 11:
                    System.out.println("both followed each other:");
                    for (UserAccount u: UserManager.followBack())
                        System.out.println(u);
                    break;

                case 12:
                    UserManager.unfollowings();
                    break;

                case 13:
                    UserManager.lostFollowers();
                    break;

                case 14:

                    break;

                case 15:

                    break;

                case 16:

                    break;

                case 17:

                    break;

                case 18:

                    break;

                case 19:

                    break;

                case 20:

                    break;

                case 21:

                    break;

                case 22:

                    break;

                case 23:

                    break;

                case 24:
                    PostManager.explore();
                    break;

                default:
                    System.out.println("Error");
                    break;
            }
        }
    }
}
