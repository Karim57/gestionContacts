package model.business;

import java.util.Objects;

public class Departement implements Comparable<Departement> {

    private int idDepartement;
    private String libelleDepartement;

    public Departement(String libelleDepartement) {
        this.setLibelleDepartement(libelleDepartement);
    }

    public Departement(int idDepartement, String libelleDepartement) {
        this(libelleDepartement);
        this.setIdDepartement(idDepartement);
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getLibelleDepartement() {
        return libelleDepartement;
    }

    public void setLibelleDepartement(String libelleDepartement) {
        this.libelleDepartement = libelleDepartement;
    }

    @Override
    public int compareTo(Departement d) {
        return this.libelleDepartement.compareTo(d.libelleDepartement);
    }

    @Override
    public String toString() {
        return libelleDepartement;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.idDepartement;
        hash = 83 * hash + Objects.hashCode(this.libelleDepartement);
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
        final Departement other = (Departement) obj;
        if (this.idDepartement != other.idDepartement) {
            return false;
        }
        if (!Objects.equals(this.libelleDepartement, other.libelleDepartement)) {
            return false;
        }
        return true;
    }

}
