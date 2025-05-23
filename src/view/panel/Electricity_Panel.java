package view.panel;

import java.awt.Color;

import database.Database_Manager;
import model.User;

public class Electricity_Panel extends Utility_Panel {

    private static final long serialVersionUID = 1L;

    public Electricity_Panel(Database_Manager database_manager, User current_user) {
        super(
            database_manager, 
            current_user, 
            "electricity",                               // utility type
            "Electricity Consumption",                   // panel title
            "Track and manage your Energy usage",   // panel subtitle
            "Electricity Saving Tips",                   // tips title
            new Color(255, 167, 0)                // tips title color (yellow-orange)
        );
    }
}