# Login System Documentation

## Table of Contents
1. [Overview](#overview)
2. [System Components](#system-components)
3. [User Documentation](#user-documentation)
4. [Technical Documentation](#technical-documentation)
5. [Database Integration](#database-integration)
6. [Security Considerations](#security-considerations)
7. [Error Handling](#error-handling)

---

## Overview

The Login System is a comprehensive authentication solution built using Java Swing that provides secure user access to the application. It implements a complete user authentication workflow including login, registration, password recovery, and password reset functionality.

### Key Features
- **User Authentication**: Secure login with username and password validation
- **User Registration**: New user account creation with validation
- **Password Recovery**: Forgot password functionality with email verification
- **Password Reset**: Secure password change mechanism
- **Input Validation**: Comprehensive form validation and error handling
- **Visual Feedback**: User-friendly error indicators and confirmation dialogs

### Importance
This system serves as the primary security gateway for the application, ensuring that only authenticated users can access protected features. It maintains data integrity and user privacy while providing a smooth user experience.

---

## System Components

### 1. Log_In_Window
**Purpose**: Primary authentication interface for existing users
**Location**: `src/view/login/Log_In_Window.java`
**Usage**: Entry point for user authentication

### 2. Sign_Up_Window  
**Purpose**: User registration interface for new accounts
**Location**: `src/view/login/Sign_Up_Window.java`
**Usage**: New user account creation

### 3. Retrieve_Window
**Purpose**: Password recovery initiation interface
**Location**: `src/view/login/Retrieve_Window.java`
**Usage**: Identity verification for password reset

### 4. New_Password_Window
**Purpose**: Password reset interface
**Location**: `src/view/login/New_Password_Window.java` 
**Usage**: Setting new password after identity verification

---

## User Documentation

### Login Process
1. Enter username and password in the login window
2. Click "Log In" button to authenticate
3. System validates credentials against database
4. On success: Navigate to main application
5. On failure: Display error indicators and optional "Forgot Password" link

### Registration Process
1. Click "Sign Up" link from login window
2. Fill in all required fields:
   - Username (unique identifier)
   - Email (valid email format required)
   - Password
   - Confirm Password
3. System validates all inputs and checks for duplicates
4. On success: Account created and navigate to main application
5. On failure: Display specific error messages

### Password Recovery Process
1. Click "Forgot Password?" link from login window
2. Enter username and email for identity verification
3. System confirms username/email match
4. Navigate to password reset window
5. Enter new password twice for confirmation
6. System updates password and returns to login

### Visual Indicators
- **Red asterisks (*)**: Indicate invalid or missing field data
- **Hover effects**: Interactive elements show visual feedback
- **Confirmation dialogs**: Prevent accidental actions

---

## Technical Documentation

### Architecture Overview
The login system follows a modular design pattern with clear separation of concerns:
- **View Layer**: Swing GUI components for user interaction
- **Database Layer**: Integration with Database_Manager for data persistence
- **Model Layer**: User object representation
- **Event Handling**: Mouse and focus listeners for user interactions

### Class Structure

#### Log_In_Window Class

**Constructor**
```java
public Log_In_Window(Database_Manager database_manager)
```
- Initializes window with database connection
- Sets up UI components and event listeners

**Key Methods**

```java
private void initialize_Window_Properties()
```
- Configures JFrame properties (size, position, close operation)
- Sets background color and layout

```java
private void initialize_UI_Components()
```
- Creates and positions all UI elements
- Implements rounded panels and buttons for modern appearance
- Sets up error indicator labels (initially hidden)

```java
private void create_Action_Listeners()
```
- Implements focus listeners for placeholder text management
- Adds mouse listeners for button interactions and hover effects
- Handles navigation between windows

```java
private void Login()
```
- **Input Validation**: Checks for empty fields and placeholder text
- **Authentication**: Verifies credentials via database manager
- **Error Display**: Shows visual error indicators on failure
- **Navigation**: Transitions to main application on success

**Event Handling Patterns**

*Focus Listeners (Username/Password Fields)*:
```java
tf_Username.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        // Clear placeholder text
    }
    @Override
    public void focusLost(FocusEvent e) {
        // Restore placeholder if empty
    }
});
```

*Mouse Listeners (Buttons)*:
```java
btn.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Execute primary action
    }
    @Override
    public void mouseEntered/Exited(MouseEvent e) {
        // Visual hover feedback
    }
});
```

#### Sign_Up_Window Class

**Validation Logic**
```java
private void SignUp()
```
- **Field Validation**: Ensures all fields contain valid data
- **Email Validation**: Uses regex pattern matching via database manager
- **Duplicate Check**: Verifies username/email uniqueness
- **Password Confirmation**: Ensures password fields match
- **Database Transaction**: Creates new user record
- **Navigation**: Proceeds to main application on success

**Error Indicator Management**
- Four separate error labels (lbl_Incorrect_Signage1-4) for specific field errors
- Dynamic visibility control based on validation results
- Color-coded feedback (red asterisks)

#### Retrieve_Window Class

**Identity Verification Process**
```java
private void confirmation()
```
- **Input Validation**: Ensures username and email are provided
- **Database Verification**: Confirms username/email pair exists
- **User Retrieval**: Fetches User object for password reset
- **Secure Navigation**: Passes verified user to password reset window

**Cancel Confirmation Logic**
- Detects unsaved changes in form fields
- Prompts user confirmation before discarding data
- Provides safe exit mechanism

#### New_Password_Window Class

**Password Update Process**
```java
private void changePassword()
```
- **Input Validation**: Ensures both password fields are completed
- **Password Matching**: Verifies new password confirmation
- **Database Update**: Updates user password via database manager
- **Secure Return**: Navigates back to login window

**Security Features**
- Password masking with bullet characters
- Confirmation requirement for password changes
- Secure password field handling

### Database Integration

**Required Database Manager Methods**
```java
// User authentication
boolean UsernamePasswordMatch(String username, String password)

// User retrieval
User getUserByUsername(String username)
User getUserByEmail(String email)

// User management  
void addUser(String username, String password, String email)
void updateUserPassword(User user, String password)

// Validation utilities
boolean validEmail(String email)
boolean checkUserEmail(String username, String email)
boolean UsernameEmailMatch(String username, String email)
```

### UI Component Details

**Custom Components**
- `Rounded_Panel`: Custom JPanel with rounded corners for modern appearance
- `Rounded_Button`: Custom JButton with rounded corners and hover effects

**Layout Management**
- Absolute positioning (setBounds) for precise control
- Consistent spacing and alignment across all windows
- Responsive visual feedback for user interactions

**Color Scheme**
- Background: Light gray (213, 213, 213)
- Panels: White backgrounds
- Buttons: Gray with darker hover states (182, 182, 182 → 150, 150, 150)
- Error indicators: Red (#FF0000)

### Event Flow Architecture

**Navigation Pattern**
1. **Window Disposal**: Current window is disposed before opening new window
2. **EventQueue Usage**: All window transitions use `EventQueue.invokeLater()` for thread safety
3. **Exception Handling**: Comprehensive try-catch blocks around database operations
4. **State Management**: User state passed between windows when required

**Thread Safety**
All GUI updates and window transitions are performed on the Event Dispatch Thread using `EventQueue.invokeLater()` to prevent threading issues.

### Error Handling Strategy

**Input Validation Levels**
1. **Client-side validation**: Immediate feedback for empty/invalid fields
2. **Format validation**: Email format and field content validation  
3. **Database validation**: Duplicate checking and constraint validation
4. **Visual feedback**: Error indicators and dialog messages

**Exception Management**
- SQL exceptions caught and logged
- User-friendly error messages displayed via console output
- Visual error indicators provide immediate feedback
- Graceful degradation on database connection issues

### Security Considerations

**Password Handling**
- Passwords retrieved as char arrays from JPasswordField
- Immediate conversion to String for database operations
- Echo character masking during input

**Input Sanitization**
- Placeholder text validation prevents accidental submission
- Empty field detection at multiple validation levels
- Special character handling in database queries

**Session Management**
- User object passed securely between authentication windows
- Database manager maintains connection state
- Proper window disposal prevents memory leaks

### Performance Optimizations

**Resource Management**
- Proper window disposal to prevent memory leaks
- Efficient event listener implementation
- Minimal database query overhead

**User Experience**
- Immediate visual feedback for user actions
- Smooth window transitions
- Responsive button hover effects
- Intuitive navigation flow

---

## Database Integration

The system requires a `Database_Manager` class with a `getUserManager()` method that provides the following functionality:

- User authentication and validation
- User creation and management
- Password updates and recovery
- Email format validation
- Duplicate user detection

---

## Security Considerations

### Password Security
- Passwords are handled as character arrays when possible
- Database queries should use prepared statements to prevent SQL injection
- Consider implementing password hashing in the database layer

### Input Validation
- All user inputs are validated both client-side and server-side
- Email format validation prevents malformed addresses
- Username and email uniqueness enforced at database level

### Session Management
- User sessions should be properly managed after authentication
- Consider implementing session timeouts for enhanced security
- Proper cleanup of sensitive data in memory

---

## Error Handling

### User-Facing Errors
- Clear, actionable error messages
- Visual indicators for problematic fields
- Confirmation dialogs for destructive actions

### Technical Error Handling
- Comprehensive exception handling around database operations
- Graceful degradation when database is unavailable
- Proper logging of system errors for debugging

### Validation Errors
- Real-time field validation
- Comprehensive form validation before submission
- Specific error messages for different validation failures

---

## Maintenance and Extension

### Adding New Validation Rules
1. Implement validation logic in the database manager
2. Add visual error indicators to the UI
3. Update validation methods in window classes
4. Test all error paths thoroughly

### Modifying UI Elements
1. Update component initialization methods
2. Adjust layout positioning if needed
3. Update event listeners for new components
4. Maintain consistent visual styling

### Database Schema Changes
1. Update database manager interface methods
2. Modify User model class if needed
3. Update all window classes that interact with changed data
4. Test all authentication flows thoroughly

This documentation provides a comprehensive guide for both users and developers working with the login system. Regular updates to this documentation should accompany any system modifications.