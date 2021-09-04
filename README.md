# Bug Tracker
This is a bug tracker web application made with Spring Boot and Thymeleaf.
## Features
- Authentication (login)
- CRUD projects, bugs and tasks
- Employee, Admin role
- Employees panel for Admin users
  - Add employee
- Mark bugs and tasks as `pending`, `in progress` or `completed`
- Dashboard
- Profile page
- Password changing option
- List of bugs, tasks, and projects
- Notifications

## Setup
Clone this repository
```sh
git clone https://github.com/0l1v3rr/bug-tracker.git
cd bug-tracker
```
Then you have to create a MySQL database named `bug_tracker`.<br>
Then create the required tables. The appropriate commands are in the [tables.sql](https://github.com/0l1v3rr/bug-tracker/blob/master/src/main/resources/tables.sql) file.<br>
Now, the data in the [data.sql](https://github.com/0l1v3rr/bug-tracker/blob/master/src/main/resources/data.sql) file can be added to the database.<br>
In line 4, change the first 3 values to whatever you want. The default password will be **pass**, but you can easily change it later.<br>
This user will be the admin, who can register more employees.<br><br>
Now in the [DB.java](https://github.com/0l1v3rr/bug-tracker/blob/master/src/main/java/org/oliverr/bugtracker/DB.java) file, change the URL, USERNAME, and PASSWORD to the appropriate values.<br>
You can use not only MySQL but also other relational databases if you modify DRIVER accordingly.<br><br>
If you have successfully set up the database, then you can run this project:
```sh
sh run.sh
```
Now go to [localhost:8080](http://localhost:8080/), and enjoy! :)<br>
Note: *Don't forget to start your database...*

### Template
Since I'm not a frontend developer, I used [THIS](https://github.com/BootstrapDash/corona-free-dark-bootstrap-admin-template) awesome template for this project.