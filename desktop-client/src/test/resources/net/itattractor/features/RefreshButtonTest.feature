#language: ru
Функционал: Проверяется отображение тикета в списке в зависимости от статуса тикета

  Предыстория:
    Допустим создаю новый тикет
    И запускаю клиентское приложение

  Сценарий: Пользователь не видит новый тикет в форме задач клиентского приложения
    Если нажимаю кнопку обновить список тикетов
    Тогда не вижу в списке последний добавленный тикет

  Сценарий: Пользователь добавлет новый тикет в прогресс и видит этот тикет в форме задач клиентского приложения
    Допустим указываю статус "accept" редактируемому тикету
    Когда нажимаю кнопку обновить список тикетов
    Тогда вижу в списке последний добавленный тикет

  Сценарий: Пользователь закрывает тикет и больше не видит этот тикет в форме задач клиентского приложения
    Допустим указываю статус "resolve" редактируемому тикету
    Когда нажимаю кнопку обновить список тикетов
    Тогда не вижу в списке последний добавленный тикет
