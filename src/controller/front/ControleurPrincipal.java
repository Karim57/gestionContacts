package controller.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.business.Contact;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import model.business.Manifestation;
import model.dao.xml.XMLContactDAO;
import model.dao.xml.XMLDepartementDAO;
import model.dao.xml.XMLEnseignantDAO;
import model.dao.xml.XMLFormationDAO;
import model.dao.xml.XMLManifestationDAO;
import model.tables.ModeleContact;
import view.front.IOPrincipale;

public class ControleurPrincipal implements ActionListener, DocumentListener, ItemListener, ListSelectionListener, WindowListener {

    private IOPrincipale vue;

    private ModeleContact donneesContact;
    private ArrayList<Departement> donneesDepartement;
    private ArrayList<Formation> donneesFormation;
    private ArrayList<Enseignant> donneesEnseignant;
    private Enseignant enseignant = null;
    private Manifestation manifEnCours;

    private static boolean exitSafe = true;

    private String chemin = null;

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }

    public void initDonnees() {
        this.donneesDepartement = new XMLDepartementDAO(this.vue.getCheminImport()).readAll();
        this.donneesEnseignant = new ArrayList<Enseignant>();
        this.donneesFormation = new ArrayList<Formation>();
        this.manifEnCours = new XMLManifestationDAO(this.vue.getCheminImport()).read();
    }

    public void remplitComboFormation1() {
        this.vue.setListeFormation1(this.donneesFormation);
    }

    public void remplitComboFormation2() {
        this.vue.setListeFormation2(this.donneesFormation);
    }

    public void remplitComboFormation3() {
        this.vue.setListeFormation3(this.donneesFormation);
    }

    public void remplitComboFormation4() {
        this.vue.setListeFormation4(this.donneesFormation);
    }

    public void remplitComboDpt() {
        this.vue.setListeDpt(this.donneesDepartement);
    }

    public ModeleContact getDonneesContact() {
        String[] champsContact = {"Nom", "Prenom", "Description", "Lieu de la rencontre"};
        this.donneesContact = new ModeleContact(champsContact);
        this.donneesContact.setDonnees(new ArrayList<Contact>());
        return donneesContact;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            this.vue.construitAjout();
        }

        if (s.equals("comboForm1")) {
            this.vue.activeAjout();
            this.vue.gereComboAjout2();
            this.vue.gereComboAjout3();
            this.vue.gereComboAjout4();
        }

        if (s.equals("comboForm2")) {
            this.vue.gereComboAjout3();
            this.vue.gereComboAjout4();
        }

        if (s.equals("comboForm3")) {
            this.vue.gereComboAjout4();
        }

        if (s.equals("connexion")) {
            this.enseignant = this.vue.getEnsSelected();
            Departement d = this.vue.getDptSelected();
            this.donneesFormation = new XMLFormationDAO(this.vue.getCheminImport()).readAll(d);
            this.vue.close(0);
        }

        if (s.equals("anuulerEns")) {
            if (enseignant == null) {
                System.exit(0);
            } else {
                this.vue.close(0);
            }
        }

        if (s.equals("Valider")) {
            Contact contact = new Contact(manifEnCours,
                    enseignant,
                    this.vue.getNomContact(),
                    this.vue.getPrenomContact(),
                    this.vue.getEmailContact(),
                    this.vue.getEtude1Contact(),
                    this.vue.getEtude2Contact(),
                    this.vue.getDescriptionContact());

            if (this.vue.getForm1() != null) {
                contact.addFormation(this.vue.getForm1());
            }
            if (this.vue.getForm2() != null) {
                contact.addFormation(this.vue.getForm2());
            }
            if (this.vue.getForm3() != null) {
                contact.addFormation(this.vue.getForm3());
            }
            if (this.vue.getForm4() != null) {
                contact.addFormation(this.vue.getForm4());
            }

            this.donneesContact.ajouterElement(contact);
            this.vue.activeSauvegarde(true);
            this.vue.viderChampsAjout();
            exitSafe = false;
        }

        if (s.equals("Profil")) {
            Contact c = this.donneesContact.getValueAt(this.vue.getSelectedContact());
            this.vue.construitProfil(c);
        }

        if (s.equals("Enseignant")) {
            this.vue.afficheConnexion(false);
        }

        if (s.equals("Exporter")) {
            if (this.chemin == null) {
                this.chemin = this.vue.fileChooserExport();
            }

            if (this.chemin != null) {
                new XMLContactDAO(this.chemin).creerListe(this.donneesContact.getDonnees());
                exitSafe = true;
                this.vue.activeSauvegarde(false);
            }
        }

        if (s.equals("ImporterFront")) {
            String c = this.vue.fileChooserImport();

            if (c != null) {

                if (this.donneesContact.getDonnees().isEmpty()) {
                    this.donneesContact.setDonnees(new XMLContactDAO(c).readAll());
                } else {
                    if (this.vue.confirmation("Tous les contacts actuels vont être perdus, continuer ?",
                            "Confirmation d'import", 2, 3) == 0) {
                        this.donneesContact.setDonnees(new XMLContactDAO(c).readAll());
                        this.vue.activeSauvegarde(true);
                        exitSafe = false;
                    }
                }
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.vue.activeAjout();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.insertUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.insertUpdate(e);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Departement d = this.vue.getDptSelected();
            if (d != null) {
                this.donneesEnseignant = new XMLEnseignantDAO(this.vue.getCheminImport()).readAll(d);
            }
            this.vue.setListeEns(donneesEnseignant);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.vue.gereButtonsActifs();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!exitSafe) {
            int response = this.vue.confirmation("Voulez-vous sauvegarder avant de quitter ?",
                    "Confirmation de fermeture", 1, 3);
            if (response == 0) {
                if (this.chemin == null) {
                    this.chemin = this.vue.fileChooserExport();
                }

                if (this.chemin != null) {
                    new XMLContactDAO(this.chemin).creerListe(this.donneesContact.getDonnees());
                }

                e.getWindow().dispose();
                System.exit(0);

            } else if (response == 1) {
                e.getWindow().dispose();
                System.exit(0);
            }
        } else {
            e.getWindow().dispose();
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
