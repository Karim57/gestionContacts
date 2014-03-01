package controller.back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
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
import model.dao.sql.SQLEnseignantDAO;
import model.dao.sql.SQLFormationDAO;
import model.dao.sql.SQLManifestationDAO;
import model.dao.xml.XMLContactDAO;
import model.dao.xml.XMLDepartementDAO;
import model.dao.xml.XMLEnseignantDAO;
import model.dao.xml.XMLFormationDAO;
import model.dao.xml.XMLManifestationDAO;
import model.tables.ModeleContact;
import model.tables.ModeleDepartement;
import model.tables.ModeleManifestation;
import view.back.IOPrincipale;
import view.back.VueEnseignants;
import view.back.VueFormation;

public class ControleurPrincipal implements ActionListener, DocumentListener, ChangeListener, ListSelectionListener, WindowListener, MouseListener, PropertyChangeListener {

    private IOPrincipale vue;
    private ModeleManifestation donneesManifestation;
    private ModeleDepartement donneesDepartement;
    private ModeleContact donneesContact;

    private VueEnseignants vueEnseignants;
    private VueFormation vueFormations;

    private static boolean enseignantOuverte = false;
    private static boolean formationOuverte = false;

    private String[] search = {""};

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (this.vue.getActivePane()) {
            case 0:
                this.gereActionCommandManif(e);
                break;
            case 1:
                this.gereActionCommandDepartement(e);
                break;
            case 2:
                this.gereActionCommandContact(e);
                break;
        }

        String s = e.getActionCommand();

