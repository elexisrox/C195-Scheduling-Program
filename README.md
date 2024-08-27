# C195 Appointment Scheduling Application
> Purpose: Provide the ability to add, modify, and delete customers and appointments, allowing access to an existing SQL database.

## Received the WGU Award of Excellence, June 2024
- Author: Elexis Rox
- Application Version 1.0
- Date: 5/28/2024

IDE: IntelliJ IDEA 2023.2.2 (Community Edition) x64
JDK version: Java 17.0.1
JavaFX version: JAVAFX.SDK.17.0.6

## How to run:
1. Run MainApplication.java. Login through the application with the following credentials (case-sensitive):

- Username: test
- Password: test

2. Click login. If credentials are correct, the Main Application View will load, and an alert will pop up indicating if there are any upcoming appointments in the next 15 minutes.

3. The main navigation consists of the 3 radio buttons at the top.

- "Appointments" allows access to view, add, update, or delete appointments. The three tabs at the top of Appointments allows the user to filter appointments to view all, the current week (Monday-Sunday), or the current entire month.

- "Customers" allows access to view, add, update, or delete customers.

- "Reports" has three tabs to view different kinds of reports compiled by the program.

    - "Contact Schedules" populates the dropdown box on the left with all contact names. Selecting a contact name populates the tableview with all of their appointments.

    - "Appointments by Month/Type" compiles a list of all appointment types, which month they occur in, and how many times they occur.

    - "Customers by Country" is my additional report choice for project requirement A3f. Selecting a country in the dropdown box on the left populates the table view with all customers that live in that country.

4. Pressing "Logout" will bring the user back to the Login screen. Pressing "Exit" will prompt the user with a confirmation to close the program.

MySQL Connector Driver Version: mysql-connector-java-8.0.25
