package main;

import javax.swing.JFrame;
import model.business.Contact;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        Contact c = new Contact();
        new VueFenetrePrincipale();
    }
}
