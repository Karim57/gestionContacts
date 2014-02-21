package view;

import controller.ControleurEnseignant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;

public class VueEnseignants extends vueAbstraite implements IObservable, IOEnseignant {

    private ControleurEnseignant monControleur;

    private JDialog frameAjoutModif;

    private JTextField tNom;

    private JTableDonnees tableEnseignants;

    private int idDpt = -1;

    public VueEnseignants() {
        this(-1);
    }

    public VueEnseignants(int id_dpt) {

        super("Gestion des enseignants");

        this.idDpt = id_dpt;
        this.monControleur = new ControleurEnseignant(this);

        this.add(creerBarreOutils(), BorderLayout.NORTH);
        this.add(creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.setSize(700, 500);
        this.setLocation(300, 100);

        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public int getIdDpt() {
        return idDpt;
    }

    @Override
    public String getLibelle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void activeButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construitAjoutModif() {
        frameAjoutModif = new JDialog();
        frameAjoutModif.setModal(true);

        frameAjoutModif.setTitle("Ajouter un enseignant");

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel pSaisie = this.construitPanelSaisieAjout();
        JPanel pValidation = this.construitPanelButtons();

        p.add(pSaisie);
        p.add(pValidation);

        this.gereEcouteurAM();

        frameAjoutModif.add(p);

        frameAjoutModif.setSize(400, 100);
        frameAjoutModif.setResizable(false);
        frameAjoutModif.setLocation(450, 300);
        frameAjoutModif.setVisible(true);

        frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public int getLigneSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getActivePane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remplitChamps(String libelle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preapreModif() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void autoriseAjoutModif() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        this.tableEnseignants = new JTableDonnees(null, this.monControleur.getDonneesEnseignant());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        p.add(this.creerPanelTable(this.tableEnseignants), gbc);

        return p;
    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
        this.bSupprimer.addActionListener(monControleur);
        this.bModifier.addActionListener(monControleur);
    }

    private void gereEcouteurAM() {

        this.tNom.getDocument().addDocumentListener(monControleur);

        this.bSubmit.addActionListener(monControleur);
        this.bCancel.addActionListener(monControleur);
    }

    private JPanel construitPanelSaisieAjout() {
        return null;
    }

}
