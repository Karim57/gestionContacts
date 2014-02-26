package controller.back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.business.Contact;
import model.business.Departement;
import model.business.Manifestation;
import model.dao.sql.SQLContactDAO;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleContact;
import model.tables.ModeleDepartement;
import model.tables.ModeleManifestation;
import view.back.IOPrincipale;
import view.back.VueEnseignants;

public class ControleurPrincipal implements ActionListener, DocumentListener, ChangeListener, ListSelectionListener, WindowListener {

    private IOPrincipale vue;
    private ModeleManifestation donneesManifestation;
    private ModeleDepartement donneesDepartement;
    private ModeleContact donneesContact;

    private VueEnseignants vueEnseignants;

    private static boolean enseignantOuverte = false;

    private static int activePane = 0;

    private String[] search = {""};

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Supprimer")) {
            if (activePane == 0) {

                if (this.vue.getLesLignesSelectionnee().length > 1) {
                    this.supprimerDesManifestaion(this.vue.getLesLignesSelectionnee());
                } else {
                    this.supprimerManifestaion(this.vue.getLesLignesSelectionnee()[0]);
                }

            }
        }

        if (s.equals("Modifier")) {
            this.vue.construitAjoutModif(); // On construit d'abord le frame
            this.vue.prepareModif(); // sinon on peut pas remplir

            if (activePane == 0) {
                Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
                this.vue.remplitChamps(manifestaion.getLibelleManif());
            } else if (activePane == 1) {
                Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
                this.vue.remplitChamps(departement.getLibelleDepartement());
            }
            // On l'affiche en modal à la fin,
            // Sinon le remplissage va pas marcher (vue bloquée)
            this.vue.afficheAjoutModif();
        }

        if (s.equals("ManifDefaut")) {
            if (!enseignantOuverte) {
                vueEnseignants = new VueEnseignants(new Departement(5, "Informatique"));
                enseignantOuverte = true;
                vueEnseignants.addWindowListener(this);
            } else {
                vueEnseignants.toFront();
            }
        }

        if (s.equals("submitAjout")) {
            String libelle = this.vue.getLibelle();
            if (activePane == 0) {
                Manifestation manifestaion = new Manifestation(libelle);
                this.donneesManifestation.ajouterElement(manifestaion);
                SQLManifestationDAO.getInstance().create(manifestaion);

            } else if (activePane == 1) {
                Departement departement = new Departement(libelle);
                this.donneesDepartement.ajouterElement(departement);
                SQLDepartementDAO.getInstance().create(departement);
            }
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String libelle = this.vue.getLibelle();
            if (activePane == 0) {
                Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
                manifestaion.setLibelleManif(libelle);
                this.donneesManifestation.setRow(this.vue.getLigneSelectionnee(), manifestaion);
                SQLManifestationDAO.getInstance().update(manifestaion);

            } else if (activePane == 1) {
                Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
                departement.setLibelleDepartement(libelle);
                this.donneesDepartement.setRow(this.vue.getLigneSelectionnee(), departement);
                SQLDepartementDAO.getInstance().update(departement);
            }
            this.vue.close();
        }

        if (s.equals("Annuler")) {
            this.vue.close();
        }

        if (s.equals("Profil")) {
            Contact contact = this.donneesContact.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.construitProfil(contact);
        }
    }

    public ModeleManifestation getDonneesManifestation() {
        String[] champsManif = {"Liste des manifestations"};
        this.donneesManifestation = new ModeleManifestation(champsManif);
        this.donneesManifestation.setDonnees(SQLManifestationDAO.getInstance().readAll());
        return donneesManifestation;
    }

    public ModeleDepartement getDonneesDepartement() {
        String[] champsDpt = {"Liste des départements"};
        this.donneesDepartement = new ModeleDepartement(champsDpt);
        this.donneesDepartement.setDonnees(SQLDepartementDAO.getInstance().readAll());
        return donneesDepartement;
    }

    public ModeleContact getDonneesContact() {
        String[] champsForm = {"Nom", "Prenom", "Description", "Lieu de la rencontre"};
        this.donneesContact = new ModeleContact(champsForm);
        this.donneesContact.setDonnees(SQLContactDAO.getInstance().readAll());
        return donneesContact;
    }

    private boolean supprimerDesManifestaion(int tab[]) {

        for (int i : tab) {
            Manifestation m = this.donneesManifestation.getValueAt(i);
            if (this.donneesContact.canDeleteManif(m)) {
                this.donneesManifestation.supprimerElement(m);
            } else {
                this.vue.afficheErreur("Impossible de supprimer une manifestation liée à une autre donnée, opération annulée.",
                        "Erreur lors de la suppression", 0);
                this.donneesManifestation.videElementsASupprimer();
                return false;
            }
        }

        if (this.vue.confirmation("Etes-vous sûr de vouloir supprimer " + tab.length + " manifestations ?",
                "Confirmation de suppression groupée", 2, 3) == 0) {

            this.donneesManifestation.confirmerSuppression();

            for (Manifestation m : this.donneesManifestation.getElementsASupprimer()) {
                SQLManifestationDAO.getInstance().delete(m);
            }

        }

        this.donneesManifestation.videElementsASupprimer();
        return true;
    }

    private void supprimerManifestaion(int i) {
        Manifestation m = this.donneesManifestation.getValueAt(i);
        if (this.donneesContact.canDeleteManif(m)) {
            if (this.vue.confirmation("Etes-vous sûr de vouloir supprimer cette manifestation",
                    m.getLibelleManif(), 2, 3) == 0) {
                this.donneesManifestation.supprimerElement(m);
                this.donneesManifestation.confirmerSuppression();
                this.donneesContact.videElementsASupprimer();
            }
        } else {
            this.vue.afficheErreur("Impossible de supprimer une manifestation liée à une autre donnée, opération annulée.",
                    "Erreur lors de la suppression", 0);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument().getProperty("id") == "tAjoutModif") {
            this.vue.autoriseAjoutModif();
        } else if (e.getDocument().getProperty("id") == "tSearch") {
            this.search = this.vue.getSearch().split(" ");
            this.vue.filtrer(this.search);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.vue.gereButtonsActifs();
        activePane = this.vue.getActivePane();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        this.vue.gereButtonsActifs();
    }

    @Override
    public void windowOpened(WindowEvent we) {
        enseignantOuverte = true;
    }

    @Override
    public void windowClosing(WindowEvent we) {
        enseignantOuverte = false;
    }

    @Override
    public void windowClosed(WindowEvent we) {
        enseignantOuverte = false;
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
        enseignantOuverte = true;
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

}
