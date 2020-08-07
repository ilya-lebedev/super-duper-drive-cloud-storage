# Super*Duper*Drive Cloud Storage
This is my project from "Spring Boot Basic" course of "Java Web Developer Nanodegree Program" in Udacity.

## Features

- Java 11
- Spring 5
- Spring Boot
- Spring MVC
- Thymeleaf
- MyBatis
- Selenium Tests
- Bootstrap

## Legend

I have been hired by Super*Duper*Drive, which is a brand new company aiming to make a dent in the 
Cloud Storage market and is already facing stiff competition from rivals like Google Drive and Dropbox. 
That hasn't dampened their spirits at all, however. They wanted to include personal information management 
features in their application to differentiate them from the competition, and the minimum viable product 
should have included three user-facing features:

1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  

Super*Duper*Drive wanted me to focus on building the web application with the skills I acquired in 
this course. That means I was responsible for developing the server, website, and tests, 
but other tasks like deployment belong to other teams at the company. 

## Starter Project
A senior developer assigned to be my tech lead and mentor, and they put together a starter project for me. 
It was a Maven project configured for all the dependencies the project requires, 
though I was feel free to add any additional dependencies I might require. 
[Starter project repository can be found here.](https://github.com/udacity/nd035-c1-spring-boot-basics-project-starter/tree/master/starter/cloudstorage).

My tech lead already designed a database schema for the project and has added it to the `src/main/resources` 
directory. That means I didn't have to design the database, only develop the Java code to interact with it. 

My tech lead also created some HTML templates from the design team's website mockups, and they placed them 
in the `src/main/resources/templates` folder. These was static pages, and I had to configure 
them with Thymeleaf to add functionality and real data from the server I developed. 
I also had to change them to support testing the application.

## Requirements and Roadmap
My tech lead was excited to work with me and has laid out a development roadmap with requirements and 
milestones. They told me that there are three layers of the application I need to implement:

1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End
The back-end is all about security and connecting the front-end to database data and actions. 

1. Managing user access with Spring Security
 - I restricted unauthorized users from accessing pages other than the login and signup pages. 
 To do this, I created a security configuration class that extends the `WebSecurityConfigurerAdapter` 
 class from Spring. Placed this class in a package reserved for configuration. 
And called this package `config`.
 - Spring Boot has built-in support for handling calls to the `/login` and `/logout` endpoints. 
 I used the security configuration to override the default login page with one of my own.
 - I also implemented a custom `AuthenticationProvider` which authorizes user logins by matching 
 their credentials against those stored in the database.  

2. Handling front-end calls with controllers
 - I wrote controllers for the application that bind application data and functionality to the 
 front-end. I used Spring MVC's application model to identify the templates served for different 
 requests and populating the view model with data needed by the template. 
 - The controllers I wrote also is responsible for determining what, if any, error messages the 
 application displays to the user. When a controller processes front-end requests, it delegates the 
 individual steps and logic of those requests to other services in the application, but it interprets 
 the results to ensure a smooth user experience.
 - I keeped controllers in a single package to isolate the controller layer. I simply called this package `controller`.

3. Making calls to the database with MyBatis mappers
 - Since I were provided with a database schema to work with, I designed Java classes to match 
 the data in the database. These became POJOs (Plain Old Java Objects) with fields that match the names 
 and data types in the schema, and I created one class per database table. I placed these classes in a `model` package.
 - To connect these model classes with database data, I implemented MyBatis mapper interfaces for each of the 
 model types. These mappers got methods that represent specific SQL queries and statements required 
 by the functionality of the application. They support the basic CRUD (Create, Read, Update, Delete) 
 operations for their respective models. I place these classes in the `mapper` package.

### The Front-End
My tech lead has done a thorough job developing HTML templates for the required application pages. 
They had included fields, modal forms, success and error message elements, as well as styling and 
functional components using Bootstrap as a framework. I edited these templates and inserted Thymeleaf 
attributes to supply the back-end data and functionality described by the following individual page 
requirements:

1. Login page
 - Everyone allowed access to this page, and users can use this page to login to the application. 
 - Login errors showing, like invalid username/password, on this page. 

2. Sign Up page
 - Everyone allowed access to this page, and potential users can use this page to sign up for 
 a new account. 
 - Validation that the username supplied does not already exist in the application, and signup 
 errors showing on the page when they arise.
 - The user's password stores securely!

3. Home page
The home page is the center of the application and hosts the three required pieces of functionality. 
The home page template presents them as three tabs that can be clicked through by the user:

 i. Files
  - The user able to upload files and see any files they previously uploaded.
  - The user able to view/download or delete previously-uploaded files.
  - Any errors related to file actions is displayed.

 ii. Notes
  - The user able to create notes and see a list of the notes they have previously created.
  - The user able to edit or delete previously-created notes.

 iii. Credentials
 - The user able to store credentials for specific websites and see a list of the credentials 
 they've previously stored. Password in this list is displaying in encrypted form.
 - The user able to view/edit or delete individual credentials. When the user views the credential, 
 they able to see the unencrypted password.

The home page have a logout button that allows the user to logout of the application and keep their 
data private.

### Testing
My tech lead was trusting me to do a good job, but testing is important whether you're an excel number-cruncher 
or a full-stack coding superstar! The QA team at SuperDuperDrive carried out extensive user testing. 
Still, my tech lead wanted me to write some simple Selenium tests to verify user-facing functionality and prove 
that my code is feature-complete before the testers get their hands on it.

1. I wrote tests for user signup, login, and unauthorized access restrictions.
 - Wrote a test that verifies that an unauthorized user can only access the login and signup pages.
 - Wrote a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, 
 and verifies that the home page is no longer accessible. 

2. I wrote tests for note creation, viewing, editing, and deletion.
 - Wrote a test that creates a note, and verifies it is displayed.
 - Wrote a test that edits an existing note and verifies that the changes are displayed.
 - Wrote a test that deletes a note and verifies that the note is no longer displayed.

3. I wrote tests for credential creation, viewing, editing, and deletion.
 - Wrote a test that creates a set of credentials, verifies that they are displayed, and verifies that 
 the displayed password is encrypted.
 - Wrote a test that views an existing set of credentials, verifies that the viewable password is unencrypted, 
 edits the credentials, and verifies that the changes are displayed.
 - Wrote a test that deletes an existing set of credentials and verifies that the credentials are no longer 
 displayed.

## Final Tips and Tricks
### Password Security
I made sure not to save the plain text credentials of the application's users in the database. That's a recipe 
for data breach disaster! I used a hashing function to store a scrambled version instead. My tech lead gave 
me a class called `HashService` that can hash passwords. When the user signs up, I only store 
a hashed version of their password in the database, and on login, I hash the password attempt before 
comparing it with the hashed password in the database. My tech lead knew that can be a little confusing, 
so they provided this code sample to help illustrate the idea:

```
byte[] salt = new byte[16];
random.nextBytes(salt);
String encodedSalt = Base64.getEncoder().encodeToString(salt);
String hashedPassword = hashService.getHashedValue(plainPassword, encodedSalt);
return hashedPassword;
```

For storing credentials in the main part of the application, we can't hash passwords because it's a one-way 
operation. The user needs access to the unhashed password, after all! So instead, I encrypt the 
passwords. My tech lead provided me with a class called `EncryptionService` that can encrypt and decrypt 
passwords. When a user adds new credentials, the password is encrypted before storing it in the database. When the 
user views those credentials, the password is decrypted before displaying it. Here's a little code snippet on how 
I used `EncryptionService`:

```
SecureRandom random = new SecureRandom();
byte[] key = new byte[16];
random.nextBytes(key);
String encodedKey = Base64.getEncoder().encodeToString(key);
String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
```

I wasn't required to understand hashing or encryption and that's why my tech lead provided these code 
samples for me. I was curious and wanted to learn a little more, so I did a quick Google search and 
followed the links below:

- [Hash Function](https://en.wikipedia.org/wiki/Hash_function)
- [Encryption](https://en.wikipedia.org/wiki/Encryption)

##Conclusion

The entire Super*Duper*Drive team wished me good luck with the project.
With their great help I finished the project successfully.
