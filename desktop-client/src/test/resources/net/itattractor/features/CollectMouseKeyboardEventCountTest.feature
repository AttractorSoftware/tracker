#language: ru

Функционал:
  Предыстория: Создание скриншота
    Допустим Запускаю клиентское приложение
    И Выбираю первую в списке задачу
    И Пишу "комментарий" и начинаю отслеживание
    И Кликаю мышью "13" раз и нажимаю клавишу 1 "5" раз
    И Жду "11" секунд

  Сценарий: Проверка сохранения скриншота
    Допустим Запускаю серверное приложение
    И Перехожу во вкладку "Tracker"
    Если Открою отчет с ссылкой "Ежедневный отчет" пользователя "tester"
    Тогда Увижу скриншот юзера "tester" с количеством кликаний мышью "13" и нажатием клавиатуры "5" раз
