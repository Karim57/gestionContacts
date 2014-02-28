package view.utils;

import javax.swing.JOptionPane;

public class GereErreurs {

    public GereErreurs(String message, String titre) {
        JOptionPane.showMessageDialog(null, message, titre, 0);
    }
}
