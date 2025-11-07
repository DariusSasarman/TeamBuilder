package datapackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


class Register {
    private static HashMap<Integer,Person> peopleList = new HashMap<Integer,Person>();
    private static HashMap<Integer,Group> groupList = new HashMap<Integer,Group>();
    private static HashMap<Integer,Bond> bondList = new HashMap<Integer,Bond>();
    private static int activeGroupId;

    /**
     *  ALL INTERACTIONS WITH HASHMAPS ARE DONE HERE
     */

    protected static Person getPerson(int id)
    {
        /// Assum all data is loaded
        Person searched = peopleList.get(id);
        if(searched == null)
        {
            throw new RuntimeException("Person does not exist in memory");
        }
        return searched;
    }

    protected static HashMap<Integer, String> getPersonList()
    {
        HashMap<Integer,String> returnedList = new HashMap<>();
        for(HashMap.Entry<Integer,Person> entry : peopleList.entrySet())
        {
            returnedList.put(entry.getKey(),entry.getValue().getName());
        }
        return returnedList;
    }

    protected static void addPerson(Person newcomer)
    {
        if(peopleList.get(newcomer.getId()) != null)
        {
            throw new RuntimeException("Person ID already exists.");
        }
        peopleList.put(newcomer.getId(),newcomer);
    }

    protected static void deletePerson(int id)
    {
        peopleList.remove(getPerson(id).getId());
    }

    protected static void setPersonName(int id, String newName)
    {
        peopleList.get(id).setName(newName);
    }

    protected static void setPersonNotes(int id, String newNotes)
    {
        peopleList.get(id).setNotes(newNotes);
    }

    protected static void setPersonImage(int id, BufferedImage newImage)
    {
        peopleList.get(id).setImage(newImage);
    }
}
