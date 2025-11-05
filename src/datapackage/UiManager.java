package datapackage;


import java.awt.image.BufferedImage;
import uipackage.Graph;

public class UiManager extends Register{

    /**
     *  ALL INTERACTIONS WITH UIPACKAGE ARE HANDLED HERE
     */

    public static void addPerson(BufferedImage img, String name, String notes)
    {
        Person newcomer = new Person(PersistenceManager.getNewPersonId(),img,name,notes);
        PersistenceManager.rememberPerson(newcomer);
        Register.addPerson(newcomer);
    }

    public static void deletePerson(int id)
    {
        Person deserter = getPerson(id);
        PersistenceManager.forgetPerson(deserter);
        Register.deletePerson(deserter);
    }

    public static String getPersonName(int id)
    {
        Person unnamed = Register.getPerson(id);
        return unnamed.getName();
    }

    public static String getPersonNotes(int id)
    {
        Person checked = Register.getPerson(id);
        return checked.getNotes();
    }

    public static BufferedImage getPersonImage(int id)
    {
        Person unmasked = Register.getPerson(id);
        return unmasked.getImage();
    }
}