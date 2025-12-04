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
import java.sql.*;
import java.util.*;


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
        String query = "SELECT * FROM public.persons WHERE person_owner_id = ?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,currentUser.getUID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("person_id");
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

    private static void loadBondInfo() throws SQLException {
        String query = "SELECT * FROM public.bonds WHERE bond_owner_id=?";
        PreparedStatement statement =dataBaseConnection.prepareStatement(query);
        statement.setInt(1,currentUser.getUID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("bond_id");
            int headId = resultSet.getInt("bond_head_id");
            int tailId = resultSet.getInt("bond_tail_id");
            int rating = resultSet.getInt("bond_rating");
            String notes = resultSet.getString("bond_notes");
            Bond bond = new Bond(id,headId,tailId,rating,notes);
            Model.addBond(bond);
        }
    }

    private static void queryDBforNextBondUID() throws SQLException {
        String query = "SELECT nextval(pg_get_serial_sequence('public.bonds','bond_id')) AS new_id";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
        {
            nextBondUID = resultSet.getInt("new_id");
        }
    }

    public static int getNextBondUID() throws SQLException {
        int returnValue = nextBondUID;
        queryDBforNextBondUID();
        return returnValue;
    }

    public static Bond readBond(int id) {
        String query = "SELECT * FROM public.bonds WHERE bond_id = ? AND bond_owner_id = ?";
        Bond ret = null;
        try
        {
            PreparedStatement statement = dataBaseConnection.prepareStatement(query);
            statement.setInt(1,id);
            statement.setInt(2,currentUser.getUID());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                int headId = resultSet.getInt("bond_head_id");
                int tailId = resultSet.getInt("bond_tail_id");
                int rating = resultSet.getInt("bond_rating");
                String notes = resultSet.getString("bond_notes");
                ret = new Bond(id,headId,tailId,rating,notes);
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error loading bond from database : " + e.getMessage());
        }
        return ret;
    }

    public static void createBondOnDb(Bond b) throws SQLException {
        String query = "INSERT INTO public.bonds " +
                "(bond_id,bond_head_id,bond_tail_id,bond_rating,bond_notes,bond_owner_id) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,b.getId());
        statement.setInt(2,b.getHeadId());
        statement.setInt(3,b.getTailId());
        statement.setInt(4,b.getRating());
        statement.setString(5,b.getNotes());
        statement.setInt(6,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void updateBondRating(int id, int newRating) throws SQLException {
        String query = "UPDATE public.bonds " +
                "SET bond_rating = ? " +
                "WHERE bond_id = ? AND bond_owner_id = ?";
        PreparedStatement statement= dataBaseConnection.prepareStatement(query);
        statement.setInt(1,newRating);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void updateBondNotes(int id, String notes) throws SQLException {
        String query = "UPDATE public.bonds " +
                "SET bond_notes = ? " +
                "WHERE bond_id = ? AND bond_owner_id = ?";

        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setString(1,notes);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void deleteBondOnDb(int id) throws SQLException {
        String query = "DELETE FROM public.bonds " +
                "WHERE bond_id = ? AND bond_owner_id = ?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,id);
        statement.setInt(2,currentUser.getUID());
        statement.executeUpdate();
    }

    /// Group queries

    private static void loadGroupInfo() throws SQLException {
        String query = "SELECT group_id FROM public.groups_table WHERE group_owner_id = ?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,currentUser.getUID());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("group_id");
            Model.addGroup(readGroup(id));
        }
    }

    private static void queryDBforNextGroupUID() throws SQLException {
        String query = "SELECT nextval(pg_get_serial_sequence('public.groups_table','group_id')) AS new_id";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
        {
            nextGroupUID = resultSet.getInt("new_id");
        }
    }

    public static int getNextGroupUID() throws SQLException {
        int returnValue = nextGroupUID;
        queryDBforNextGroupUID();
        return returnValue;
    }

    public static void createGroupOnDB(Group group) throws SQLException {
        String query = "INSERT INTO public.groups_table " +
                "(group_id,group_title,group_owner_id) " +
                "VALUES (?,?,?)";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,group.getId());
        statement.setString(2,group.getTitle());
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
        query = "INSERT INTO public.person_in_group " +
                "(person_id,group_id,owner_id) " +
                "VALUES (?,?,?)";
        statement = dataBaseConnection.prepareStatement(query);
        for(Integer id : group.getPersonIdList())
        {
            statement.setInt(1,id);
            statement.setInt(2,group.getId());
            statement.setInt(3,currentUser.getUID());
            statement.executeUpdate();
        }
    }

    public static Group readGroup(int id) {

        Group ret = new Group(id,"");
        try{
            String query = "SELECT person_id FROM person_in_group WHERE group_id = ? AND owner_id = ?";
            PreparedStatement statement = dataBaseConnection.prepareStatement(query);
            statement.setInt(1,id);
            statement.setInt(2,currentUser.getUID());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                ret.addPersonToGroup(resultSet.getInt("person_id"));
            }
            query = "SELECT group_title FROM groups_table WHERE group_id = ? AND group_owner_id = ?";
            statement = dataBaseConnection.prepareStatement(query);
            statement.setInt(1,id);
            statement.setInt(2,currentUser.getUID());
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                ret.setTitle(resultSet.getString("group_title"));
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error loading groups from database: " + e.getMessage());
        }
        return ret;
    }

    public static void updateGroupTitle(int id, String title) throws SQLException {
        String query = "UPDATE public.groups_table " +
                "SET group_title = ? " +
                "WHERE group_id = ? AND group_owner_id = ?";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setString(1,title);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        statement.executeUpdate();
    }

    public static void updateGroupPersonIds(int id, ArrayList<Integer> personIds) throws SQLException {
        /// THIS FUNCTION STARTS FROM THE IDEA THAT
        /// THE OLD GROUP CONFIGURATION IS IN 'MODEL'
        Set<Integer> oldSet = new HashSet<>(Model.getGroup(id).getPersonIdList());
        Set<Integer> newSet = new HashSet<>(personIds);

        Set<Integer> insert = new HashSet<>(newSet);
        insert.removeAll(oldSet);
        String query = "INSERT INTO person_in_group " +
                "(person_id, group_id, owner_id) VALUES" +
                "(?,?,?)";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());

        for(Integer ins : insert)
        {
            statement.setInt(1,ins);
            statement.executeUpdate();
        }

        Set<Integer> delete = new HashSet<>(oldSet);
        delete.removeAll(newSet);
        query = "DELETE FROM person_in_group " +
                "WHERE person_id = ? AND " +
                "group_id = ? AND " +
                "owner_id = ?";
        statement= dataBaseConnection.prepareStatement(query);
        statement.setInt(2,id);
        statement.setInt(3,currentUser.getUID());
        for(Integer del : delete)
        {
            statement.setInt(1,del);
            statement.executeUpdate();
        }
    }

    public static void deleteGroupOnDb(int id) throws SQLException {
        String query = "DELETE FROM public.groups_table " +
                "WHERE group_id = ? AND group_owner_id = ?;";
        PreparedStatement statement = dataBaseConnection.prepareStatement(query);
        statement.setInt(1,id);
        statement.setInt(2,currentUser.getUID());
        statement.executeUpdate();
    }

}
