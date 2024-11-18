package database;

import controller.Contact;

/**
 *
 * @author Riley O'Donnell
 * 
 * For use in _Scene_Transitions
 */
public class _Contact_Formatter {
    public static String formatContact(Contact contact) {
        return contact.get_ID() + " - " + contact.get_name();
    }
    
    
    
}
