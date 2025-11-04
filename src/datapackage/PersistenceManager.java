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
}