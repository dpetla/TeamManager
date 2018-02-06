# Team Manager

This is was an JavaFX project I worked on my 'Object Oriented Programming 2 - Java' class.
I created a team manager for a soccer team with the following features:
* load players and statistics from local file
* save chages to local file
* Export data to new file
* add new players
* edit player data
* delete palyer
* search player by name
* filter players by position

## Screenshots
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager1.jpg "Entry screen")
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager2.jpg "File Options")
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager3.jpg "Edit player")
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager4.jpg "Search player by name")
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager5.jpg "add new player window")
![alt text](https://github.com/dpetla/JavaFx/blob/master/imgs/TeamManager6.jpg "Export players data")


## Project Instructions:
Write a program that uses a GUI to allow the user to view records in a file. You decide what you want to keep track of (media such as book/movie/cd collection, inventory in a store, members of a group or organization, etc). Your program must include the following:

* A file that contains records used in your program (you can use a sequential file or a random access file).
    * Use a sequential file if you are going to be adding records and read them back sequentially. You can also use a sequential file to perform searches that will need to iterate through the entire file.
    * Use a random access file if you are going to be adding and editing records, or going directly to specific records (for example, by allowing a user to select a record by clicking on something in a list box).
    * Deleting records is cumbersome in both kinds of files. However, if you're allowing the user to delete, then obviously you're also allowing edits. In this case, you'd want to use a random access file.
* A class that models a record in your file. For example, if your file contains book information with the fields ISBN, Title, and Author, then you will need a Book class with data members for ISBN, Title, and Author (and other appropriate code and members such as constructors, accessor/mutator methods, toString, etc).
* A class that models the list of records, using an ArrayList (or other Collections class) of record objects (e.g. I might have BookList class of Book objects). This class will perform operations such as adding Book objects to the list, removing book objects from the list, replacing modified book objects in the list, searching for specific book objects, reading in Book objects from a file, saving the list of book objects to the file, etc. The list acts as the data file while the program is running: load the list with book objects at program startup, then on exit, overwrite the file with the book objects from the list. Don't perform add/edit/delete operations on the file directly as they occur - use the list.
* A class that contains the GUI for your program.
Your application must include, AT MINIMUM, the following functionality:

* The ability to ADD, EDIT, and DELETE records in the file.
    * Your user should be able to add, edit, and delete records, in a professional and user-friendly manner.
    * This must also include things like proper data validation.
    * If the user is in the middle of adding or editing a record, they should have the option to cancel the operation and go back to the record they were viewing before they started the add/edit operation.
    * The user should be prompted for confirmation when deleting a record.
    * All changes to the records should be applied to the data file.
* The ability to NAVIGATE records in the file.
    * For example, you could use a set of First/Previous/Next/Last buttons to navigate the records (the user should not be able to navigate beyond the first and last record).
    * For example, you could populate a list box with records (show only 1 or 2 fields as appropriate) and the user can select a record from the list to view the rest of the fields.
* The ability to SEARCH for records in the file.
    * The user should be able to enter a search key and then be shown a list of records that match that search key (e.g. a search key of "fred" on a first name field would yield records with the first name "Fred", "Frederick", or "Alfred").
    * The user can choose one of the matching records and view that record's details on the main screen.
    * You only need one search so you can choose what field you'd like to make available for searching (for example, if your application kept track of your video game collection, you might wish to search by title.
    * If a search yields no result, an appropriate message should be displayed.
