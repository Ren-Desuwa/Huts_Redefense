# Home Panel Documentation

## Overview

The `Home_Panel` class serves as the main dashboard interface for a utility expense tracking application. It provides users with a comprehensive view of their electricity, water, and gas consumption data, along with real-time insights and helpful tips for managing utility expenses.

## Purpose and Importance

The Home Panel is the central hub of the application where users can:

- **Monitor Current Usage**: View the latest readings for electricity, water, and gas consumption
- **Track Spending**: See overall expenses across all utilities
- **Analyze Trends**: Understand consumption patterns through visual indicators and graphs
- **Get Insights**: Receive automated tips for reducing utility costs
- **Navigate Data**: Interact with panels to view detailed graphs for specific utilities

This panel is crucial for providing users with immediate visibility into their utility consumption patterns, enabling them to make informed decisions about their energy and water usage.

## Where It's Used

The Home Panel is typically:
- The first screen users see after logging into the application
- Accessible from the main navigation menu
- Used as a launching point for detailed utility analysis
- Referenced for quick consumption overviews

---

# Technical Documentation

## Class Structure

### Package and Imports
```java
package view.panel;
```

**Key Dependencies:**
- `javax.swing.*` - GUI components
- `database.Database_Manager` - Data access layer
- `model.Reading`, `model.User` - Data models
- `visuals.*` - Custom UI components

### Class Declaration
```java
public class Home_Panel extends JPanel
```

## Field Documentation

### Core System Fields

| Field | Type | Purpose |
|-------|------|---------|
| `database_Manager` | `Database_Manager` | Handles all database operations and data retrieval |
| `utility_Tips_Manager` | `Utility_Tips_Manager` | Singleton instance managing utility tips |
| `current_User` | `User` | Current logged-in user context |

### UI Container Fields

| Field | Type | Description |
|-------|------|-------------|
| `panel_Welcome_Title` | `JPanel` | Header section with welcome message |
| `panel_Information` | `JPanel` | Container for utility information panels |
| `panel_Tips` | `JPanel` | Bottom section displaying utility tips |
| `panel_Graph_Container` | `JPanel` | Container for the main graph visualization |

### Utility Information Panels

| Field | Type | Utility Type |
|-------|------|--------------|
| `panel_Electricity_Info` | `JPanel` | Electricity consumption data |
| `panel_Water_Info` | `JPanel` | Water consumption data |
| `panel_Gas_Info` | `JPanel` | Gas consumption data |
| `panel_Overall_Info` | `JPanel` | Combined utility expenses |

### Data Display Labels

#### Reading Values
- `lbl_Electricity_Reading_Value` - Current electricity reading
- `lbl_Water_Reading_Value` - Current water reading  
- `lbl_Gas_Reading_Value` - Current gas reading
- `lbl_OverAll_Reading_Value` - Total combined expenses

#### Trend Indicators
- `lbl_Trend_Of_Reading_Electricity` - Electricity usage trend
- `lbl_Trend_Of_Reading_Water` - Water usage trend
- `lbl_Trend_Of_Reading_Gas` - Gas usage trend
- `lbl_Trend_Of_Reading_Overall` - Overall expense trend

#### Units and Headers
- Unit labels for each utility type (KwH, m³, Qty, Php)
- Title labels for each information panel

### Graph Components

| Field | Type | Purpose |
|-------|------|---------|
| `graph_Panel` | `Graph_Panel` | Main visualization component |
| `panel_Behind1/2/3` | `Rounded_Panel` | Layered background effect panels |

### Tips System

| Field | Type | Purpose |
|-------|------|---------|
| `panel_Tip_1/2/3` | `Rounded_Panel` | Individual tip display containers |
| `lbl_Tip_1/2/3` | `JLabel` | Tip content labels |
| `lbl_Tip_Type_1/2/3` | `JLabel` | Tip category labels |

## Constructor

```java
public Home_Panel(Database_Manager database_Manager, User current_User)
```

**Parameters:**
- `database_Manager` - Database access interface
- `current_User` - Current user session

**Initialization Sequence:**
1. Sets panel properties (background, size, layout)
2. Calls `initialize_UI()` to build interface
3. Calls `create_Action_Listeners()` to set up interactions
4. Calls `setup_Data()` to populate initial data

## Method Documentation

### UI Initialization

