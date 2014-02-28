package view.front;

import controller.front.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
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
    private JTextArea note;
    private JTextField prenomTextField;
    private JTextField emailTextField;
    private JTextField etude1TextField;
    private JTextField etude2TextField;
    JButton retourButton = new JButton("Retour");
    JButton okButton = new JButton("Ok");

    public VuePrincipaleFront() {

        super("Gestion des contacts");

        this.monControleur = new ControleurPrincipal(this);

        this.add(this.creerBarreOutils(), BorderLayout.NORTH);
        this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();
        this.gereButtonsActifs();

        this.setSize(700, 350);
        this.setLocation(350, 250);

        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void gereEcouteur() {
        
        this.bNouveau.addActionListener(monControleur);
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

    protected JPanel creerPanelTable(JTableDonnees table) {

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
    public String getSearch() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gereButtonsActifs() {
    }

    @Override
    public void construitAjoutModif() {

        JDialog frameAjoutModif = new JDialog(this, "Enregistrement");
        frameAjoutModif.setModal(true);

        /* 1- Initialisation du container. */
        frameAjoutModif.setLayout(new GridBagLayout());

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
        JLabel descriptionTextField = new JLabel("Description :");


        JComboBox formation1ComboBox = new JComboBox(new String[]{"Choix 1"});
        JComboBox formation2ComboBox = new JComboBox(new String[]{"Choix 2"});
        JComboBox formation3ComboBox = new JComboBox(new String[]{"Choix 3"});
        JComboBox formation4ComboBox = new JComboBox(new String[]{"Choix 4"});

        note = new JTextArea(5, 1);
        JScrollPane scrollingNote = new JScrollPane(note);

        nomTextField = new JTextField();
        prenomTextField = new JTextField();
        emailTextField = new JTextField();
        etude1TextField = new JTextField();
        etude2TextField = new JTextField();

        retourButton = new JButton("Retour");
        okButton = new JButton("Ok");

        GridBagConstraints gbc = new GridBagConstraints();
        Font font = new Font("Arial", Font.BOLD, 18);
        titreLabel.setFont(font);
        gbc.gridx = gbc.gridy = 0;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 20, 0);
        frameAjoutModif.add(titreLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(nomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(prenomTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(etude1Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(etude1TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(etude2Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(etude2TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(formation1Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(formation1ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(formation2Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(formation2ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(formation3Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(formation3ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(formation4Label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 10);
        frameAjoutModif.add(formation4ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 0, 0);
        frameAjoutModif.add(descriptionTextField, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 0, 10);
        gbc.weightx = 1.;
        gbc.weighty = 1.;
        frameAjoutModif.add(scrollingNote, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridheight = gbc.gridwidth = 0;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 10, 10);
        frameAjoutModif.add(retourButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 10, 10, 10);
        frameAjoutModif.add(okButton, gbc);


        frameAjoutModif.setMinimumSize(new Dimension(350, 500));
        frameAjoutModif.setLocationRelativeTo(null);
        
        frameAjoutModif.setVisible(true);
    }

    @Override
    public void afficheAjoutModif() {
    }

    @Override
    public int getLigneSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int[] getLesLignesSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepareModif() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void autoriseAjoutModif() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int confirmation(String message, String titre, int typeMessage, int icone) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void afficheErreur(String message, String titre, int typeMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
