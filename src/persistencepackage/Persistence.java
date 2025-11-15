package persistencepackage;


import datapackage.Bond;
import datapackage.Group;
import datapackage.Person;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public  class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private static int nextPersonUID = 0;
    private static int nextBondUID = 0;
    private static int nextGroupUID = 0;


    public Persistence() {
        /// TODO: FIGURE OUT HOW TO STORE THE DATABASE CONNECTION
        queryDB();
    }

    private static void queryDB() {
        /// TODO: QUERY DB TO CAPTURE EXISTING DATA
        System.out.println("Calling Database Connection...");
        System.out.println("Done! Information loaded.");
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
        queryDBforNextBondUID();
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