#### `initialize_UI()`
**Purpose:** Constructs the entire user interface layout

**Implementation Details:**
- Uses absolute positioning (`setLayout(null)`)
- Creates rounded panels with custom `Rounded_Panel` class
- Implements layered background effect for graph section
- Sets up two timer systems for real-time updates

**UI Sections Created:**
1. **Header Section** - Welcome message, username, date/time
2. **Information Panels** - Four utility data display panels  
3. **Graph Section** - Layered visualization container
4. **Tips Section** - Three rotating tip panels

#### Timer Systems

**Clock Timer:**
```java
Timer clock_timer = new Timer(60_000, e -> {
    lbl_Time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
});
```
- Updates every minute
- Calculates initial delay to sync with system clock

**Tips Timer:**
```java
Timer tips_timer = new Timer(30_000, e -> {
    // Updates all three tip panels with random tips
});
```
- Refreshes tips every 30 seconds
- Immediately loads initial tips (`setInitialDelay(0)`)

### Event Handling

#### `create_Action_Listeners()`
**Purpose:** Sets up mouse interactions for utility panels

**Implementation:**
- Uses `createPanelMouseAdapter()` helper method
- Implements hover effects (background color changes)
- Triggers specific graph views on panel clicks

#### `createPanelMouseAdapter(Runnable onClick)`
**Purpose:** Factory method for consistent panel interactions

**Mouse Events:**
- `mouseClicked` - Executes provided action
- `mouseEntered` - Changes background to hover color (200, 200, 200)
- `mouseExited` - Restores default background (220, 220, 220)

### Data Management

#### `setup_Data()`
**Purpose:** Populates UI with current utility data

**Process Flow:**
1. **Graph Panel Initialization**
   - Creates graph panel if null and database available
   - Adds to container and refreshes display

2. **Data Retrieval**
   ```java
   Reading electricity = database_Manager.getReadingManager().getLatest_Reading_By_Type(current_User, "electricity");
   ```
   - Fetches latest readings for each utility type

3. **Label Updates**
   - Updates reading values and trend indicators
   - Uses `updateReading_Label()` method from reading manager

4. **Overall Calculation**
   ```java
   double total = 0;
   if (electricity != null) total += electricity.getTotal_Price();
   // ... sum all available readings
   ```

5. **Graph Refresh**
   - Calls `graph_Panel.refreshData()` to update visualizations

#### `home_Panel_Refresh()`
**Purpose:** Public method to refresh panel data
**Implementation:** Simple wrapper calling `setup_Data()`

## Design Patterns Used

### Singleton Pattern
- `Utility_Tips_Manager.getInstance()` ensures single tips manager instance

### Factory Pattern
- `createPanelMouseAdapter()` creates consistent mouse listeners

### Observer Pattern
- Timer-based updates for real-time data refresh

## Color Scheme

| Element | Color Code | Usage |
|---------|------------|-------|
| Main Background | `(213, 213, 213)` | Panel background |
| White Panels | `(255, 255, 255)` | Content containers |
| Info Panels | `(220, 220, 220)` | Utility information background |
| Hover State | `(200, 200, 200)` | Panel hover effect |
| Tips Background | `(235, 235, 235)` | Tip panel background |

## Error Handling

The class implements basic error handling in `setup_Data()`:
```java
try {
    // Data setup operations
} catch (Exception e) {
    e.printStackTrace();
}
```

## Dependencies

### External Classes
- `Database_Manager` - Data access layer
- `User`, `Reading` - Data models  
- `Graph_Panel` - Visualization component
- `Rounded_Panel` - Custom UI component
- `Utility_Tips_Manager` - Tips management system

### Java Libraries
- Swing components for UI
- Time API for date/time handling
- AWT for colors and fonts

## Performance Considerations

- **Timer Optimization**: Clock timer uses calculated initial delay for precise minute synchronization
- **Lazy Loading**: Graph panel created only when database is available
- **Memory Management**: No explicit cleanup implemented for timers
- **UI Updates**: Minimal repainting through targeted label updates

## Future Enhancement Opportunities

1. **Responsive Design**: Convert from absolute to flexible layouts
2. **Error Recovery**: Add user-friendly error messages
3. **Data Caching**: Implement local data caching for performance
4. **Animation**: Add smooth transitions for data updates
5. **Accessibility**: Improve keyboard navigation and screen reader support