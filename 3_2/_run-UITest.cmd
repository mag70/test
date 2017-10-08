@chcp 1251 >nul

set path=%path%;C:\Program Files\Java\jdk1.8.0_45\bin
:unix java -cp .:/usr/share/java/junit.jar org.junit.runner.JUnitCore [test class name|your.package.TestClassName]
:start 
cmd /C "java -DpricePath=%pricePath% -DbaseUrl=%baseUrl% -DactionUrl=%actionUrl% -D%webDrvName%=%webDrvPath% -cp log4j-1.2.17.jar;C:\Users\shiv\.m2\repository\junit\junit\4.12\junit-4.12.jar;C:\selenium-webdriver\*;C:\selenium-webdriver\lib\*;. org.junit.runner.JUnitCore com.example.tests.UITest"
:mvn clean test -Dtest=your.package.TestClassName
:mvn clean test -Dtest=your.package.TestClassName#particularMethod
:ant -f build.xml method
echo errorlevel=%errorlevel%

@chcp 866 >nul