#Description of APP
Maze resolver with return info of smallest number of turns(direction changes) in maze that goes from start to end.

Start - can be found on N/W wall

and End - can be found on S/E wall.

App search for all good path and count changes in directions.

In REST there are almost all data from algorithm calc.

We use DFC and BFS algorithm (all can be tested in JUnit Tests)

On Resource endpoint (REST) there are only needed algorithm for the task.

```
Algorithm based on (with changes to resolve task): 
https://www.baeldung.com/java-solve-maze
```

```
Test mazes are in directory - doc/mazes
You can upload them by GUI
```

### How to run
```
By:

Backend - http://localhost:8080/
- maven
or
- IDE as Spring Boot app

Frontend - http://localhost:4200/
- ng serve   [from maze-fron directory]
or 
- IDE as NPM
```

### Example usage of REST endpoints:

Adds 10 elements to Maze list
```
GET  http://localhost:8080/api/v1/maze/init
```

Get list without paging (default params)
```
GET  http://localhost:8080/api/v1/maze
```

Usage of Paging
```
GET  http://localhost:8080/api/v1/maze?size=3&page=2
```


Other info
```
Angular 9 
npm install -g @angular/cli

ng --version

Angular CLI: 9.0.5
Node: 12.13.1
OS: win32 x64

https://www.javaguides.net/2019/06/angular-8-spring-boot-2-example-tutorial.html

go to Angular app dir:

- IDEA - npm
ng serve

http://localhost:4200/


TODOs:
Add Angular to JAR
https://dzone.com/articles/building-a-web-app-using-spring-boot-angular-6-and

```