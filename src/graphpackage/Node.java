package graphpackage;

import datapackage.Model;
import datapackage.Person;

import java.awt.*;
import java.awt.image.BufferedImage;

class Node extends DrawableElement{

    private int personId;

    public Node( int personId, int x, int y, int radiusPhoto, int BorderWidth)
    {
        super(x,radiusPhoto,y,BorderWidth);
        this.personId = personId;
    }

    public int getX()
    {
        return this.x1;
    }

    public int getY()
    {
        return this.y1;
    }

    public int getRadiusPhoto()
    {
        return this.x2;
    }

    public int getBorderWidth()
    {
        return this.y2;
    }

    private BufferedImage getImage()
    {
        return Model.getPerson(personId).getImage();
    }

    private String getName()
    {
        return Model.getPerson(personId).getName();
    }

    @Override
    public void onDraw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = getRadiusPhoto();
        int centerX = getX();
        int centerY = getY();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        BufferedImage img = getImage();
        if (img != null) {
            g2d.setClip(new java.awt.geom.Ellipse2D.Float(centerX - radius, centerY - radius, 2 * radius, 2 * radius));
            g2d.drawImage(img, centerX - radius, centerY - radius, 2 * radius, 2 * radius, null);
            g2d.setClip(null);
        }

        String name = getName();
        int rectWidth = getBorderWidth();
        int rectHeight = getBorderWidth()/8;
        int rectX = centerX - rectWidth / 2;
        int rectY = centerY + radius - rectHeight / 2;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, rectHeight / 2, rectHeight / 2);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(rectX, rectY, rectWidth, rectHeight, rectHeight / 2, rectHeight / 2);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(name);
        int textHeight = fm.getAscent();
        int textX = rectX + (rectWidth - textWidth) / 2;
        int textY = rectY + (rectHeight + textHeight) / 2 - 2;
        g2d.drawString(name, textX, textY);
    }
}
