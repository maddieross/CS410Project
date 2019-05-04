# CS410Project
______________
## Group Members
#### Hannah Johnson
#### Maddie Ross

______________
## Implementation Details
When it comes to the design of our database we only have 4 tables: car, model, rent, and client. We initially had one table per car type, but when we started writing queries we switched the design and sunk the car types into the "parent" car table.

We both were coming from a web dev background so we split all database access methods into a Data Access Object. In the Dao class we handle connecting to, all queries and updates, and shutting down the connection. Instead of creating classes for every entity we print out the information to the user directly from the Dao.

The other file is Project.java. In this file we handle parsing of the program arguments. Everytime the program is started we look for one of the commands. If it is found and the arguments are correct we run the command. If it is not found then we show the help message.

