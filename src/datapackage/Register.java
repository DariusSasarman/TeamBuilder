package datapackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import uipackage.Graph;

public class Register {
    private ArrayList<Person> peopleList;
    private ArrayList<Group> groupList;
    private ArrayList<Bond> bondList;
    private int activeGroupId;

    private int binarySearch (int ind)
    {
        /// TODO
        return 0;
    }

    private int getActiveGroupId() {
        return activeGroupId;
    }

    private void setActiveGroupId(int activeGroupId) {
            boolean exists = false;
            /// TODO: replace with binarySearch
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

    public class UiManager {

        public ArrayList<Integer> getPeopleIdInActiveGroup()
        {
            ArrayList<Integer> ret = new ArrayList<>();
            for(Person p : Register.this.peopleList)
            {
                ret.add(p.getId());
            }
            return ret;
        }

        public String getNameById (int id) {
            /// TODO
            return new String(); }

        public String getPersonNotesById (int id)
        {
            /// TODO
            return new String();
        }

        public void getOnDrawInformation(Graphics g)
        {
            /// TODO
        }
    }

    public class PersistenceManager {
        public void addPersonFromDB(int id, BufferedImage image, String name, String notes) {
            peopleList.add(new Person(id, image, name, notes));
            peopleList.sort((p1,p2)-> p1.compareTo(p2));
            ///  replace with binary-search insertion later
        }

        public void addBondFromDB(int bondId, int headId, int tailId, int rating, String notes) {
            bondList.add(new Bond(bondId, headId, tailId, rating, notes));
            bondList.sort((b1,b2) -> b1.compareTo(b2));
            /// replace with binary-search insertion

        }

        public void addGroupFromDB(int id, String title, ArrayList<Integer> target)
        {
            groupList.add(new Group(id,title,target));
            bondList.sort((g1,g2)-> g1.compareTo(g2));
            /// replace with binary-search insertion

        }
    }

}
