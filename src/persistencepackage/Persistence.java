package persistencepackage;


import java.awt.image.BufferedImage;

public class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private  int nextPersonUID = 0;

    public Persistence() {
        /// TODO: FIGURE OUT HOW TO STORE THE DATABASE CONNECTION
        queryDB();
    }

    private void queryDBforNextPersonUID()
    {
        /// TODO: QUERY DB FOR THE NEXT PERSON UI
        nextPersonUID++;
    }

    private  void queryDB()
    {
        /// TODO: QUERY DB TO CAPTURE EXISTING DATA
        System.out.println("Calling Database Connection...");
        System.out.println("Done! Information loaded.");
    }

    public int getNextPersonUID()
    {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

    public  void createPersonOnDb(int id, BufferedImage image, String name, String notes)
    {
        /// TODO: Query DB for CRUD create Operation
    }

    public void deletePersonOnDb(int id)
    {
        /// TODO: Query DB for CRUD delete Operation
    }

    public void updatePersonName(int id, String newName)
    {
        /// TODO: Query DB for CRUD update Operation
    }

    public void updatePersonNotes(int id, String newNotes)
    {
        /// TODO: Query DB for CRUD update Operation
    }

    public void updatePersonImage(int id, BufferedImage newImage)
    {
        /// TODO: Query DB for CRUD update Operation
    }
}
