package persistencepackage;

import datapackage.PersistenceManager;

public class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private static int nextPersonUID = 0;

    public Persistence() {
        /// QUERY DB FOR ALL ITEMS
        queryDB();
    }

    private static void queryDBforNextPersonUID()
    {
        /// TODO: QUERY DB FOR THE NEXT PERSON UI
        nextPersonUID++;
    }

    private static void queryDB()
    {
        System.out.println("Calling Database Connection...");
        new PersistenceManager();
    }

    public static int getNextPersonUID()
    {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

}
