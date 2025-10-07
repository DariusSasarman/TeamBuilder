package DataPackage;

import java.awt.image.BufferedImage;

//abstract
//static

class Person {
    private int id;
    private BufferedImage image;
    private String name;
    private String notes;

    public Person (int id, BufferedImage image, String name)
    {
        this.id = id;
        this.image = image;
        this.name = name;
        this.notes = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId()
    {
        return this.id;
    }

    public String getNotes()
    {
        return this.notes;
    }

    public BufferedImage getImage()
    {
        return this.image;
    }
}
