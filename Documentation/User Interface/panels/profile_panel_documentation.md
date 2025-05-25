# Profile Panel Documentation

## Table of Contents
1. [Overview](#overview)
2. [User Documentation](#user-documentation)
3. [Technical Documentation](#technical-documentation)
4. [Dependencies](#dependencies)
5. [Usage Examples](#usage-examples)

---

## Overview

The `Profile_Panel` class is a comprehensive user interface component that displays and manages user profile information in a Java Swing application. It serves as the central hub for users to view their account details, usage statistics, and perform account-related actions.

---

## User Documentation

### Purpose and Importance

The Profile Panel is a critical component of the application that provides users with:

- **Account Overview**: A centralized view of user information including username, email, and profile initials
- **Usage Statistics**: Visual representation of utility readings (electricity, water, gas) with interactive elements
- **Account Management**: Quick access to profile editing and password change functionality
- **Navigation Hub**: Direct access to different utility reading panels through clickable statistics

### Key Features

#### Visual Elements
- **Profile Header**: Blue-themed header section displaying user initials in a circular avatar, username, and email
- **Account Information Section**: Organized display of username, email, and total submissions count
- **Usage Statistics**: Three interactive cards showing electricity, water, and gas reading counts
- **Action Buttons**: Easy access to password change and profile editing functions

#### Interactive Components
- **Edit Profile Link**: Clickable text in the header for quick profile editing
- **Statistics Cards**: Clickable panels that navigate to respective utility reading sections
- **Change Password Button**: Dedicated button for password management
- **Hover Effects**: Visual feedback on interactive elements

### Where It's Used

The Profile Panel is typically accessed through:
- Main navigation menu as a "Profile" or "Account" option
- User dropdown menus in the application header
- Dashboard sections for account management

### Benefits for Users

1. **Centralized Information**: All account details in one location
2. **Quick Navigation**: Direct access to utility reading sections
3. **Visual Statistics**: Easy-to-understand representation of usage data
4. **Account Security**: Convenient password change functionality
5. **Profile Customization**: Simple profile editing capabilities

---

## Technical Documentation

### Class Structure

```java
public class Profile_Panel extends JPanel
```

**Package**: `view.panel`

**Inheritance**: Extends `JPanel` for Swing GUI integration

### Dependencies

**External Libraries**:
- Java Swing (javax.swing.*)
- Java AWT (java.awt.*)

**Internal Dependencies**:
- `database.Database_Manager`: Database operations
- `model.User`: User data model
- `view.Main_Frame`: Parent frame reference
- `view.panel.misc.Change_Password_Window`: Password change dialog
- `view.panel.misc.Edit_Profile_Window`: Profile editing dialog
- `visuals.*`: Custom visual components (Circle_Panel, Rounded_Button, etc.)

### Field Documentation

#### Data Fields
- `Database_Manager database_manager`: Handles all database operations
- `User current_user`: Current user object containing user data
- `Main_Frame main_frame`: Reference to parent frame for navigation
- `Profile_Panel profile_Panel`: Self-reference for window creation

#### UI Component Fields

**Main Panels**:
- `JPanel panel_Main`: Root container with rounded corners
- `JPanel panel_Header`: Blue header section (300px height)
- `JPanel panel_Content`: White content area below header
- `JPanel panel_Profile_Image`: Circular container for user initials

**Statistics Panels**:
- `JPanel panel_Statistics`: Container for all usage statistics
- `JPanel panel_Electricity_Stats`: Electricity reading statistics card
- `JPanel panel_Water_Stats`: Water reading statistics card  
- `JPanel panel_Gas_Stats`: Gas reading statistics card

**Labels**: Comprehensive labeling system for all displayed information including user details, statistics, and section headers

**Interactive Elements**:
- `JButton btn_Change_Password`: Rounded button for password changes
- Various clickable labels and panels with mouse listeners

### Method Documentation

#### Constructor
```java
public Profile_Panel(Main_Frame main_frame, Database_Manager database_manager, User current_user)
```

**Purpose**: Initializes the profile panel with required dependencies

**Parameters**:
- `main_frame`: Parent frame reference for navigation
- `database_manager`: Database connection for user data retrieval
- `current_user`: User object containing current user information

**Process**:
1. Stores references to dependencies
2. Sets panel dimensions (986x688) and layout
3. Calls initialization methods in sequence
4. Updates UI with current user information

#### Core Methods

##### `initialize_UI()`
**Purpose**: Creates and configures all UI components

**Structure**:
- **Main Panel Setup**: Creates rounded container with proper styling
- **Header Section**: Blue background with profile image and user info
- **Content Section**: White background with account information and statistics
- **Statistics Cards**: Three interactive panels for utility readings
- **Action Elements**: Buttons and separators for user actions

**UI Architecture**:
```
┌─ panel_Main (Rounded, BorderLayout) ─┐
│  ┌─ panel_Header (Blue, 300px) ─────┐ │
│  │  • Profile Image (Circle)        │ │
│  │  • Username & Email              │ │
│  │  • Edit Profile Link             │ │
│  └───────────────────────────────────┘ │
│  ┌─ panel_Content (White) ──────────┐ │
│  │  • Account Information           │ │
│  │  • Usage Statistics (3 cards)    │ │
│  │  • Action Buttons                │ │
│  └───────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

##### `createActionListeners()`
**Purpose**: Establishes event handling for interactive components

**Event Handlers**:
1. **Change Password Button**:
   - Click: Opens password change window
   - Hover: Color transition effects (192,192,192 → 128,128,128)

2. **Edit Profile Label**:
   - Click: Opens profile editing window
   - Hover: Color change (White → Blue)

3. **Statistics Panels**:
   - Click: Navigation to respective utility panels
   - Implemented through `addPanelClickListener()` helper method

##### `updateUserInfo(User user)`
**Purpose**: Refreshes all displayed user information

**Data Sources**:
- User object: Username, email
- Database queries: Statistics and reading counts

**Database Operations**:
```java
totalSubmissions = database_manager.getReadingManager().getTotal_Readings(current_user);
electricityCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "electricity").size();
waterCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "water").size();
gasCount = database_manager.getReadingManager().getAll_Readings_By_Type(current_user, "gas").size();
```

**Error Handling**: SQLException catch block defaults all counts to 0 and prints stack trace

**UI Updates**:
- Header information (username, email)
- Account information section
- Statistics counters
- Profile initials (first 2 characters of username)

##### `createChangePasswordWindow()`
**Purpose**: Launches password change dialog

**Implementation**:
- Uses `EventQueue.invokeLater()` for thread safety
- Creates modal `Change_Password_Window`
- Passes current frame as parent for proper modality
- Includes exception handling with stack trace output

##### `openEditProfileWindow()`
**Purpose**: Launches profile editing dialog

**Implementation**:
- Thread-safe window creation using `EventQueue.invokeLater()`
- Passes self-reference for callback updates
- Proper exception handling

##### `addPanelClickListener(JPanel panel, Runnable action)`
**Purpose**: Helper method for adding click listeners to panels

**Parameters**:
- `panel`: Target panel for click detection
- `action`: Runnable to execute on click (typically navigation methods)

### Design Patterns Used

1. **Observer Pattern**: Mouse listeners for UI interaction
2. **Template Method**: Consistent UI initialization structure  
3. **Dependency Injection**: Constructor injection of required services
4. **Event-Driven Architecture**: Action listeners for user interactions

### Performance Considerations

1. **Database Queries**: Statistics are loaded on demand during `updateUserInfo()`
2. **UI Threading**: Window creation operations use `EventQueue.invokeLater()`
3. **Memory Management**: Proper component references and cleanup
4. **Exception Handling**: Graceful degradation for database errors

### Security Features

1. **Password Management**: Secure password change functionality
2. **User Session**: Maintains current user context
3. **Database Security**: Uses parameterized database manager
4. **UI Security**: Proper input validation through dedicated windows

### Customization Options

**Visual Customization**:
- Colors: Header blue (68,162,255), content white (250,250,250)
- Fonts: Tahoma family with varying sizes
- Layout: Fixed positioning with specific bounds
- Rounded corners: 100px main panel, 15px statistics cards

**Functional Customization**:
- Statistics display can be modified through label updates
- Navigation targets configurable through Main_Frame methods
- Tooltip messages customizable in Following_Tool_Tip instances

---

## Dependencies

### Required Java Packages
- `java.awt.*`: Layout management and visual components
- `javax.swing.*`: Swing GUI components
- `java.awt.event.*`: Event handling
- `java.sql.*`: Database exception handling

### Internal Application Dependencies
- **Database Layer**: `Database_Manager` and related managers
- **Model Layer**: `User` data model
- **View Layer**: `Main_Frame`, dialog windows
- **Visual Components**: Custom rounded and circular panels
- **Utility Classes**: Tooltip and visual enhancement components

### External Resources
- Font: Tahoma (system font)
- Colors: Predefined RGB values
- Layout: Absolute positioning with specific coordinates

---

## Usage Examples

### Basic Initialization
```java
// Assuming you have the required dependencies
Database_Manager dbManager = new Database_Manager();
User currentUser = // ... obtain user object
Main_Frame parentFrame = // ... parent frame reference

Profile_Panel profilePanel = new Profile_Panel(parentFrame, dbManager, currentUser);
parentFrame.add(profilePanel, BorderLayout.CENTER);
```

### Updating User Information
```java
// After user data changes
User updatedUser = // ... get updated user data
profilePanel.updateUserInfo(updatedUser);
```

### Integration with Navigation
```java
// In Main_Frame class
public void showProfilePanel() {
    Profile_Panel profile = new Profile_Panel(this, databaseManager, currentUser);
    mainContentPane.removeAll();
    mainContentPane.add(profile, BorderLayout.CENTER);
    mainContentPane.revalidate();
    mainContentPane.repaint();
}
```

This documentation provides comprehensive coverage of both the user-facing functionality and technical implementation details of the Profile_Panel class, making it suitable for end users, developers, and maintainers of the application.