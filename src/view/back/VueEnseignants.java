package view.back;

import view.IObservable;
import view.back.IOEnseignant;
import controller.back.ControleurEnseignant;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableRowSorter;
import model.business.Departement;
import model.business.Enseignant;
import view.JTableDonnees;
import view.VueAbstraite;

public class VueEnseignants extends VueAbstraite implements IObservable, IOEnseignant {

    private ControleurEnseignant monControleur;

    private JDialog frameAjoutModif;

    private JTextField tNom;
    private JTextField tPrenom;
    private JComboBox<Departement> cDepartements;

    private JTableDonnees tableEnseignants;

    public VueEnseignants(Departement departement) {
        this();
        this.setDpt(departement);
    }

    public VueEnseignants() {

        super("Gestion des enseignants");

        this.monControleur = new ControleurEnseignant(this);

        this.add(super.creerBarreOutils(), BorderLayout.NORTH);
        this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

        this.monControleur.remplitComboDpt();

        this.gereEcouteur();

        this.setSize(700, 500);
        this.setLocation(300, 100);

        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gereButtonsActifs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construitAjoutModif() {
        this.frameAjoutModif = new JDialog();
        this.frameAjoutModif.setModal(true);

        this.frameAjoutModif.setTitle("Ajouter un enseignant");

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel pSaisie = this.construitPanelSaisieAjout();
        JPanel pValidation = this.construitPanelButtons();

        p.add(pSaisie);
        p.add(pValidation);

        this.gereEcouteurAM();

        this.frameAjoutModif.add(p);

        this.frameAjoutModif.setSize(400, 170);
        this.frameAjoutModif.setResizable(false);
        this.frameAjoutModif.setLocation(450, 300);
        this.frameAjoutModif.setVisible(true);

        this.frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private JPanel construitPanelSaisieAjout() {

        JPanel panelAjout = new JPanel();

        JLabel lNom = new JLabel("Nom");
        JLabel lPrenom = new JLabel("Prenom");
        JLabel lDepartement = new JLabel("Département");

        this.tNom = new JTextField(255);
        this.tPrenom = new JTextField(255);

        this.tNom.getDocument().putProperty("id", "tAjoutModifNom");
        this.tPrenom.getDocument().putProperty("id", "tAjoutModifPrenom");

        this.cDepartements = new JComboBox<Departement>();

        panelAjout.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelAjout.add(lNom, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelAjout.add(this.tNom, gbc);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelAjout.add(lPrenom, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelAjout.add(this.tPrenom, gbc);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelAjout.add(lDepartement, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelAjout.add(this.cDepartements, gbc);

        return panelAjout;

    }

    @Override
    public int getLigneSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remplitChamps(Enseignant e) {
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
    public void setListeDepartement(ArrayList<Departement> liste) {
        DefaultComboBoxModel modelDpt = new DefaultComboBoxModel<Departement>(liste.toArray(new Departement[liste.size()]));
        super.cListeDpt.setModel(modelDpt);
     //************************************************   super.cListeDpt.insertItemAt("Tous les départements", 0);
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
        super.bNouveau.addActionListener(monControleur);
        super.bSupprimer.addActionListener(monControleur);
        super.bModifier.addActionListener(monControleur);

        super.cListeDpt.addItemListener(monControleur);

        super.tSearch.getDocument().addDocumentListener(monControleur);
    }

    private void gereEcouteurAM() {

        this.tNom.getDocument().addDocumentListener(monControleur);

        super.bSubmit.addActionListener(monControleur);
        super.bCancel.addActionListener(monControleur);
    }

    @Override
    public Departement getDptSelected() {
        return (Departement) this.cListeDpt.getSelectedItem();
    }

    @Override
    public void filtrer(String[] search, String dpt) {

        TableRowSorter sorter = new TableRowSorter(this.tableEnseignants.getModel());

        List<RowFilter<Object, Object>> filtrePlusieursMots = new ArrayList<RowFilter<Object, Object>>();
        List<RowFilter<Object, Object>> filtrePlusieursMotsEtDpt = new ArrayList<RowFilter<Object, Object>>();

        for (String s : search) {
            s = s.trim();
            List<RowFilter<Object, Object>> filtreUnMot = new ArrayList<RowFilter<Object, Object>>();
            filtreUnMot.add(RowFilter.regexFilter("(?i)" + s, 0));
            filtreUnMot.add(RowFilter.regexFilter("(?i)" + s, 1));
            filtrePlusieursMots.add(RowFilter.orFilter(filtreUnMot));
        }

        filtrePlusieursMotsEtDpt.add(RowFilter.andFilter(filtrePlusieursMots));

        if (dpt != "") {
            filtrePlusieursMotsEtDpt.add(RowFilter.regexFilter("^" + dpt + "$", 2));
        }

        sorter.setRowFilter(RowFilter.andFilter(filtrePlusieursMotsEtDpt));

        this.tableEnseignants.setRowSorter(sorter);

    }

    @Override
    public String getSearch() {
        return super.tSearch.getText().trim();
    }

    @Override
    public void afficheAjoutModif() {
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

    @Override
    public int[] getLesLignesSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSelectedDepartement() {
        return super.cListeDpt.getSelectedIndex();
    }

    public void setDpt(Departement d) {
        if (d == null) {
            super.cListeDpt.setSelectedIndex(0);
        } else {
            super.cListeDpt.setSelectedItem(d);
        }
    }

}
