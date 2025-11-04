package uipackage;

import datapackage.UiManager;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UiHandler {

    public UiHandler()
    {
        new UiManager();
    }
    /// Person settings
    public void handleAddPersonRequest(BufferedImage img, String name, String notes)
    {
        UiManager.addPersonToRegister(img,name,notes);
    }

    public void handleDeletePersonRequest(int id) {
        // TODO: Remove person from data (and their bonds)
    }

    public void handleEditPersonRequest(int id, BufferedImage img, String name, String notes)
    {
        /// TODO: Register
    }

    public String handleGetPersonName(int id)
    {
        return new String("Yeah");
    }

    public String handleGetPersonNotes(int id)
    {
        return new String("Notes");
    }

    public BufferedImage handleGetPersonImage(int id)
    {
        return new BufferedImage(100,100,1);
    }

    public HashMap<Integer, String> handleGetPersonList()
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

    /// Bond settings
    public void handleAddBondRequest(int headId, int tailId, int rating)
    {
        /// TODO: Register
    }

    public int handleGetBondHeadId(int id) {
        // TODO: Get from register
        return 10;
    }

    public int handleGetBondTailId(int id) {
        // TODO: Get from register
        return 13;
    }

    public int handleGetBondRating(int id) {
        // TODO: Get from register
        return 7;
    }

    public String handleGetBondNotes(int id) {
        // TODO: Get from register
        return "They seem to get along";
    }

    public void handleEditBondRequest(int id, int newRating, String notes) {
        // TODO: Update bond in register
    }

    public void handleDeleteBondRequest(int id) {
        // TODO: Remove bond from register
    }
    public HashMap<Integer,String> handleGetBondList()
    {
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "John and Maryy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy");
        list.put(13,"John" + " and " + "Steve");
        list.put(12,"John2" + " and " + "Steve2");
        list.put(11,"John3" + " and " + "Steve3");
        list.put(10,"John4" + " and " + "Steve4");
        list.put(9,"John5" + " and " + "Steve5");
        list.put(8,"John6" + " and " + "Steve6");
        return list;
    }

    /// Group Settings
    public void handleAddGroupRequest(String title, ArrayList<Integer> personIds) {
        // TODO: Create new group in register
    }

    public String handleGetGroupTitle(int id) {
        // TODO: Get from register
        return "My Team";
    }

    public ArrayList<Integer> handleGetGroupMembers(int id) {
        // TODO: Get from register
        ArrayList<Integer> members = new ArrayList<>();
        members.add(10);
        members.add(13);
        return members;
    }

    public void handleEditGroupRequest(int id, String title, ArrayList<Integer> personIds) {
        // TODO: Update group in register
    }

    public void handleDeleteGroupRequest(int id) {
        // TODO: Remove group from register
    }

    public HashMap<Integer,String> handleGetGroupList()
    {
        // TODO: Get groups from register
        HashMap<Integer,String> list = new HashMap<>();
        list.put(13,"30415");
        list.put(12,"30414");
        list.put(11,"30412");
        list.put(10,"30411");
        list.put(9,"30410");
        list.put(8,"3041-1");
        return list;
    }

    /// Save location settings
    public void handleSetSaveLocation(String filepath) {
        // TODO: Change Persistence location
    }

    public void handleGetSaveLocation(String filepath) {
        // TODO: Load data from file
    }

    /// Active group interactions
    public void handleChangeCurrentGroup(int groupId) {
        // TODO: Set this as the active group
    }

    public HashMap<Integer, String> handleGetPeopleInCurrentGroup() {
        // TODO: Return only people in active group
        HashMap<Integer,String> list = new HashMap<>();
        list.put(10,"John");
        list.put(13,"Mary");
        return list;
    }

    public void handleAddPersonToCurrentGroup(int id)
    {
        /// TODO: Register add to Current Group
    }

    public HashMap<Integer, String> handleGetPeopleNotInCurrentGroup() {
        // TODO: Return people NOT in active group
        HashMap<Integer,String> list = new HashMap<>();
        list.put(8,"Marcus");
        list.put(9,"Peter");
        return list;
    }

    public void handleRemovePersonFromCurrentGroup(int id)
    {
        /// TODO: Register add to Active group
    }

    public HashMap<Integer, String> handleGetBondsInCurrentGroup() {
        // TODO: Return bonds within active group (format: "Alice and Bob")
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "John and Mary");
        list.put(2, "Mary and Peter");
        return list;
    }

    public void handleRaiseBondInCurrentGroup(int bondId) {
        // TODO: Increase bond rating by 1 (max 10)
    }

    public void handleLowerBondInCurrentGroup(int bondId) {
        // TODO: Decrease bond rating by 1 (min 1)
    }

    public void handleRaiseAllBondsInCurrentGroup() {
        // TODO: Increase all bonds in current group by 1 (max 10)
    }

    public void handleLowerAllBondsInCurrentGroup() {
        // TODO: Decrease all bonds in current group by 1 (min 1)
    }

    /// Right side

    public HashMap<Integer, String> handleGetActiveGroupPartitions(Integer groupCount)
    {
        /// TODO: Partition Generator
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "John and Maryy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy yy");
        list.put(2, "Mary and Peter");
        return list;
    }

    public HashMap<Integer,String> handleGetAscendingDirectCentrality(){
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "Top");
        list.put(2, "Mid");
        list.put(3, "Bot");
        return list;
    }

    public HashMap<Integer,String> handleGetAscendingIndirectCentrality(){
        HashMap<Integer,String> list = new HashMap<>();
        list.put(1, "Top");
        list.put(2, "Mid");
        list.put(3, "Bot");
        return list;
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
