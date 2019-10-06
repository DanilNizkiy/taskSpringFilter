travis-ci:
[![Build Status](https://travis-ci.com/DanilNizkiy/taskSpringFilter.png)](https://travis-ci.com/DanilNizkiy/taskSpringFilter)

Для использования выполнить следующее:<br/>
1)git clone https://github.com/DanilNizkiy/taskSpringFilter.git<br/>
2)В корне проекта выполнить команду: docker-compose up -d<br/>
3)Выполнить запрос для загрузки базы данных: curl http://localhost:8080/hello/addDbContacts<br/>
<br/>
Пример запроса **фильтрации** с использванием curl :<br/>
curl 'http://localhost:8080/hello/contacts?nameFilter=%5E%D0%90.%2A%24&responseSize=555'<br/>
где, **nameFilter** - параметр который принимает регулярное выражение<br/>
**responseSize** - параметр который принимает максимальное количество элементов которые желаете получить<br/>
**%5E%D0%90.%2A%24** - закодированое регулярное выражения ^A.*$ (НЕ начинаются с A)<br/>
<br/>
Пример запроса **"получить все"** по принципу метода пагинации с использванием curl:<br/>
curl 'http://localhost:8080/hello/getAll?page=5&pageSize=77'<br/>
<br/>
Пример запроса для получения **количеста строк** базы данных с использванием curl:<br/>
curl http://localhost:8080/hello/getDatabaseLength<br/>
