travis-ci:
[![Build Status](https://travis-ci.com/DanilNizkiy/taskSpringFilter.png)](https://travis-ci.com/DanilNizkiy/taskSpringFilter)

Для использования выполнить следующее:
1)git clone https://github.com/DanilNizkiy/taskSpringFilter.git
2)В корне проекта выполнить команду: docker-compose up -d
3)Выполнить запрос для загрузки базы данных: curl http://localhost:8080/hello/addDbContacts

Пример запроса ФИЛЬТРАЦИИ с использванием curl :
curl 'http://localhost:8080/hello/contacts?nameFilter=%5E%D0%90.%2A%24&responseSize=555'
где, nameFilter - параметр который принимает регулярное выражение
responseSize - параметр который принимает максимальное количество элементов которые желаете получить
%5E%D0%90.%2A%24 - закодированое регулярное выражения ^A.*$ (НЕ начинаются с A)

Пример запроса ПОЛУЧИТЬ ВСЕ по принципу метода пагинации с использванием curl:
curl 'http://localhost:8080/hello/getAll?page=5&pageSize=77'

Пример запроса для получения КОЛИЧЕСТВА СТРОК базы данных с использванием curl:
curl http://localhost:8080/hello/getDatabaseLength
