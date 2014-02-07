/*
package view.departement;

import controller.ControllerDepartement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.tables.ModeleDepartement;

public class AjoutDepartement {
    ControllerDepartement monControleur;

private JTextField tLibelle;
private JButton bSubmit;

public AjoutDepartement() extends DepartementTable implements AjoutObservable{

monControleur = super.getMonControleur();

this.construitInterface();
this.gereEcouteur();

this.setLocation(600, 100);
this.setSize(400, 100);

this.setResizable(false);

this.setVisible(true);

}

@Override
public String getLibelle() {
return tLibelle.getText();
}

private void gereEcouteur() {
this.bSubmit.addActionListener(monControleur);

this.tLibelle.getDocument().addDocumentListener(this.monControleur);
}

private void construitInterface() {

JPanel p = new JPanel();
p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

JPanel pSaisie = this.construitPanelSaisieDepartement();
JPanel pValidation = this.construitPanelButtons();

p.add(pSaisie);
p.add(pValidation);

this.add(p);
}

@Override
public void activeButton() {
this.bSubmit.setEnabled(this.tLibelle.getText().trim().length() > 0);
}

@Override
public void close() {
this.dispose();
}

@Override
public void remplitTable(ModeleDepartement modele) {
}

}
*/