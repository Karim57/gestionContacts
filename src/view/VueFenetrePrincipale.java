package view;

import controller.ControlleurPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import model.tables.ModeleManifestation;
import view.manifestation.VueManifestation;
import view.ManifestationObservable;

public class VueFenetrePrincipale extends JFrame implements ManifestationObservable {

    private ControlleurPrincipal monControleur;
    private JButton bNouveau;
    private VueManifestation tableManif;

    public VueFenetrePrincipale() {

        super("Gestion des événements");

        this.tableManif = new VueManifestation();
        this.monControleur = new ControlleurPrincipal(this);

        this.setJMenuBar(this.creerBarreMenu());
        this.add(creerBarreOutils(), BorderLayout.NORTH);
        this.add(creerPanelPrincipal(), BorderLayout.CENTER);

        this.gereEcouteur();

        this.setSize(700, 500);
        this.setLocation(300, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void gereEcouteur() {
        this.bNouveau.addActionListener(monControleur);
    }

    private JMenuBar creerBarreMenu() {

        JMenuBar mb = new JMenuBar();
        mb.setBackground(new Color(245, 246, 247));
        JMenu file = new JMenu("Fichier");

        ClassLoader cl = this.getClass().getClassLoader();

        JMenuItem newEvent = new JMenuItem("Nouveau", KeyEvent.VK_N);
        newEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        ImageIcon iconAdd = new ImageIcon(cl.getResource("view/images/add.png"));

        newEvent.setIcon(iconAdd);

        JMenuItem editEvent = new JMenuItem("Editer");
        JMenuItem deleteEvent = new JMenuItem("Supprimer");
        JMenuItem generateXMLEvent = new JMenuItem("Générer XML");

        file.add(newEvent);
        file.add(editEvent);
        file.add(deleteEvent);
        file.addSeparator();
        file.add(generateXMLEvent);

        JMenu view = new JMenu("Fenêtre");
        JMenuItem manifestation = new JMenuItem("Manifestations");
        JMenuItem enseignant = new JMenuItem("Enseignants");
        JMenuItem formation = new JMenuItem("Formations");

        view.add(manifestation);
        view.add(enseignant);
        view.add(formation);

        JMenu help = new JMenu("?");

        JMenu settings = new JMenu("Paramètres");

        mb.add(file);
        mb.add(view);
        mb.add(settings);
        mb.add(help);

        return mb;

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

        JButton editEvent = new JButton(new ImageIcon(cl.getResource("view/images/edit.png")));
        editEvent.setBorderPainted(false);
        editEvent.setFocusPainted(false);

        JButton deleteEvent = new JButton(new ImageIcon(cl.getResource("view/images/trash.png")));
        deleteEvent.setBorderPainted(false);
        deleteEvent.setFocusPainted(false);

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
        jtb.add(editEvent, gbc);

        gbc.gridx = 3;
        jtb.add(deleteEvent, gbc);

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
        JTabbedPane tp = new JTabbedPane();

        JPanel jp2 = new JPanel();
        tp.addTab("Manifestations", tableManif.creerPanelTableManifestation());
        tp.addTab("Enseignants", jp2);
        tp.addTab("Etudiants", null);
        tp.addTab("Formations", null);
        tp.addTab("Départements", null);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        p.add(tp, gbc);
        return p;
    }

    public void creerTable(Object[][] donnees, String[] entetes) {

        JTable tableau = new JTable(donnees, entetes);

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());

        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        tableau.getTableHeader().setBackground(new Color(245, 246, 247));
        tableau.getTableHeader().setFont(new Font("Arial", 1, 12));
        jp.add(tableau.getTableHeader(), gbc2);

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 0;
        gbc2.gridy = 1;

        jp.add(tableau, gbc2);

        JScrollPane pane = new JScrollPane(tableau);

        jp.add(pane, gbc2);

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
    public void remplitTable(ModeleManifestation modele) {
    }

    @Override
    public void construitAjout() {
        tableManif.construitAjout();
    }
}
