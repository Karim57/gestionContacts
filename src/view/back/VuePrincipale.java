package view.back;

import controller.back.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import model.business.Contact;
import model.business.Departement;
import model.business.Manifestation;
import view.JTableDonnees;
import view.VueAbstract;

public class VuePrincipale extends VueAbstract implements IOPrincipale {

    private ControleurPrincipal monControleur;

    private JTabbedPane tpPrincipal;

    private JDialog frameAjoutModif;

    private JTextField tLibelle;

    private JTableDonnees tableManifestations;
    private JTableDonnees tableContacts;
    private JTableDonnees tableDepartements;

    private JWindow wSplashScreen;
    private JLabel lMessage;

    private JToolBar pOutils;

    private JFileChooser chooserImport;

    public VuePrincipale() {

        super("Gestion des événements");
        try {
            this.creerSplashScreen();
            this.lMessage.setText("Chargement des données ...");
            Thread.sleep(2000);
            this.monControleur = new ControleurPrincipal(this);

            pOutils = super.creerBarreOutils();

            this.add(pOutils, BorderLayout.NORTH);
            this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

            this.gereEcouteur();

            this.gereButtonsActifs();

            this.setSize(1000, 600);
            this.setLocation(300, 100);

            this.lMessage.setText("Création de l'interface graphique");
            Thread.sleep(3000);

            this.wSplashScreen.dispose();

            this.setVisible(true);

            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } catch (InterruptedException ex) {
            Logger.getLogger(VuePrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void creerSplashScreen() {
        this.wSplashScreen = new JWindow();

        JLabel lTitre = new JLabel("Gestionnaire de rencontres étudiants");
        lTitre.setFont(new Font(null, WIDTH, 26));
        lTitre.setBorder(new EmptyBorder(4, 25, 10, 20));

        ClassLoader cl = this.getClass().getClassLoader();
        JLabel lLogo = new JLabel(new ImageIcon(cl.getResource("view/images/logo2.png")));
        lLogo.setBorder(new EmptyBorder(10, 5, 10, 20));

        this.lMessage = new JLabel();

        this.lMessage.setForeground(Color.GRAY);
        this.lMessage.setFont(new Font(null, 1, 13));
        this.lMessage.setBorder(new EmptyBorder(5, 24, 0, 10));

        JLabel lCopyright = new JLabel();

        lCopyright.setForeground(Color.BLACK);
        lCopyright.setFont(new Font(null, 0, 10));
        lCopyright.setBorder(new EmptyBorder(4, 24, 4, 10));
        lCopyright.setText("Copyright (c) Licence Professionnelle Web et Commerce Electronique");

        JPanel pLabel = new JPanel(new BorderLayout());
        pLabel.setBackground(Color.WHITE);
        pLabel.setBorder(new MatteBorder(5, 0, 0, 0, Color.darkGray));
        pLabel.add(this.lMessage, BorderLayout.NORTH);
        pLabel.add(lCopyright, BorderLayout.CENTER);

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.yellow);
        p.setBorder(new LineBorder(Color.black, 1));
        p.add(lLogo, BorderLayout.NORTH);
        p.add(lTitre, BorderLayout.CENTER);
        p.add(pLabel, BorderLayout.SOUTH);

        this.wSplashScreen.add(p);
        this.wSplashScreen.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.wSplashScreen.setLocation(dim.width / 2 - wSplashScreen.getSize().width / 2, dim.height / 3 - wSplashScreen.getSize().height / 2);
        this.wSplashScreen.setVisible(true);

    }

    @Override
    public String fileChooser() {

        FileSystemView fsv = FileSystemView.getFileSystemView();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(fsv.getRoots()[0]);
        chooser.setDialogTitle("Dossier d'exportation");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setApproveButtonText("Sélectionner");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        return null;

    }

    private void gereEcouteur() {
        super.bNouveau.addActionListener(monControleur);
        super.bSupprimer.addActionListener(monControleur);
        super.bModifier.addActionListener(monControleur);

        super.bExporter.addActionListener(monControleur);
        super.bImporter.addActionListener(monControleur);
        super.bProfil.addActionListener(monControleur);

        super.bOuvreEns.addActionListener(monControleur);
        super.bOuvreDpt.addActionListener(monControleur);

        super.bStats.addActionListener(monControleur);

        this.tpPrincipal.addChangeListener(monControleur);

        super.tSearch.getDocument().addDocumentListener(monControleur);

        this.tableManifestations.getSelectionModel().addListSelectionListener(monControleur);
        this.tableDepartements.getSelectionModel().addListSelectionListener(monControleur);
        this.tableContacts.getSelectionModel().addListSelectionListener(monControleur);

        pOutils.addMouseListener(monControleur);
        tpPrincipal.addMouseListener(monControleur);

        this.tableManifestations.addMouseListener(monControleur);
        this.tableDepartements.addMouseListener(monControleur);
        this.tableContacts.addMouseListener(monControleur);

        this.addMouseListener(monControleur);

    }

    private void gereEcouteurAM() {

        this.tLibelle.getDocument().addDocumentListener(monControleur);

        super.bSubmit.addActionListener(monControleur);
        super.bCancel.addActionListener(monControleur);
    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        this.tpPrincipal = new JTabbedPane();

        this.tableManifestations = new JTableDonnees(null, this.monControleur.getDonneesManifestation());
        this.tpPrincipal.addTab("Manifestations", this.creerPanelTable(this.tableManifestations));
        this.tableManifestations.filtrer(null);

        this.tableDepartements = new JTableDonnees(null, this.monControleur.getDonneesDepartement());
        this.tpPrincipal.addTab("Départements", this.creerPanelTable(this.tableDepartements));
        this.tableDepartements.filtrer(null);

        this.tableContacts = new JTableDonnees(null, this.monControleur.getDonneesContact());
        this.tpPrincipal.addTab("Contacts", this.creerPanelTable(this.tableContacts));
        this.tableContacts.filtrer(null);

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
    public String fileChooserImport() {

        chooserImport = new JFileChooser();

        chooserImport.addPropertyChangeListener(monControleur);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers XML", "xml");
        chooserImport.setFileFilter(filter);
        chooserImport.setAcceptAllFileFilterUsed(false);
        FileSystemView fsv = FileSystemView.getFileSystemView();
        chooserImport.setCurrentDirectory(fsv.getRoots()[0]);
        chooserImport.setDialogTitle("Importer des contacts");
        chooserImport.setApproveButtonText("Ouvrir");

        if (chooserImport.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooserImport.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    @Override
    public void creerPanelRecap(Manifestation manifestation, String date_deb, String date_fin, List<Departement> listeDpt) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        if (manifestation != null) {
            JLabel lManif = new JLabel("Manifestation : " + manifestation.getLibelleManif());
            p.add(lManif);
        }

        if (date_deb != null) {
            JLabel lDate_deb = new JLabel("Date début : " + date_deb);
            p.add(lDate_deb);
        }

        if (date_fin != null) {
            JLabel lDate_fin = new JLabel("Date fin : " + date_fin);
            p.add(lDate_fin);
        }

        if (!listeDpt.isEmpty()) {
            p.add(new JLabel("Départements : "));
        }
        for (Departement d : listeDpt) {
            if (d != null) {
                JLabel lDpt = new JLabel(d.getLibelleDepartement());
                p.add(lDpt);
            }
        }
        p.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.chooserImport.setAccessory(p);
        this.chooserImport.revalidate();
    }

    @Override
    public void gereButtonsActifs() {

        super.cListeDpt.setVisible(false);

        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                super.bNouveau.setEnabled(true);
                super.bModifier.setEnabled(true);
                super.bSupprimer.setEnabled(true);
                super.bExporter.setEnabled(true);
                super.bStats.setEnabled(true);

                super.bImporter.setEnabled(false);
                super.bOuvreDpt.setEnabled(false);
                super.bOuvreEns.setEnabled(false);
                super.bProfil.setEnabled(false);

                if (this.tableManifestations.getSelectedRowCount() == 1) {
                    super.bModifier.setEnabled(true);
                    super.bStats.setEnabled(true);
                    super.bExporter.setEnabled(true);
                } else {
                    super.bModifier.setEnabled(false);
                    super.bStats.setEnabled(false);
                    super.bExporter.setEnabled(false);
                }

                if (this.tableManifestations.getSelectedRowCount() > 0) {
                    super.bSupprimer.setEnabled(true);
                } else {
                    super.bSupprimer.setEnabled(false);
                }

                break;
            case 1:
                super.bNouveau.setEnabled(true);
                super.bModifier.setEnabled(true);
                super.bSupprimer.setEnabled(true);
                super.bOuvreDpt.setEnabled(true);
                super.bOuvreEns.setEnabled(true);

                super.bExporter.setEnabled(false);
                super.bStats.setEnabled(false);
                super.bImporter.setEnabled(false);
                super.bProfil.setEnabled(false);

                if (this.tableDepartements.getSelectedRowCount() == 1) {
                    super.bModifier.setEnabled(true);
                } else {
                    super.bModifier.setEnabled(false);
                }

                if (this.tableDepartements.getSelectedRowCount() > 0) {
                    super.bSupprimer.setEnabled(true);
                } else {
                    super.bSupprimer.setEnabled(false);
                }

                if ((this.tableDepartements.getSelectedRowCount() <= 1)) {
                    super.bOuvreDpt.setEnabled(true);
                    super.bOuvreEns.setEnabled(true);
                } else {
                    super.bOuvreDpt.setEnabled(false);
                    super.bOuvreEns.setEnabled(false);
                }
                break;
            case 2:
                super.bImporter.setEnabled(true);
                super.bProfil.setEnabled(true);

                super.bNouveau.setEnabled(false);
                super.bModifier.setEnabled(false);
                super.bSupprimer.setEnabled(false);
                super.bOuvreDpt.setEnabled(false);
                super.bOuvreEns.setEnabled(false);
                super.bExporter.setEnabled(false);
                super.bStats.setEnabled(false);

                if ((this.tableContacts.getSelectedRowCount() == 1)) {
                    super.bProfil.setEnabled(true);
                } else {
                    super.bProfil.setEnabled(false);
                }

                break;
        }
    }

    @Override
    public void construitProfil(Contact c) {

        String[] s = {"", ""};
        Object[][] data = {
            {"Lieu de la rencontre", c.getManifestation().getLibelleManif()},
            {"Enseignant", c.getEnseignant().getNomEnseignant() + " " + c.getEnseignant().getPrenomEnseignant()},
            {"Date", c.getDateContact()},
            {"Heure", c.getHeureContact()},
            {"Nom du contact", c.getNomContact()},
            {"Description", c.getDescriptionContact()}
        };

        JTable jt = new JTable(data, s);
        jt.setRowHeight(5, 120);

        JLabel lManifestation = new JLabel("Contact rencontré à : " + c.getManifestation().getLibelleManif());
        lManifestation.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lNomContact = new JLabel("Nom : " + c.getNomContact());
        lNomContact.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel lPrenomContact = new JLabel("Prenom : " + c.getPrenomContact());
        lPrenomContact.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lMailContact = new JLabel("E-mail : " + c.getEmailContact());
        lMailContact.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lEtude1 = new JLabel("Etude 1 : " + c.getEtudes1Contact());
        lEtude1.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lEtude2 = new JLabel("Etude 2 : " + c.getEtudes2Contact());
        lEtude2.setBorder(new EmptyBorder(5, 0, 0, 0));

        JTextArea lDescription = new JTextArea(c.getDescriptionContact());
        lDescription.setWrapStyleWord(true);
        lDescription.setEditable(false);
        lDescription.setLineWrap(true);
        JScrollPane spDescription = new JScrollPane(lDescription);
        lDescription.setBorder(new EmptyBorder(3, 20, 0, 15));
        spDescription.setBorder(new EmptyBorder(0, 0, 10, 0));
        spDescription.setOpaque(true);
        lDescription.setBackground(new Color(240, 240, 240));
        JLabel lDescTitle = new JLabel("Description : ");
        lDescTitle.setBorder(new EmptyBorder(15, 0, 0, 0));

        JLabel lHeure = new JLabel(" à " + c.getHeureContact().toString());
        lHeure.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lDate = new JLabel(" Le : " + c.getDateContact().toString());
        lDate.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lEns = new JLabel("Enseignant : " + c.getEnseignant().getNomEnseignant() + " " + c.getEnseignant().getPrenomEnseignant());
        lEns.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lChoix1 = new JLabel();
        lChoix1.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lChoix2 = new JLabel();
        lChoix2.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lChoix3 = new JLabel();
        lChoix3.setBorder(new EmptyBorder(5, 0, 0, 0));

        JLabel lChoix4 = new JLabel();
        lChoix4.setBorder(new EmptyBorder(5, 0, 0, 0));

        if (c.getListeFormations().size() > 0) {
            lChoix1.setText("Formation souhaitée 1 : " + c.getListeFormations().get(0).getLibelleFormation());
        }

        if (c.getListeFormations().size() > 1) {
            lChoix2.setText("Formation souhaitée 2 : " + c.getListeFormations().get(1).getLibelleFormation());
        }

        if (c.getListeFormations().size() > 2) {
            lChoix3.setText("Formation souhaitée 3 : " + c.getListeFormations().get(2).getLibelleFormation());
        }

        if (c.getListeFormations().size() > 3) {
            lChoix4.setText("Formation souhaitée 3 : " + c.getListeFormations().get(3).getLibelleFormation());
        }

        JDialog vueProfil = new JDialog(this, "Aperçu d'un contact");
        JPanel pInfosRencontre = new JPanel();
        pInfosRencontre.setLayout(new BoxLayout(pInfosRencontre, BoxLayout.Y_AXIS));

        pInfosRencontre.add(lManifestation);
        pInfosRencontre.add(lEns);
        pInfosRencontre.add(lDate);
        pInfosRencontre.add(lHeure);

        JPanel pInfosContact = new JPanel();
        pInfosContact.setLayout(new BoxLayout(pInfosContact, BoxLayout.Y_AXIS));

        pInfosContact.add(lNomContact);
        pInfosContact.add(lPrenomContact);
        pInfosContact.add(lMailContact);
        pInfosContact.add(lEtude1);
        pInfosContact.add(lEtude2);

        pInfosContact.add(lChoix1);
        pInfosContact.add(lChoix2);
        pInfosContact.add(lChoix3);
        pInfosContact.add(lChoix4);

        pInfosContact.add(lDescTitle);

        JPanel pTotal = new JPanel();

        pTotal.setLayout(new BoxLayout(pTotal, BoxLayout.Y_AXIS));

        pTotal.add(pInfosRencontre, BorderLayout.NORTH);
        pTotal.add(pInfosContact, BorderLayout.CENTER);
        pTotal.add(spDescription, BorderLayout.SOUTH);

        vueProfil.add(pTotal);

        vueProfil.pack();
        vueProfil.setSize(vueProfil.getWidth() + 200, vueProfil.getHeight());
        vueProfil.setLocation(400, 100);
        vueProfil.setModal(true);
        vueProfil.setVisible(true);

    }

    @Override
    public void construitAjoutModif() {

        this.frameAjoutModif = new JDialog(this, "");
        this.frameAjoutModif.setAlwaysOnTop(true);

        if (this.tpPrincipal.getSelectedIndex() == 0) {
            this.frameAjoutModif.setTitle("Ajouter une manifestaion");
        } else if (this.tpPrincipal.getSelectedIndex() == 1) {
            this.frameAjoutModif.setTitle("Ajouter un département");
        }

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel pSaisie = this.construitPanelSaisieAjout();
        JPanel pValidation = super.construitPanelButtons();

        p.add(pSaisie);
        p.add(pValidation);

        this.gereEcouteurAM();

        this.frameAjoutModif.add(p);

        this.frameAjoutModif.setSize(400, 100);
        this.frameAjoutModif.setResizable(false);
        this.frameAjoutModif.setLocation(450, 300);

        this.frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    @Override
    public void afficheAjoutModif() {
        this.frameAjoutModif.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frameAjoutModif.setVisible(true);
    }

    @Override
    public void prepareModif() {

        if (this.tpPrincipal.getSelectedIndex() == 0) {
            this.frameAjoutModif.setTitle("Modifier une manifestaion");
        } else if (this.tpPrincipal.getSelectedIndex() == 1) {
            this.frameAjoutModif.setTitle("Modifier un département");
        }
        super.bSubmit.setText("Modifer");
        super.bSubmit.setActionCommand("submitModif");
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
        this.tLibelle.getDocument().putProperty("id", "tAjoutModif");

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
                selectedRow = this.tableManifestations.convertRowIndexToModel(this.tableManifestations.getSelectedRow());
                break;
            case 1:
                selectedRow = this.tableDepartements.convertRowIndexToModel(this.tableDepartements.getSelectedRow());
                break;
            case 2:
                selectedRow = this.tableContacts.convertRowIndexToModel(this.tableContacts.getSelectedRow());
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
        super.bSubmit.setEnabled(this.tLibelle.getText().trim().length() > 0);
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
            case 1:
                this.tableDepartements.filtrer(search);
                break;
            case 2:
                this.tableContacts.filtrer(search);
                break;
        }
    }

    @Override
    public int confirmation(String message, String titre, int typeMessage, int icone) {
        return JOptionPane.showConfirmDialog(this, message, titre, typeMessage, icone, null);
    }

    @Override
    public void afficheMessage(String message, String titre, int typeMessage) {
        JOptionPane.showMessageDialog(this, message, titre, typeMessage);
    }

    @Override
    public int[] getLesLignesSelectionnee() {
        int[] selectedRows = {0};
        switch (this.tpPrincipal.getSelectedIndex()) {
            case 0:
                selectedRows = this.tableManifestations.getLignesSelectionnees();
                break;
            case 1:
                selectedRows = this.tableDepartements.getLignesSelectionnees();
                break;
            case 2:
                selectedRows = this.tableContacts.getLignesSelectionnees();
                break;
        }
        return selectedRows;
    }

    @Override
    public void annulerSelectionTables() {
        this.tableContacts.clearSelection();
        this.tableDepartements.clearSelection();
        this.tableManifestations.clearSelection();
    }
}
