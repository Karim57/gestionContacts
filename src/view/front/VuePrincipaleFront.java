package view.front;

import controller.front.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import model.business.Contact;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import view.JTableDonnees;

public class VuePrincipaleFront extends JFrame implements IOPrincipale {

    private ControleurPrincipal monControleur;
    private JButton bNouveau;
    private JButton bExporter;
    private JTableDonnees tableContacts;
    private JTextField nomTextField;
    private JComboBox formation1ComboBox;
    private JComboBox formation2ComboBox;
    private JComboBox formation3ComboBox;
    private JComboBox formation4ComboBox;
    private JTextArea descriptionTextArea;
    private JTextField prenomTextField;
    private JTextField emailTextField;
    private JTextField etude1TextField;
    private JTextField etude2TextField;
    private JButton okButton;

    private JLabel aucunDepartementLabel;
    private JLabel aucunEnseignantLabel;

    private JComboBox departementComboBox;
    private JComboBox enseignantComboBox;
    private JButton retourButtonEns;
    private JButton okButtonEns;

    private JButton bProfil;
    private JButton bOuvreEns;
    private JButton bImporter;

    private JDialog frameConnexion;

    private String cheminImport = null;

    public VuePrincipaleFront() {

        super("Gestion des contacts");

        this.monControleur = new ControleurPrincipal(this);

        this.add(this.creerBarreOutils(), BorderLayout.NORTH);
        this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.pack();
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);

        if (this.cheminImport == null) {
            this.cheminImport = this.fileChooser();
            this.monControleur.initDonnees();
        }
        this.addWindowListener(monControleur);
        this.setVisible(true);
        this.afficheConnexion(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
        this.bOuvreEns.addActionListener(monControleur);
        this.bExporter.addActionListener(monControleur);
        this.bProfil.addActionListener(monControleur);
        this.bImporter.addActionListener(monControleur);

        this.tableContacts.getSelectionModel().addListSelectionListener(monControleur);
    }

    private void gereEcouteurAjout() {

        this.nomTextField.getDocument().addDocumentListener(monControleur);
        this.prenomTextField.getDocument().addDocumentListener(monControleur);
        this.emailTextField.getDocument().addDocumentListener(monControleur);
        this.etude1TextField.getDocument().addDocumentListener(monControleur);

        this.okButton.addActionListener(monControleur);

        this.formation1ComboBox.addActionListener(monControleur);
        this.formation2ComboBox.addActionListener(monControleur);
        this.formation3ComboBox.addActionListener(monControleur);
        this.formation4ComboBox.addActionListener(monControleur);
    }

    @Override
    public Departement getDptSelected() {
        if (departementComboBox.getSelectedIndex() != 0) {
            return (Departement) this.departementComboBox.getSelectedItem();
        }
        return null;
    }

    @Override
    public Enseignant getEnsSelected() {
        return (Enseignant) this.enseignantComboBox.getSelectedItem();
    }