        // Bouttons généralistes
        if (s.equals("Annuler")) {
            this.vue.close();
        }

    }

    private void gereActionCommandManif(ActionEvent e) {

        String s = e.getActionCommand();

        // Gestion des bouttons à l'accueil
        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Modifier")) {
            this.vue.construitAjoutModif(); // On construit d'abord le frame
            this.vue.prepareModif(); // sinon on peut pas remplir

            Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.remplitChamps(manifestaion.getLibelleManif());

            // On l'affiche en modal à la fin,
            // Sinon le remplissage va pas marcher (vue bloquée)
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Supprimer")) {
            this.supprimerDesManifestaion(this.vue.getLesLignesSelectionnee());
        }

        if (s.equals("Exporter")) {
            String chemin = this.vue.fileChooser();
            Manifestation m = this.donneesManifestation.getDonnees().get(this.vue.getLigneSelectionnee());
            if (chemin != null) {
                new XMLManifestationDAO(chemin).create(m);
                new XMLDepartementDAO(chemin).creerListe(this.donneesDepartement.getDonnees());
                new XMLEnseignantDAO(chemin).creerListe(SQLEnseignantDAO.getInstance().readAll());
                new XMLFormationDAO(chemin).creerListe(SQLFormationDAO.getInstance().readAll());
                this.vue.afficheMessage("Les fichiers XML ont été générés", "Opération réussi", 1);
            }
        }

        if (s.equals("Stats")) {

        }

        // Gestion des buttons d'ajout - modification
        if (s.equals("submitAjout")) {
            String libelle = this.vue.getLibelle();
            Manifestation manifestaion = new Manifestation(libelle);
            this.donneesManifestation.ajouterElement(manifestaion);
            SQLManifestationDAO.getInstance().create(manifestaion);
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String libelle = this.vue.getLibelle();
            Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
            manifestaion.setLibelleManif(libelle);
            this.donneesManifestation.setRow(this.vue.getLigneSelectionnee(), manifestaion);
            SQLManifestationDAO.getInstance().update(manifestaion);
            this.vue.close();
        }

    }

    private void gereActionCommandDepartement(ActionEvent e) {

        String s = e.getActionCommand();

        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
            this.vue.afficheAjoutModif();
        }

        if (s.equals("Modifier")) {
            Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.construitAjoutModif();
            this.vue.prepareModif();
            this.vue.remplitChamps(departement.getLibelleDepartement());

            this.vue.afficheAjoutModif();
        }

        if (s.equals("Supprimer")) {
            this.supprimerDesDpt(this.vue.getLesLignesSelectionnee());
        }

        if (s.equals("Enseignant")) {

            Departement d = null;
            if (this.vue.getLesLignesSelectionnee().length == 1) {
                int l = this.vue.getLigneSelectionnee();
                d = this.donneesDepartement.getValueAt(l);
            }

            if (!enseignantOuverte) {
                vueEnseignants = new VueEnseignants();
                vueEnseignants.setName("Enseignants");
                vueEnseignants.setDpt(d);
                enseignantOuverte = true;
                vueEnseignants.addWindowListener(this);
            } else {
                vueEnseignants.setDpt(d);
                vueEnseignants.toFront();
            }
        }

        if (s.equals("Formation")) {
            Departement d = null;
            if (this.vue.getLesLignesSelectionnee().length == 1) {
                int l = this.vue.getLigneSelectionnee();
                d = this.donneesDepartement.getValueAt(l);
            }

            if (!formationOuverte) {
                vueFormations = new VueFormation();
                vueFormations.setName("Formations");
                vueFormations.setDpt(d);
                formationOuverte = true;
                vueFormations.addWindowListener(this);
            } else {
                vueFormations.setDpt(d);
                vueFormations.toFront();
            }

        }

        // Bouttons Ajout-Modif
        if (s.equals("submitAjout")) {
            String libelle = this.vue.getLibelle();
            Departement departement = new Departement(libelle);
            this.donneesDepartement.ajouterElement(departement);
            SQLDepartementDAO.getInstance().create(departement);
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String libelle = this.vue.getLibelle();
            Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
            departement.setLibelleDepartement(libelle);
            this.donneesDepartement.setRow(this.vue.getLigneSelectionnee(), departement);
            SQLDepartementDAO.getInstance().update(departement);
            this.vue.close();
        }
    }

    private void gereActionCommandContact(ActionEvent e) {

        String s = e.getActionCommand();

        if (s.equals("Profil")) {
            Contact contact = this.donneesContact.getValueAt(this.vue.getLigneSelectionnee());
            this.vue.construitProfil(contact);
        }

        if (s.equals("Importer")) {
            String c = this.vue.fileChooserImport();

            if (c != null) {
                this.donneesContact.addDonnees((new XMLContactDAO(c).readAll()));
                SQLContactDAO.getInstance().createList(new XMLContactDAO(c).readAll());
            }
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
        String[] champsContact = {"Nom", "Prenom", "Description", "Lieu de la rencontre"};
        this.donneesContact = new ModeleContact(champsContact);
        this.donneesContact.setDonnees(SQLContactDAO.getInstance().readAll());
        return donneesContact;
    }

    /**
     * Suppression Manifestations - Uniquement celle qui n'apparaissent pas dans
     * contacts
     *
     * @param tab manifestations à supprimer
     * @return résultat
     */
    private boolean supprimerDesManifestaion(int tab[]) {

        for (int i : tab) {
            Manifestation m = this.donneesManifestation.getValueAt(i);
            if (SQLContactDAO.getInstance().nbContactsParManifestation(m) == 0) {
                this.donneesManifestation.supprimerElement(m);
            } else {
                this.vue.afficheMessage("Impossible de supprimer une manifestation liée à un contact, opération annulée.",
                        "Erreur lors de la suppression", 0);
                this.donneesManifestation.videElementsASupprimer();
                return false;
            }
        }

        int response = -1;

        if (tab.length > 1) {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer " + tab.length + " manifestations ?",
                    "Confirmation de suppression groupée", 2, 3);
        } else {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer cette manifestation ?",
                    this.donneesManifestation.getElementsASupprimer().get(0).getLibelleManif(), 2, 3);
        }

        if (response == 0) {
            this.donneesManifestation.confirmerSuppression();
            SQLManifestationDAO.getInstance().deleteList(this.donneesManifestation.getElementsASupprimer());
        }

        this.donneesManifestation.videElementsASupprimer();
        return true;
    }

    /**
     * Suppression de départements - Uniquement les département qui ne sont pas
     * lié à des contacts - Si un dpt est lié à une formation ou enseignant,
     * alors suppression en cascade
     *
     * @param tab départements à supprimer
     * @return résultat
     */
    private boolean supprimerDesDpt(int tab[]) {

        int nbEnsTotal = 0;
        int nbFormTotal = 0;

        for (int i : tab) {
            Departement d = this.donneesDepartement.getValueAt(i);

            int nbEns = SQLEnseignantDAO.getInstance().nbEnseignantsParDpt(d);
            int nbForm = SQLFormationDAO.getInstance().nbFormationsParDpt(d);

            nbEnsTotal = nbEnsTotal + nbEns;
            nbFormTotal = nbFormTotal + nbForm;

            if (SQLContactDAO.getInstance().nbContactsParDptEnseignants(d) == 0
                    && SQLContactDAO.getInstance().nbContactsParDptFormation(d) == 0) {
                this.donneesDepartement.supprimerElement(d);
            } else {
                this.vue.afficheMessage("Impossible de supprimer un département lié à un contact, opération annulée.",
                        "Erreur lors de la suppression", 0);
                this.donneesDepartement.videElementsASupprimer();
                return false;
            }
        }

        int response = -1;

        if (tab.length > 1) {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer " + tab.length + " départements ? "
                    + nbEnsTotal + " enseignant(s) et "
                    + nbFormTotal + " formation(s) vont être supprimés",
                    "Confirmation de suppression groupée", 2, 3);
        } else {
            response = this.vue.confirmation("Etes-vous sûr de vouloir supprimer ce département ? "
                    + nbEnsTotal + " enseignant(s) et "
                    + nbFormTotal + " formation(s) vont être supprimés",
                    this.donneesDepartement.getElementsASupprimer().get(0).getLibelleDepartement(), 2, 3);
        }

        if (response == 0) {
            this.donneesDepartement.confirmerSuppression();
            SQLDepartementDAO.getInstance().deleteList(this.donneesDepartement.getElementsASupprimer());
        }

        this.donneesDepartement.videElementsASupprimer();
        return true;
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
        if (e.getDocument().getProperty("id").equals("tAjoutModif")) {
            this.vue.autoriseAjoutModif();
        } else if (e.getDocument().getProperty("id").equals("tSearch")) {
            this.search = this.vue.getSearch().split(" ");
            this.vue.filtrer(this.search);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.vue.gereButtonsActifs();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        this.vue.gereButtonsActifs();
    }

    @Override
    public void windowOpened(WindowEvent we) {
        if (we.getWindow().getName().equals("Enseignants")) {
            enseignantOuverte = true;
        } else if (we.getWindow().getName().equals("Formations")) {
            formationOuverte = true;
        }
    }

    @Override
    public void windowClosing(WindowEvent we) {
        if (we.getWindow().getName().equals("Enseignants")) {
            enseignantOuverte = false;
        } else if (we.getWindow().getName().equals("Formations")) {
            formationOuverte = false;
        }
    }

    @Override
    public void windowClosed(WindowEvent we) {
        this.windowClosing(we);
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
        this.windowOpened(we);
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.vue.annulerSelectionTables();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            File file = (File) evt.getNewValue();
            if (file != null) {
                String c = file.getAbsolutePath();
                String dateMax = new XMLContactDAO(c).getMaxDate();
                String dateMin = new XMLContactDAO(c).getMinDate();
                Manifestation m = new XMLContactDAO(c).getManifestation();
                ArrayList<Departement> liste = new XMLContactDAO(c).getListDpt();

                this.vue.creerPanelRecap(m, dateMin, dateMax, liste);
            }
        }
    }

}
