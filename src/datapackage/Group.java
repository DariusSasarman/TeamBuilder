package datapackage;

import java.util.ArrayList;

class Group {
    private int groupId;

    private  ArrayList<Integer> PersonIdList;

    public Group(int id) {
        this.groupId = id;
        this.PersonIdList = new ArrayList<>();
    }

    public Group(int id, ArrayList<Integer> target) {
        this.groupId = id;
        this.PersonIdList = target;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id)
    {
        this.groupId = id;
    }
    public void addPersonToGroup(Integer targetId) {PersonIdList.add(targetId);}

    public void removePersonFromGroup(Integer targetId) {PersonIdList.remove(targetId);}

    public ArrayList<Integer> getPersonIdList() {return PersonIdList;}

    public void setPersonIdList(ArrayList<Integer> personIdList) {PersonIdList = personIdList;}
}
