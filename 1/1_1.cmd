chcp 65001
set endpoint=http://www.tender.pro
set resource=/api/_info.tenderlist_by_set.json

set nm=%~n0
if exist %nm%.json del %nm%.json
if exist %nm%.headers del %nm%.headers
curl -o %nm%.json -D %nm%.header -H "Accept:application/json; charset=UTF-8" %endpoint%%resource%?_key=7b56c77b9f70220c3d5d4ce6477674ea^&set_type_id=2^&set_id=1^&max_rows=3^&open_only=1
chcp 866