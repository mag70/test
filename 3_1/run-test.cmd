@chcp 1251 >nul
set fld=%date:~8,2%%date:~3,2%%date:~0,2%T%time:~0,2%%time:~3,2%%time:~6,2%
md "%fld%"
pushd "%fld%"

call "C:\Program Files\SmartBear\SoapUI-Pro-5.1.2\bin\testrunner.bat" -sTestSuite1 -r -a -j -R"TestSuite Report" -EDefault "%%~dp0REST-TestZadanie-soapui-project.xml"

set ret=0
for %%i in (*-FAILED.txt) do (set ret=1)&(echo ERROR)

popd
@chcp 866 >nul
echo %ret%
exit /B %ret%