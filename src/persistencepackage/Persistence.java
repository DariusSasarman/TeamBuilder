package persistencepackage;


import datapackage.Bond;
import datapackage.Group;
import datapackage.Model;
import datapackage.Person;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;


public  class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private static int nextPersonUID = 0;
    private static int nextBondUID = 0;
    private static int nextGroupUID = 0;

    private static LoginManager loginManager;
    private static User currentUser;

    public Persistence() {
        /// TODO: FIGURE OUT HOW TO STORE THE DATABASE CONNECTION

        loginManager = new LoginManager();
        currentUser = loginManager.login();
        if(currentUser != null)
        {
            queryDB();
        }
    }

    public static void queryDB() {
        Model.clearInfo();
        System.out.println("Logging in : " + currentUser.getUsername());
        /// TODO: QUERY DB TO CAPTURE EXISTING DATA
        System.out.println("Calling Database Connection...");
        /// TODO: READ ALL DATA based on USER
        System.out.println("Connection secured!");


        System.out.println("Loading all data in memory...");
        /// TODO: UPLOAD ALL DATA in memory using Model.addXXXXX();
        System.out.println("Done! Information loaded.");

        BufferedImage img = null;

        // Robin
        try {
            URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=Robin&backgroundColor=ffcc00");
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.addPerson(new Person(getNextPersonUID(), img, "Robin"));

        // Starfire
        try {
            URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=Starfire&backgroundColor=ff9966");
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.addPerson(new Person(getNextPersonUID(), img, "Starfire"));

        // Beast Boy
        try {
            URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=BeastBoy&backgroundColor=66cc66");
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.addPerson(new Person(getNextPersonUID(), img, "Beast Boy"));

        // Raven
        try {
            URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=Raven&backgroundColor=6666cc");
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.addPerson(new Person(getNextPersonUID(), img, "Raven"));

        // Cyborg
        try {
            URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=Cyborg&backgroundColor=cccccc");
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.addPerson(new Person(getNextPersonUID(), img, "Cyborg"));

        // Add bonds between team members
        Model.addBond(new Bond(getNextBondUID(), 0, 1, 10)); // Robin - Starfire
        Model.addBond(new Bond(getNextBondUID(), 2, 3, 8));  // Beast Boy - Raven
        Model.addBond(new Bond(getNextBondUID(), 0, 4, 9));  // Robin - Cyborg

        // Create Teen Titans group
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0); // Robin
        list.add(1); // Starfire
        list.add(2); // Beast Boy
        list.add(3); // Raven
        list.add(4); // Cyborg
        Model.addGroup(new Group(getNextGroupUID(), "Teen Titans", list));
        Model.setActiveGroupId(0);
    }

    public static void changeAccount(String username, String password) {
        User newUser = loginManager.changeAccount(username, password);
        if (newUser != null) {
            currentUser = newUser;
            queryDB();
        }
    }

    /// Person queries

    private static void queryDBforNextPersonUID() {
        /// TODO: QUERY DB FOR THE NEXT PERSON UI
        nextPersonUID++;
    }

    public static int getNextPersonUID() {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

    public static void createPersonOnDb(Person P) {
        /// TODO: Query DB for CRUD create Operation
    }

    public static void deletePersonOnDb(int id) {
        /// TODO: Query DB for CRUD delete Operation
    }

    public static Person readPerson(int id) {
        /// TODO : Query DB for CRUD read Operation
        return new Person(-1, new BufferedImage(10, 10, 10), new String("Not yet"));
    }

    public static void updatePersonName(int id, String newName) {
        /// TODO: Query DB for CRUD update Operation
    }

    public static void updatePersonNotes(int id, String newNotes) {
        /// TODO: Query DB for CRUD update Operation
    }

    public static void updatePersonImage(int id, BufferedImage newImage) {
        /// TODO: Query DB for CRUD update Operation
    }

    /// Bond queries

    private static void queryDBforNextBondUID() {
        /// TODO: Query db for this
        nextBondUID++;
    }

    public static int getNextBondUID() {
        int returnValue = nextBondUID;
        queryDBforNextBondUID();
        return returnValue;
    }

    public static Bond readBond(int id) {
        /// TODO: this.
        return new Bond(-1, -1, -1, -1);
    }

    public static void createBondOnDb(Bond b)
    {
        /// TODO: guess what
    }

    public static void updateBondRating(int id, int newRating) {
        /// TODO:....
    }

    public static void updateBondNotes(int id, String notes) {
        /// TODO: hihihi
    }

    public static void deleteBondOnDb(int id) {
        /// TODO: hahaha
    }

    /// Group queries
    private static void queryDBforNextGroupUID() {
        /// TODO: Query db for this
        nextGroupUID++;
    }

    public static int getNextGroupUID() {
        int returnValue = nextGroupUID;
        queryDBforNextGroupUID();
        return returnValue;
    }

    public static void createGroupOnDB(Group group) {
        /// TODO : This.
    }

    public static Group readGroup(int id) {
        /// TODO : This.
        return new Group(-1,"");
    }

    public static void updateGroupTitle(int id, String title) {
        /// TODO : This.
    }

    public static void updateGroupPersonIds(int id, ArrayList<Integer> personIds) {
        /// TODO: This
    }

    public static void deleteGroupOnDb(int id) {
        /// TODO: This
    }

}
