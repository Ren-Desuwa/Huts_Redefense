# Login System Documentation

## Overview
This documentation covers the four main classes that make up the login and authentication system of the Java Swing application. These classes handle user authentication, registration, password recovery, and password changes.

---

## 1. Log_In_Window.java

### Purpose
The main login interface that allows existing users to authenticate and access the application.

### Key Features
- User authentication via username and password
- Input validation with visual error indicators (red asterisks)
- Placeholder text that disappears when fields are focused
- Password masking for security
- Navigation to sign-up and password recovery windows
- Hover effects on interactive elements

### Core Functionality
- **Authentication**: Validates user credentials against the database
- **Error Handling**: Shows visual indicators for invalid inputs or failed login attempts
- **Navigation**: Provides links to registration and password recovery
- **Security**: Implements password masking and secure credential handling

### Database Integration
- Uses `Database_Manager` to verify username/password combinations
- Retrieves user objects for successful logins
- Handles SQL exceptions gracefully

### UI Components
- Username and password input fields with placeholders
- Login button with hover effects
- "Forgot Password?" link (appears after failed login)
- "Sign Up" link for new users
- Error indicators (red asterisks) for invalid fields

### Why It's Important
- **Security Gateway**: First line of defense for application access
- **User Experience**: Provides intuitive login process with clear feedback
- **System Entry Point**: Controls access to the main application features

### Where It's Used
- Application startup (primary entry point)
- After user logout
- After password changes
- When returning from registration or password recovery

---

## 2. Sign_Up_Window.java

### Purpose
Handles new user registration with comprehensive validation and immediate account creation.

### Key Features
- Multi-field registration form (username, email, password, confirm password)
- Real-time input validation with visual feedback
- Email format validation
- Duplicate user/email checking
- Password confirmation matching
- Automatic login after successful registration

### Core Functionality
- **Registration**: Creates new user accounts in the database
- **Validation**: Ensures data integrity with multiple validation checks
- **Security**: Implements password confirmation and secure storage
- **User Experience**: Provides immediate feedback on registration issues

### Validation Rules
- All fields must be filled
- Email must be in valid format
- Username and email must be unique
- Passwords must match
- No placeholder text accepted as input

### Database Integration
- Adds new users to the database
- Checks for existing usernames and emails
- Validates email format
- Retrieves newly created user for immediate login

### UI Components
- Four input fields (username, email, password, confirm password)
- Sign-up button with hover effects
- "Log in" link to return to login
- Individual error indicators for each field
- Success/error message dialogs

### Why It's Important
- **User Onboarding**: Enables new users to join the system
- **Data Integrity**: Ensures valid user data through comprehensive validation
- **Security**: Implements proper password handling and duplicate prevention
- **Seamless Experience**: Automatically logs in users after registration

### Where It's Used
- Accessed from login window "Sign Up" link
- New user acquisition flow
- Account creation process

---

## 3. Retrieve_Window.java

### Purpose
Handles password recovery by verifying user identity through username and email matching.

### Key Features
- Two-step verification (username + email)
- Identity confirmation before password reset
- Clean, focused interface for recovery process
- Cancel option with confirmation dialog

### Core Functionality
- **Identity Verification**: Confirms user ownership through username/email match
- **Security**: Prevents unauthorized password resets
- **User Guidance**: Clear process for password recovery
- **Data Protection**: Validates user identity before allowing password changes

### Verification Process
1. User enters username and email
2. System verifies the combination exists in database
3. If valid, proceeds to password reset window
4. If invalid, shows error message

### Database Integration
- Verifies username/email combinations
- Retrieves user object for password reset process
- Handles database exceptions gracefully

### UI Components
- Username and email input fields with placeholders
- Confirm button with hover effects
- Cancel button with confirmation dialog
- Focus listeners for better user experience

### Why It's Important
- **Account Recovery**: Helps users regain access to their accounts
- **Security**: Implements proper identity verification before password reset
- **User Retention**: Prevents account abandonment due to forgotten passwords
- **Trust**: Provides secure method for account recovery

### Where It's Used
- Accessed from login window "Forgot Password?" link
- Password recovery workflow
- Account access restoration process

---

## 4. New_Password_Window.java

### Purpose
Allows users to set a new password after successful identity verification during password recovery.

### Key Features
- Secure password entry with confirmation
- Password matching validation
- Direct database password update
- Cancel option with unsaved changes warning

### Core Functionality
- **Password Reset**: Updates user password in database
- **Validation**: Ensures password confirmation matches
- **Security**: Implements secure password handling and storage
- **User Control**: Allows cancellation with appropriate warnings

### Security Features
- Password masking during input
- Confirmation field to prevent typos
- Secure password storage in database
- Automatic return to login after successful change

### Database Integration
- Updates user password in database
- Uses secure password storage methods
- Handles update exceptions appropriately

### UI Components
- Two password fields (new password, confirm password)
- Change Password button with hover effects
- Cancel button with confirmation dialog
- Focus listeners for placeholder text management

### Why It's Important
- **Account Security**: Enables users to set new secure passwords
- **Recovery Completion**: Final step in password recovery process
- **User Control**: Provides secure method to update passwords
- **System Security**: Maintains password security standards

### Where It's Used
- Final step of password recovery process
- Accessed after successful identity verification
- Password reset completion workflow

---

## System Integration

### Flow Relationships
```
Log_In_Window ←→ Sign_Up_Window
     ↓
Retrieve_Window → New_Password_Window → Log_In_Window
     ↓
Main_Frame (Application)
```

### Database Dependencies
All classes depend on:
- `Database_Manager`: Central database access
- `User` model: User data representation
- User management methods for authentication and updates

### Common Design Patterns
- **Consistent UI**: All windows share similar design elements
- **Error Handling**: Visual feedback through red asterisks and dialogs
- **Focus Management**: Placeholder text handling across all input fields
- **Navigation**: Proper window disposal and new window creation
- **Thread Safety**: UI updates through EventQueue.invokeLater()

### Security Considerations
- Password masking in all password fields
- Input validation to prevent injection attacks
- Secure database credential verification
- Proper session management through user objects

---

## Maintenance Notes

### Key Areas for Updates
- Password strength requirements
- Email validation rules
- Error message customization
- UI theme consistency
- Database connection handling

### Testing Considerations
- Input validation edge cases
- Database connection failures
- User experience flow testing
- Security vulnerability assessment
- Cross-platform UI compatibility