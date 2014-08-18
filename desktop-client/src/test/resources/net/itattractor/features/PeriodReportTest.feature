#language: ru

Функционал: Проверка отчета о работе всех пользователей за разные промежутки времени

  Структура сценария: Пользователь видит отчет за месяц по умолчанию
    Допустим зашел в трак под пользователем "<user>"
    И создал тикет под названием "<ticket_name>" и запустил клиент под пользователем "<user>"
    И сделал рабочую запись "<work_log>" в "<start_time>"
    И активно работал над ней до "<end_time>"
    Когда через вкладку "Tracker" кликаю по ссылке "Отчет за период"
    Тогда вижу страницу с названием отчета "Ежемесячный отчет" за последний месяц "July 2014"
    И вижу вкладку для пользователя "<user>"
    Если кликаю по вкладке "<user>"
    То вижу таблицу с названием тикета "<ticket_name>", затраченным временем "<ticket_time>" и общим временем "<total_time>"

  Примеры:
   | user      | ticket_name | work_log | start_time          | end_time            | ticket_time  | total_time   |
   | tester    | ticket1     | comment1 | 01.07.2014 14:59:00 | 01.07.2014 15:51:00 | 1 hs 0 mins  | 1 hs 0 mins  |
   | tester    | ticket2     | comment2 | 29.07.2014 15:59:00 | 29.07.2014 17:11:00 | 1 hs 20 mins | 2 hs 20 mins |
   | testerTwo | ticket3     | comment1 | 30.07.2014 12:09:00 | 30.07.2014 13:51:00 | 1 hs 50 mins | 1 hs 50 mins |

  Структура сценария: Пользователь выбирает месяц для отчета используя стрелки навигации "назад-вперед"
    Допустим зашел в трак под пользователем "<user>"
    И создал тикет под названием "<ticket_name>" и запустил клиент под пользователем "<user>"
    И сделал рабочую запись "<work_log>" в "<start_time>"
    И активно работал над ней до "<end_time>"
    Когда через вкладку "Tracker" кликаю по ссылке "Отчет за период"
    Тогда вижу страницу с названием отчета "Ежемесячный отчет" за последний месяц "September 2014"
    Если на переключателе даты кликаю на стрелку "<arrow>"
    То вижу страницу с отчетом за месяц "<chosen_month>"
    И вижу вкладку для пользователя "<user>"
    Если кликаю по вкладке "<user>"
    То вижу таблицу с названием тикета "<ticket_name>", затраченным временем "<ticket_time>" и общим временем "<total_time>"

  Примеры:
    | user      | work_log | start_time          | end_time            | arrow | chosen_month | ticket_name | ticket_time   | total_time   |
    | tester    | comment1 | 01.08.2014 12:01:00 | 01.08.2014 13:11:00 | <<    | August 2014  | ticket4     | 1 hs 10 mins  | 1 hs 10 mins |
    | testerTwo | comment1 | 30.10.2014 12:29:00 | 30.10.2014 13:01:00 | >>    | October 2014 | ticket5     | 0 hs 40 mins  | 0 hs 40 mins |

  Структура сценария: Пользователь строит отчет за определенный период
    Допустим зашел в трак под пользователем "<user>"
    И создал тикет под названием "<ticket_name>" и запустил клиент под пользователем "<user>"
    И сделал рабочую запись "<work_log>" в "<start_time>"
    И активно работал над ней до "<end_time>"
    Когда через вкладку "Tracker" кликаю по ссылке "Отчет за период"
    Тогда вижу страницу с формой для построения отчета "Please choose dates from and to"
    Если указываю период с "<From>" по "<To>"
    И нажимаю на кнопку "Построить отчет"
    То вижу страницу с названием отчета за период "Отчет за период с <From> по <To>"
    И вижу вкладку для пользователя "<user>"
    Если кликаю по вкладке "<user>"
    То вижу таблицу с названием тикета "<ticket_name>", затраченным временем "<ticket_time>" и общим временем "<total_time>"

  Примеры:
    | user      | work_log | start_time          | end_time            | From       | To         | ticket_name | ticket_time   | total_time   |
    | tester    | comment1 | 01.05.2014 12:01:00 | 01.05.2014 13:01:00 | 01-05-2014 | 30-06-2014 | ticket6     | 1 hs 0 mins   | 1 hs 0 mins  |
    | testerTwo | comment1 | 30.06.2014 12:01:00 | 30.06.2014 13:31:00 | 01-05-2014 | 30-06-2014 | ticket7     | 1 hs 30 mins  | 1 hs 30 mins |