package Files;

import Main.UserAccount;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import static Main.UserManager.allUsers;



public class writing {


    private static String MainAddress = "D:\\Instagram Simulation\\all users\\";

    //using for a full saving
    public static void saveAll() throws Exception {
        for (UserAccount account : allUsers)
            WriteUserInfo(account);

    }


    //using for special savings
    public static void WriteUserInfo(UserAccount user) throws IOException {

        //the ones whom the user unfollowed them himself
        FileOutputStream write1 = new FileOutputStream(MainAddress + user.getID() + "\\recent unfollowing");
        ObjectOutputStream write2 = new ObjectOutputStream(write1);


        for (UserAccount eachUser : user.getUnfollowers())
            write2.writeObject(eachUser);

        write2.close();
        write1.close();


        //people who unfollowed the user
        write1 = new FileOutputStream(MainAddress + user.getID() + "\\unfollowed you");
        write2 = new ObjectOutputStream(write1);


        for (UserAccount eachUser : user.getUnfollowed_you())
            write2.writeObject(eachUser);


        write2.close();
        write1.close();
    }
}
