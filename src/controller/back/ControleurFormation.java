package controller.back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.business.Departement;
import model.business.Formation;
import model.dao.sql.SQLContactDAO;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLFormationDAO;
import model.tables.ModeleFormation;
import view.back.IOFormation;

public class ControleurFormation implements ActionListener, DocumentListener, ListSelectionListener, ItemListener {

    private IOFormation vue;
    private ModeleFormation donneesFormation;

    private String[] search = {""};
    private String searchDpt = "";

    public ControleurFormation(IOFormation v) {
        this.vue = v;
    }

    public ModeleFormation getDonneesFormation() {
        String[] champsForm = {"Libellé", "Département"};
        this.donneesFormation = new ModeleFormation(champsForm);
        this.donneesFormation.setDonnees(SQLFormationDAO.getInstance().readAll());

        return donneesFormation;
    }

    public void remplitComboDpt() {
        this.vue.setListeDepartement(SQLDepartementDAO.getInstance().readAll());
    }

    private boolean supprimerDesFormations(int tab[]) {

        for (int i : tab) {
            Formation f = this.donneesFormation.getValueAt(i);
            if (SQLContactDAO.getInstance().nbContactsParFormation(f) == 0) {
                this.donneesFormation.supprimerElement(f);
            } else {
                this.vue.afficheMessage("Impossible de supprimer une formation lié à un contact, opération annulée.",
                        "Erreur lors de la suppression", 0);
                this.donneesFormation.videElementsASupprimer();
                return false;
            }
        }

        int response = -1;

        if (tab.length > 1) {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer " + tab.length + " formation ?",
                    "Confirmation de suppression groupée", 2, 3);
        } else {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer cette formation ?",
                    this.donneesFormation.getElementsASupprimer().get(0).getLibelleFormation(), 2, 3);
        }

        if (response == 0) {
            this.donneesFormation.confirmerSuppression();
            SQLFormationDAO.getInstance().deleteList(this.donneesFormation.getElementsASupprimer());
        }

        this.donneesFormation.videElementsASupprimer();
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
            this.vue.setListeDepartementAM(SQLDepartementDAO.getInstance().readAll());
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Modifier")) {

            this.vue.construitAjoutModif(); // On construit d'abord le frame
            this.vue.prepareModif(); // sinon on peut pas remplir

            Formation formation = this.donneesFormation.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.setListeDepartementAM(SQLDepartementDAO.getInstance().readAll());
            this.vue.remplitChamps(formation);

            // On l'affiche en modal à la fin,
            // Sinon le remplissage va pas marcher (vue bloquée)
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Supprimer")) {
            this.supprimerDesFormations(this.vue.getLesLignesSelectionnee());
        }

        if (s.equals("submitAjout")) {
            String libelle = this.vue.getLibelle();
            Departement d = this.vue.getDptAjoutModif();

            Formation formation = new Formation(libelle, d);
            this.donneesFormation.ajouterElement(formation);
            SQLFormationDAO.getInstance().create(formation);
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String libelle = this.vue.getLibelle();
            Departement d = this.vue.getDptAjoutModif();

            Formation formation = this.donneesFormation.getValueAt(this.vue.getLigneSelectionnee());
            formation.setLibelleFormation(libelle);
            formation.setDepartement(d);
            this.donneesFormation.setRow(this.vue.getLigneSelectionnee(), formation);
            SQLFormationDAO.getInstance().update(formation);
            this.vue.close();
        }

        if (s.equals("Stats")) {

        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

        if (e.getDocument().getProperty("id").equals("tAjoutModifNom")
                || e.getDocument().getProperty("id").equals("tAjoutModifPrenom")) {
            this.vue.autoriseAjoutModif();
        } else if (e.getDocument().getProperty("id") == "tSearch") {
            search = this.vue.getSearch().split(" ");
            this.vue.filtrer(this.search, this.searchDpt);
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.vue.gereButtonsActifs();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (this.vue.getSelectedDepartementIndex() == 0) {
            this.searchDpt = "";
        } else {
            this.searchDpt = this.vue.getDptFiltre().toString();
        }
        this.vue.filtrer(this.search, this.searchDpt);
    }

}
