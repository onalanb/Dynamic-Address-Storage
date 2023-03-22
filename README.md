# Dynamic-Address-Storage
Dynamic address storage using REST that follows all CRUDS operations.

#Installation instructions

## Runtime requirements
* Code runs on ubuntu environment
* Requires sudo access to install mysql and other tools
* Requires latest version of ubunut, otherwise some tweaks needs to be performed to install the packages


## High level installation 
1) Need to install intellij IDE (for running locally)
2) Need to install Jakarta-EE (for server maintenance)
3) Need to install mysql server (for storing data and to connect from IDE)
4) Need to install mysql (mysql command line to run sql commands for testing and data generation)
5) Need to install glassfish (for running the server)


## installing intellij IDE
* Download intellij from https://www.jetbrains.com/idea/download/ and install it using sudo or run the bin file directly.
* Login into your student intellij account to get premium access


## Using Jakarta-EE in intellij
Use this link to setup the Java EE setup in intellij.

Link : https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-ee-application.html#ear

## Installing mysql server and mysql
Use this document to install the mysql server and setting up the root and password. Based on the setup, change the username and password in the code.

Link : https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04

## Instaling glassfish server
Download the glassfish server from this link and set it up in your intellij

Link : https://projects.eclipse.org/projects/ee4j.glassfish

# How to run the code ?
After installation, run the address.sql query to populate the database. Then use the intellij Run button to run the glassFish server. Change the root and password in sql connection. Then server should be running. For any errors, check the server log to debug.

# Instructions to supporting multi country search.

Approach:
* To support multi country search, we will provide command separated list of countries.
* We will split the country value at https://github.com/onalanb/Dynamic-Address-Storage/blob/main/TP/src/main/java/edu/SeattleU/team2/AddressResource.java#L258 , based on each value in the split request, we will pass it into the sql query.
* Based on the countries we got, we update the PreparedStatement mysql connection with the list of countries we got with "Or" condition.

With this approach, when user enters, "United States, Europe". Sql query will look for country matching with United States or with Europe and rest of the results will be filtered based on the user inputs.


