#language: ru

Функционал: Если пользователь оставляет комментарий к тикету через форму записи, то комментарий отображается на странице тикета во вкладке Work Log
  Предыстория:
    Допустим открываю главную страницу тракера
    И запускаю клиентское приложение

  Сценарий: Пользователь пишет комментарий и комментарий отображается во вкладке Work Log
    Допустим создаю новый тикет со статусом "accept"
    И нажимаю кнопку обновить список тикетов
    Когда выбираю последний созданный тикет
    И пишу "Тестовый комментарий" и начинаю отслеживание
    И кликаю мышью "13" раз и нажимаю клавишу 1 "15" раз
    И жду "15" секунд
    И обновляю страницу
    Тогда вижу в списке work-log последнию запись с комментарием "Тестовый комментарий"

  Сценарий: Пользователь видит количество проработанных часов над задачами
    Допустим я начал трекать время в "13.09.2013 18:20:10"
    И сделал рабочую запись "принял задачу на себя" в "13.09.2013 18:22:22"
    И перестал трекать время в "13.09.2013 18:31:10"
    Если открываю тикет
    То вижу в списке work-log последнию запись с комментарием "принял задачу на себя"


  Сценарий: Пользователь пишет комментарий и комментарий отображается во вкладке Work Log
    Допустим создаю новый тикет со статусом "accept"
    И нажимаю кнопку обновить список тикетов
    Когда выбираю последний созданный тикет
    И пишу "Тестовый комментарий1" и начинаю отслеживание
    И кликаю мышью "13" раз и нажимаю клавишу 1 "15" раз
    И жду "11" секунд
    И обновляю страницу

    И пишу "Тестовый комментарий2" и начинаю отслеживание
    И кликаю мышью "14" раз и нажимаю клавишу 1 "16" раз
    И жду "11" секунд
    И обновляю страницу

    И пишу "Тестовый комментарий3" и начинаю отслеживание
    И кликаю мышью "15" раз и нажимаю клавишу 1 "17" раз
    И жду "11" секунд
    И обновляю страницу

    И пишу "Тестовый комментарий4" и начинаю отслеживание
    И кликаю мышью "16" раз и нажимаю клавишу 1 "18" раз
    И жду "11" секунд
    И обновляю страницу

    И пишу "Тестовый комментарий5" и начинаю отслеживание
    И кликаю мышью "17" раз и нажимаю клавишу 1 "19" раз
    И жду "11" секунд
    И обновляю страницу

    И пишу "Тестовый комментарий6" и начинаю отслеживание
    И кликаю мышью "18" раз и нажимаю клавишу 1 "20" раз
    И жду "15" секунд
    И обновляю страницу
    Тогда вижу в списке work-log записи с time-spent "10 min,10 min,10 min,10 min,10 min,10 min"