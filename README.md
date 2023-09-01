# Тестовое задание NTI TEAM

### Описание:
3141 год.

Вселенная исследована и поделена.
Верховный правитель назначает Повелителей Планет, общее количество которых исчисляется миллионами.
Опытные Повелители могут одновременно управлять несколькими Планетами. Никакой демократии, поэтому одной планетой может править только один Повелитель.
Все это безобразие требует системы учета и надзора.

### Задание:

Разработать Spring Boot приложение на Java.
Приложение должно иметь API и работать с реляционной БД. Для простоты отладки это может быть in-memory БД, например HSQLDB или иная.

Базовые характеристики сущностей:

Повелитель:
- [X] Имя
- [X] Возраст

Планета:
- [X] Название

Один Повелитель может управлять несколькими Планетами

Одной Планетой может править только один Повелитель

Необходимо разработать структуру таблиц для хранения Повелителей и Планет и связь между ними.

Поддержать методы API:
- [X] Добавить нового Повелителя
- [X] Добавить новую Планету
- [X] Назначить Повелителя управлять Планетой
- [X] Уничтожить Планету
- [X] Найти всех Повелителей бездельников, которые прохлаждаются и не управляют никакими Планетами
- [X] Отобразить ТОП 10 самых молодых Повелителей

Написать тесты для этого функционала

### Использованные технологии

*   Java SE 11
*   Spring Boot
* Web
* Test
* Validation
*   HSQLDB database
*   JUnit 5
*   Mockito
*   SpringDoc
*   Swagger UI