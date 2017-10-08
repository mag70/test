chcp 65001
set endpoint=http://www.tender.pro
set resource=/api/_company.info_public.json
set nm=%~n0
if exist %nm%.json del %nm%.json
if exist %nm%.headers del %nm%.headers
curl -o %nm%.json -D %nm%.header -H "Accept:application/json; charset=UTF-8" %endpoint%%resource%?id=205483
chcp 866