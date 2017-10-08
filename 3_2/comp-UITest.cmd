@chcp 1251 >nul

set path=%path%;C:\Program Files\Java\jdk1.8.0_45\bin
:-help
:javac -cp C:\Users\shiv\.m2\repository\junit\junit\4.12\junit-4.12.jar;C:\Users\shiv\.m2\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar Selenium2JUnit4.java
:javac -encoding utf8 -cp htmlunit-driver-2.27-with-dependencies.jar;log4j-1.2.17.jar;C:\Users\shiv\.m2\repository\junit\junit\4.12\junit-4.12.jar;C:\selenium-webdriver\*;C:\selenium-webdriver\lib\* com\example\tests\UITest.java
javac -encoding utf8 -cp log4j-1.2.17.jar;C:\Users\shiv\.m2\repository\junit\junit\4.12\junit-4.12.jar;C:\selenium-webdriver\*;C:\selenium-webdriver\lib\* com\example\tests\UITest.java
echo errorlevel:%errorlevel%

@chcp 866 >nul