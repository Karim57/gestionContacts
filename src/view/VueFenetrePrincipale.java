package view;

import controller.ControlleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Element;
import model.business.Manifestation;
import view.IObservable;

public class VueFenetrePrincipale extends JFrame implements IObservable {

    private ControlleurPrincipal monControleur;

    private JButton bNouveau;
    private JButton bModifier;
    private JButton bSupprimer;

    private JTabbedPane tpPrincipal;

    private JDialog frameAjoutModif;

    private JTextField tLibelle;

    private JButton bSubmit;
    private JButton bCancel;

    private JTableDonnees tableManifestations;
    private JTableDonnees tableContacts;
    private JTableDonnees tableDepartements;

    public VueFenetrePrincipale() {

        super("Gestion des événements");

        JWindow w = new JWindow();
        JLabel l = new JLabel("Chargement ... Veuillez patienter");
        l.setFont(new Font(null, WIDTH, 26));
        l.setBorder(new EmptyBorder(4, 25, 4, 20));
        ClassLoader cl = this.getClass().getClassLoader();
        JLabel l2 = new JLabel(new ImageIcon(cl.getResource("view/images/logo2.png")));
        l2.setBorder(new EmptyBorder(20, 20, 4, 20));
        JPanel p = new JPanel();
        p.setBackground(Color.yellow);
        p.setBorder(new LineBorder(Color.black));
        p.setLayout(new BorderLayout());
        p.add(l2, BorderLayout.NORTH);
        p.add(l, BorderLayout.SOUTH);
        w.add(p);
        w.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(dim.width / 2 - w.getSize().width / 2, dim.height / 3 - w.getSize().height / 2);
        w.setVisible(true);
        this.monControleur = new ControlleurPrincipal(this);

        this.tLibelle = new JTextField();
        this.bSubmit = new JButton();

        this.add(creerBarreOutils(), BorderLayout.NORTH);
        this.add(creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.setSize(700, 500);
        this.setLocation(300, 100);
        this.setVisible(true);
        w.dispose();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
        this.bSupprimer.addActionListener(monControleur);
        this.bModifier.addActionListener(monControleur);

        this.tpPrincipal.addChangeListener(monControleur);

    }

    private void gereEcouteurAM() {

        this.tLibelle.getDocument().addDocumentListener(monControleur);

        this.bSubmit.addActionListener(monControleur);
        this.bCancel.addActionListener(monControleur);
    }

    private JToolBar creerBarreOutils() {

        JToolBar jtb = new JToolBar();
        jtb.setLayout(new GridBagLayout());
        jtb.setBorder(new EmptyBorder(0, 5, 0, 0));
        jtb.setBackground(new Color(240, 240, 240));
        ClassLoader cl = this.getClass().getClassLoader();

        this.bNouveau = new JButton(new ImageIcon(cl.getResource("view/images/add2.png")));
        this.bNouveau.setActionCommand("Nouveau");
        this.bNouveau.setBorderPainted(false);
        this.bNouveau.setFocusPainted(false);

        this.bModifier = new JButton(new ImageIcon(cl.getResource("view/images/edit.png")));
        this.bModifier.setActionCommand("Modifier");
        this.bModifier.setBorderPainted(false);
        this.bModifier.setFocusPainted(false);

        bSupprimer = new JButton(new ImageIcon(cl.getResource("view/images/trash.png")));
        bSupprimer.setBorderPainted(false);
        bSupprimer.setFocusPainted(false);
        bSupprimer.setActionCommand("Supprimer");

        JButton bsearch = new JButton(new ImageIcon(cl.getResource("view/images/search.png")));
        bsearch.setBorderPainted(false);
        bsearch.setFocusPainted(false);

        JButton xmlEvent = new JButton(new ImageIcon(cl.getResource("view/images/xml.png")));
        xmlEvent.setBorderPainted(false);
        xmlEvent.setFocusPainted(false);

        JSeparator js = new JSeparator(JSeparator.VERTICAL);
        js.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JSeparator js2 = new JSeparator(JSeparator.VERTICAL);
        js2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;

        jtb.add(this.bNouveau, gbc);

        gbc.gridx = 1;
        jtb.add(js, gbc);

        gbc.gridx = 2;
        jtb.add(bModifier, gbc);

        gbc.gridx = 3;
        jtb.add(bSupprimer, gbc);

        gbc.gridx = 4;
        jtb.add(js2, gbc);

        gbc.gridx = 5;
        jtb.add(xmlEvent, gbc);

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 200, 0, 5);
        gbc.gridx = 6;
        JTextField search = new JTextField(100);
        jtb.add(search, gbc);

        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 7;
        jtb.add(bsearch, gbc);

        jtb.setFloatable(false);

        jtb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return jtb;

    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        this.tpPrincipal = new JTabbedPane();

        this.tableManifestations = new JTableDonnees(null, this.monControleur.getDonneesManifestation());
        tpPrincipal.addTab("Manifestations", this.creerPanelTable(this.tableManifestations));

        /* TableRowSorter sorter = new TableRowSorter(this.monControleur.getDonneesManifestation());
         this.tableManifestations.setRowSorter(sorter);
         sorter.setRowFilter(RowFilter.regexFilter("3", 1));*/
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
        table.setIntercellSpacing(new Dimension(20, 1));
        jp.add(table, gbc2);

        JScrollPane pane = new JScrollPane(table);

        jp.add(pane, gbc2);

        return jp;

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
    public void activeButton() {

    }

    @Override
    public void construitAjoutModif() {

        frameAjoutModif = new JDialog();
        frameAjoutModif.setModal(true);
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
        frameAjoutModif.setVisible(true);

        frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    @Override
    public void preapreModif() {

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

    private JPanel construitPanelButtons() {

        JPanel panelButton = new JPanel();

        panelButton.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 2, 10);
        this.bCancel = new JButton("Annuler");
        panelButton.add(this.bCancel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 2, 10);
        this.bSubmit = new JButton("Ajouter");
        this.bSubmit.setActionCommand("submitAjout");
        this.bSubmit.setEnabled(false);
        panelButton.add(this.bSubmit, gbc);

        return panelButton;
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

}
