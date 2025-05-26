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
            "water",                               // utility type water
            "Water Consumption",                   // panel title
            "Track and manage your water usage",   // panel subtitle
            "Water Saving Tips",                   // tips title
            new Color(0, 119, 190)                 // tips title color (blue)
        );
    }
}
/*
 * File: Water_Panel.java
 *
 * Description:
 * This file defines the `Water_Panel` class, which is a child of the `Utility_Panel` class. 
 * It is specifically designed to manage and display water consumption data. 
 * The class customizes the parent `Utility_Panel` with water-specific configurations, such as titles, subtitles, and color themes.
 *
 * Variables:
 * - `database_manager` (Database_Manager): Manages database operations, including water-related actions.
 * - `current_user` (User): Represents the currently logged-in user.
 * - `utility_type` (String): Set to "water" to specify the type of utility being managed.
 * - `panel_title` (String): Set to "Water Consumption" to display the title of the panel.
 * - `panel_subtitle` (String): Set to "Track and manage your water usage" to display the subtitle of the panel.
 * - `tips_title` (String): Set to "Water Saving Tips" to display the title of the tips section.
 * - `tips_title_color` (Color): Set to a blue color (`new Color(0, 119, 190)`) to represent water.
 *
 * Functions:
 * 1. `Water_Panel(Database_Manager database_manager, User current_user)`:
 *    - Constructor that initializes the `Water_Panel` with water-specific configurations.
 *    - Calls the parent `Utility_Panel` constructor with the appropriate parameters.
 *
 * Usage:
 * This class is used to display and manage water consumption data in the application. 
 * It inherits functionality from the `Utility_Panel` class and customizes it for water-related operations.
 */
