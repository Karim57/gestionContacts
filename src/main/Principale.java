package main;

import javax.swing.JFrame;
import static model.business.ModelXML.genererXML;
import static model.business.ModelXML.sauvegarderDansFichier;
import view.SetParams;

public class Principale {

    public static void main(String[] args) {

        new SetParams(new JFrame());
     
       sauvegarderDansFichier(genererXML());
    }

}
