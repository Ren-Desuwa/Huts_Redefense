# Utility Management System Documentation

## Table of Contents
1. [System Overview](#system-overview)
2. [Architecture Overview](#architecture-overview)
3. [User Documentation](#user-documentation)
4. [Technical Documentation](#technical-documentation)

---

## System Overview

### What is the Utility Management System?

The Utility Management System is a Java Swing-based desktop application designed to help users track and manage their utility consumption and costs. The system allows users to record, monitor, and analyze their electricity, water, and gas usage over time.

### Key Features

- **User Authentication**: Secure login and registration system
- **Multi-Utility Tracking**: Support for electricity, water, gas, and other utility types
- **Data Persistence**: SQLite database for reliable data storage
- **User-Friendly Interface**: Intuitive GUI with navigation panels
- **Profile Management**: Users can manage their account information
- **Reading Management**: Add, view, and track utility readings with rates and costs

### Why is it Important?

1. **Cost Management**: Helps users monitor utility expenses and identify usage patterns
2. **Environmental Awareness**: Enables tracking of resource consumption for sustainability goals
3. **Budget Planning**: Provides historical data for better financial planning
4. **Usage Analytics**: Allows users to analyze consumption trends over time

### Where is it Used?

- **Residential**: Homeowners tracking household utility consumption
- **Small Businesses**: Small office or retail spaces monitoring utility costs
- **Property Management**: Landlords tracking utility usage across properties
- **Personal Finance**: Individuals managing monthly utility budgets

---

## Architecture Overview

### System Architecture

The application follows a **Model-View-Controller (MVC)** pattern with the following layers:

```
┌─────────────────┐
│   Presentation  │  ← Swing GUI Components (View Layer)
│     Layer       │
├─────────────────┤
│   Business      │  ← Application Logic & Controllers
│     Layer       │
├─────────────────┤
│   Data Access   │  ← Database Managers (DAO Pattern)
│     Layer       │
├─────────────────┤
│   Data Layer    │  ← SQLite Database
└─────────────────┘
```

### Design Patterns Used

1. **Singleton Pattern**: `Database_Manager` ensures single database connection
2. **Data Access Object (DAO)**: `User_Manager` and `Reading_Manager` handle data operations
3. **Model-View-Controller (MVC)**: Separation of concerns across layers
4. **Factory Pattern**: Manager initialization in `Database_Manager`

---

## User Documentation

### Getting Started

1. **Launch Application**: Run the application through `Launcher.java`
2. **Login/Register**: Create a new account or login with existing credentials
3. **Navigate**: Use the sidebar to access different utility panels
4. **Add Readings**: Input your utility meter readings with dates and rates
5. **View Data**: Monitor your consumption patterns and costs

### Main Features

#### Authentication
- **Registration**: Create account with username, email, and password
- **Login**: Secure authentication with credential validation
- **Profile Management**: Update account information

#### Utility Tracking
- **Electricity Panel**: Track electrical consumption and costs
- **Water Panel**: Monitor water usage and billing
- **Gas Panel**: Record gas consumption data
- **Home Dashboard**: Overview of all utility data

#### Data Management
- **Add Readings**: Input meter readings with automatic cost calculation
- **View History**: Access historical consumption data
- **Update Information**: Modify existing readings and user data

---

## Technical Documentation

### Core Components

#### 1. Database_Manager Class

**Purpose**: Centralized database connection management using Singleton pattern

**Key Responsibilities**:
- Manage SQLite database connection
- Initialize specialized data managers
- Enable foreign key constraints
- Provide connection cleanup

```java
// Key Methods
public static Database_Manager getInstance()  // Singleton access
private void initializeManagers()            // Initialize DAO managers
public Connection getConnection()             // Provide database connection
public void closeConnection()                // Resource cleanup
```

**Technical Details**:
- **Database URL**: `jdbc:sqlite:database/Data.db`
- **Driver**: SQLite JDBC driver (`org.sqlite.JDBC`)
- **Foreign Keys**: Enabled with `PRAGMA foreign_keys = ON`
- **Connection Management**: Single connection shared across managers

#### 2. User_Manager Class

**Purpose**: Data Access Object for user-related database operations

**Key Responsibilities**:
- User CRUD operations (Create, Read, Update, Delete)
- Authentication and validation
- Current user session management
- Email validation with regex patterns

```java
// CRUD Operations
public void addUser(String username, String password, String email)
public void deleteUser(int userId) 
public void updateUser(User user, String username, String password, String email)
public User getUserById(int userId)
public User getUserByUsername(String username)
public User getUserByEmail(String email)

// Authentication & Validation
public boolean UsernamePasswordMatch(String username, String password)
public boolean UsernameEmailMatch(String username, String email)
public boolean checkUserEmail(String username, String email)
public boolean validEmail(String email)

// Session Management
public void setCurrentUser(User user)
public User getCurrentUser()
public void setCurrentUserNull()
```

**Technical Details**:
- **SQL Collation**: 
  - Username: `COLLATE BINARY` (case-sensitive)
  - Email: `COLLATE NOCASE` (case-insensitive)
- **Prepared Statements**: Used throughout for SQL injection prevention
- **Email Regex**: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$`
- **Exception Handling**: SQLException caught and logged

#### 3. Model Classes

##### User Model
```java
// Fields
private int user_id;
private String username;
private String password;
private String email;

// Constructors
User()                                              // Default constructor
User(String username, String password, String email) // Without ID
User(int id, String username, String password, String email) // With ID
```

##### Reading Model
```java
// Fields
private int reading_id;
private int user_id;           // Foreign key to users table
private LocalDate date;        // Reading date
private String type;           // "electricity", "water", "gas", "other"
private double reading;        // Meter reading value
private double rate;           // Cost per unit
private double total_price;    // Calculated total cost

// Constructors
Reading()  // Default
Reading(int user_id, LocalDate date, String type, double reading, double rate, double total_price)
Reading(int user_id, LocalDate date, String type, double reading, double total_price)
Reading(int reading_id, int user_id, LocalDate date, String type, double reading, double rate, double total_price)
```

#### 4. Main_Frame Class

**Purpose**: Primary application window with navigation and panel management

**Key Responsibilities**:
- Card layout management for different utility panels
- Sidebar navigation with styled buttons
- User session management
- UI component initialization and styling

```java
// Panel Management
private void createPanels()          // Initialize all content panels
private CardLayout card_layout       // Switch between panels
private JPanel card_panel           // Container for switchable content

// Navigation Methods
public void showElectricityPanel()
public void showWaterPanel()
public void showGasPanel()
public void showHomePanel()
public void showProfilePanel()

// UI Components
private JButton side_home_button;
private JButton side_electricity_button;
private JButton side_water_button;
private JButton side_gas_button;
private JButton side_profile_button;
private JButton side_logout_button;
```

**Technical Details**:
- **Layout**: `BorderLayout` with sidebar and main content area
- **Sidebar**: `BoxLayout` with vertical arrangement
- **Button Styling**: Custom `Rounded_Button` with hover effects
- **Colors**: Consistent color scheme (RGB: 213,213,213 background, 70,70,70 buttons)
- **Dimensions**: Fixed window size 1200x725 pixels

#### 5. Launcher Class

**Purpose**: Application entry point and initialization

**Technical Details**:
- **Thread Management**: Uses `EventQueue.invokeLater()` for EDT compliance
- **Database Initialization**: Creates singleton Database_Manager instance
- **Initial Window**: Launches `Log_In_Window` as first user interface

### Database Schema

#### Users Table
```sql
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);
```

#### Readings Table (Inferred)
```sql
CREATE TABLE readings (
    reading_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    date DATE NOT NULL,
    type TEXT NOT NULL,
    reading REAL NOT NULL,
    rate REAL,
    total_price REAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

### Security Considerations

#### Implemented Security Features:
1. **Prepared Statements**: Prevents SQL injection attacks
2. **Input Validation**: Email format validation with regex
3. **Case-Sensitive Authentication**: Username matching with binary collation
4. **Foreign Key Constraints**: Maintains referential integrity

#### Security Limitations:
1. **Plain Text Passwords**: Passwords stored without hashing
2. **No Session Timeout**: User sessions persist indefinitely
3. **No Encryption**: Database file not encrypted
4. **No Rate Limiting**: No protection against brute force attacks

### Performance Characteristics

#### Strengths:
- **Single Connection**: Efficient resource usage with connection reuse
- **Prepared Statements**: Query compilation optimization
- **Singleton Pattern**: Reduced object creation overhead

#### Potential Bottlenecks:
- **Single-threaded Database Access**: No connection pooling
- **In-Memory Session**: Large user bases could impact memory
- **Swing EDT**: All UI operations on single thread

### Error Handling Strategy

#### Database Errors:
- **SQLException**: Caught and logged with stack traces
- **ClassNotFoundException**: Handled during JDBC driver loading
- **Connection Failures**: Graceful degradation with null checks

#### UI Errors:
- **User Feedback**: Confirmation dialogs for critical actions
- **Input Validation**: Email format checking before database operations
- **Duplicate Prevention**: Username/email uniqueness validation

### Extension Points

#### Adding New Utility Types:
1. Update `Reading` model type validation
2. Create new panel class extending base panel
3. Add navigation button to `Main_Frame`
4. Register panel in card layout

#### Enhanced Authentication:
1. Implement password hashing in `User_Manager`
2. Add session timeout mechanism
3. Implement password complexity requirements
4. Add two-factor authentication support

#### Reporting Features:
1. Create report generation classes
2. Add export functionality to panels
3. Implement data visualization components
4. Add date range filtering capabilities