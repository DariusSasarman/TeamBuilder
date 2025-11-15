package graphpackage;

import datapackage.Person;

import java.awt.*;

public class Node extends DrawableElement{

    private Person p;

    public Node( Person p, int x1, int x2, int y1, int y2, Color c)
    {
        super(x1,x2,y1,y2,c);
        this.p = p;
    }

    @Override
    public void onDraw(Graphics g) {

    }

    @Override
    public void onClick()
    {

    }
}
