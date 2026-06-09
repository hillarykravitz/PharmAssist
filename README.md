# Secure Pharmacy Prescription Management System

A robust, full-scale enterprise desktop application built with Java and SQL. This system simulates a real-world pharmacy workflow, managing patient records, prescription tracking, and medication inventories with a focus on security, role-based access control, and data auditing.

## 🚀 Key Features

* **Role-Based Access Control (RBAC):** Dynamically alters the interface based on user permissions. Supports two user roles:
* **Pharmacist Interface:** Full administrative capabilities, including writing, verifying, and modifying prescriptions.
* **Technician Interface:** Streamlined workflow optimized for customer pick-up, search, and processing transactions.
* **BCrypt Password Security:** Implements robust user authentication by hashing and encrypting passwords using BCrypt to prevent unauthorized credential access.
* **Automated Data Auditing:** Generates explicit, real-time access log rows in the database whenever an employee views or edits sensitive patient data, maintaining strict enterprise compliance.
* **Relational Database Design:** Features a fully structured SQL schema mapping relationships between patients, employees, medications, and prescription lifecycles.

## 🛠️ Tech Stack

* **Language:** Java
* **GUI Framework:** JavaFX
* **Database:** SQL (Schema file included)
* **Security:** BCrypt Hashing Library

## 📊 Database Architecture

The application relies on a local relational SQL database. The project repository includes the foundational initialization script containing all required `CREATE TABLE` statements, establishing foreign key constraints for:
* **Employees** (including salted/hashed passwords and system roles)
* **Patients** (demographics and medical identification)
* **Medications** (inventory levels and dosing info)
* **Prescriptions** (status tracking: Pending, Verified, Ready for Pickup, Complete)
* **Access Logs** (immutable security audit trail)

## 💻 How It Works (Workflow)

1. **Login:** User authenticates via the login portal. The system verifies credentials using BCrypt.
2. **Dashboard Routing:** Based on the employee's database role, the application loads either the Pharmacist or Technician GUI.
3. **Prescription Lifecycle:** Pharmacists enter and and verify prescriptions, Technicians fill pending prescriptions, and once verified, can process simulated customer pick-ups.
4. **Audit Trail:** Every patient record retrieval automatically fires an internal log query to write an unalterable records to the security table.

   
