package graphpackage;

import java.awt.*;

public abstract class DrawableElement {
    protected int x1;
    protected int x2;
    protected int y1;
    protected int y2;

    protected Color color;

    public DrawableElement(int x1, int x2, int y1, int y2, Color c)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = c;
    }

    abstract public void onDraw(Graphics g);

    abstract public void onClick();
}
