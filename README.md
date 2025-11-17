*A Java + JDBC + SQLite based telecom billing prototype simulating real-world customer billing workflows.*

---

## 📌 Overview

The **Telecom Billing System** is a console-based Java application that simulates how telecom companies manage customers, record usage, calculate bills, and generate invoices. It demonstrates key software engineering concepts including:

* Java application structure
* JDBC-based database interaction
* Modular code design
* Real billing workflow simulation
* CRUD operations
* Persistent storage (SQLite)
* End-to-end billing lifecycle

This project is intentionally built **from scratch without frameworks** to showcase fundamental development skills that are often tested in interviews.

---

## 🎯 Purpose of This Project

This project was developed to:

1. **Demonstrate practical Java development** using JDBC and a local database.
2. **Simulate a realistic telecom workflow**, including:
   * Customer onboarding
   * Usage event recording
   * Billing & invoice generation
   * Payment updates
     
3. Provide a **clean, interview-ready project** for backend, Java, and full-stack developer roles.
4. Give students & learners a simple but powerful project to understand:
   * How backend systems work
   * Billing logic
   * Data persistence
   * End-to-end application flow
5. Expand later into:
   * REST APIs
   * Web UI using React
   * Microservices
   * MySQL migration
---

## 🏗️ System Architecture

The system follows a simple, modular architecture:

```
+-------------------+
|    Main.java      | → Program entry point, shows menu
+-------------------+

+-------------------+
| BillingSystem.java| → Handles user input, workflows,
|                   |    and interacts with DBHelper
+-------------------+

+-------------------+
|   DBHelper.java   | → All database operations:
|                   |   creating tables
|                   |   inserting data
|                   |   fetching usage
|                   |   generating invoices
+-------------------+

+-------------------+
|   SQLite DB       | → billing.db stores:
|                   |   customers, usage, invoices
+-------------------+
```

This separation ensures clean code, easy debugging, and maintainability.

---

## 🔑 Key Features

### 1️⃣ Customer Management

* Add new customers
* Store name, mobile number, address
* Auto-generate Customer ID

### 2️⃣ Usage Tracking

* Record telecom usage by units (min/MB)
* Save unit rate (₹ per unit)
* Store timestamp

### 3️⃣ Automated Billing

* Fetch all usage for a customer
* Calculate total due amount:

```
usage_units × unit_rate (per record)
```

* Sum up all usage dynamically

### 4️⃣ Invoice Generation

* Create invoice only if usage exists
* Store invoice date (today’s date)
* Save amount due
* Assign invoice ID

### 5️⃣ Payment Handling

* Mark invoice as paid
* Update records accordingly

### 6️⃣ View All Records

* List customers
* List invoices (Paid/Unpaid)

---

## 🛠️ Tech Stack

### **Languages**

* Java (Core Java, OOP)

### **Database**

* SQLite (lightweight embedded DB)

### **Libraries**

* JDBC (Java Database Connectivity)

### **Tools**

* IntelliJ IDEA
* Git & GitHub

---

## 📂 Project Structure

```
Telecom-Billing-System/
│
├── src/
│   ├── Main.java
│   ├── BillingSystem.java
│   └── DBHelper.java
│
├── README.md
├── .gitignore
└── billing.db  (ignored)
```

---

## ▶️ How to Run the Project

### **Prerequisites**

* JDK 17 or above
* IntelliJ IDEA
* SQLite JDBC driver (added as a library)

### **Run Steps**

1. Clone the repo:

   ```bash
   git clone https://github.com/Kumari-Mansi/Telecom-Billing-System
   ```
2. Open in IntelliJ
3. Add `sqlite-jdbc-3.42.0.0.jar` to **Project → Libraries**
4. Run `Main.java`
5. The console menu will appear:

```
1) Initialize DB
2) Add Customer
3) Record Usage
4) Compute Due & Generate Invoice
5) List Customers
6) List Invoices
7) Mark Invoice Paid
0) Exit
```

## 🖥️ Demo Workflow (Copy & Run)

## 🖼️ Demo Screenshot

Below is a screenshot from the live running console showing customer creation and invoice listing:

![Telecom Billing Demo](demo_screenshot.png)


### 1. Add customer

```
2
Riya
9876543210
Ranchi
```

### 2. Record usage

```
3
1
120
0.5
3
1
30
0.5
```

### 3. Generate invoice

```
4
1
y
```

### 4. Mark invoice as paid

```
7
1
```

### 5. View invoices

```
6
```

---

## 🗄️ Database Schema

### Customer Table

| Column  | Type    | Description                |
| ------- | ------- | -------------------------- |
| id      | INTEGER | Auto-increment primary key |
| name    | TEXT    | Customer full name         |
| mobile  | TEXT    | Phone number               |
| address | TEXT    | Address                    |

### Usage Table

Records every usage event:

| Column      | Type    |
| ----------- | ------- |
| id          | INTEGER |
| customer_id | INTEGER |
| usage_units | REAL    |
| unit_rate   | REAL    |
| usage_date  | TEXT    |

### Invoice Table

| Column       | Type          |
| ------------ | ------------- |
| id           | INTEGER       |
| customer_id  | INTEGER       |
| invoice_date | TEXT          |
| amount       | REAL          |
| paid         | INTEGER (0/1) |

---

## 🧩 Future Enhancements

The project is intentionally extendable. Planned (or possible) upgrades:

### 🔹 Move DB to MySQL

For production-level architecture.

### 🔹 Add REST API (Spring Boot)

Convert billing flow into:

* POST /customer
* POST /usage
* POST /invoice
* GET /invoice/{id}

### 🔹 Add Frontend

React/Tailwind UI to manage customers and invoices.

### 🔹 Add Authentication

JWT-based login for telecom staff.

### 🔹 Add Reports

Usage reports, monthly statements.

### 🔹 Add Email/SMS notification mock

Send invoice alerts to customers.

---

## 🎓 Learning Outcomes

From this project, you gain strong practical exposure to:

* Java backend development
* JDBC database connections
* SQL querying
* CRUD operations
* Billing & invoice business logic
* Clean project structuring
* Console application design
* Debugging real workflows
* Git/GitHub usage
* Software engineering fundamentals

This is an excellent talking point in interviews for:

* Backend roles
* Java developer roles
* Full-stack roles
* Cloud/DevOps foundation
* Service-based and product companies

---

## 🤝 Contributing

Feel free to fork & enhance the system. PRs are welcome!

---

## 📝 License

MIT License – Free to use and modify.

---

## ⭐ Acknowledgment

This project was built as a practice-based learning exercise to understand backend development, databases, and real-world billing workflows.
