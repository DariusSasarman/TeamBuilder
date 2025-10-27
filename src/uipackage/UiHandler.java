package uipackage;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class UiHandler {
    public void handleAddPersonRequest(BufferedImage img, String name, String notes)
    {
        /// TODO: Register
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

    public void handleAddBondRequest(int headId, int tailId, int rating)
    {
        /// TODO: Register
    }
}
