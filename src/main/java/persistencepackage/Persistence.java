package persistencepackage;


import datapackage.Bond;
import datapackage.Group;
import datapackage.Model;
import datapackage.Person;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class Persistence {

    /**
     * ALL INTERACTIONS WITH DATABASE ARE DONE HERE
     */

    private static Connection dataBaseConnection ;

    private static int nextPersonUID = 0;
    private static int nextBondUID = 0;
    private static int nextGroupUID = 0;

    private static LoginManager loginManager;
    private static User currentUser;

    public Persistence() {
        loadConnection();
        while(currentUser == null)
        {
            loginManager = new LoginManager(dataBaseConnection);
            try
            {
                currentUser = loginManager.login();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(null,"Error connecting to database:" + e.getMessage());
            }
        }
        try
        {
            queryDB();
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null,"Error connecting to database: " + e.getMessage());
        }

    }

    public static void changeAccount(String username, String password) {
        try
        {
            User newUser = loginManager.changeAccount(username, password);
            if (newUser != null) {
                currentUser = newUser;
                queryDB();
            }
            else {
                throw new SQLException("Account doesn't exist.");
            }
        }
        catch (SQLException | IOException e)
        {
            JOptionPane.showMessageDialog(null,"Couldn't change account: " + e.getMessage());
        }
    }

    private static void loadConnection()
    {
        System.out.println("Calling Database Connection...");
        try {
            InputStream input = Persistence.class
                    .getClassLoader()
                    .getResourceAsStream("db/database.properties");

            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }

            prop.load(input);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            dataBaseConnection = DriverManager.getConnection(url,user,password);
            System.out.println("Connection secured! " + dataBaseConnection.getMetaData());

            input.close();
        } catch (IOException | RuntimeException | SQLException e) {
            JOptionPane.showMessageDialog(null,"Error connecting to database: " + e.getMessage());
        }
    }

    public static void queryDB() throws SQLException, IOException {
        Model.clearInfo();
        System.out.println("Logging in : " + currentUser.getUsername());
        System.out.println("Loading all data in memory...");
        loadPersonInfo();
        loadBondInfo();
        loadGroupInfo();

        System.out.println("Done! Information loaded.");
    }


    /// Person queries

    private static void loadPersonInfo() throws SQLException, IOException {
        String query = "SELECT * FROM public.persons WHERE person_owner_id=?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,currentUser.getUID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            Integer id = resultSet.getInt("person_id");
            String name= resultSet.getString("person_name");
            String notes = resultSet.getString("person_notes");
            byte[] imageBytes = resultSet.getBytes("person_image");
            BufferedImage image = null;
            if (imageBytes != null) {
                try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
                    image = ImageIO.read(bais);
                }
            }
            Person add = new Person(id,image,name,notes);
            Model.addPerson(add);
        }
    }

    private static void queryDBforNextPersonUID() throws SQLException {
        String query = "SELECT nextval(pg_get_serial_sequence('public.persons','person_id')) AS new_id";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
        {
            nextPersonUID = resultSet.getInt("new_id");
        }
    }

    public static int getNextPersonUID() throws SQLException {
        int returnValue = nextPersonUID;
        queryDBforNextPersonUID();
        return returnValue;
    }

    public static void createPersonOnDb(Person P) throws SQLException, IOException {
        String query = "INSERT INTO public.persons " +
                "(person_id,person_name,person_image,person_notes,person_owner_id)" +
                "VALUES (?,?,?,?,?)";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,P.getId());
        statement.setString(2,P.getName());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(P.getImage(),"png",baos);
        byte[] bytes = baos.toByteArray();
        statement.setBytes(3,bytes);
        statement.setString(4,P.getNotes());
        statement.setInt(5,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void deletePersonOnDb(int id) throws SQLException {
        String query = "DELETE FROM public.persons " +
                " WHERE person_id = ? AND person_owner_id = ?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,id);
        statement.setInt(2,currentUser.getUID());
        statement.executeUpdate();
    }

    public static Person readPerson(int id) {
        String query = "SELECT * FROM public.persons WHERE person_owner_id=? AND person_id=?";
        Person ret = null;
        try
        {
            PreparedStatement statement = dataBaseConnection.prepareStatement(query);
            statement.setInt(1,currentUser.getUID());
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                String name= resultSet.getString("person_name");
                String notes = resultSet.getString("person_notes");
                byte[] imageBytes = resultSet.getBytes("person_image");
                BufferedImage image = null;
                if (imageBytes != null) {
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
                        image = ImageIO.read(bais);
                    }
                }
                ret = new Person(id,image,name,notes);
            }
        }
        catch (SQLException | IOException e)
        {
            JOptionPane.showMessageDialog(null,"Error loading person: "+e.getMessage());
        }
        return ret;
    }

    public static void updatePersonName(int id, String newName) throws SQLException {
        String query = "UPDATE public.persons " +
                "SET person_name=? " +
                "WHERE person_id=? " +
                "AND person_owner_id=?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setString(1,newName);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void updatePersonNotes(int id, String newNotes) throws SQLException {
        String query = "UPDATE public.persons " +
                "SET person_notes=? " +
                "WHERE person_id=? " +
                "AND person_owner_id=?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setString(1,newNotes);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void updatePersonImage(int id, BufferedImage newImage) throws SQLException, IOException {
        String query = "UPDATE public.persons " +
                "SET person_image=? " +
                "WHERE person_id=? " +
                "AND person_owner_id=?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage,"png",baos);
        byte[] bytes = baos.toByteArray();
        statement.setBytes(1,bytes);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    /// Bond queries


    private static void loadBondInfo() {

    }

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


    private static void loadGroupInfo() {
    }

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
