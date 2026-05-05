🏦 Banking System API

A Spring Boot-based Banking System API that allows users to perform core banking operations such as account creation, deposits, withdrawals, transfers, and viewing account statements.

🚀 Features
- ✅ Open a new bank account
- 💰 Deposit money
- 💸 Withdraw money
- 🔄 Transfer money between accounts
- 📊 View account balance
- 📜 Generate transaction statements
- 🧠 API Design Overview

This project is based on requirements defined in the assignment.

📌 API Endpoints

1️⃣ Open Account

POST /account/openAccount

Request Body:

{
  "acctNumber": 1234567890,
  "name": "Harsh",
  "balance": 5000
}
2️⃣ Deposit Money

PATCH /account/deposit/{acct_number}

Request Body:

{
  "amount": 1000
}
3️⃣ Withdraw Money

PATCH /account/withdraw/{acct_number}

Request Body:

{
  "amount": 500
}
4️⃣ Transfer Money

POST /account/transfer

Request Body:

{
  "fromAccount": 1234567890,
  "toAccount": 9876543210,
  "amount": 1000
}
5️⃣ Show Balance

GET /account/showBalance/{acct_number}

6️⃣ Show All Accounts

GET /account/showAll


🗃️ Database Schema

| Column      | Type   |
| ----------- | ------ |
| acct_number | Long   |
| name        | String |
| balance     | Double |

Statement Table

| Column           | Type    |
| ---------------- | ------- |
| no               | Integer |
| date             | Date    |
| from_acct_number | Long    |
| to_acct_number   | Long    |
| type             | Enum    |
| amount           | Double  |


🔄 Entity Relationships
One Account → Many Statements (Sent & Received)
Many Statements → One Account


👨‍💻 Author
Harsh Chopda


7️⃣ Transfer via Statement Controller

PUT /statement/transferMoney/{fromAcctNumber}
