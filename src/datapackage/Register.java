package datapackage;

import java.awt.*;
import java.util.ArrayList;


class Register {
    protected static ArrayList<Person> peopleList = new ArrayList<>();
    protected static ArrayList<Group> groupList = new ArrayList<>();
    protected static ArrayList<Bond> bondList = new ArrayList<>();
    protected static int activeGroupId;

    /**
     *  ALL INTERACTIONS WITH ARRAYLISTS ARE DONE HERE
     */

    protected static void addPerson(Person newcomer)
    {
        peopleList.add(newcomer);
    }
}
