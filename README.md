# Bank Loan Process

## Description
- This project is fully didactic. It contains some architectural gaps, missing integrations, and several issues in the source code. Tests are also missing — I’m working on that.

- The project consists of four microservices working together to provide a basic loan service. It simulates access to internal and external systems with the goal of verifying whether a client is eligible to proceed with a loan request.

- There is no security implementation yet (e.g., JWT, SSL, certificates). I’m working on it.
  Remember: this project is just for fun, and the main goal is to make the loan process work.   

- Feel free to explore it if you want to learn how transactions and loan processes work in a microservices environment. 
## Microservices
- Availability
  - Checks whether the client has any financial restrictions.  
  
- Score
  - Performs a simple credit score evaluation.

- Bank 
  - Provides basic banking operations such as deposit, withdrawal, and transfer.   

- Loan
  - The most important microservice — it orchestrates the entire loan process.
    It integrates with all other microservices in the project.
    To approve a loan, it must communicate with the other modules to verify whether the client has any financial issues.

## Dependencies
- Postgres
- Apache Kafka
- Redis

