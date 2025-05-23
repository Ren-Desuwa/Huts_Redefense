package view.panel;

import java.awt.Color;

import database.Database_Manager;
import model.User;

public class Water_Panel extends Utility_Panel {

    private static final long serialVersionUID = 1L;

    public Water_Panel(Database_Manager database_manager, User current_user) {
        super(
            database_manager, 
            current_user, 
            "water",                               // utility type
            "Water Consumption",                   // panel title
            "Track and manage your water usage",   // panel subtitle
            "Water Saving Tips",                   // tips title
            new Color(0, 119, 190)                 // tips title color (blue)
        );
    }
}