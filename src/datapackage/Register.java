package datapackage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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
        Person searched = peopleList.get(id);
        if(searched == null)
        {
            /// TODO: Query DB to check if it exists??
            throw new RuntimeException("Person does not exist in memory");
        }
        return searched;
    }

    protected static void addPerson(Person newcomer)
    {
        if(peopleList.get(newcomer.getId()) != null)
        {
            throw new RuntimeException("Person ID already exists.");
        }
        peopleList.put(newcomer.getId(),newcomer);
    }

    protected static void deletePerson(Person deserter)
    {
        if(peopleList.get(deserter.getId()) == null)
        {
            return;
        }
        peopleList.remove(deserter.getId());
    }
}
