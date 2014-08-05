Outline
============

This standalone program can identify fraud transactions for the given day and treshold.

Transaction logs is kept in the classpath with the filename, transaction-log.txt. You can modify the file to play around with the program.


Building the program
======================

1. Build the system using the standard maven command:

       	mvn clean install

Running the program
======================

1. Run the sample standalone program by issuing this command from the command line. This will read the transaction-log.txt file and identify fraud accounts. 

        mvn exec:java