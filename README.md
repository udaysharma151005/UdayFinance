# 💼 Uday Finance Portal

Welcome to your personal finance command center. This is a full-stack Spring Boot application that helps users manage their expenses, track income, monitor budgets, and maintain ledger records in a clean, responsive dashboard interface.

---

## 🚀 Features

- 👤 Login & Registration with glassmorphism UI  
- 📊 Dashboard displaying income, expense, and overall balance  
- 💸 Budget Tracker with monthly limits and progress indicator  
- 📒 Ledger system for lend/borrow tracking  
- ✨ Editable transactions and records  
- 🌓 Dark mode & responsive design  
- 🎨 Modern UI with animations, top navbar, glowing buttons  
- 🔐 Secure access via session-based authentication

---

## 🛠 Technologies

- Java 17  
- Spring Boot  
- Thymeleaf  
- Chart.js (for visualizations)  
- CSS3 (custom styling and transitions)  
- Maven  
- H2 Database (in-memory) — optional MySQL compatible

---

## 📦 Getting Started
1. Clone This repo
 
2. Open in IntelliJ IDEA or VS Code
Ensure JDK 17 and Maven are configured.

3. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=your_password

4. Run the application
mvn spring-boot:run

Application Routes
Endpoint	Description
/register	Create a new account
/login	Login with credentials
/dashboard	View income, expense, balance cards
/budget	Set and monitor monthly budget
/ledger	Add, view, and edit lend/borrow data


📁 Project Structure
src/
└── main/
    ├── java/
    │   └── com.uday.financeportal/
    │       ├── controller/
    │       ├── model/
    │       └── repository/
    └── resources/
        ├── templates/
        ├── static/css/
        └── application.properties

Feedback & Contributions
Pull requests are welcome. Found a bug or want a new feature? Feel free to open an issue or drop a suggestion.

## 📸 Dashboard Preview

![Dashboard](screenshots/dashboard-preview.png)

ᐧ


