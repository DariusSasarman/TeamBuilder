package datapackage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import uipackage.Graph;

class Register {
    protected static ArrayList<Person> peopleList = new ArrayList<>();
    protected static ArrayList<Group> groupList = new ArrayList<>();
    protected static ArrayList<Bond> bondList = new ArrayList<>();
    protected static int activeGroupId;

    protected static int binarySearch (int ind)
    {
        /// TODO
        return 0;
    }

    protected static int getActiveGroupId() {
        return activeGroupId;
    }

    protected static void setActiveGroupId(int newActiveGroupId) {
            boolean exists = false;
            /// TODO: replace with binarySearch
            for(Group g : groupList)
            {
                if (g.getGroupId() == newActiveGroupId)
                {
                    exists = true;
                }
            }
            if (exists)
            {
                activeGroupId = newActiveGroupId;
            }
            else
            {
                throw new IllegalArgumentException("There's no such group");
            }
    }

}
