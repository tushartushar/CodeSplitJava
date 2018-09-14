# CodeSplitJava
CodeSplitJava splits Java projects by putting classes and methods into separate files. It is useful when we want to have definition of a class or a method.


## Compilation
We use maven to develop and build this application with the help of Eclipse IDE and libraries.
To create a runnable jar, run the following command in the directory where the repository is cloned:
```text
mvn clean install
```
If you use Eclipse: 
* open the project using Eclipse
* then right-click on the project name and select 'run as > maven install'

## Execute the tool
After the previous step is done:
* Open terminal/command line console and run the jar
```text
  java -jar CodeSplitJava.jar -i <path of the input source folder> -o <path of the output folder> -m <mode>
  ```