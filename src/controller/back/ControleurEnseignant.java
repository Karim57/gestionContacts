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
import model.business.Enseignant;
import model.dao.sql.SQLContactDAO;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLEnseignantDAO;
import model.tables.ModeleEnseignant;
import view.back.IOEnseignant;

public class ControleurEnseignant implements ActionListener, DocumentListener, ListSelectionListener, ItemListener {

    private IOEnseignant vue;
    private ModeleEnseignant donneesEnseignant;

    private String[] searchNomPrenom = {""};
    private String searchDpt = "";

    public ControleurEnseignant(IOEnseignant v) {
        this.vue = v;
    }

    public ModeleEnseignant getDonneesEnseignant() {
        String[] champsForm = {"Nom", "Prenom", "Département"};
        this.donneesEnseignant = new ModeleEnseignant(champsForm);
        this.donneesEnseignant.setDonnees(SQLEnseignantDAO.getInstance().readAll());

        return donneesEnseignant;
    }

    public void remplitComboDpt() {
        this.vue.setListeDepartement(SQLDepartementDAO.getInstance().readAll());
    }

    private boolean supprimerDesEnseignants(int tab[]) {

        for (int i : tab) {
            Enseignant e = this.donneesEnseignant.getValueAt(i);
            if (SQLContactDAO.getInstance().nbContactsParEns(e) == 0) {
                this.donneesEnseignant.supprimerElement(e);
            } else {
                this.vue.afficheMessage("Impossible de supprimer un enseignant lié à un contact, opération annulée.",
                        "Erreur lors de la suppression", 0);
                this.donneesEnseignant.videElementsASupprimer();
                return false;
            }
        }

        int response = -1;

        if (tab.length > 1) {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer " + tab.length + " enseigants ?",
                    "Confirmation de suppression groupée", 2, 3);
        } else {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer cet enseignant ?",
                    this.donneesEnseignant.getElementsASupprimer().get(0).getNomEnseignant() + " "
                    + this.donneesEnseignant.getElementsASupprimer().get(0).getPrenomEnseignant(), 2, 3);
        }

        if (response == 0) {
            this.donneesEnseignant.confirmerSuppression();
            SQLEnseignantDAO.getInstance().deleteList(this.donneesEnseignant.getElementsASupprimer());
        }

        this.donneesEnseignant.videElementsASupprimer();
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

            Enseignant enseignant = this.donneesEnseignant.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.setListeDepartementAM(SQLDepartementDAO.getInstance().readAll());
            this.vue.remplitChamps(enseignant);

            // On l'affiche en modal à la fin,
            // Sinon le remplissage va pas marcher (vue bloquée)
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Supprimer")) {
            this.supprimerDesEnseignants(this.vue.getLesLignesSelectionnee());
        }

        if (s.equals("submitAjout")) {
            String nom = this.vue.getNom();
            String prenom = this.vue.getPrenom();
            Departement d = this.vue.getDptAjoutModif();

            Enseignant enseignant = new Enseignant(nom, prenom, d);
            this.donneesEnseignant.ajouterElement(enseignant);
            SQLEnseignantDAO.getInstance().create(enseignant);
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String nom = this.vue.getNom();
            String prenom = this.vue.getPrenom();
            Departement d = this.vue.getDptAjoutModif();

            Enseignant enseignant = this.donneesEnseignant.getValueAt(this.vue.getLigneSelectionnee());
            enseignant.setNomEnseignant(nom);
            enseignant.setPrenomEnseignant(prenom);
            enseignant.setDepartement(d);
            this.donneesEnseignant.setRow(this.vue.getLigneSelectionnee(), enseignant);
            SQLEnseignantDAO.getInstance().update(enseignant);
            this.vue.close();
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
            searchNomPrenom = this.vue.getSearch().split(" ");
            this.vue.filtrer(this.searchNomPrenom, this.searchDpt);
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
        this.vue.filtrer(this.searchNomPrenom, this.searchDpt);
    }

}
