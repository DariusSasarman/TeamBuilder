import datapackage.PersistenceManager;
import persistencepackage.Persistence;
import uipackage.Ui;
import uipackage.UiHandler;

public class Main {
    public static void main(String[] args) {
        new Persistence();
        new Ui(new UiHandler());
    }
}
