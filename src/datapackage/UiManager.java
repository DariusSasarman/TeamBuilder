package datapackage;


import java.awt.*;
import java.util.ArrayList;

public class UiManager extends Register{

    public ArrayList<Integer> getPeopleIdInActiveGroup()
    {
        ArrayList<Integer> ret = new ArrayList<>();
        for(Person p : peopleList)
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