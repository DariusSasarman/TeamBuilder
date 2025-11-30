package main.java;

import main.java.persistencepackage.Persistence;
import main.java.uipackage.View;
import main.java.uipackage.Controller;

public class Main {
    public static void main(String[] args) {
        new Persistence();
        new View(new Controller());
    }
}
