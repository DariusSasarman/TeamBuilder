package graphpackage;

import datapackage.Bond;
import datapackage.Model;

import javax.swing.*;
import java.awt.*;

public class Edge extends DrawableElement{

    private int bondId;

    public Edge(int bondId, int x1, int x2, int y1, int y2)
    {
        super(x1,x2,y1,y2);
        this.bondId = bondId;
    }

    @Override
    public void onDraw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        int rating = Model.getBond(bondId).getRating();

        float width = 30/ Math.max(1f, 5f * rating / 10f) ;
        g2d.setStroke(new BasicStroke(width));

        Color color;
        if (rating <= 5) {
            // red to yellow
            int green = (int)(255 * rating / 5.0);
            color = new Color(255, green, 0);
        } else {
            // yellow to green
            int red = (int)(255 * (10 - rating) / 5.0);
            color = new Color(red, 255, 0);
        }
        g2d.setColor(color);
        g2d.drawLine(x1, y1, x2, y2);
    }
}
