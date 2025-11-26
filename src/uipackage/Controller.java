package uipackage;

import datapackage.Bond;
import datapackage.Group;
import datapackage.Model;
import datapackage.Person;
import graphpackage.GraphArea;
import persistencepackage.Persistence;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Controller {

    /*
        CONTROLLER CLASS
     */

    /// Person settings
    public void handleAddPersonRequest(BufferedImage img, String name, String notes)
    {
        Person newcomer = new Person(Persistence.getNextPersonUID(),img,name,notes);
        Model.addPerson(newcomer);
        Persistence.createPersonOnDb(newcomer);
    }

    public void handleDeletePersonRequest(int id) {
        Model.deletePerson(id);
        Persistence.deletePersonOnDb(id);
    }

    public void handleEditPersonRequest(int id, BufferedImage img, String name, String notes)
    {
        Person target = Model.getPerson(id);

        if(!target.getImage().equals(img))
        {
            Model.setPersonImage(id, img);
            Persistence.updatePersonImage(id, img);
        }

        if(!target.getName().equals(name))
        {
            Model.setPersonName(id, name);
            Persistence.updatePersonName(id, name);
        }

        if(!target.getNotes().equals(notes))
        {
            Model.setPersonNotes(id, notes);
            Persistence.updatePersonNotes(id, notes);
        }
    }

    public String handleGetPersonName(int id)
    {
        return Model.getPerson(id).getName();
    }

    public String handleGetPersonNotes(int id)
    {
        return Model.getPerson(id).getNotes();
    }

    public BufferedImage handleGetPersonImage(int id)
    {
        return Model.getPerson(id).getImage();
    }

    public HashMap<Integer, String> handleGetPersonList()
    {
        return Model.getPersonList();
    }

    /// Bond settings
    public void handleAddBondRequest(int headId, int tailId, int rating)
    {
        Integer check = Model.findBondByHeads(headId,tailId);
        if(check != null)
        {
            Model.getBond(check).setRating(rating);
            return;
        }
        Bond b = new Bond(Persistence.getNextBondUID(),headId,tailId,rating);
        Model.addBond(b);
        Persistence.createBondOnDb(b);
    }

    public int handleGetBondHeadId(int id) {
        return Model.getBond(id).getHeadId();
    }

    public int handleGetBondTailId(int id) {
        return Model.getBond(id).getTailId();
    }

    public int handleGetBondRating(int id) {
        return Model.getBond(id).getRating();
    }

    public String handleGetBondNotes(int id) {
        return Model.getBond(id).getNotes();
    }

    public void handleEditBondRequest(int id, int newRating, String notes) {
        Bond edited = Model.getBond(id);
        if(edited.getRating() != newRating)
        {
            Model.setBondRating(id,newRating);
            Persistence.updateBondRating(id,newRating);
        }
        if(!edited.getNotes().equals(notes))
        {
            Model.setBondNotes(id,notes);
            Persistence.updateBondNotes(id,notes);
        }
    }

    public void handleDeleteBondRequest(int id) {
        Model.deleteBond(id);
        Persistence.deleteBondOnDb(id);
    }
    public HashMap<Integer,String> handleGetBondList()
    {
        return Model.getBondList();
    }

    /// Group Settings
    public void handleAddGroupRequest(String title, ArrayList<Integer> personIds) {
        Group group = new Group(Persistence.getNextGroupUID(),title,personIds);
        Model.addGroup(group);
        Persistence.createGroupOnDB(group);
    }

    public String handleGetGroupTitle(int id) {
        return Model.getGroup(id).getTitle();
    }

    public ArrayList<Integer> handleGetGroupMembers(int id) {
        return Model.getGroup(id).getPersonIdList();
    }

    public void handleEditGroupRequest(int id, String title, ArrayList<Integer> personIds) {
        Group target = Model.getGroup(id);
        if(!target.getTitle().equals(title))
        {
            Model.setGroupTitle(id,title);
            Persistence.updateGroupTitle(id,title);

        }
        if(!target.getPersonIdList().equals(personIds))
        {
            Model.setGroupPersonIds(id,personIds);
            Persistence.updateGroupPersonIds(id,personIds);
        }
    }

    public void handleDeleteGroupRequest(int id) {
        Model.deleteGroup(id);
        Persistence.deleteGroupOnDb(id);
    }

    public HashMap<Integer,String> handleGetGroupList()
    {
        return Model.getGroupList();
    }

    /// Account settings

    public void handleLogin(String username, String password) {
        Persistence.changeAccount(username,password);
    }


    /// Active group interactions
    public void handleChangeCurrentGroup(int groupId) {
        Model.setActiveGroupId(groupId);
    }

    public HashMap<Integer, String> handleGetPeopleInCurrentGroup() {
        return Model.getPeopleInActiveGroup();
    }

    public void handleAddPersonToCurrentGroup(int newcomerId)
    {
        Model.addPersonToActiveGroup(newcomerId);
    }

    public HashMap<Integer, String> handleGetPeopleNotInCurrentGroup() {
        return Model.getPeopleNotInActiveGroup();
    }

    public void handleRemovePersonFromCurrentGroup(int id)
    {
        Model.removePersonFromCurrentGroup(id);
    }

    public HashMap<Integer, String> handleGetBondsInCurrentGroup() {
        return Model.getBondsInCurrentGroup();
    }

    public void handleRaiseBondInCurrentGroup(int bondId) {
       Bond target = Model.getBond(bondId);
       target.setRating(target.getRating() + 1);
    }

    public void handleLowerBondInCurrentGroup(int bondId) {
        Bond target = Model.getBond(bondId);
        target.setRating(target.getRating() - 1);
    }

    public void handleRaiseAllBondsInCurrentGroup() {
        for( Integer id : handleGetBondsInCurrentGroup().keySet())
        {
            handleRaiseBondInCurrentGroup(id);
        }
    }

    public void handleLowerAllBondsInCurrentGroup() {
        for( Integer id : handleGetBondsInCurrentGroup().keySet())
        {
            handleLowerBondInCurrentGroup(id);
        }
    }

    /// Right side

    public HashMap<Integer, String> handleGetActiveGroupPartitions(Integer groupCount, GraphArea graphArea)
    {
        /// TODO: Partition Generator
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "John and Maryy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy");
        list.put(2, "Mary and Peter");
        return list;
    }

    public LinkedHashMap<Integer,String> handleGetAscendingDirectCentrality(GraphArea graphArea){
        return graphArea.getAscendingDirectCentrality();
    }

    public LinkedHashMap<Integer,String> handleGetAscendingIndirectCentrality(GraphArea graphArea){
        return graphArea.getAscendingIndirectCentrality();
    }

    public HashMap<Integer,String> handleGetClustering(){
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "Ala, bala and portocala");
        list.put(2, "Innie, minnie, miny and moe");
        list.put(3, "Holly, Jolly and Christmas");
        return list;
    }

    public HashMap<Integer,String> handleGetKCoreDecomposition()
    {
        HashMap<Integer,String> list = new HashMap<>();
        list.put(13,"Mary");
        list.put(12,"Mark");
        list.put(11,"Martin");
        list.put(8,"Marcus");
        list.put(10,"John");
        list.put(9,"Peter");
        list.put(7,"Quarkus");
        list.put(6,"React");
        list.put(5,"Angular");
        return list;
    }

    public HashMap<Integer,String> handleGetDijkstraRoute(int id1, int id2)
    {
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, this.handleGetPersonName(id1) + " knows " + "(9/10) " + "George");
        list.put(2, "George" + " knows " + "(7/10) " + "Marcel");
        list.put(3, "Marcel" + " knows " + "(10/10) " + this.handleGetPersonName(id2));
        return list;
    }

    public double handleGetActiveGroupRating()
    {
        return 10;
    }


}
