package uipackage;

import javax.swing.*;

public class UiHandler {
    public static void increment(Integer count, JLabel label)
    {
        label.setText("The button has been pressed " + count + " times");
    }
}
