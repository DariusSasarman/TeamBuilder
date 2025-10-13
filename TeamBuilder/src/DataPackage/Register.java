package DataPackage;

import UiPackage.Graph;

import java.awt.*;
import java.util.ArrayList;

public class Register {
    private ArrayList<Person> peopleList;
    private ArrayList<Group> groupList;
    private ArrayList<Bond> bondList;
    private int activeGroupId;

    

    public int getActiveGroupId() {
        return activeGroupId;
    }

    public void setActiveGroupId(int activeGroupId) {
        boolean exists = false; 
        for(Group g : groupList)
        {
            if (g.getGroupId() == activeGroupId)
            {
                exists = true;
            }
        }
        if (exists)
        {
            this.activeGroupId = activeGroupId;
        }
        else
        {
            throw new IllegalArgumentException("Group does not exist");
        }
    }

    /// UI handler Elements
    public ArrayList<Integer> getPeopleIdInActiveGroup()
    {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(Person p : peopleList)
        {
            ret.add(p.getId());
        }
        return ret;
    }

    private int generateNewId()
    {
        int a = 0;

        return a;
    }

    public String getNameById (int id)
    {
        /// Throw error if not in active group
        return new String();
    }

    public String getPersonNotesById (int id)
    {
        /// Throw error if not in active group
        return new String();
    }


    public Graph getOnDrawInformation()
    {
        /// TODO
        return new Graph();
    }

    /// Persistence Elements
}
