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

    public static void forgetPerson(Person p)
    {
        Persistence.deletePersonOnDb(p.getId());
    }
}