# Utility Panel System Documentation

## Table of Contents
1. [Overview](#overview)
2. [System Architecture](#system-architecture)
3. [User Documentation](#user-documentation)
4. [Technical Documentation](#technical-documentation)
5. [Implementation Details](#implementation-details)
6. [Usage Examples](#usage-examples)

---

## Overview

The Utility Panel System is a Java Swing-based application designed to help users track and manage their utility consumption across different types of utilities including electricity, gas, and water. The system provides a unified interface for monitoring usage patterns, viewing historical data, and receiving conservation tips.

### Key Features
- **Multi-Utility Support**: Track electricity, gas, and water consumption
- **Real-Time Monitoring**: Display current readings and trends
- **Historical Analysis**: Interactive graphs showing monthly expenses over time
- **Conservation Tips**: Dynamic utility-specific saving recommendations
- **User-Friendly Interface**: Intuitive GUI with modern rounded panels and responsive design

### Importance
This system serves several critical purposes:
- **Cost Management**: Helps users monitor and reduce utility expenses
- **Environmental Awareness**: Promotes conservation through usage tracking
- **Data-Driven Decisions**: Provides historical data for informed consumption choices
- **Budget Planning**: Enables better financial planning through usage pattern analysis

---

## System Architecture

### Class Hierarchy
```
Utility_Panel (Abstract Base Class)
├── Electricity_Panel
├── Gas_Panel
└── Water_Panel
```

### Dependencies
- **Database Layer**: `Database_Manager` for data persistence
- **Model Layer**: `User` and `Reading` entities
- **View Layer**: Custom UI components (`Rounded_Panel`, `Rounded_Button`, `Graph_Panel`)
- **Utility Layer**: `Utility_Tips_Manager` for conservation tips

---

## User Documentation

### What Each Panel Does

#### Electricity Panel
- **Purpose**: Tracks electrical energy consumption in kilowatt-hours (kWh)
- **Features**: 
  - Current reading display
  - Monthly expense tracking
  - Energy-specific saving tips (yellow-orange theme)
- **Use Case**: Monitor home or business electricity usage to identify high-consumption periods

#### Gas Panel
- **Purpose**: Monitors gas consumption measured in quantity units
- **Features**: 
  - Gas usage tracking
  - Cost analysis over time
  - Gas-specific conservation recommendations (red-orange theme)
- **Use Case**: Track heating, cooking, or industrial gas usage

#### Water Panel
- **Purpose**: Tracks water consumption in cubic meters (m³)
- **Features**: 
  - Water usage monitoring
  - Historical consumption patterns
  - Water conservation tips (blue theme)
- **Use Case**: Monitor household or commercial water usage for conservation efforts

### User Interface Components

#### Header Section
- **Title**: Displays the utility type (e.g., "Electricity Consumption")
- **Subtitle**: Descriptive text about the panel's purpose
- **Date/Time**: Real-time clock display updated every minute

#### Current Reading Section
- **Reading Value**: Shows the most recent utility reading
- **Unit Display**: Indicates measurement unit (kWh, Qty, m³)
- **Trend Indicator**: Shows usage trend compared to previous readings
- **Add Reading Button**: Allows users to input new readings

#### Graph Section
- **Monthly Expenses**: Visual representation of costs over time
- **Year Navigation**: Browse historical data by year
- **Interactive Controls**: Previous/Next year buttons when data is available

#### Recent Readings Section
- **Tabular Data**: Lists recent readings with date, value, rate, and total cost
- **Scrollable View**: Access to historical reading entries
- **Column Headers**: Date, Readings, Rate, Total Price

#### Conservation Tips Section
- **Dynamic Tips**: Rotating utility-specific conservation advice
- **Interactive Refresh**: Click title to get new tips
- **Auto-Update**: Tips refresh automatically every 30 seconds

---

## Technical Documentation

### Base Class: Utility_Panel

#### Constructor Parameters
```java
public Utility_Panel(
    Database_Manager database_manager,    // Data access layer
    User current_user,                   // Current user context
    String utility_type,                 // "electricity", "gas", "water"
    String panel_title,                  // Display title
    String panel_subtitle,               // Descriptive subtitle
    String reading_unit,                 // Measurement unit
    String tips_title,                   // Tips section title
    Color tips_title_color              // Theme color for tips
)
```

#### Key Fields

##### Database and User Management
- `Database_Manager database_manager`: Handles all database operations
- `User current_user`: Current user session context
- `String utility_type`: Identifies the specific utility ("electricity", "gas", "water")

##### UI Configuration
- `String panel_title`: Main panel heading
- `String panel_subtitle`: Descriptive text below title
- `String reading_unit`: Unit of measurement for readings
- `Color tips_title_color`: Theme color for the tips section

##### Year Navigation System
- `int current_graph_year`: Currently displayed year in graph
- `int[] years`: Array of years with available data
- `boolean hasNextYear`: Flag for forward navigation availability
- `boolean hasPreviousYear`: Flag for backward navigation availability

##### UI Components
The class maintains references to all major UI panels and components for dynamic updates and event handling.

### Core Methods

#### Data Management Methods

##### `setupData()`
**Purpose**: Initializes and refreshes all panel data
**Functionality**:
- Retrieves recent readings from database
- Updates current reading display
- Configures year navigation based on available data
- Refreshes graph display
- Handles error cases gracefully

**Algorithm**:
1. Query database for recent readings
2. Get latest reading for current display
3. Determine available years for navigation
4. Update navigation button visibility
5. Refresh graph component

##### `Panel_Refresh()`
**Purpose**: Public method to refresh panel data
**Usage**: Called by parent components when data changes

##### `Refresh_Graph()`
**Purpose**: Specifically refreshes the graph component
**Logic**: 
- Checks if year has changed
- Updates graph year if necessary
- Triggers graph data refresh

#### UI Initialization Methods

##### `initialize_UI(String tips_title)`
**Purpose**: Creates and configures all UI components
**Structure**:
1. **Header Section**: Title, subtitle, date/time display
2. **Graph Container**: Chart display with year navigation
3. **Current Reading**: Latest reading with trend indicator
4. **Tips Panel**: Conservation advice with auto-refresh
5. **Recent Readings**: Scrollable table of historical data

**Key Implementation Details**:
- Uses custom `Rounded_Panel` for modern appearance
- Implements `Timer` for real-time clock updates
- Sets up automatic tips rotation every 30 seconds
- Configures responsive layout with absolute positioning

##### `create_Actions_Listeners()`
**Purpose**: Establishes event handling for interactive components
**Event Handlers**:
- **Year Navigation**: Mouse listeners for previous/next buttons with hover effects
- **Add Reading**: Button click handler to open reading input dialog
- **Tips Refresh**: Click handler for manual tips update

**Mouse Event Handling Pattern**:
```java
addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) { /* Action logic */ }
    
    @Override
    public void mouseEntered(MouseEvent e) { /* Hover effect */ }
    
    @Override
    public void mouseExited(MouseEvent e) { /* Reset appearance */ }
});
```

#### Navigation Logic

##### Year Navigation System
**Purpose**: Allows users to browse historical data by year
**Implementation**:
- Maintains current year state
- Queries database for available data years
- Enables/disables navigation buttons based on data availability
- Updates graph display when year changes

**Navigation Constraints**:
- Only allows navigation within years that contain data
- Dynamically shows/hides navigation buttons
- Provides visual feedback for enabled/disabled states

### Derived Classes

#### Electricity_Panel
```java
public Electricity_Panel(Database_Manager database_manager, User current_user)
```
**Configuration**:
- Utility Type: "electricity"
- Title: "Electricity Consumption"
- Subtitle: "Track and manage your Energy usage"
- Unit: "kWh" (kilowatt-hours)
- Tips Title: "Electricity Saving Tips"
- Theme Color: `Color(255, 167, 0)` (yellow-orange)

#### Gas_Panel
```java
public Gas_Panel(Database_Manager database_manager, User current_user)
```
**Configuration**:
- Utility Type: "gas"
- Title: "Gas Consumption"
- Subtitle: "Track and manage your gas usage"
- Unit: "Qty" (quantity)
- Tips Title: "Gas Saving Tips"
- Theme Color: `Color(255, 77, 0)` (red-orange)

#### Water_Panel
```java
public Water_Panel(Database_Manager database_manager, User current_user)
```
**Configuration**:
- Utility Type: "water"
- Title: "Water Consumption"
- Subtitle: "Track and manage your water usage"
- Unit: "m³" (cubic meters)
- Tips Title: "Water Saving Tips"
- Theme Color: `Color(0, 119, 190)` (blue)

---

## Implementation Details

### Design Patterns Used

#### Template Method Pattern
The `Utility_Panel` base class defines the structure and behavior, while derived classes provide specific configurations through constructor parameters.

#### Observer Pattern
Components observe database changes and refresh automatically when data is updated.

#### Factory Pattern
Each utility panel acts as a specialized factory for its specific utility type configuration.

### UI Design Principles

#### Responsive Layout
- Fixed positioning with careful dimension management
- Consistent spacing and alignment across components
- Scalable font sizes and color schemes

#### User Experience Features
- **Real-time Updates**: Clock updates every minute
- **Visual Feedback**: Hover effects on interactive elements
- **Error Handling**: Graceful degradation when data is unavailable
- **Accessibility**: High contrast colors and readable fonts

#### Theme Integration
Each utility type has a distinct color theme:
- **Electricity**: Yellow-orange for energy/power association
- **Gas**: Red-orange for heat/flame association  
- **Water**: Blue for water association

### Performance Considerations

#### Timer Management
- Clock timer runs every minute with precise initial delay calculation
- Tips timer rotates content every 30 seconds
- Timers are properly managed to prevent memory leaks

#### Database Optimization
- Lazy loading of graph data
- Efficient queries for year navigation
- Caching of reading lists for performance

#### Memory Management
- Proper component disposal
- Event listener cleanup
- Efficient data structures for reading storage

---

## Usage Examples

### Creating a New Utility Panel
```java
// Create an electricity panel
Database_Manager dbManager = new Database_Manager();
User currentUser = getCurrentUser();
Electricity_Panel electricityPanel = new Electricity_Panel(dbManager, currentUser);

// Add to parent container
parentPanel.add(electricityPanel);
```

### Refreshing Panel Data
```java
// Refresh all panel data
utilityPanel.Panel_Refresh();

// Refresh only the graph
utilityPanel.Refresh_Graph();
```

### Integration with Main Application
```java
public class MainApplicationWindow extends JFrame {
    private Utility_Panel currentUtilityPanel;
    
    public void switchToElectricity() {
        if (currentUtilityPanel != null) {
            remove(currentUtilityPanel);
        }
        currentUtilityPanel = new Electricity_Panel(dbManager, currentUser);
        add(currentUtilityPanel);
        revalidate();
        repaint();
    }
}
```

### Handling Data Updates
```java
// After adding a new reading
Reading newReading = new Reading(/* parameters */);
dbManager.getReadingManager().addReading(newReading);

// Refresh the panel to show new data
utilityPanel.Panel_Refresh();
```

---

## Error Handling and Edge Cases

### Database Connection Issues
- Graceful degradation when database is unavailable
- Placeholder components when graph data cannot be loaded
- Error logging for debugging purposes

### Data Validation
- Null checking for reading values
- Handling of empty datasets
- Proper initialization of UI components even without data

### Year Navigation Edge Cases
- Handling single-year datasets
- Proper button state management
- Boundary checking for year navigation

This documentation provides both high-level understanding for users and detailed technical information for developers working with the utility panel system.