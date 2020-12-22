package shopping.model;

import shopping.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI();
            }
        });
    }
}
