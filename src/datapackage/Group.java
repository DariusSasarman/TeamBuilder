package datapackage;

import java.util.ArrayList;

class Group {
    private int groupId;
    private String title;
    private  ArrayList<Integer> PersonIdList;

    public Group(int id, String title) {
        this.groupId = id;
        this.title = title;
        this.PersonIdList = new ArrayList<>();
    }

    public Group(int id, String title, ArrayList<Integer> target) {
        this.groupId = id;
        this.title = title;
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

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public int compareTo(Group other) {return Integer.compare(this.groupId, other.groupId);}
}
