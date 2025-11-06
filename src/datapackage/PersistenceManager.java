package datapackage;

import persistencepackage.Persistence;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PersistenceManager extends Register {

    /**
     *  ALL INTERACTIONS WITH PERSISTENCE ARE HANDLED HERE
     */

    public static int getNewPersonId()
    {
        return Persistence.getNextPersonUID();
    }

    public static void rememberPerson(Person p)
    {
        Persistence.createPersonOnDb(p.getId(),p.getImage(),p.getName(),p.getNotes());
    }

    public static void forgetPerson(int id)
    {
        Persistence.deletePersonOnDb(id);
    }

    public static void revisePersonName(int id, String name)
    {
        Persistence.updatePersonName(id,name);
    }

    public static void revisePersonNotes(int id, String notes)
    {
        Persistence.updatePersonNotes(id,notes);
    }
    public static void revisePersonImage(int id, BufferedImage image)
    {
        Persistence.updatePersonImage(id,image);
    }
}