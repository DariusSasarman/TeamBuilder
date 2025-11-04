package datapackage;


import java.awt.image.BufferedImage;
import uipackage.Graph;

public class UiManager extends Register{

    /**
     *  ALL INTERACTIONS WITH UIPACKAGE ARE HANDLED HERE
     */

    public static void addPersonToRegister(BufferedImage img, String name, String notes)
    {
        Person newcomer = new Person(PersistenceManager.getNewPersonId(),img,name,notes);
        addPerson(newcomer);
    }
}