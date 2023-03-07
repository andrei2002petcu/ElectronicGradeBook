# ElectronicGradeBook
## How to Use

* `Search Page`, insert a full name specifying his role and press *Search* to display information about the user or *Reset* to empty the Search fields. If the User is valid a new page with the info will pop, else a message will be displayed.
* `Student Page`, upon selecting one of the courses in which the student is signed up, details related to the students's situation in that particular course are provided
* `Teacher Page`, the teacher's courses are displayed and by selecting any, 2 lists are provided. A full list of the students that take part in that course and a list with students that will promote

## Implementation

The core classes of the application are:

* `Catalog`, in which the courses are being stored. It is built using the **Singleton Design Pattern** in order to remain unique
* `Course`, the most important class of the project. It contains all the information related to the course: teacher, assistants, students, grades, promotion condition, course strategy and more
* `User`, class that constructs each user (Student, Parent, Assistant, Teacher), using the **Factory Design Pattern**

The input data of the Catalog is read by parsing a JSON file using *JSON Simple Java Toolkit*

![Screenshot_1](https://user-images.githubusercontent.com/94044661/223537775-d86bdf3d-74c0-4174-9e71-47714967feb5.png)
![Screenshot_2](https://user-images.githubusercontent.com/94044661/223537792-1a74ab91-ee12-40e0-9736-3f3ba52067e4.png)
