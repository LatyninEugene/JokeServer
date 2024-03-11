## Joke Server

Проект позволяет получить случайную шутку.

>Создан специально как пример работы, если спросят на собеседовании. 

Используемый стек:
- Java 17
- Spring (Web, Security, OpenApi)
- MongoDB
- Junit, Mockito

Использование стороннего API:
- [Joke Api](https://github.com/15Dkatz/official_joke_api) (Предоставляет случайные шутки)
- [Yandex Translate Api](https://cloud.yandex.ru/ru/docs/translate/) (Позволяет возвращать шутки в переводе)
- Deepl Api (WIP)


### Локальный Запуск 
В директории `docker/run` лежит файл `docker-compose.yaml`
и из этой директории необходимо выполнить команду `docker-compose up --build`.
Дождавшись окончания, можно перейти `http://localhost:9090/swagger-ui.html`, где
будет список точек, которые доступны.

#### Активация Перевода от Яндекса
>Эта услуга НЕ бесплатная

Для работы **Yandex Translate Api** надо в `.env` для **YANDEX_ENABLED** указать **true** 
и установить **YANDEX_OAUTH_TOKEN**, **YANDEX_FOLDER_ID**, получение которых можно найти в [документации](https://cloud.yandex.ru/ru/docs/translate/quickstart#before-begin)

