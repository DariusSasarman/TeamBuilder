package datapackage;


import java.awt.image.BufferedImage;
import java.util.HashMap;

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
        PersistenceManager.forgetPerson(id);
        Register.deletePerson(id);
    }

    public static String getPersonName(int id)
    {
        return Register.getPerson(id).getName();
    }

    public static String getPersonNotes(int id)
    {
        return Register.getPerson(id).getNotes();
    }

    public static BufferedImage getPersonImage(int id)
    {
        return Register.getPerson(id).getImage();
    }

    public static HashMap<Integer, String> getPersonList()
    {
        return Register.getPersonList();
    }

    public static void editPersonName(int id, String newName)
    {
        PersistenceManager.revisePersonName(id,newName);
        Register.setPersonName(id,newName);
    }

    public static void editPersonNotes(int id, String newNotes)
    {
        PersistenceManager.revisePersonNotes(id,newNotes);
        Register.setPersonNotes(id,newNotes);
    }

    public static void editPersonImage(int id, BufferedImage newImage)
    {
        PersistenceManager.revisePersonImage(id,newImage);
        Register.setPersonImage(id, newImage);
    }
}