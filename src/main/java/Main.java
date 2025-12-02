
import persistencepackage.Persistence;
import uipackage.View;
import uipackage.Controller;

public class Main {
    public static void main(String[] args) {
        new Persistence();
        new View(new Controller());
    }
}
