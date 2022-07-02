package Main;

import java.util.Scanner;

public class PostMenu {

    static Scanner sc = new Scanner(System.in);

    public static void menu() {

        boolean exit = true;

        while (exit) {
            System.out.println("--- Post Menu ---");
            System.out.println("[1] Comment");
            System.out.println("[2] like");
            System.out.println("[3] dislike");
            System.out.println("[4] Search");
            System.out.println("[5] Add to saved post");
            System.out.println("[6] Exit");

            int n = sc.nextInt();

            switch (n) {
                case 1:
                    comment();
                    break;

                case 2:
                    like();
                    break;

                case 3:
                    dislike();
                    break;

                case 4:
                    search();
                    break;

                case 5:
                    addSavedPost();
                    break;

                case 6:
                    exit = false;
                    break;

                default:
                    System.out.println("Error");
                    break;

            }
        }
    }

    public static void checkToFollow(int post_id){

        String ID = UserManager.userLoggedIn.getID();

        if (!PostManager.checkToFollow(post_id, ID)){
            System.out.println("You have not followed the account of the owner of this post");
            System.out.println("Select number\n1.follow\n2.ignore");
            int n = sc.nextInt();

            switch (n) {
                case 1:
                    UserManager.follow(PostManager.search(post_id).getUser_Post().getID());
                    break;

                case 2:
                    break;

                default:
                    System.out.println("Error");
                    break;

            }
        }
    }

    public static void search(){

        System.out.println("Enter post id:");

        int p_id = sc.nextInt();

        if (PostManager.search(p_id).equals(null))
            System.out.println("Post does not exist");
        else {
            System.out.println(PostManager.search(p_id).toString());
            checkToFollow(p_id);
        }
    }

    public static void addSavedPost(){

        System.out.println("Enter post id:");
        int p_id = sc.nextInt();
        boolean found = UserManager.addSavedPost(p_id);
        if (found) {
            System.out.println(PostManager.search(p_id).toString() + " added to saved post successfully");
            checkToFollow(p_id);
        }
        else
            System.out.println("Post does not found");
    }

    public static void like(){

        System.out.println("Enter post id:");
        int p_id = sc.nextInt();
        boolean found = UserManager.like(p_id);
        if (found) {
            System.out.println(PostManager.search(p_id).toString() + " liked");
            checkToFollow(p_id);
        }
        else
            System.out.println("Post does not found");
    }

    public static void dislike(){

        System.out.println("Enter post id:");
        int p_id = sc.nextInt();
        boolean found = UserManager.disLike(p_id);
        if (found) {
            System.out.println(PostManager.search(p_id).toString() + " disliked");
            checkToFollow(p_id);
        }
        else
            System.out.println("Post does not found");
    }

    public static void comment() {

        System.out.println("Enter post id and comment text:");
        int p_id = sc.nextInt();
        boolean found = PostManager.comment(p_id, sc.next(), UserManager.userLoggedIn);
        if (found) {
            System.out.println("You left a comment for " + PostManager.search(p_id).toString());
            checkToFollow(p_id);
        }
        else
            System.out.println("Post does not found");
    }
}