package DataPackage;

import UiPackage.Graph;
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
            /// Replace with binarySearch
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
                throw new IllegalArgumentException("There's no such group");
            }
    }

    class UiManager {

        public ArrayList<Integer> getPeopleIdInActiveGroup()
        {
            ArrayList<Integer> ret = new ArrayList<Integer>();
            for(Person p : Register.this.peopleList)
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
            return new String();
        }

        public String getPersonNotesById (int id)
        {
            return new String();
        }


        public Graph getOnDrawInformation()
        {
            return new Graph();
        }
    }


    class PersistenceManager {

    }

}
