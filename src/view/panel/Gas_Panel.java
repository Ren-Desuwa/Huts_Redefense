package view.panel;

import java.awt.Color;

import database.Database_Manager;
import model.User;

public class Gas_Panel extends Utility_Panel {

    private static final long serialVersionUID = 1L;

    public Gas_Panel(Database_Manager database_manager, User current_user) {
        super(
            database_manager, 
            current_user, 
            "gas",                               // utility type
            "Gas Consumption",                   // panel title
            "Track and manage your gas usage",   // panel subtitle
            "Gas Saving Tips",                   // tips title
            new Color(255, 77, 0)                // tips title color (red-orange)
        );
    }
}