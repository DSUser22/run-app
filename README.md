# run-app

This app can help people to prepare for a marathon. It creates plan depended on the date of marathon, how many times a week you want to train(3-5) and your last running distance.
It contains registration with confirmation letters, authentication, creating plan and getting trainings with dates and distances.
There are 4 type of training: 
- speed running
- ordinary running
- long running
- gym (if you want to train 5 times a week).


By default, start of preparing is beginning of next week or if today is Monday - this week; and as usual date of marathon is sunday, so app consider this.


Технологии: Spring Boot, Spring Data JPA, Spring Security.
БД: PostgreSQL


Основные эндпоинты:

- api/v1/registration [POST] - регистрация нового пользователя
![img.png](img.png)
- Подтверждение регистрации:
![img_1.png](img_1.png)
![img_2.png](img_2.png)
- api/v1/auth [POST] - аутентификация, выдача jwt-токена
![img_3.png](img_3.png)
С каждым следующим запросом должен идти выданный токен:
![img_4.png](img_4.png)
- api/v1/my/plan [POST] - создание бегового плана
![img_5.png](img_5.png)
- api/v1/my/plan/training/all [GET] - получение всех тренировок
![img_6.png](img_6.png)
- api/v1/my/plan/training [GET] - получение тренировки по дате
![img_7.png](img_7.png) 
- api/v1/my/plan [DELETE] - удаление плана
![img_8.png](img_8.png)
- api/v1/delete [DELETE] - удаление пользователя
![img_9.png](img_9.png)

Требуется заполнить application.yml:
- spring.datasource.password,
- spring.datasource.url,
- spring.datasource.username,
- spring.mail.host,
- spring.mail.port,
- spring.mail.username,
- spring.mail.password.
