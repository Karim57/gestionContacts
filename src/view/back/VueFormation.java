package view.back;

import controller.back.ControleurEnseignant;
import controller.back.ControleurFormation;
import java.awt.BorderLayout;
import java.awt.Dialog;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableRowSorter;
import model.business.Departement;
import model.business.Formation;
import view.JTableDonnees;
import view.VueAbstract;

public class VueFormation extends VueAbstract implements IOFormation {

    private ControleurFormation monControleur;

    private JDialog frameAjoutModif;

    private JTextField tLibelle;
    private JComboBox<Departement> cDepartements;

    private JTableDonnees tableFormation;

    public VueFormation() {

        super("Gestion des formations");

        this.monControleur = new ControleurFormation(this);

        this.add(super.creerBarreOutils(), BorderLayout.NORTH);
        this.add(this.creerPanelPrincipal(), BorderLayout.CENTER);

        this.monControleur.remplitComboDpt();
        super.cListeDpt.setSelectedIndex(0);

        this.gereEcouteur();
        this.gereButtonsActifs();

        this.setSize(850, 550);
        this.setLocation(350, 250);

        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private JPanel construitPanelSaisieAjout() {

        JPanel panelAjout = new JPanel();

        JLabel lLibelle = new JLabel("Libellé de la formation");
        JLabel lDepartement = new JLabel("Département");

        this.tLibelle = new JTextField(255);

        this.tLibelle.getDocument().putProperty("id", "tAjoutModifNom");

        this.cDepartements = new JComboBox<Departement>();

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

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 2, 10);

        panelAjout.add(lDepartement, gbc);

        gbc.ipady = 4;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelAjout.add(this.cDepartements, gbc);

        return panelAjout;

    }

    private JPanel creerPanelPrincipal() {

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        this.tableFormation = new JTableDonnees(null, this.monControleur.getDonneesFormation());
        this.tableFormation.filtrer(null);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        p.add(this.creerPanelTable(this.tableFormation), gbc);

        return p;
    }

    private void gereEcouteur() {
        super.bNouveau.addActionListener(monControleur);
        super.bSupprimer.addActionListener(monControleur);
        super.bModifier.addActionListener(monControleur);

        super.bStats.addActionListener(monControleur);

        super.cListeDpt.addItemListener(monControleur);

        super.tSearch.getDocument().addDocumentListener(monControleur);

        this.tableFormation.getSelectionModel().addListSelectionListener(monControleur);

    }

    private void gereEcouteurAM() {

        this.tLibelle.getDocument().addDocumentListener(monControleur);

        super.bSubmit.addActionListener(monControleur);
        super.bCancel.addActionListener(monControleur);
    }

    @Override
    public void filtrer(String[] search, String dpt) {

        TableRowSorter sorter = new TableRowSorter(this.tableFormation.getModel());

        List<RowFilter<Object, Object>> filtrePlusieursMots = new ArrayList<RowFilter<Object, Object>>();
        List<RowFilter<Object, Object>> filtrePlusieursMotsEtDpt = new ArrayList<RowFilter<Object, Object>>();

        for (String s : search) {
            s = s.trim();
            filtrePlusieursMots.add(RowFilter.regexFilter("(?i)" + s, 0));
        }

        filtrePlusieursMotsEtDpt.add(RowFilter.andFilter(filtrePlusieursMots));

        if (dpt != "") {
            filtrePlusieursMotsEtDpt.add(RowFilter.regexFilter("^" + dpt + "$", 1));
        }

        sorter.setRowFilter(RowFilter.andFilter(filtrePlusieursMotsEtDpt));

        this.tableFormation.setRowSorter(sorter);

    }

    public void setDpt(Departement d) {
        if (d == null) {
            super.cListeDpt.setSelectedIndex(0);
        } else {
            super.cListeDpt.setSelectedItem(d);
        }
    }

    @Override
    public String getSearch() {
        return super.tSearch.getText().trim();
    }

    @Override
    public void close() {
        this.frameAjoutModif.dispose();
    }

    @Override
    public void gereButtonsActifs() {
        super.bNouveau.setEnabled(true);
        super.bModifier.setEnabled(true);
        super.bSupprimer.setEnabled(true);

        super.bStats.setVisible(false);
        super.bExporter.setVisible(false);
        super.bImporter.setVisible(false);
        super.bOuvreDpt.setVisible(false);
        super.bOuvreEns.setVisible(false);
        super.bProfil.setVisible(false);

        super.js2.setVisible(true);
        super.js4.setVisible(false);

        if (this.tableFormation.getSelectedRowCount() == 1) {
            super.bModifier.setEnabled(true);
        } else {
            super.bModifier.setEnabled(false);
        }

        if (this.tableFormation.getSelectedRowCount() > 0) {
            super.bSupprimer.setEnabled(true);
        } else {
            super.bSupprimer.setEnabled(false);
        }
    }

    @Override
    public void construitAjoutModif() {
        this.frameAjoutModif = new JDialog();
        this.frameAjoutModif.setModal(true);

        this.frameAjoutModif.setTitle("Ajouter une formation");

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

        this.frameAjoutModif.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void afficheAjoutModif() {
        this.frameAjoutModif.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frameAjoutModif.setVisible(true);
    }

    @Override
    public int getLigneSelectionnee() {
        return this.tableFormation.convertRowIndexToModel(this.tableFormation.getSelectedRow());
    }

    @Override
    public int[] getLesLignesSelectionnee() {
        return this.tableFormation.getLignesSelectionnees();
    }

    @Override
    public void prepareModif() {
        this.frameAjoutModif.setTitle("Modifier une formation");

        super.bSubmit.setText("Modifer");
        super.bSubmit.setActionCommand("submitModif");
    }

    @Override
    public void autoriseAjoutModif() {
        super.bSubmit.setEnabled(this.tLibelle.getText().trim().length() > 0);
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
    public void setListeDepartement(ArrayList<Departement> liste) {
        DefaultComboBoxModel modelDpt = new DefaultComboBoxModel<Departement>(liste.toArray(new Departement[liste.size()]));
        super.cListeDpt.setModel(modelDpt);
        super.cListeDpt.insertItemAt("Tous les départements", 0);
    }

    @Override
    public void remplitChamps(Formation f) {
        this.tLibelle.setText(f.getLibelleFormation());
        this.cDepartements.setSelectedItem(f.getDepartement());
    }

    @Override
    public void setListeDepartementAM(ArrayList<Departement> liste) {
        DefaultComboBoxModel modelDpt = new DefaultComboBoxModel<Departement>(liste.toArray(new Departement[liste.size()]));
        this.cDepartements.setModel(modelDpt);
    }

    @Override
    public String getLibelle() {
        return this.tLibelle.getText();
    }

    @Override
    public int getSelectedDepartementIndex() {
        return super.cListeDpt.getSelectedIndex();
    }

    @Override
    public Departement getDptFiltre() {
        return (Departement) this.cListeDpt.getSelectedItem();
    }

    @Override
    public Departement getDptAjoutModif() {
        return (Departement) this.cDepartements.getSelectedItem();
    }

}
