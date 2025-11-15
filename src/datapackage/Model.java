package datapackage;

import java.awt.image.BufferedImage;
import java.util.HashMap;


class Model {
    private static HashMap<Integer,Person> peopleList = new HashMap<Integer,Person>();
    private static HashMap<Integer,Group> groupList = new HashMap<Integer,Group>();
    private static HashMap<Integer,Bond> bondList = new HashMap<Integer,Bond>();
    private static int activeGroupId;


    public Person getPerson(int id)
    {
        Person searched = peopleList.get(id);
        if(searched == null)
        {
            throw new RuntimeException("Person does not exist in memory");
        }
        return searched;
    }

    public HashMap<Integer, String> getPersonList()
    {
        HashMap<Integer,String> returnedList = new HashMap<>();
        for(HashMap.Entry<Integer,Person> entry : peopleList.entrySet())
        {
            returnedList.put(entry.getKey(),entry.getValue().getName());
        }
        return returnedList;
    }

    public void addPerson(Person newcomer)
    {
        if(peopleList.get(newcomer.getId()) != null)
        {
            throw new RuntimeException("Person ID already exists.");
        }
        peopleList.put(newcomer.getId(),newcomer);
    }

    public void deletePerson(int id)
    {
        peopleList.remove(getPerson(id).getId());
    }

    public void setPersonName(int id, String newName)
    {
        peopleList.get(id).setName(newName);
    }

    public void setPersonNotes(int id, String newNotes)
    {
        peopleList.get(id).setNotes(newNotes);
    }

    public void setPersonImage(int id, BufferedImage newImage)
    {
        peopleList.get(id).setImage(newImage);
    }
}
