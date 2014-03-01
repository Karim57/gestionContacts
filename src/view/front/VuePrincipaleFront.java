package view.front;

import controller.front.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;
import model.business.Departement;
import model.business.Formation;
import model.dao.xml.XMLFormationDAO;
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
    private JButton retourButton;
    private JButton okButton;

    private String cheminImport = null;

    public VuePrincipaleFront() {

        super("Gestion des contacts");

        this.monControleur = new ControleurPrincipal(this);

        this.add(this.creerBarreOutils(), BorderLayout.NORTH);
        this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.setSize(700, 350);
        this.setLocation(350, 250);

        if (this.cheminImport == null) {
            this.afficheMessage("Merci de choisir le dossier ou sont stockès les fichiers XML", "Dossier d'import", 1);
            this.cheminImport = this.fileChooser();
            this.monControleur.initDonnees();
        }

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
    }

    private void gereEcouteurAjout() {

        this.nomTextField.getDocument().addDocumentListener(monControleur);
        this.prenomTextField.getDocument().addDocumentListener(monControleur);
        this.emailTextField.getDocument().addDocumentListener(monControleur);
        this.etude1TextField.getDocument().addDocumentListener(monControleur);

        this.formation1ComboBox.addItemListener(monControleur);
        this.formation2ComboBox.addItemListener(monControleur);
        this.formation3ComboBox.addItemListener(monControleur);
        this.formation4ComboBox.addItemListener(monControleur);
    }

    private JToolBar creerBarreOutils() {

        JToolBar jtb = new JToolBar();
        jtb.setLayout(new BorderLayout());
        jtb.setBackground(new Color(240, 240, 240));

        jtb.add(this.construitPanelOutilsGauche());

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

    private JPanel construitPanelOutilsGauche() {

        ClassLoader cl = this.getClass().getClassLoader();

        Color bgColor = new Color(240, 240, 240);

        this.bNouveau = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        this.configurerButtons(bNouveau, "Nouveau");
        this.bNouveau.setToolTipText("Ajouter");

        this.bExporter = new JButton(new ImageIcon(cl.getResource("view/images/export.png")));
        this.configurerButtons(bExporter, "Exporter");

        JPanel p = new JPanel(new GridBagLayout());

        p.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;

        p.add(this.bNouveau, gbc);

        gbc.gridx = 3;
        p.add(this.bExporter, gbc);

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
        chooser.setDialogTitle("Dossier d'XML");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setApproveButtonText("Sélectionner");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    /**
     * Récupère toutes les informations en une seule fois
     *
     * @return table des informations
     */
    @Override
    public Object[] getInfosAjout() {
        Object[] liste = new Object[10];

        liste[0] = this.nomTextField.getText();
        liste[1] = this.prenomTextField.getText();
        liste[2] = this.emailTextField.getText();
        liste[3] = this.etude1TextField.getText();
        liste[4] = this.etude2TextField.getText();

        liste[5] = this.formation1ComboBox.getSelectedItem();
        liste[6] = this.formation1ComboBox.getSelectedIndex();
        liste[7] = this.formation2ComboBox.getSelectedItem();
        liste[8] = this.formation2ComboBox.getSelectedIndex();
        liste[9] = this.formation3ComboBox.getSelectedItem();
        liste[10] = this.formation3ComboBox.getSelectedIndex();
        liste[11] = this.formation4ComboBox.getSelectedItem();
        liste[12] = this.formation4ComboBox.getSelectedIndex();

        liste[14] = this.descriptionTextArea.getText();
        return liste;

    }

    @Override
    public void setListeFormation(ArrayList<Formation> liste) {

        DefaultComboBoxModel modelFormation1 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation1ComboBox.setModel(modelFormation1);
        this.formation1ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation1ComboBox.setSelectedIndex(0);

        DefaultComboBoxModel modelFormation2 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation2ComboBox.setModel(modelFormation2);
        this.formation2ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation2ComboBox.setSelectedIndex(0);

        DefaultComboBoxModel modelFormation3 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation3ComboBox.setModel(modelFormation3);
        this.formation3ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation3ComboBox.setSelectedIndex(0);

        DefaultComboBoxModel modelFormation4 = new DefaultComboBoxModel<Formation>(liste.toArray(new Formation[liste.size()]));
        this.formation4ComboBox.setModel(modelFormation4);
        this.formation4ComboBox.insertItemAt("Choisir une formation", 0);
        this.formation4ComboBox.setSelectedIndex(0);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        formation2ComboBox = new JComboBox<Formation>();
        formation3ComboBox = new JComboBox<Formation>();
        formation4ComboBox = new JComboBox<Formation>();

        this.monControleur.remplitComboFormation();

        descriptionTextArea = new JTextArea(5, 1);
        JScrollPane scrollingNote = new JScrollPane(descriptionTextArea);

        nomTextField = new JTextField();
        prenomTextField = new JTextField();
        emailTextField = new JTextField();
        etude1TextField = new JTextField();
        etude2TextField = new JTextField();

        retourButton = new JButton("Retour");
        okButton = new JButton("Valider");

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

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridheight = gbc.gridwidth = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 115, 10, 10);
        frameAjout.add(retourButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 10, 10, 10);
        frameAjout.add(okButton, gbc);

        frameAjout.setSize(600, 500);

        frameAjout.setLocationRelativeTo(null);
        frameAjout.setVisible(true);
    }

    @Override
    public void autoriseAjout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int confirmation(String message, String titre, int typeMessage, int icone) {
        return JOptionPane.showConfirmDialog(this, message, titre, typeMessage, icone, null);
    }

    @Override
    public void afficheMessage(String message, String titre, int typeMessage) {
        JOptionPane.showMessageDialog(this, message, titre, typeMessage);
    }
}
