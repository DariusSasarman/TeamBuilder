package datapackage;

import java.util.ArrayList;

public class Group extends Entity{

    private  ArrayList<Integer> PersonIdList;

    public Group(int id, String title) {
        super(id,title);
        this.PersonIdList = new ArrayList<>();
    }

    public Group(int id, String title, ArrayList<Integer> target) {
        super(id,title);
        this.PersonIdList = target;
    }

    public void addPersonToGroup(Integer targetId) {PersonIdList.add(targetId);}

    public void removePersonFromGroup(Integer targetId) {PersonIdList.remove(targetId);}

    public ArrayList<Integer> getPersonIdList() {return PersonIdList;}

    public void setPersonIdList(ArrayList<Integer> personIdList) {PersonIdList = personIdList;}

    public String getTitle() {return super.getNotes();}

    public void setTitle(String title) { super.setNotes(title);}

}
