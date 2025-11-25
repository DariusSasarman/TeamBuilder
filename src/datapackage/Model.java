package datapackage;

import persistencepackage.Persistence;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Model {
    private static HashMap<Integer,Person> peopleList = new HashMap<Integer,Person>();
    private static HashMap<Integer,Group> groupList = new HashMap<Integer,Group>();
    private static HashMap<Integer,Bond> bondList = new HashMap<Integer,Bond>();
    private static HashMap<String, Integer> bondCheckList = new HashMap<>();
    private static int activeGroupId;


    public Model()
    {
        Persistence.queryDB();
    }

    public static void clearInfo()
    {
        peopleList.clear();
        groupList.clear();
        bondList.clear();
        bondCheckList.clear();
    }

    ///  Person commands

    public static Person getPerson(int id)
    {
        Person searched = peopleList.get(id);
        if(searched == null)
        {
            searched = Persistence.readPerson(id);
        }
        return searched;
    }

    public static HashMap<Integer, String> getPersonList()
    {
        HashMap<Integer,String> returnedList = new HashMap<>();
        for(HashMap.Entry<Integer,Person> entry : peopleList.entrySet())
        {
            returnedList.put(entry.getKey(),entry.getValue().getName());
        }
        return returnedList;
    }

    public static void addPerson(Person newcomer)
    {
        if(peopleList.get(newcomer.getId()) != null)
        {
            throw new RuntimeException("Person ID already exists.");
        }
        peopleList.put(newcomer.getId(),newcomer);
    }

    public static void deletePerson(int id)
    {
        peopleList.remove(getPerson(id).getId());
    }

    public static void setPersonName(int id, String newName)
    {
        peopleList.get(id).setName(newName);
    }

    public static void setPersonNotes(int id, String newNotes)
    {
        peopleList.get(id).setNotes(newNotes);
    }

    public static void setPersonImage(int id, BufferedImage newImage)
    {
        peopleList.get(id).setImage(newImage);
    }

    /// Bond commands

    public static Integer findBondByHeads(int headId, int tailId) {
        return bondCheckList.get(Math.max(headId, tailId) + "-" + Math.min(headId, tailId));
    }

    public static Bond getBond(int id)
    {
        Bond returned = bondList.get(id);
        if(returned == null)
        {
            returned = Persistence.readBond(id);
        }
        return returned;
    }

    public static void addBond(Bond added)
    {
        Integer check = findBondByHeads(added.getHeadId(),added.getTailId());
        if(findBondByHeads(added.getHeadId(),added.getTailId()) != null)
        {
            Bond b = getBond(check);
            b.setNotes(added.getNotes());
            b.setRating(added.getRating());
            return;
        }
        bondList.put(added.getId(),added);
        bondCheckList.put(Math.max(added.getHeadId(),added.getTailId()) + "-" + Math.min(added.getHeadId(),added.getTailId()),added.getId());
    }

    public static void setBondRating(int id, int newRating) {
        bondList.get(id).setRating(newRating);
    }

    public static void setBondNotes(int id, String notes) {
        bondList.get(id).setNotes(notes);
    }

    public static void deleteBond(int id) {
        Bond bond = bondList.get(id);
        bondCheckList.remove(Math.max(bond.getHeadId(), bond.getTailId()) + "-" + Math.min(bond.getHeadId(), bond.getTailId()));
        bondList.remove(id);
    }

    public static HashMap<Integer, String> getBondList() {
        HashMap<Integer,String> ret = new HashMap<>();
        for(Bond b : bondList.values())
        {
            ret.put(b.getId(), peopleList.get(b.getHeadId()).getName() + " and " + peopleList.get(b.getTailId()).getName());
        }
        return ret;
    }

    /// Group Commands

    public static void addGroup(Group group) {
        groupList.put(group.getId(),group);
    }

    public static Group getGroup(int id) {
        Group returned = groupList.get(id);
        if(returned == null)
        {
            returned = Persistence.readGroup(id);
        }
        return returned;
    }

    public static void setGroupTitle(int id, String title) {
        groupList.get(id).setTitle(title);
    }

    public static void setGroupPersonIds(int id, ArrayList<Integer> personIds) {
        groupList.get(id).setPersonIdList(personIds);
    }

    public static void deleteGroup(int id) {
        groupList.remove(id);
    }

    public static HashMap<Integer, String> getGroupList() {
        HashMap<Integer,String> ret = new HashMap<>();
        for(Group group : groupList.values())
        {
            ret.put(group.getId(),group.getTitle());
        }
        return ret;
    }

    /// Active group info
    public static void setActiveGroupId(int id)
    {
        if(groupList.get(id) != null)
        {
            activeGroupId = id;
        }
        else
        {
            activeGroupId = -1;
            throw new RuntimeException("Group hasn't been found.");
        }
    }

    public static HashMap<Integer,String> getPeopleInActiveGroup()
    {
        HashMap<Integer,String> ret = new HashMap<>();
        if(groupList.get(activeGroupId) == null)
        {
            return ret;
        }
        for(Integer i : groupList.get(activeGroupId).getPersonIdList())
        {
            ret.put(i,peopleList.get(i).getName());
        }
        return ret;
    }

    public static void addPersonToActiveGroup(int newcomerId) {
        if(groupList.get(activeGroupId) != null)
        {
            groupList.get(activeGroupId).getPersonIdList().add(newcomerId);
        }
        else {
            throw new RuntimeException("No active group is set.");
        }
    }

    public static HashMap<Integer, String> getPeopleNotInActiveGroup() {
        HashMap<Integer,String> ret = new HashMap<>();
        for(Person p : peopleList.values())
        {
            ret.put(p.getId(),p.getName());
        }
        if(groupList.get(activeGroupId) != null)
        {
            for(Integer i : groupList.get(activeGroupId).getPersonIdList())
            {
                ret.remove(i);
            }
        }
        return ret;
    }

    public static void removePersonFromCurrentGroup(int id) {
        if(groupList.get(activeGroupId) != null) {
            groupList.get(activeGroupId).getPersonIdList().remove(id);
        }
    }

    public static HashMap<Integer, String> getBondsInCurrentGroup() {
        HashMap<Integer,String> ret = new HashMap<>();
        Set<Integer> peopleId = getPeopleInActiveGroup().keySet();
        for(Bond bond : bondList.values())
        {
            if(peopleId.contains(bond.getHeadId()) && peopleId.contains(bond.getTailId()))
            {
                String description = getPerson(bond.getHeadId()).getName() + " and " + getPerson(bond.getTailId()).getName();
                ret.put(bond.getId(),description);
            }
        }
        return ret;
    }
}
