package Files;

import Main.UserAccount;
import Main.UserManager;

import java.io.*;


//this function will be used when the user wants the unfollowing or unfollowed
public class reading {

    private static String MainAddress = "D:/Instagram Simulation/all users/";


    public static void readAll() throws IOException ,ClassNotFoundException
    {
        String[] files_name;
        File file = new File("D:/Instagram Simulation/all users");

        files_name = file.list();

        int find=0;
        for (String id : files_name)
        {
            for (UserAccount users : UserManager.allUsers)
            {
                if (find==1)
                    continue;

                if (id.equals(users.getID()))
                {
                    readUserInfo_unfollowed(users);
                    readUserInfo_unfollowing(users);
                    find=1;
                }
            }
        }
    }


    //the ones the user unfollowed them himself recently
    public static void readUserInfo_unfollowing(UserAccount user) throws IOException ,ClassNotFoundException
    {
        FileInputStream read1=new FileInputStream(MainAddress+user.getID()+"/recent unfollowing");
        ObjectInputStream read2=new ObjectInputStream(read1);

        while (read1.available()!=0)
            user.getUnfollowers().add((UserAccount) read2.readObject());

        read2.close();
        read1.close();
    }

    public static void readUserInfo_unfollowed(UserAccount user) throws IOException ,ClassNotFoundException
    {
        FileInputStream read1=new FileInputStream(MainAddress+user.getID()+"/unfollowed you");
        ObjectInputStream read2=new ObjectInputStream(read1);

        while (read1.available()!=0)
            user.getUnfollowed_you().add((UserAccount) read2.readObject());

        read2.close();
        read1.close();
    }
}
