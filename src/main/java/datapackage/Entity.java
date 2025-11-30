package main.java.datapackage;

abstract class Entity {
    private int id;
    private String notes;

    public Entity(int id)
    {
        this.id = id;
        this.notes = "";
    }

    public Entity(int id, String notes)
    {
        this.id = id;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
