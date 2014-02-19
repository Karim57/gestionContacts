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

        }
        return o;
    }

}
