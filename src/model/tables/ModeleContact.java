package model.tables;

import model.business.Contact;

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

}
