
/*package view.front;

import controller.front.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.business.Contact;
import view.JTableDonnees;
import view.VueAbstraite;

public class VuePrincipaleFront extends VueAbstraite implements IOPrincipale {

    private ControleurPrincipal monControleur;

    private JDialog frameAjoutModif;

    private JTableDonnees tableContacts;

    public VuePrincipaleFront() {

        super("Gestion des événements");
       
            this.add(creerBarreOutils(), BorderLayout.NORTH);
            this.add(creerPanelPrincipal(), BorderLayout.CENTER);

            this.gereEcouteur();

            this.gereButtonsActifs();

            this.setSize(1000, 600);
            this.setLocation(300, 100);

         

            this.setVisible(true);

            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       
    }

   
    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
        this.bSupprimer.addActionListener(monControleur);
        this.bModifier.addActionListener(monControleur);

        this.bExporter.addActionListener(monControleur);
        this.bProfil.addActionListener(monControleur);


        this.tSearch.getDocument().addDocumentListener(monControleur);

        this.tableContacts.getSelectionModel().addListSelectionListener(monControleur);

    }

    private void gereEcouteurAM() {

      

        this.bSubmit.addActionListener(monControleur);
        this.bCancel.addActionListener(monControleur);
    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
     

        this.tableContacts = new JTableDonnees(null, this.monControleur.getDonneesManifestation());
      


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        p.add(tpPrincipal, gbc);

        return p;
    }

    @Override
    public void remplitChamps(String libelle) {
        this.tLibelle.setText(libelle);
    }


    @Override
    public void close() {
        this.frameAjoutModif.dispose();
    }

    @Override
    public void gereButtonsActifs() {

        this.cListeDpt.setVisible(false);

        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                this.bNouveau.setEnabled(true);
                this.bNouveau.setToolTipText("Ajouter une manifestation");
                this.bModifier.setEnabled(true);
                this.bModifier.setToolTipText("Modifier cette manifestation");
                this.bSupprimer.setEnabled(true);
                this.bExporter.setEnabled(false);

                if (this.tableContacts.getSelectedRowCount() == 1) {
                    this.bModifier.setEnabled(true);
                } else {
                    this.bModifier.setEnabled(false);
                }

                if (this.tableContacts.getSelectedRowCount() > 0) {
                    this.bSupprimer.setEnabled(true);
                } else {
                    this.bSupprimer.setEnabled(false);
                }

                break;
            case 1:
                this.bNouveau.setEnabled(true);
                this.bNouveau.setToolTipText("Ajouter un département ");
                this.bModifier.setEnabled(true);
                this.bModifier.setToolTipText("Modifier ce département");
                this.bSupprimer.setEnabled(true);
                this.bExporter.setEnabled(false);
/*
                if (this.tableDepartements.getSelectedRowCount() == 1) {
                    this.bModifier.setEnabled(true);
                } else {
                    this.bModifier.setEnabled(false);
                }

                if (this.tableDepartements.getSelectedRowCount() > 0) {
                    this.bSupprimer.setEnabled(true);
                } else {
                    this.bSupprimer.setEnabled(false);
                }
                break;
            case 2:
                this.bNouveau.setEnabled(false);
                this.bModifier.setEnabled(false);
                this.bSupprimer.setEnabled(false);
                this.bExporter.setEnabled(true);
                this.bExporter.setToolTipText("Importer");
                break;
        }
    }

    @Override
    public void construitAjoutModif() {

        frameAjoutModif = new JDialog(this, "");
        frameAjoutModif.setAlwaysOnTop(true);

        if (this.tpPrincipal.getSelectedIndex() == 0) {
            frameAjoutModif.setTitle("Ajouter une manifestaion");
        } else if (this.tpPrincipal.getSelectedIndex() == 1) {
            frameAjoutModif.setTitle("Ajouter un département");
        }

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

        frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    @Override
    public void afficheAjoutModif() {
        frameAjoutModif.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        frameAjoutModif.setVisible(true);
    }

    @Override
    public void prepareModif() {

        if (this.tpPrincipal.getSelectedIndex() == 0) {
            frameAjoutModif.setTitle("Modifier une manifestaion");
        } else if (this.tpPrincipal.getSelectedIndex() == 1) {
            frameAjoutModif.setTitle("Modifier un département");
        }
        this.bSubmit.setText("Modifer");
        this.bSubmit.setActionCommand("submitModif");
    }

    private JPanel construitPanelSaisieAjout() {

        JPanel panelAjout = new JPanel();

        JLabel lLibelle = new JLabel();

        if (this.tpPrincipal.getSelectedIndex() == 0) {
            lLibelle.setText("Libellé la manifestation");
        } else if (this.tpPrincipal.getSelectedIndex() == 1) {
            lLibelle.setText("Libellé du département");
        }

        this.tLibelle = new JTextField(255);
        tLibelle.getDocument().putProperty("id", "tAjoutModif");

        panelAjout.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelAjout.add(lLibelle, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelAjout.add(this.tLibelle, gbc);

        return panelAjout;

    }

    @Override
    public int getLigneSelectionnee() {
        int selectedRow = 0;
        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                selectedRow = this.tableContacts.getSelectedRow();
                break;
            case 1:
                selectedRow = this.tableDepartements.getSelectedRow();
                break;
            case 2:
                selectedRow = this.tableContacts.getSelectedRow();
                break;
        }
        return selectedRow;
    }


    @Override
    public void autoriseAjoutModif() {
        this.bSubmit.setEnabled(this.tLibelle.getText().trim().length() > 0);
    }

    @Override
    public String getSearch() {
        return this.tSearch.getText().trim();
    }


    @Override
    public int confirmation(String message, String titre, int typeMessage, int icone) {
        return JOptionPane.showConfirmDialog(this, message, titre, typeMessage, icone, null);
    }

    @Override
    public void afficheErreur(String message, String titre, int typeMessage) {
        JOptionPane.showMessageDialog(this, message, titre, typeMessage);
    }

    @Override
    public int[] getLesLignesSelectionnee() {
        int[] selectedRows = {0};
        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                selectedRows = this.tableContacts.getSelectedRows();
                break;
            case 1:
                selectedRows = this.tableDepartements.getSelectedRows();
                break;
            case 2:
                selectedRows = this.tableContacts.getSelectedRows();
                break;
        }
        return selectedRows;
    }

    @Override
    public String getLibelle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getActivePane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construitProfil(Contact c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filtrer(String[] search) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
*/