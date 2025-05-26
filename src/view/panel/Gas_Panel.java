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
            "gas",                               // utility type gas
            "Gas Consumption",                   // panel title
            "Track and manage your gas usage",   // panel subtitle
            "Gas Saving Tips",                   // tips title
            new Color(255, 77, 0)                // tips title color (red-orange)
        );
    }
}
/*
 * File: Gas_Panel.java
 *
 * Description:
 * This file defines the `Gas_Panel` class, which is a child of the `Utility_Panel` class. 
 * It is specifically designed to manage and display gas consumption data. 
 * The class customizes the parent `Utility_Panel` with gas-specific configurations, such as titles, subtitles, and color themes.
 *
 * Variables:
 * - `database_manager` (Database_Manager): Manages database operations, including gas-related actions.
 * - `current_user` (User): Represents the currently logged-in user.
 * - `utility_type` (String): Set to "gas" to specify the type of utility being managed.
 * - `panel_title` (String): Set to "Gas Consumption" to display the title of the panel.
 * - `panel_subtitle` (String): Set to "Track and manage your gas usage" to display the subtitle of the panel.
 * - `tips_title` (String): Set to "Gas Saving Tips" to display the title of the tips section.
 * - `tips_title_color` (Color): Set to a red-orange color (`new Color(255, 77, 0)`) to represent gas.
 *
 * Functions:
 * 1. `Gas_Panel(Database_Manager database_manager, User current_user)`:
 *    - Constructor that initializes the `Gas_Panel` with gas-specific configurations.
 *    - Calls the parent `Utility_Panel` constructor with the appropriate parameters.
 *
 * Usage:
 * This class is used to display and manage gas consumption data in the application. 
 * It inherits functionality from the `Utility_Panel` class and customizes it for gas-related operations.
 */
