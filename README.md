# Bank Loan Process

## Description
- This project is 100% didadic. There are some architectural gaps, missing integrations and several fails
in the source code and test missing (I'm work on it).

- The project consist in four microservices working together to provider a loan basic service. This project try to
simulate the access to internal/external services with the goal to check if the client is available to proceed the loan deel.

- Does not have any implementations about security, ex: JWT, SSL, Certificates, etc. I'm working on it. 
Remember, this project is just for fun. My main target was to make the loan process works.   

- Enjoy it if you want to learn about the transaction and loans process over a microservice 
environment. 
## Microservices
- Availability
  - Microservice with target to check if the client has some financial restriction.  
  
- Score
  - Simple score check. 

- Bank 
  - This microservice provide some functionalities to make deposit, withdrawal, and transfer.   

- Loan
  - Most important microservice that make this process works. This module har some integration with all microservices 
  of this project.
  - This module will communicate with all modules. To deal a loan, the bank need to check if the client has some 
  financial issues.

## Dependencies
- Postgres
- Apache Kafka
- Redis

