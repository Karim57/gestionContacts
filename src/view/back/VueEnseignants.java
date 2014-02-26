package view.back;

import view.IObservable;
import view.back.IOEnseignant;
import controller.back.ControleurEnseignant;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableRowSorter;
import model.business.Contact;
import model.business.Departement;
import view.JTableDonnees;
import view.VueAbstraite;


public class VueEnseignants extends VueAbstraite implements IObservable, IOEnseignant {

    private ControleurEnseignant monControleur;

    private JDialog frameAjoutModif;

    private JTextField tNom;

    private JTableDonnees tableEnseignants;

    public VueEnseignants(Departement departement) {
        this();
        this.cListeDpt.setSelectedItem(departement);
    }

    public VueEnseignants() {

        super("Gestion des enseignants");

        this.monControleur = new ControleurEnseignant(this);

        this.add(creerBarreOutils(), BorderLayout.NORTH);
        this.add(creerPanelPrincipal(), BorderLayout.CENTER);

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
        frameAjoutModif = new JDialog();
        frameAjoutModif.setModal(true);

        frameAjoutModif.setTitle("Ajouter un enseignant");

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
    public int getLigneSelectionnee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remplitChamps(String libelle) {
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
        this.cListeDpt.setModel(new DefaultComboBoxModel<Departement>(liste.toArray(new Departement[liste.size()])));
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
        this.bNouveau.addActionListener(monControleur);
        this.bSupprimer.addActionListener(monControleur);
        this.bModifier.addActionListener(monControleur);

        this.cListeDpt.addItemListener(monControleur);

        this.tSearch.getDocument().addDocumentListener(monControleur);
    }

    private void gereEcouteurAM() {

        this.tNom.getDocument().addDocumentListener(monControleur);

        this.bSubmit.addActionListener(monControleur);
        this.bCancel.addActionListener(monControleur);
    }

    private JPanel construitPanelSaisieAjout() {
        return null;
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
        filtrePlusieursMotsEtDpt.add(RowFilter.regexFilter("^" + dpt + "$", 2));

        sorter.setRowFilter(RowFilter.andFilter(filtrePlusieursMotsEtDpt));

        this.tableEnseignants.setRowSorter(sorter);

    }

    @Override
    public String getSearch() {
        return this.tSearch.getText().trim();
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

}
