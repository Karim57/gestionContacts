package model.business;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Objects;

public class Contact implements Comparable<Contact> {

    private int idContact;
    private String nomContact;
    private String prenomContact;
    private String emailContact;
    private String etudes1Contact;
    private String etudes2Contact;
    private String descriptionContact;
    private Date dateContact;
    private Time heureContact;
    private Manifestation manifestation;
    private Enseignant enseignant;
    private ArrayList<Formation> listeFormations;

    public Contact(int idContact, String nomContact, String prenomContact, String emailContact, String etudes1Contact, String etudes2Contact, String descriptionContact, Manifestation manifestation, Enseignant enseignant) {

        this(idContact,
                nomContact,
                prenomContact,
                emailContact,
                etudes1Contact,
                etudes2Contact, descriptionContact,
                new Date(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()),
                manifestation,
                enseignant);
    }

    public Contact(int idContact, String nomContact, String prenomContact, String emailContact, String etudes1Contact, String etudes2Contact, String descriptionContact, Date dateContact, Time heureContact, Manifestation manifestation, Enseignant enseignant) {
        this.idContact = idContact;
        this.nomContact = nomContact;
        this.prenomContact = prenomContact;
        this.emailContact = emailContact;
        this.etudes1Contact = etudes1Contact;
        this.etudes2Contact = etudes2Contact;
        this.descriptionContact = descriptionContact;
        this.dateContact = dateContact;
        this.heureContact = heureContact;
        this.manifestation = manifestation;
        this.enseignant = enseignant;
        this.listeFormations = new ArrayList<Formation>();
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getPrenomContact() {
        return prenomContact;
    }

    public void setPrenomContact(String prenomContact) {
        this.prenomContact = prenomContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getEtudes1Contact() {
        return etudes1Contact;
    }

    public void setEtudes1Contact(String etudes1Contact) {
        this.etudes1Contact = etudes1Contact;
    }

    public String getEtudes2Contact() {
        return etudes2Contact;
    }

    public void setEtudes2Contact(String etudes2Contact) {
        this.etudes2Contact = etudes2Contact;
    }

    public String getDescriptionContact() {
        return descriptionContact;
    }

    public void setDescriptionContact(String descriptionContact) {
        this.descriptionContact = descriptionContact;
    }

    public Date getDateContact() {
        return dateContact;
    }

    public void setDateContact(Date dateContact) {
        this.dateContact = dateContact;
    }

    public Time getHeureContact() {
        return heureContact;
    }

    public void setHeureContact(Time heureContact) {
        this.heureContact = heureContact;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @Override
    public int compareTo(Contact c) {
        return this.nomContact.compareTo(c.nomContact);
    }

    public ArrayList<Formation> getListeFormations() {
        return listeFormations;
    }

    public void addFormation(Formation formation) {
        if (this.listeFormations.size() < 4) {
            this.listeFormations.add(formation);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idContact;
        hash = 79 * hash + Objects.hashCode(this.nomContact);
        hash = 79 * hash + Objects.hashCode(this.prenomContact);
        hash = 79 * hash + Objects.hashCode(this.emailContact);
        hash = 79 * hash + Objects.hashCode(this.etudes1Contact);
        hash = 79 * hash + Objects.hashCode(this.etudes2Contact);
        hash = 79 * hash + Objects.hashCode(this.descriptionContact);
        hash = 79 * hash + Objects.hashCode(this.dateContact);
        hash = 79 * hash + Objects.hashCode(this.heureContact);
        hash = 79 * hash + Objects.hashCode(this.manifestation);
        hash = 79 * hash + Objects.hashCode(this.enseignant);
        hash = 79 * hash + Objects.hashCode(this.listeFormations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (this.idContact != other.idContact) {
            return false;
        }
        if (!Objects.equals(this.nomContact, other.nomContact)) {
            return false;
        }
        if (!Objects.equals(this.prenomContact, other.prenomContact)) {
            return false;
        }
        if (!Objects.equals(this.emailContact, other.emailContact)) {
            return false;
        }
        if (!Objects.equals(this.etudes1Contact, other.etudes1Contact)) {
            return false;
        }
        if (!Objects.equals(this.etudes2Contact, other.etudes2Contact)) {
            return false;
        }
        if (!Objects.equals(this.descriptionContact, other.descriptionContact)) {
            return false;
        }
        if (!Objects.equals(this.dateContact, other.dateContact)) {
            return false;
        }
        if (!Objects.equals(this.heureContact, other.heureContact)) {
            return false;
        }
        if (!Objects.equals(this.manifestation, other.manifestation)) {
            return false;
        }
        if (!Objects.equals(this.enseignant, other.enseignant)) {
            return false;
        }
        if (!Objects.equals(this.listeFormations, other.listeFormations)) {
            return false;
        }
        return true;
    }

}
