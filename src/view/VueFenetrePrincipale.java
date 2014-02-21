package view;

import controller.ControleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
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

    public VueFenetrePrincipale() {

        super("Gestion des événements");

        JWindow w = new JWindow();
        JLabel l = new JLabel("Gestionnaire de rencontres étudiants");
        l.setFont(new Font(null, WIDTH, 26));
        l.setBorder(new EmptyBorder(4, 25, 4, 20));
        ClassLoader cl = this.getClass().getClassLoader();
        JLabel l2 = new JLabel(new ImageIcon(cl.getResource("view/images/logo2.png")));
        l2.setBorder(new EmptyBorder(20, 20, 4, 20));
        JLabel l3 = new JLabel("Chargement des données ...");
        l3.setFont(new Font(null, WIDTH, 12));
        l3.setBorder(new EmptyBorder(0, 20, 2, 20));
        JPanel p = new JPanel();
        p.setBackground(Color.yellow);
        p.setBorder(new LineBorder(Color.black));
        p.setLayout(new BorderLayout());
        p.add(l2, BorderLayout.NORTH);
        p.add(l, BorderLayout.CENTER);
        p.add(l3, BorderLayout.SOUTH);
        w.add(p);
        w.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation(dim.width / 2 - w.getSize().width / 2, dim.height / 3 - w.getSize().height / 2);
        w.setVisible(true);

        this.monControleur = new ControleurPrincipal(this);

        this.add(creerBarreOutils(), BorderLayout.NORTH);
        this.add(creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.setSize(700, 500);
        this.setLocation(300, 100);

        l3.setText("Ouverture du programme ...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(VueFenetrePrincipale.class.getName()).log(Level.SEVERE, null, ex);
        }

        w.dispose();

        this.setVisible(true);

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
