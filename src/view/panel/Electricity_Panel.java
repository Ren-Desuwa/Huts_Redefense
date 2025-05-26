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
            "electricity",                               // utility type electricity
            "Electricity Consumption",                   // panel title
            "Track and manage your Energy usage",   	 // panel subtitle
            "Electricity Saving Tips",                   // tips title
            new Color(255, 167, 0)                		 // tips title color (yellow-orange)
        );
    }
}
/*
 * File: Electricity_Panel.java
 *
 * Description:
 * This file defines the `Electricity_Panel` class, which is a child of the `Utility_Panel` class. 
 * It is specifically designed to manage and display electricity consumption data. 
 * The class customizes the parent `Utility_Panel` with electricity-specific configurations, such as titles, subtitles, and color themes.
 *
 * Variables:
 * - `database_manager` (Database_Manager): Manages database operations, including electricity-related actions.
 * - `current_user` (User): Represents the currently logged-in user.
 * - `utility_type` (String): Set to "electricity" to specify the type of utility being managed.
 * - `panel_title` (String): Set to "Electricity Consumption" to display the title of the panel.
 * - `panel_subtitle` (String): Set to "Track and manage your Energy usage" to display the subtitle of the panel.
 * - `tips_title` (String): Set to "Electricity Saving Tips" to display the title of the tips section.
 * - `tips_title_color` (Color): Set to a yellow-orange color (`new Color(255, 167, 0)`) to represent electricity.
 *
 * Functions:
 * 1. `Electricity_Panel(Database_Manager database_manager, User current_user)`:
 *    - Constructor that initializes the `Electricity_Panel` with electricity-specific configurations.
 *    - Calls the parent `Utility_Panel` constructor with the appropriate parameters.
 *
 * Usage:
 * This class is used to display and manage electricity consumption data in the application. 
 * It inherits functionality from the `Utility_Panel` class and customizes it for electricity-related operations.
 */
