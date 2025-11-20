package graphpackage;

import java.awt.*;

abstract class DrawableElement {
    protected int x1;
    protected int x2;
    protected int y1;
    protected int y2;

    public DrawableElement(int x1, int x2, int y1, int y2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    abstract public void onDraw(Graphics g);

}
