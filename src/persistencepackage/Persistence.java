package persistencepackage;

import datapackage.PersistenceManager;

import java.awt.image.BufferedImage;

public class Persistence {

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
        new PersistenceManager();
    }

    public static int getNextPersonUID()
    {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

    public static void createPersonOnDb(int id, BufferedImage image, String name, String notes)
    {
        /// TODO: Query DB for CRUD create Operation
    }

    public static void deletePersonOnDb(int id)
    {
        /// TODO: Query DB for CRUD delete Operation
    }
}
