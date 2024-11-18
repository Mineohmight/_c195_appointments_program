# C195 Appointment Program

## Purpose
To develop a GUI-based desktop scheduling application for a global consulting organization.

## Author
**Riley O'Donnell**

- **Email**: rodon15@wgu.edu

## Version
**1.0.0**

## How to Run the Program

This program was created using **NetBeans**. To run it in NetBeans, follow these steps:

1. **Create a New NetBeans Project**:
   - Open NetBeans and create a new project.

2. **Set Up Packages**:
   - Under **"Source Packages"**, create the following:
     - `controller`
     - `database`
     - `main`
   - Under **"Other Sources"**, create:
     - `main`

3. **Import Files**:
   - Import the files into their respective packages, referring to the folder structure of the project.

4. **Place `.fxml` Files**:
   - Move `.fxml` files to **"Other Sources" > "main"**.

5. **Place `.properties` Files**:
   - Move `.properties` files to **"Other Sources" > "<default package>"**.

6. **Set Up `module-info.java`**:
   - Place the `module-info.java` file under **"Source Packages" > "<default package>"**.

7. **Replace the `pom.xml`**:
   - Replace the default `pom.xml` in the project with the `pom.xml` from this project.

---

## Additional Report
The application includes a **User Report**, which displays the appointments associated with a specific user (either user "test" or user "admin").

---

## Driver Version
**MySQL Connector Driver**: `mysql-connector-java-8.0.26`  
**Revision**: `9aae1e450989d62c06616c1dcda3e404ef84df70`