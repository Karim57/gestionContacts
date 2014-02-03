package model.business;

import java.util.Date;

public class Contact {

    private int idContact;
    private Manifestation manifestation;
    private Enseignant enseignant;
    private String nom;
    private String prenom;
    private String email;
    private String etudes1;
    private String etudes2;
    private Date date;
    private Date heure;
    private String description;
    private Formation formations;

    public Contact(Date date) {
        this();
        this.date = new Date();
        this.heure = new Date();
    }

    public Contact() {
        super();
        this.manifestation=null;
        this.enseignant=null;
        this.nom = "";
        this.prenom = "";
        this.email = "";
        this.etudes1="";
        this.etudes2="";
        this.description="";
        
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtudes1() {
        return etudes1;
    }

    public void setEtudes1(String etudes1) {
        this.etudes1 = etudes1;
    }

    public String getEtudes2() {
        return etudes2;
    }

    public void setEtudes2(String etudes2) {
        this.etudes2 = etudes2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Formation getFormations() {
        return formations;
    }

    public void setFormations(Formation formations) {
        this.formations = formations;
    }

}
