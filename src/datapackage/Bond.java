package datapackage;

class Bond {
    private int id;
    private int headId;
    private int tailId;
    private int rating;
    private String notes;

    public Bond (int bondId, int headId, int tailId, int rating, String notes)
    {
        this.id = bondId;
        this.headId = headId;
        this.tailId = tailId;
        this.rating = rating;
        this.notes = notes;
    }

    public Bond (int bondId, int headId, int tailId, int rating)
    {
        this.id = bondId;
        this.headId = headId;
        this.tailId = tailId;
        this.rating = rating;
        this.notes = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public void setTailId(int tailId) {
        this.tailId = tailId;
    }

    public void setRating(int rating) {this.rating = rating;}

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {return id;}

    public int getHeadId() {
        return headId;
    }

    public int getTailId() {
        return tailId;
    }

    public int getRating() {return rating;}

    public String getNotes() {
        return notes;
    }

    public int compareTo (Bond other)
    {
        return Integer.compare(this.id,other.id);
    }
}
