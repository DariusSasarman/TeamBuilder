package main.java.datapackage;

import java.awt.image.BufferedImage;

public class Person extends Entity {
    private BufferedImage image;
    private String name;

    public Person (int id, BufferedImage image, String name)
    {
        super(id);
        this.image = image;
        this.name = name;
    }

    public Person (int id, BufferedImage image, String name, String notes)
    {
        super(id,notes);
        this.image = image;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    public String getName()
    {
        return this.name;
    }

    public BufferedImage getImage()
    {
        return this.image;
    }

}
