# test
<<<<<<< HEAD

Ответ 1:
1) http://www.tender.pro/api/_info.tenderlist_by_set.json?_key=7b56c77b9f70220c3d5d4ce6477674ea&set_type_id=2&set_id=1&max_rows=3&open_only=1
2) http://www.tender.pro/api/_tender.info.json?_key=7b56c77b9f70220c3d5d4ce6477674ea&company_id=205483&id=289099
3) http://www.tender.pro/api/_company.info_public.json?id=205483
В папку выложены подробности (список запросов и для каждого из них: командный файл, заголовок ответа, тело ответа)

Ответ 2: Каждого юзера выбрать "лучшие" дизайны на Каждом из Используемых блоков

1) Сводный список “у кого что показываем”: в файле 2_rezult.tsv
2) Использованный при решении запрос (с комментариями): в файле 2_request.sql

Ответ 3.1: Автотест для функции API "_tender.info", служащей для получения содержания тендера.

Тестирование средствами SoapUI Pro
1) Код: в виде проекта REST-TestZadanie-soapui-project.xml
   снимок приложения: cover.png
2) Подготовка: не требуется
3) Запуск теста: run-test.cmd
4) Пример результатов:
 - запуска: в папке 171008T221811/index.html
 - готовый pdf-отчет: ReportForTestCase1.pdf
5) Что проверяется:
    - время ответа
    - правильность кода ответа HTTP
    - корректность ответа функции, при правильных и не правильных параметрах или их отсутсвии, а также для недоступного тендера
6) Какой функционал покрывается автотестом: все параметры и выход метода
 
 - готовый отчет о покрытии (есть и в конце pdf-отчета): cover.png

Ответ 3.2: UI-тестирование страницы https://www.tender.pro/price_list.shtml пункта меню "Кабинет пользователя\Прайс-лист"

Тестирование средствами Selenium3/JUnit4
1) Код: Java в com/
2) Подготовка:
 - однажды:
   - на площадке:
     - завести тестовую компанию (старый прайс-лист этой компании будет удаляться во время тестирования)
     - вручную добавить сотрудника в компанию: Иванов Иван Иванович
   - в тестовой среде:
     - файл price206383.xls с прайс-листом положить в корень C:\ (или поменять pricePath в 3_2/run-UITest.cmd) 
     - браузер можно выбрать/изменить в run-UITest.cmd (отлажена работа для InternetExplorer)
 - непосредственно перед запуском:
   - войти в кабинет пользователя тестовой компании (требуется имя и пароль), получить текущее значение sid и companyid из адресной строки и прописать их в run-UITest.cmd
3) Запуск теста: run-UITest.cmd
4) Пример результатов: выложен в log/
5) Что проверяется: наличие пункта Прайс-лист в меню Кабинета пользователя, корректность отображения таблицы сведений прайс-листа, при его отсутствии, выборе недействительных позиций и другого ответственного лица, в т.ч. появление сообщения об ошибке, при неправильно закодированном параметре "ответственное лицо"
6) Какой функционал покрывается UI-тестом: отображение страницы "Прайс-лист"

begin 08.10.17
=======
in readme-edits
>>>>>>> 8b626e7ad91d201d268dcd3853da2522dc31deba
