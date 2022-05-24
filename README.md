##DEMO STENSUL USING JAVA 

###Structure
This framework is based on a Page Object Model Design (POM) Using Maven and Java Language. That being said, the `DemoTest` is a class where all the test cases
are written and are derived from all of their Page Objects contained in `src/main/java/pageObjects`

Property files are specified in ``local.properties``

You can run all test using `mvn clean test -DsuiteXmlFile=demoStensulTest.xml -Denv.USER=local.properties`

Running a bash file , you can call the maven command and saving some time without having
to write the entire line. I also created a bash file where we can only run ```sh ./DemoStensul.sh``` and contains the same instruction for the maven command

I took the liberty of creating functions for Logs and folders for screenshots in order to keep an order in the execution wise.

###Test cases : 
1. Create an Item is having a bug that's why it has the ``@Test`` commented , until the bug is fixed.
2. Edit and item will grab an specific element , specified for us by an index (There is also a bug in the typo)
3. Delete Item it's working as expected, and also has a specific index we need to indicate in order to execute the test
4. Check max lenght
5. Check if there is a title it depends that the title exists 
