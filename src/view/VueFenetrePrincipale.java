package view;

import controller.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VueFenetrePrincipale extends vueAbstraite implements IObservable {

    private ControleurPrincipal monControleur;

    private JTabbedPane tpPrincipal;

    private JDialog frameAjoutModif;

    private JTextField tLibelle;

    private JTableDonnees tableManifestations;
    private JTableDonnees tableContacts;
    private JTableDonnees tableDepartements;

    private JWindow wSplashScreen;
    private JLabel lMessage;

    public VueFenetrePrincipale() {

        super("Gestion des événements");
        try {
            this.creerSplashScreen();
            this.lMessage.setText("Chargment des données");
            Thread.sleep(2000);
            this.monControleur = new ControleurPrincipal(this);
            this.lMessage.setText("Création de l'interface graphique");
            Thread.sleep(3000);
            this.add(creerBarreOutils(), BorderLayout.NORTH);
            this.add(creerPanelPrincipal(), BorderLayout.CENTER);

            this.gereEcouteur();

            this.gereButtonsActifs();

            this.setSize(700, 500);
            this.setLocation(300, 100);

            wSplashScreen.dispose();

            this.setVisible(true);

            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } catch (InterruptedException ex) {
            Logger.getLogger(VueFenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void creerSplashScreen() {
        wSplashScreen = new JWindow();

        JLabel lTitre = new JLabel("Gestionnaire de rencontres étudiants");
        lTitre.setFont(new Font(null, WIDTH, 26));
        lTitre.setBorder(new EmptyBorder(4, 25, 4, 20));

        ClassLoader cl = this.getClass().getClassLoader();
        JLabel lLogo = new JLabel(new ImageIcon(cl.getResource("view/images/logo2.png")));
        lLogo.setBorder(new EmptyBorder(20, 20, 4, 20));

        lMessage = new JLabel();

        lMessage.setFont(new Font(null, WIDTH, 12));
        lMessage.setBorder(new EmptyBorder(0, 20, 2, 20));

        JPanel p = new JPanel();
        p.setBackground(Color.yellow);
        p.setBorder(new LineBorder(Color.black));
        p.setLayout(new BorderLayout());
        p.add(lLogo, BorderLayout.NORTH);
        p.add(lTitre, BorderLayout.CENTER);
        p.add(lMessage, BorderLayout.SOUTH);

        wSplashScreen.add(p);
        wSplashScreen.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        wSplashScreen.setLocation(dim.width / 2 - wSplashScreen.getSize().width / 2, dim.height / 3 - wSplashScreen.getSize().height / 2);
        wSplashScreen.setVisible(true);

    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
        this.bSupprimer.addActionListener(monControleur);
        this.bModifier.addActionListener(monControleur);
        
        this.bManifDefaut.addActionListener(monControleur);

        this.tpPrincipal.addChangeListener(monControleur);

        this.tSearch.getDocument().addDocumentListener(monControleur);

        this.tableManifestations.getSelectionModel().addListSelectionListener(monControleur);
        this.tableDepartements.getSelectionModel().addListSelectionListener(monControleur);


    }

    private void gereEcouteurAM() {

        this.tLibelle.getDocument().addDocumentListener(monControleur);

        this.bSubmit.addActionListener(monControleur);
        this.bCancel.addActionListener(monControleur);
    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        this.tpPrincipal = new JTabbedPane();

        this.tableManifestations = new JTableDonnees(null, this.monControleur.getDonneesManifestation());
        tpPrincipal.addTab("Manifestations", this.creerPanelTable(this.tableManifestations));

        this.tableDepartements = new JTableDonnees(null, this.monControleur.getDonneesDepartement());
        tpPrincipal.addTab("Départements", this.creerPanelTable(this.tableDepartements));

        this.tableContacts = new JTableDonnees(null, this.monControleur.getDonneesContact());
        tpPrincipal.addTab("Contacts", this.creerPanelTable(this.tableContacts));

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
    public String getLibelle() {
        return this.tLibelle.getText();
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
                this.bManifDefaut.setEnabled(false);

                if (this.tableManifestations.getSelectedRowCount() == 1) {
                    this.bModifier.setEnabled(true);
                } else {
                    this.bModifier.setEnabled(false);
                }

                if (this.tableManifestations.getSelectedRowCount() > 0) {
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
                this.bManifDefaut.setEnabled(false);

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
                this.bManifDefaut.setEnabled(true);
                this.bManifDefaut.setToolTipText("Importer");
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
                selectedRow = this.tableManifestations.getSelectedRow();
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
    public int getActivePane() {
        return this.tpPrincipal.getSelectedIndex();
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
    public void filtrer(String[] search) {
        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                this.tableManifestations.filtrer(search);
                break;
        }
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
                selectedRows = this.tableManifestations.getSelectedRows();
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
}
