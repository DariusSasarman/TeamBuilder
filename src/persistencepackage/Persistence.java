package persistencepackage;


import datapackage.Person;

import java.awt.image.BufferedImage;

public  class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private static int nextPersonUID = 0;

    public Persistence() {
        /// TODO: FIGURE OUT HOW TO STORE THE DATABASE CONNECTION
        queryDB();
    }

    private static void queryDBforNextPersonUID()
    {
        /// TODO: QUERY DB FOR THE NEXT PERSON UI
        nextPersonUID++;
    }

    private static void queryDB()
    {
        /// TODO: QUERY DB TO CAPTURE EXISTING DATA
        System.out.println("Calling Database Connection...");
        System.out.println("Done! Information loaded.");
    }

    public static int getNextPersonUID()
    {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

    public static  void createPersonOnDb(Person P)
    {
        /// TODO: Query DB for CRUD create Operation
    }

    public static void deletePersonOnDb(int id)
    {
        /// TODO: Query DB for CRUD delete Operation
    }

    public static void updatePersonName(int id, String newName)
    {
        /// TODO: Query DB for CRUD update Operation
    }

    public static void updatePersonNotes(int id, String newNotes)
    {
        /// TODO: Query DB for CRUD update Operation
    }

    public static void updatePersonImage(int id, BufferedImage newImage)
    {
        /// TODO: Query DB for CRUD update Operation
    }
}
