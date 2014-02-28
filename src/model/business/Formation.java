package model.business;

import java.util.Objects;

public class Formation implements Comparable<Formation> {

    private int idFormation;
    private String libelleFormation;
    private Departement departement;

    public Formation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public Formation(String libelleFormation, Departement d) {
        this(libelleFormation);
        this.setDepartement(d);
    }

    public Formation(int idFormation, String libelleFormation) {
        this.idFormation = idFormation;
        this.libelleFormation = libelleFormation;
    }

    public Formation(int idFormation, String libelleFormation, Departement departement) {
        this.idFormation = idFormation;
        this.libelleFormation = libelleFormation;
        this.departement = departement;
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getLibelleFormation() {
        return libelleFormation;
    }

    public void setLibelleFormation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public Departement getDepartement() {
        return this.departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public int compareTo(Formation f) {
        return this.libelleFormation.compareTo(f.libelleFormation);
    }

    @Override
    public String toString() {
        return libelleFormation + " " + departement;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.idFormation;
        hash = 19 * hash + Objects.hashCode(this.libelleFormation);
        hash = 19 * hash + Objects.hashCode(this.departement);
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
        final Formation other = (Formation) obj;
        if (this.idFormation != other.idFormation) {
            return false;
        }
        if (!Objects.equals(this.libelleFormation, other.libelleFormation)) {
            return false;
        }
        if (!Objects.equals(this.departement, other.departement)) {
            return false;
        }
        return true;
    }
    
    
    
}
