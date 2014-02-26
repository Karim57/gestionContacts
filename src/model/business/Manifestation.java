package model.business;

import java.util.Objects;

public class Manifestation implements Comparable<Manifestation> {

    private int idManif;
    private String libelleManif;

    public Manifestation() {
        this.setIdManif(idManif);
        this.setLibelleManif(libelleManif);
    }

    public Manifestation(String libelleManif) {
        this.setLibelleManif(libelleManif);
    }

    public Manifestation(int idManif, String libelleManif) {
        this(libelleManif);
        this.setIdManif(idManif);
    }

    public int getIdManif() {
        return idManif;
    }

    public void setIdManif(int idManif) {
        this.idManif = idManif;
    }

    public String getLibelleManif() {
        return libelleManif;
    }

    public void setLibelleManif(String libelleManif) {
        this.libelleManif = libelleManif;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.idManif;
        hash = 67 * hash + Objects.hashCode(this.libelleManif);
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
        final Manifestation other = (Manifestation) obj;
        if (this.idManif != other.idManif) {
            return false;
        }
        if (!Objects.equals(this.libelleManif, other.libelleManif)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manifestation{" + "idManif=" + idManif + ", libelleManif=" + libelleManif + '}';
    }

    @Override
    public int compareTo(Manifestation m) {
        return this.libelleManif.compareTo(m.libelleManif);
    }

}
