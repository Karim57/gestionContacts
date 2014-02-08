//mffffffffffff
//trtrtr
package model.business;

import java.util.Date;

public class Contact {
    
    private int idContact;
    private String nomContact;
    private String prenomContact;
    private String nomConemailContact;
    private String etudes1Contact;
    private String etudes2Contact;
    private String descriptionContact;
    private Date dateContact;
    private Date heureContact;
    private Manifestation manifestation;
    private Enseignant enseignant;

    public Contact(Date dateContact) {
         this.dateContact = new Date();
    }
    
        
    
    
}
