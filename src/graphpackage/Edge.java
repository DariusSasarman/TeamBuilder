package graphpackage;

import datapackage.Bond;

import java.awt.*;

public class Edge extends DrawableElement{

    private Bond b;

    public Edge(Bond b, int x1, int x2, int y1, int y2, Color color)
    {
        super(x1,x2,y1,y2,color);
        this.b = b;
    }

    @Override
    public void onDraw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void onClick()
    {

    }
}
