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

        BufferedImage img = null;


        // Define person data
        String[] names = {"Alex", "Jordan", "Maya", "Sam", "Taylor", "Casey", "Riley", "Morgan", "Cameron", "Drew"};
        String[] colors = {"ff6b6b", "4ecdc4", "ffe66d", "a8e6cf", "dda15e", "bc6c25", "b8b8ff", "ffaaa5", "95e1d3", "f38181"};

        // Create all persons
        for (int i = 0; i < names.length; i++) {
            try {
                URL url = new URL("https://api.dicebear.com/7.x/adventurer/png?seed=" + names[i] + "&backgroundColor=" + colors[i]);
                img = ImageIO.read(url);
                Model.addPerson(new Person(getNextPersonUID(), img, names[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Add all bonds
        for (int i = 0; i<11; i++) {
            Model.addBond(new Bond(getNextBondUID(), (int) (Math.random()*names.length), (int)(Math.random()* names.length), (int)(Math.random()*10)));
        }

        // Create group with all members
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            list.add(i);
        }
        Model.addGroup(new Group(getNextGroupUID(), "Adventure Squad", list));
        Model.setActiveGroupId(0);

        System.out.println("Done! Information loaded.");
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
