#language: ru

Функционал: Если пользователь оставляет комментарий к тикету через форму записи, то комментарий записывается в файл tracker.xml, который находиться в домашней директории

  Сценарий: Пользователь пишет комментарий и он записывается в файл tracker.xml
    Допустим запускаю клиентское приложение
    И выбираю первую в списке задачу
    Если пишу "тестовый комментарий" и начинаю отслеживание
    Тогда в домашней директории в файле "tracker.xml" в последней записи вижу "тестовый комментарий"