    @Override
    public void afficheConnexion(boolean obligatoire) {
        frameConnexion = new JDialog(this, "Connexion");

        if (obligatoire) {
            frameConnexion.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }

        frameConnexion.setLayout(new GridBagLayout());

        JLabel departementLabel = new JLabel("Département :");
        JLabel enseignantLabel = new JLabel("Enseignant :");

        aucunDepartementLabel = new JLabel("Aucun département");
        aucunEnseignantLabel = new JLabel("Aucun enseignant");

        aucunDepartementLabel.setVisible(false);
        aucunEnseignantLabel.setVisible(false);

        departementComboBox = new JComboBox<Departement>();
        departementComboBox.addItemListener(monControleur);

        enseignantComboBox = new JComboBox<Enseignant>();

        retourButtonEns = new JButton("Annuler");
        okButtonEns = new JButton("Connexion");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gbc.gridy = 0;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.insets = new Insets(10, 10, 0, 0);
        frameConnexion.add(departementLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 10);
        frameConnexion.add(departementComboBox, gbc);
        frameConnexion.add(aucunDepartementLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameConnexion.add(enseignantLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameConnexion.add(enseignantComboBox, gbc);
        frameConnexion.add(aucunEnseignantLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 10, 10);
        frameConnexion.add(retourButtonEns, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 10, 10);
        frameConnexion.add(okButtonEns, gbc);

        frameConnexion.setModal(true);
        frameConnexion.setMinimumSize(new Dimension(400, 190));
        frameConnexion.setLocationRelativeTo(null);

        this.monControleur.remplitComboDpt();

        retourButtonEns.setActionCommand("anuulerEns");
        okButtonEns.setActionCommand("connexion");
        okButtonEns.addActionListener(monControleur);
        retourButtonEns.addActionListener(monControleur);

        frameConnexion.setVisible(true);

    }

    private JToolBar creerBarreOutils() {

        JToolBar jtb = new JToolBar();
        jtb.setLayout(new BorderLayout());
        jtb.setBackground(new Color(240, 240, 240));

        jtb.add(this.construitPanelOutils(), BorderLayout.EAST);

        jtb.setFloatable(false);

        jtb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return jtb;

    }

    private void configurerButtons(JButton b, String ac) {

        Dimension dimButtons = new Dimension(40, 30);
        Color bgColor = new Color(240, 240, 240);

        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setActionCommand(ac);
        b.setBackground(bgColor);
        b.setPreferredSize(dimButtons);
    }

    private JPanel construitPanelOutils() {

        ClassLoader cl = this.getClass().getClassLoader();

        Color bgColor = new Color(240, 240, 240);

        this.bNouveau = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        this.configurerButtons(bNouveau, "Nouveau");
        this.bNouveau.setToolTipText("Ajouter");

        this.bImporter = new JButton(new ImageIcon(cl.getResource("view/images/import.png")));
        this.configurerButtons(bImporter, "Importer");
        this.bImporter.setToolTipText("Importer");

        this.bExporter = new JButton(new ImageIcon(cl.getResource("view/images/export.png")));
        this.configurerButtons(bExporter, "Exporter");
        this.bExporter.setToolTipText("Enregistrer");
        this.bExporter.setEnabled(false);

        this.bProfil = new JButton(new ImageIcon(cl.getResource("view/images/profil.png")));
        this.configurerButtons(bProfil, "Profil");
        this.bProfil.setToolTipText("Voir les détails du contact");
        this.bProfil.setEnabled(false);

        this.bOuvreEns = new JButton(new ImageIcon(cl.getResource("view/images/person.png")));
        this.configurerButtons(bOuvreEns, "Enseignant");
        this.bOuvreEns.setToolTipText("Changer d'enseignant");

        JPanel p = new JPanel(new GridBagLayout());

        p.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;

        p.add(this.bNouveau, gbc);

        gbc.gridx = 2;
        p.add(this.bProfil, gbc);

        gbc.gridx = 3;
        p.add(this.bOuvreEns, gbc);

        gbc.gridx = 4;
        p.add(this.bExporter, gbc);

        gbc.gridx = 5;
        p.add(this.bImporter, gbc);

        return p;
    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        this.tableContacts = new JTableDonnees(null, this.monControleur.getDonneesContact());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        p.add(this.creerPanelTable(this.tableContacts), gbc);

        return p;
    }

    private JPanel creerPanelTable(JTableDonnees table) {

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        table.getTableHeader().setBackground(new Color(245, 246, 247));
        table.getTableHeader().setFont(new Font("Arial", 1, 12));
        jp.add(table.getTableHeader(), gbc2);

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        jp.add(table, gbc2);

        JScrollPane pane = new JScrollPane(table);

        jp.add(pane, gbc2);

        return jp;

    }

    @Override
    public String getCheminImport() {
        return this.cheminImport;
    }

    private String fileChooser() {

        FileSystemView fsv = FileSystemView.getFileSystemView();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(fsv.getRoots()[0]);
        chooser.setDialogTitle("Choisir le dossier ou sont enregistrés les fichiers XML");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setApproveButtonText("Sélectionner");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            System.exit(0);
        }

        return null;
    }

    @Override
    public String fileChooserExport() {

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers XML", "xml");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(new File(this.cheminImport));
        chooser.setDialogTitle("Enregistrer le fichier de contact");
        chooser.setSelectedFile(new File("contacts.xml"));
        chooser.setApproveButtonText("Sauvegarder");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    @Override
    public String fileChooserImport() {

        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers XML", "xml");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(new File(this.cheminImport));
        chooser.setDialogTitle("Importer des contacts");
        chooser.setApproveButtonText("Ouvrir");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    @Override
    public String getNomContact() {
        return this.nomTextField.getText();
    }

    @Override
    public String getPrenomContact() {
        return this.prenomTextField.getText();
    }

    @Override
    public String getEmailContact() {
        return this.emailTextField.getText();
    }

    @Override
    public String getEtude1Contact() {
        return this.etude1TextField.getText();
    }

    @Override
    public String getEtude2Contact() {
        return this.etude2TextField.getText();
    }

    @Override
    public String getDescriptionContact() {
        return this.descriptionTextArea.getText();
    }

    @Override
    public Formation getForm1() {
        if (this.formation1ComboBox.getSelectedIndex() != 0) {
            return (Formation) this.formation1ComboBox.getSelectedItem();
        }
        return null;
    }

    @Override
    public Formation getForm2() {
        if (this.formation2ComboBox.getSelectedIndex() != 0) {
            return (Formation) this.formation2ComboBox.getSelectedItem();
        }
        return null;
    }

    @Override
    public Formation getForm3() {
        if (this.formation3ComboBox.getSelectedIndex() != 0) {
            return (Formation) this.formation3ComboBox.getSelectedItem();
        }
        return null;
    }

    public Formation getForm4() {
        if (this.formation4ComboBox.getSelectedIndex() != 0) {
            return (Formation) this.formation4ComboBox.getSelectedItem();
        }
        return null;
    }

    @Override
    public void setListeEns(ArrayList<Enseignant> liste) {
        if (this.departementComboBox.getSelectedIndex() == 0) {
            this.enseignantComboBox.setVisible(false);
            this.okButtonEns.setEnabled(false);
            this.aucunEnseignantLabel.setVisible(true);
            this.aucunEnseignantLabel.setText("Choisissez d'abord un département");
        } else {
            if (liste.isEmpty()) {
                this.enseignantComboBox.setVisible(false);
                this.aucunEnseignantLabel.setVisible(true);
                this.okButtonEns.setEnabled(false);
                this.aucunEnseignantLabel.setText("Aucun enseignant");
            } else {
                DefaultComboBoxModel modelEns = new DefaultComboBoxModel<Enseignant>(liste.toArray(new Enseignant[liste.size()]));
                this.enseignantComboBox.setModel(modelEns);
                this.okButtonEns.setEnabled(true);
                this.enseignantComboBox.setVisible(true);
                this.aucunEnseignantLabel.setVisible(false);
                this.enseignantComboBox.setSelectedIndex(0);
            }
        }
    }

    @Override
    public void setListeDpt(ArrayList<Departement> liste) {

        if (liste.isEmpty()) {
            this.enseignantComboBox.setVisible(false);
            this.aucunEnseignantLabel.setVisible(true);
            this.okButtonEns.setEnabled(false);
            this.aucunDepartementLabel.setVisible(true);
            this.departementComboBox.setVisible(false);
        } else {
            this.aucunDepartementLabel.setVisible(false);
            this.departementComboBox.setVisible(true);
            DefaultComboBoxModel modelDpt = new DefaultComboBoxModel<Departement>(liste.toArray(new Departement[liste.size()]));
            this.departementComboBox.setModel(modelDpt);
            this.departementComboBox.insertItemAt("Choisir un département", 0);
            this.departementComboBox.setSelectedIndex(0);
            this.okButtonEns.setEnabled(false);
        }
    }

    @Override
    public void setListeFormation1(ArrayList<Formation> liste
    ) {
        DefaultComboBoxModel modelFormation1 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation1ComboBox.setModel(modelFormation1);
        this.formation1ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation1ComboBox.setSelectedIndex(0);
    }

    @Override
    public void setListeFormation2(ArrayList<Formation> liste
    ) {
        DefaultComboBoxModel modelFormation2 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation2ComboBox.setModel(modelFormation2);
        this.formation2ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation2ComboBox.setSelectedIndex(0);
    }

    @Override
    public void setListeFormation3(ArrayList<Formation> liste
    ) {
        DefaultComboBoxModel modelFormation3 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation3ComboBox.setModel(modelFormation3);
        this.formation3ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation3ComboBox.setSelectedIndex(0);
    }

    @Override
    public void setListeFormation4(ArrayList<Formation> liste
    ) {
        DefaultComboBoxModel modelFormation4 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation4ComboBox.setModel(modelFormation4);
        this.formation4ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation4ComboBox.setSelectedIndex(0);
    }

    @Override
    public void close(int i) {
        if (i == 0) {
            this.frameConnexion.dispose();
        }
    }

    @Override
    public void construitAjout() {

        JDialog frameAjout = new JDialog(this, "Enregistrement");
        frameAjout.setModal(true);

        /* 1- Initialisation du container. */
        frameAjout.setLayout(new GridBagLayout());

        /* 2- Création et initialisation d'une série de composants. */
        JLabel titreLabel = new JLabel("Fiche de Rendez vous");
        JLabel nomLabel = new JLabel("Nom :");
        JLabel prenomLabel = new JLabel("Prénom :");
        JLabel emailLabel = new JLabel("Email :");
        JLabel etude1Label = new JLabel("Niveau étude 1 :");
        JLabel etude2Label = new JLabel("Niveau étude 2 :");
        JLabel formation1Label = new JLabel("Formation visée :");
        JLabel formation2Label = new JLabel("(optionnelle) ");
        JLabel formation3Label = new JLabel("(optionnelle) ");
        JLabel formation4Label = new JLabel("(optionnelle) ");
        JLabel descriptionLabel = new JLabel("Description :");

        formation1ComboBox = new JComboBox<Formation>();
        this.formation1ComboBox.setActionCommand("comboForm1");

        formation2ComboBox = new JComboBox<Formation>();
        this.formation2ComboBox.setActionCommand("comboForm2");

        formation3ComboBox = new JComboBox<Formation>();
        this.formation3ComboBox.setActionCommand("comboForm3");

        formation4ComboBox = new JComboBox<Formation>();
        this.formation4ComboBox.setActionCommand("comboForm4");

        this.monControleur.remplitComboFormation1();

        descriptionTextArea = new JTextArea(5, 1);
        JScrollPane scrollingNote = new JScrollPane(descriptionTextArea);

        nomTextField = new JTextField();
        prenomTextField = new JTextField();
        emailTextField = new JTextField();
        etude1TextField = new JTextField();
        etude2TextField = new JTextField();

        okButton = new JButton("Valider");
        this.okButton.setEnabled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        Font font = new Font("Arial", Font.BOLD, 18);
        titreLabel.setFont(font);
        gbc.gridx = gbc.gridy = 0;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 20, 0);
        frameAjout.add(titreLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(nomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(prenomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(etude1Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(etude1TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(etude2Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(etude2TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(formation1Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(formation1ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(formation2Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(formation2ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(formation3Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(formation3ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(formation4Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjout.add(formation4ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjout.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 0, 10);
        gbc.weightx = 1.;
        gbc.weighty = 1.;
        frameAjout.add(scrollingNote, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 10, 10, 10);
        frameAjout.add(okButton, gbc);

        this.gereEcouteurAjout();

        frameAjout.setSize(600, 500);

        frameAjout.setLocationRelativeTo(null);
        frameAjout.setVisible(true);
    }

    @Override
    public int confirmation(String message, String titre, int typeMessage, int icone
    ) {
        return JOptionPane.showConfirmDialog(this, message, titre, typeMessage, icone, null);
    }

    @Override
    public void afficheMessage(String message, String titre, int typeMessage
    ) {
        JOptionPane.showMessageDialog(this, message, titre, typeMessage);
    }

    @Override
    public void gereComboAjout2() {
        if (this.formation1ComboBox.getSelectedIndex() == 0) {
            this.formation2ComboBox.setEnabled(false);
            this.formation2ComboBox.removeAllItems();
        } else {
            this.monControleur.remplitComboFormation2();
            this.formation2ComboBox.removeItem(this.formation1ComboBox.getSelectedItem());
            this.formation2ComboBox.setEnabled(true);
        }
    }

    @Override
    public void gereComboAjout3() {
        if (this.formation2ComboBox.getSelectedIndex() == 0
                || this.formation1ComboBox.getSelectedIndex() == 0) {
            this.formation3ComboBox.setEnabled(false);
            this.formation3ComboBox.removeAllItems();
        } else {
            this.monControleur.remplitComboFormation3();
            this.formation3ComboBox.removeItem(this.formation1ComboBox.getSelectedItem());
            this.formation3ComboBox.removeItem(this.formation2ComboBox.getSelectedItem());
            this.formation3ComboBox.setEnabled(true);
        }
    }

    @Override
    public void gereComboAjout4() {
        if (this.formation3ComboBox.getSelectedIndex() == 0
                || this.formation2ComboBox.getSelectedIndex() == 0
                || this.formation1ComboBox.getSelectedIndex() == 0) {
            this.formation4ComboBox.setEnabled(false);
            this.formation4ComboBox.removeAllItems();
        } else {
            this.monControleur.remplitComboFormation4();
            this.formation4ComboBox.removeItem(this.formation1ComboBox.getSelectedItem());
            this.formation4ComboBox.removeItem(this.formation2ComboBox.getSelectedItem());
            this.formation4ComboBox.removeItem(this.formation3ComboBox.getSelectedItem());
            this.formation4ComboBox.setEnabled(true);
        }
    }

    @Override
    public void activeAjout() {
        this.okButton.setEnabled(this.nomTextField.getText().trim().length() != 0
                && this.prenomTextField.getText().trim().length() != 0
                && this.emailTextField.getText().trim().length() != 0
                && this.etude1TextField.getText().trim().length() != 0
                && formation1ComboBox.getSelectedIndex() != 0);
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
    public int getSelectedContact() {
        return this.tableContacts.getSelectedRow();
    }

    @Override
    public void gereButtonsActifs() {
        if (this.tableContacts.getSelectedRowCount() != 1) {
            this.bProfil.setEnabled(false);
        } else {
            this.bProfil.setEnabled(true);
        }
    }

    @Override
    public void activeSauvegarde(boolean b) {
        this.bExporter.setEnabled(b);
    }

    @Override
    public void viderChampsAjout() {
        this.nomTextField.setText("");
        this.prenomTextField.setText("");
        this.emailTextField.setText("");
        this.etude1TextField.setText("");
        this.etude2TextField.setText("");

        this.formation1ComboBox.setSelectedIndex(0);

        this.descriptionTextArea.setText("");
    }
}
