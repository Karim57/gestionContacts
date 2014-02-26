package model.business;

public class Enseignant implements Comparable<Enseignant> {

    private int idEnseignant;
    private String nomEnseignant;
    private String prenomEnseignant;
    private Departement departement;

    public Enseignant(String nomEnseignant, String prenomEnseignant) {
        this.setNomEnseignant(nomEnseignant);
        this.setPrenomEnseignant(prenomEnseignant);
    }
    
     public Enseignant(int idEnseignant,String nomEnseignant, String prenomEnseignant) {
        this(nomEnseignant, prenomEnseignant);
        this.setIdEnseignant(idEnseignant);
    }

    public Enseignant(int idEnseignant, String nomEnseignant, String prenomEnseignant, Departement departement) {
        this(nomEnseignant, prenomEnseignant);
        this.setIdEnseignant(idEnseignant);
        this.setDepartement(departement);
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public String getNomEnseignant() {
        return nomEnseignant;
    }

    public void setNomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
    }

    public String getPrenomEnseignant() {
        return prenomEnseignant;
    }

    public void setPrenomEnseignant(String prenomEnseignant) {
        this.prenomEnseignant = prenomEnseignant;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public int compareTo(Enseignant e) {
        return this.nomEnseignant.compareTo(e.nomEnseignant);
    }

}
