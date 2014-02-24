package model.tables;

import java.util.Iterator;
import model.business.Contact;
import model.business.Manifestation;

public class ModeleContact extends ModeleGenerique<Contact> {

    public ModeleContact(String[] cols) {
        super(cols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Contact contact = this.donnees.get(rowIndex);
        Object o = null;

        switch (columnIndex) {
            case 0:
                o = contact.getNomContact();
                break;
            case 1:
                o = contact.getPrenomContact();
                break;
            case 2:
                o = contact.getDescriptionContact();
                break;
            case 3:
                o = contact.getManifestation().getLibelleManif();
                break;
        }
        return o;
    }

    public boolean canDeleteManif(Manifestation m) {
        for (Contact c : this.donnees) {
            if (m.equals(c.getManifestation())) {
                return false;
            }
        }
        return true;
    }

}
