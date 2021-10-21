# Getting Started

### Prerequisite
Ensure you have the following installed and globally accessible:
* [Java 11](https://openjdk.java.net/)
* [Maven](http://maven.apache.org/install.html)
* Able to run shell scripts

### Running the application

The following guides illustrate how to run the application:

* Clone the repo
* On your terminal, navigate to the root of the repo and run  `bash start.sh`. 
* The application will start on port `8080` on your local machine. Ensure the port is not in use.
* To view available endpoints open http://localhost:8080/api-doc on your browser
* To stop the application from running; while on the terminal where the application is running press Ctrl+C

Sample Request: `GET` http://localhost:8080/routing/KEN/RWA \
Sample Response: ```{"route":["KEN","TZA","RWA"]}```