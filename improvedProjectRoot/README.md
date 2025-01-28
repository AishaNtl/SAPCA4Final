# secureAppCA4
 Secure App Programming CA4

Setup Instructions

Prerequisites:
1. Ensure JDK 8 or later is installed, installation may be verified by running;
java -version

2. The project includes SQLite JDBC driver in the /lib directory. If you need to replace it this link directly mirrors the one found on the SQLite JDBC GitHub and proceed to save it in the /lib directory.
https://sourceforge.net/projects/sqlite-jdbc-driver.mirror/

Steps to Project Set Up
1. Clone or Download Repository
- Clone the repository:
git clone <ADD REPO LINK>
- Navigate to the project directory:
cd projectRoot

2. Set Up the SQLite Database

1. Compile and run DatabaseSetup.java to create the database and initialize the users table:
cd src
javac -cp .;../lib/sqlite-jdbc-3.48.0.0.jar DatabaseSetup.java
java -cp .;../lib/sqlite-jdbc-3.48.0.0.jar DatabaseSetup
(make sure that the version number matches with your own or else this will not work)
2. Verif the database.db file has been created in the /data directory

Running the Application: Insecure Version
1. Compile the InsecureApp.java file:
javac -cp .;../lib/sqlite-jdbc-3.48.0.0.jar InsecureApp.java
2. Run the InsecureApp:
java -cp .;../lib/sqlite-jdbc-3.42.0.0.jar InsecureApp

Running the Application: Secure Version
1. Compile the SecureApp.java file:
javac -cp .;../lib/sqlite-jdbc-3.42.0.0.jar SecureApp.java
2. Run the SecureApp:
java -cp .;../lib/sqlite-jdbc-3.42.0.0.jar SecureApp

Testing instructions: Insecure Version

Test the insecure application to identify vulnerabilities like:
1. SQL Injection:
- Input: admin' -- as the username and leave the password blank.
- Outcome: Bypasses authentication and logs in as admin.
2. Sensitive Data Exposure:
- Inspect the database to see plaintext passwords.

Testing Instructions: Secure Version

Verify that vulnerabiities have been mitigated in the secure verson:
1. SqL Injection Prevention:
- the app uses prepared statments. so inputs like admin' -- fail.
2. Secure password Storage:
- Passwords are hashed using password_hash.

Expected Output

InsecureApp
Input:
Enter username: admin
Enter password: password
Output:
Login successful. Welcome, admin!

SecureApp
Input w/ incorrect credentials:
Enter username: admin
Enter password: wrongpassword
Output:
Invalid username or password.

Troubleshooting
1. Error: No suitable driver found for jdbc:sqlite:
Ensure the SQLite JDBC driver (sqlite-jdbc-3.48.0.0.jar) is in the /lib directory and included in the classpath during compilation and execution. (I ran into this one alot make sure the crosscheck the sqlite version)

2. Database Not Found:
Ensure the database path in the Java files points to ../data/database.db.

3. Permissions Error:
Ensure the /data directory has write permissions for the application to create and access the database.


Notes:

Vulnerabilities in InsecureApp:
1. SQL Injection:
- User input is directly concatentaed into the query string.
- Exploit: admin' -- bypasses authentication.
2. Sensitive Data Exposure:
- Passwords are stred in plaintext in the database.

Mitigations in SecureApp;
1. SQL Injection Mitigation:
License
This project is for educational purposes and is licensed under the MIT license :) .

