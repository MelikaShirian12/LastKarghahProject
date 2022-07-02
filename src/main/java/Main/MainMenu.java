package Main;

import java.util.Scanner;

public class MainMenu {
    static Scanner sc = new Scanner(System.in);

    public static void menu() {
        while (true) {
            System.out.println("--- Main Menu ---");
            System.out.println("[1] Login");
            System.out.println("[2] Register");
            System.out.println("[3] Save Data");
            System.out.println("[4] Exit");

            int n = sc.nextInt();

            switch (n) {
                case 1:
                    login();
                    break;

                case 2:
                    register();
                    break;

                case 3:
                    // todo
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Error");
                    break;

            }
        }
    }

    private static void login() {
        System.out.println("Enter id and password:");
        Scanner scc=new Scanner(System.in);
        String id=scc.nextLine();
        String pass=scc.nextLine();

        for (UserAccount u : UserManager.allUsers)
            if (u.getID().equals(id) && u.getPassword().equals(pass)) {
                UserManager.userLoggedIn=u;
                UserMenu.menu();
                return;
            }
        System.out.println("Wrong id or password"); // todo exception
    }

    private static void register() {
        System.out.println("Please enter required information for signup:");
        System.out.println("1. id, 2. name, 3. birthdate, 4. phone number, 5. password, 6. bio");

        if (UserManager.register(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next()))
            System.out.println("Register Successfully!");
        else
            System.out.println("This id is not available");

    }
}
