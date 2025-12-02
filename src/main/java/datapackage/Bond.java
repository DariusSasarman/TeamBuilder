package datapackage;

public class Bond extends Entity{

    private int headId;
    private int tailId;
    private int rating;

    public Bond (int bondId, int headId, int tailId, int rating, String notes)
    {
        super(bondId,notes);
        this.headId = headId;
        this.tailId = tailId;
        this.rating = rating;
        if(this.rating <= 0)
        {
            this.rating = 1;
        }
        if(this.rating >= 11)
        {
            this.rating = 10;
        }
    }

    public Bond (int bondId, int headId, int tailId, int rating)
    {
        super(bondId);
        this.headId = headId;
        this.tailId = tailId;
        this.rating = rating;
        if(this.rating <= 0)
        {
            this.rating = 1;
        }
        if(this.rating >= 11)
        {
            this.rating = 10;
        }
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public void setTailId(int tailId) {
        this.tailId = tailId;
    }

    public void setRating(int rating) {
        this.rating = rating;
        if(this.rating <= 0)
        {
            this.rating = 1;
        }
        if(this.rating >= 11)
        {
            this.rating = 10;
        }
    }

    public int getHeadId() {
        return headId;
    }

    public int getTailId() {
        return tailId;
    }

    public int getRating() {return rating;}

}
