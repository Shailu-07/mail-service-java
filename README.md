# 📧 Mail Service Application (Core Java)

A CLI-based Mail Service Application developed using **Core Java, JDBC, and MySQL**.  
This project simulates core email functionalities such as user authentication, mail composition, inbox, sent mails, starred mails, and bin using a database-driven backend.

---

## 🚀 Features

- User Registration & Login Authentication
- Compose and Send Emails
- Inbox (Received Mails)
- Sent Mails
- Draft Mails (In-memory)
- Starred Mails
- Bin (Soft Delete)
- All Mail View
- Database persistence using JDBC
- Clean architecture using DAO pattern

---

## 🛠️ Tech Stack

- **Language:** Core Java  
- **Database:** MySQL  
- **Database Connectivity:** JDBC  
- **Architecture:** DAO Pattern  
- **IDE:** Eclipse  
- **Version Control:** Git & GitHub  

---

## 🗂️ Project Structure
com.mailservice
├── controller → Application flow (menus & input handling)
├── dao → Database access using JDBC
├── model → Entity classes (User, Mail)
├── db → Database connection
└── driver → Main class

## 🧩 Database Design

### users table
| Column | Description |
|------|------------|
| user_id | Primary Key |
| name | User full name |
| email | Unique email |
| password | Login password |
| contact | Contact number |
| dob | Date of birth |

### mails table
| Column | Description |
|------|------------|
| mail_id | Primary Key |
| sender_email | Sender |
| receiver_email | Receiver |
| subject | Mail subject |
| body | Mail content |
| status | SENT / STARRED / BIN |
