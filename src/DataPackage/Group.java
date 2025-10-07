package DataPackage;

import java.util.ArrayList;

public class Group {
    private int groupId;

    private  ArrayList<Integer> PersonIdList;

    public Group(int id) {
        this.groupId = id;
        this.PersonIdList = new ArrayList<Integer>();
    }

    public Group(int id, ArrayList<Integer> target) {
        this.groupId = id;
        this.PersonIdList = target;
    }

    public void addPersonToGroup(Integer targetId) {PersonIdList.add(targetId);}

    public void removePersonFromGroup(Integer targetId) {PersonIdList.remove(targetId);}

    public ArrayList<Integer> getPersonIdList() {return PersonIdList;}

    public void setPersonIdList(ArrayList<Integer> personIdList) {PersonIdList = personIdList;}
}
