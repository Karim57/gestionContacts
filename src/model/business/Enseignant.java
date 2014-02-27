package model.business;

import java.util.Objects;

public class Enseignant implements Comparable<Enseignant> {

    private int idEnseignant;
    private String nomEnseignant;
    private String prenomEnseignant;
    private Departement departement;

    public Enseignant(int idEnseignant, String nomEnseignant, String prenomEnseignant, Departement d) {
        this(nomEnseignant, prenomEnseignant, d);
        this.setIdEnseignant(idEnseignant);
    }

    public Enseignant(int idEnseignant, String nomEnseignant, String prenomEnseignant) {
        this.nomEnseignant = nomEnseignant;
        this.prenomEnseignant = prenomEnseignant;
        this.setIdEnseignant(idEnseignant);
    }

    public Enseignant(String nomEnseignant, String prenomEnseignant, Departement departement) {
        this.setNomEnseignant(nomEnseignant);
        this.setPrenomEnseignant(prenomEnseignant);
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idEnseignant;
        hash = 83 * hash + Objects.hashCode(this.nomEnseignant);
        hash = 83 * hash + Objects.hashCode(this.prenomEnseignant);
        hash = 83 * hash + Objects.hashCode(this.departement);
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
        final Enseignant other = (Enseignant) obj;
        if (this.idEnseignant != other.idEnseignant) {
            return false;
        }
        if (!Objects.equals(this.nomEnseignant, other.nomEnseignant)) {
            return false;
        }
        if (!Objects.equals(this.prenomEnseignant, other.prenomEnseignant)) {
            return false;
        }
        if (!Objects.equals(this.departement, other.departement)) {
            return false;
        }
        return true;
    }

}
