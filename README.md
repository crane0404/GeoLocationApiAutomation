# Geolocation API Test Framework

This project contains automated tests for the Geolocation API using Java, TestNG, RestAssured, Maven, and Log4j.

## Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java Development Kit (JDK)** - Ensure JDK 8 or higher is installed.
   
2. **Apache Maven** - Build automation tool for managing dependencies and running tests.

3. **Integrated Development Environment (IDE)** - Optional but recommended (e.g., IntelliJ IDEA, Eclipse).

4. **Log4j Configuration** - Ensure `log4j2.xml` is properly configured in the `src/main/resources` folder for logging.

## Setup

Follow these steps to set up the project and run the tests:

### 1. Clone the Repository

```bash
git clone https://github.com/crane0404/GeoLocationApiAutomation.git
cd repository-folder
```

### 2. Set API Key Environment Variable

#### For Windows:

1. Open Command Prompt as Administrator.

2. Set the environment variable using the following command:

   ```cmd
   setx GOOGLE_API_KEY "your_actual_api_key_here"
   ```

#### For Mac/Linux:

1. Open Terminal.

2. Set the environment variable using the following command:

   ```cmd
   export GOOGLE_API_KEY="your_actual_api_key_here"
   ```

### 3. Import the project in an IDE

### 4. Execute the tests:

  1. Navigate to `testng.xml`
  2. Right click and select `Run`

### 5. view the ExtentReports

  1. Navigate to `test-output`
  2. Open the HTML files in browser

## Test Cases

  The test cases are organized in `GeolocationAPITests.java` file located in the `src/test/java` directory.
