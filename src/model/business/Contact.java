package model.business;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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

    public Contact() {

        this.dateContact = new LocalDate().toDateTimeAtCurrentTime().toDate();
        LocalTime localTime = new LocalTime();
        this.heureContact = new Time(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute());
        this.listeFormations = new ArrayList<Formation>();
    }
    
        public Contact(int idContact, Manifestation manif, Enseignant ens) {

        this.setIdContact(idContact);
        this.setManifestation(manif);
        this.setEnseignant(ens);
        this.dateContact = new LocalDate().toDateTimeAtCurrentTime().toDate();
        LocalTime localTime = new LocalTime();
        this.heureContact = new Time(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute());
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
            this.addFormation(formation);
        }
    }

}
