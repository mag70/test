:parameters
set sid=827782
set companyid=206383

set baseUrl=http://www.tender.pro
set actionUrl=/cabinet/info?sid=%sid%^^^&companyid=%companyid%
set pricePath=C:\price206383.xls

:withBugs 
:goto FF
:goto Chrome

:ready
goto IE

:HtmlUnit
set webDrvName=stub
set webDrvPath=stub
goto common

:FF
set webDrvName=webdriver.gecko.driver
:set webDrvPath=C:\\geckodriver\\geckodriver-32.exe
set webDrvPath=C:\\geckodriver\\geckodriver-64.exe
goto common

:IE
set webDrvName=webdriver.ie.driver
set webDrvPath=C:\\IEDriver\\IEDriverServer-64.exe
goto common

:Chrome
set webDrvName=webdriver.chrome.driver
set webDrvPath=C:\\ChromeDriver\\chromedriver.exe
goto common

:common
if exist log\nul del /Q log
if exist log\nul rd /Q log
call _run-UITest
