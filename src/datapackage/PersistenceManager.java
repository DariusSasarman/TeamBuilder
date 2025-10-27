package datapackage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PersistenceManager extends Register {
    public void addPersonFromDB(int id, BufferedImage image, String name, String notes) {
        peopleList.add(new Person(id, image, name, notes));
    }

    public void addBondFromDB(int bondId, int headId, int tailId, int rating, String notes) {
        bondList.add(new Bond(bondId, headId, tailId, rating, notes));
    }

    public void addGroupFromDB(int id, String title, ArrayList<Integer> target)
    {
        groupList.add(new Group(id,title,target));
    }
}