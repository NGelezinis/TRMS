# Tuition Reimbursment Management System (TRMS)

## Project Description

A reimbursement system for a company's employees. Employees can request reimbursements and managers can approve or deny those requests.

## Technologies Used

* AWS
* Log4J
* JUnit
* NoSQL
* Postman
* Javalin
* Java
* REST
* Maven

## Features

List of features ready and TODOs for future development
* Users can submit reimbursement requests.
* Users can view the status of previously submitted requests.
* Users can cancel their reimbursement for at any time prior to being reimbursed
* All people who are required to approve the form can view the form
* Users can upload documents
* All users can send each other messages and there is an automatic message sent if the Benefits Coordinator changes how much the reimbursement is for

To-do list:
* Submitting a document to skip supervisor skips supervisor
* Add timing to skip supervisor and department head approval if they take too long
## Getting Started
make sure you are using java 8
make sure you have an AWS account
git clone https://github.com/NGelezinis/TRMS.git
change the application.conf file to be to your region
set AWS_USER and AWS_PASS in driver configuration for your own account
1. Opening a terminal inside our `src/main/resources` folder.
2. `curl https://certs.secureserver.net/repository/sf-class2-root.crt -O`
3. `openssl x509 -outform der -in sf-class2-root.crt -out temp_file.der`
4. `keytool -import -alias cassandra -keystore cassandra_truststore.jks -file temp_file.der`
for password use the password you have selected in the application.conf file
