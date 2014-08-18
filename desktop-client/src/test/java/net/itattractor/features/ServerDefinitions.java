package net.itattractor.features;

import cucumber.api.java.After;
import cucumber.api.java.ru.*;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class ServerDefinitions {
    private final CommonDefinitions commonDefinitions;
    private final ClientDefinitions clientDefinitions;

    public ServerDefinitions(CommonDefinitions commonDefinitions, ClientDefinitions clientDefinitions) {
        this.commonDefinitions = commonDefinitions;
        this.clientDefinitions = clientDefinitions;
    }

    @И("^перехожу во вкладку \"([^\"]*)\"$")
    public void перехожу_во_вкладку(String tab) throws Throwable {
        elementWaitByXpath("//a[text()='" + tab + "']").click();
    }

    @То("^вижу скриншот юзера с количеством кликаний мышью \"([^\"]*)\" и нажатием клавиатуры \"([^\"]*)\" раз$")
    public void вижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(String clickCount, String pressCount) throws Throwable {
        WebElement mouse_count_cont = Driver.getServerInstance().findElement(By.xpath("//*[@class='mouse-event-count'][contains(., '"+clickCount+"')]"));
        WebElement keyboard_count_cont = Driver.getServerInstance().findElement(By.xpath("//*[@class='keyboard-event-count'][contains(., '"+pressCount+"')]"));

        Assert.assertEquals(mouse_count_cont.getText(), clickCount);
        Assert.assertEquals(keyboard_count_cont.getText(), pressCount);
    }

    @И("^вижу скриншот с количеством кликаний мышью \"([^\"]*)\" и нажатий клавиатуры \"([^\"]*)\" раз$")
    public void вижу_скриншот_с_количеством_кликаний_мышью_и_нажатий_клавиатуры_раз(String clickCount, String pressCount) throws Throwable {
        вижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(clickCount,pressCount);
    }

    @Если("^открываю отчет со ссылкой \"([^\"]*)\"$")
    public void открываю_отчет_со_ссылкой(String report_name) throws Throwable {
        elementWaitByXpath("//a[text()='" + report_name + "']").click();
    }

    @Тогда("^вижу в меню вкладку \"([^\"]*)\"$")
    public void вижу_в_меню_вкладку(String tab) throws Throwable {
        List<WebElement> items = Driver.getServerInstance().findElements(By.cssSelector("#mainnav a"));
        boolean contains = false;
        for (WebElement item : items) {
            if (tab.equals(item.getText())) {
                contains = true;
            }
        }
        Assert.assertTrue(contains);
    }

    @Когда("^заполняю поля нового тикета и сохраняю его$")
    public void заполняю_поля_нового_тикета_и_сохраняю_его() throws Throwable {
        Driver.getServerInstance().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement summary = Driver.getServerInstance().findElement(By.xpath("//*[@id=\"field-summary\"]"))    ;
        String ticketSummary = new Date().toString();
        summary.sendKeys(ticketSummary);
        Driver.getServerInstance().findElement(By.name("submit")).click();
        CommonData.latestTicketId = Driver.getServerInstance().findElement(By.className("trac-id")).getText();
        CommonData.latestTicketSummary = ticketSummary;
    }

    @И("^указываю статус \"([^\"]*)\" редактируемому тикету$")
    public void указываю_статус_редактируемому_тикету(String status) throws Throwable {
        WebElement modifyBtn = Driver.getServerInstance().findElement(By.cssSelector("#no4"));
        modifyBtn.click();
        Driver.getServerInstance().findElement(By.id("action_" + status)).click();
        Driver.getServerInstance().findElement(By.name("submit")).click();
    }

    @То("^вижу заголовок \"([^\"]*)\"$")
    public void вижу_заголовок(String header) throws Throwable {
        WebElement reportFor = elementWaitByXpath("//ul[@id='nav']/p");
        Assert.assertEquals(header, reportFor.getText());
    }

    @Допустим("^создаю новый тикет со статусом \"([^\"]*)\"$")
    public void создаю_новый_тикет_со_статусом(String ticketStatus) throws Throwable {
        перехожу_во_вкладку("New Ticket");
        заполняю_поля_нового_тикета_и_сохраняю_его();
        указываю_статус_редактируемому_тикету(ticketStatus);
    }


    @И("^укажу период с \"([^\"]*)\" по \"([^\"]*)\"$")
    public void укажу_период_(String from,String to) throws Throwable {
        WebElement fromDate = elementWaitByCss("#fromDMY");
        fromDate.clear();
        fromDate.sendKeys(from);
        fromDate.sendKeys();
        WebElement toDate = elementWaitByCss("#toDMY");
        toDate.clear();
        toDate.sendKeys(to);
        toDate.sendKeys();
        WebElement trackerCalendarMakeReportButton = elementWaitByXpath("//ul[@id='nav']//input[@value='Построить отчет']");
        trackerCalendarMakeReportButton.click();
    }

    @Тогда("^вижу у последнего созданного тикета \"([^\"]*)\" наработанных минут$")
    public void вижу_у_последнего_созданного_тикета_наработанных_минут(String mins) throws Throwable {
        кликаю_на_последний_тикет();
        WebElement element = elementWaitByXpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']//b[@id='min']");
        Assert.assertEquals(mins,element.getText());
    }

    @Если("^кликаю по первому скриншоту$")
    public void кликаю_по_первому_скриншоту() throws Throwable {
        WebElement screeningsBlock = elementWaitByCss(".tracker-image");
        screeningsBlock.findElement(By.xpath("a/img")).click();
    }

    @То("^вижу модальное окно скриншота$")
    public void вижу_модальное_окно_скриншота() throws Throwable {
        WebElement modalWindow = elementWaitByXpath("//./div[@id='boxes']/div");
        String display = modalWindow.getCssValue("display");
        Assert.assertEquals(display, "block");
    }

    @After
    public void closeBrowser() throws Exception {
        Driver.closeServerInstance();
    }

    @И("^открываю отчет с ссылкой \"([^\"]*)\" за \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void открываю_отчет_с_ссылкой_за_пользователя(String link_text, String date, String author) throws Throwable {
        elementWaitByXpath("//li[contains(@author,'" + author + "')]/a[text()='" + link_text + "']").click();
        elementWaitByXpath("//*[@id=\"tracker-date\"]").clear();
        elementWaitByXpath("//*[@id=\"tracker-date\"]").sendKeys(date);
        elementWaitByXpath("//*[@id=\"tracker-filter\"]/div[2]/input").click();
    }

    @Допустим("^началась работа по новому тикету$")
    public void началась_работа_по_новому_тикету() throws Throwable {
        создаю_новый_тикет_со_статусом("accept");
        commonDefinitions.запускаю_клиентское_приложение();
        clientDefinitions.работаю_над_последним_созданым_тикетом();
    }

    @Допустим("^начинаю работать над новым тикетом с видом деятельности \"([^\"]*)\"$")
    public void начинаю_работать_над_новым_тикетом_с_видом_деятельности(String worklog) throws Throwable {
        создаю_новый_тикет_со_статусом("accept");
        commonDefinitions.запускаю_клиентское_приложение();
        clientDefinitions.выбираю_последний_созданный_тикет();
        clientDefinitions.пишу_и_начинаю_отслеживание(worklog);
        clientDefinitions.кликаю_мышью_раз_и_нажимаю_клавишу_раз(13, 15);
        clientDefinitions.жду_секунд(11);
    }

    @Допустим("^создаю новый тикет$")
    public void создаю_новый_тикет() throws Throwable {
        commonDefinitions.открываю_главную_страницу_тракера();
        перехожу_во_вкладку("New Ticket");
        заполняю_поля_нового_тикета_и_сохраняю_его();
    }

    @То("^вижу пользователя \"([^\"]*)\" в списке Users$")
    public void вижу_пользователя_в_списке_Users(String username) throws Throwable {
        WebElement user = elementWaitByXpath("//li[contains(@author,'" + username + "')]/h1");
        Assert.assertEquals(username, user.getText());
    }
    @То("^вижу ссылку \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void вижу_ссылку_пользователя(String link,String username) throws Throwable {
        List<WebElement> items = Driver.getServerInstance().findElements(By.xpath("//ul[@id='nav-user-list']/li[contains(@author,'" + username + "')]/a"));
        boolean contains = false;
        for (WebElement item : items) {
            if (link.equals(item.getText())) {
                contains = true;
            }
        }
        Assert.assertTrue(contains);
    }

    private WebElement elementWaitByXpath(String xpathExpression) throws Exception {
        WebDriverWait wait = new WebDriverWait(Driver.getServerInstance(), 10);

        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
    }

    private WebElement elementWaitByCss(String expression) throws Exception {
        WebDriverWait wait = new WebDriverWait(Driver.getServerInstance(), 10);

        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(expression)));
    }

    @И("^обновляю страницу$")
    public void обновляю_страницу() throws Throwable {
        Driver.getServerInstance().navigate().refresh();
    }

    @Тогда("^вижу в списке work-log последнию запись с комментарием \"([^\"]*)\"$")
    public void вижу_в_списке_work_log_запись_с_комментарием(String comment_text) throws Throwable {
        commentIsInWorklog(comment_text);
    }

    @Тогда("^вижу в списке work-log записи с time-spent \"([^\"]*)\"$")
    public void вижу_в_списке_work_log_запись_с_комментариями(String time_spent_text) throws Throwable {

        timeSpentIsTrue(time_spent_text);
    }

    @То("^вижу последний тикет с видом деятельности \"([^\"]*)\" и затраченным временем \"([^\"]*)\" минут$")
    public void вижу_последний_тикет_с_видом_деятельности_и_затраченным_временем(String worklog,String mins) throws Throwable {
        кликаю_на_последний_тикет();
        WebElement element = elementWaitByXpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']//li[@class='item']//div[@class='comment']");
        Assert.assertEquals(worklog, element.getText());
        WebElement item = elementWaitByXpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']//span[@id='minutes']");
        Assert.assertEquals(mins, item.getText());
    }

    public void кликаю_на_последний_тикет() throws Throwable {
        WebElement ticket = elementWaitByXpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']");
        ticket.click();
    }


    @Если("^открываю тикет$")
    public void открываю_тикет() throws Throwable {
        Driver.getServerInstance().findElement(new By.ByXPath("//*[@id=\"proj-search\"]")).sendKeys(CommonData.latestTicketId);
        Driver.getServerInstance().findElement(new By.ByXPath("//*[@id=\"search\"]/div/input[2]")).click();
    }


    @Допустим("^я начал трекать время в \"([^\"]*)\"$")
    public void я_начал_трекать_время_в(String dateTime) throws Throwable {
        commonDefinitions.открываю_главную_страницу_тракера();
        создаю_новый_тикет_со_статусом("accept");
        Driver.getClientInstance().getTimeProvider().setDateTime(dateTime);
        commonDefinitions.запускаю_клиентское_приложение();
        clientDefinitions.выбираю_последний_созданный_тикет();
    }

    @И("^перестал трекать время в \"([^\"]*)\"$")
    public void перестал_трекать_время_в(String dateTime) throws Throwable {
        Driver.getClientInstance().getTimeProvider().setDateTime(dateTime);
        clientDefinitions.жду_секунд(5);
    }


    @То("^в журнале работ вижу запись \"([^\"]*)\" и потраченное время на него \"([^\"]*)\"$")
    public void в_журнале_работ_вижу_запись_и_потраченное_время_на_него(String workLog, String timeSpent) throws Throwable {
        WebElement webElement = elementWaitByXpath("//*[@id=\"work-log\"]/ul//div[@class=\"comment\"][text()=\"" + workLog + "\"]/..//*[@class=\"time-spent\"]");
        Assert.assertEquals(timeSpent, webElement.getText());
    }

    private void commentIsInWorklog(String workLog) throws Exception {
        WebElement commentElement = elementWaitByXpath("//*[@id=\"work-log\"]/ul/li[last()]");
        Assert.assertEquals(workLog, commentElement.findElement(By.cssSelector(".comment")).getText());
    }

    private void timeSpentIsTrue(String timeSpent) throws Exception {
        WebElement timeSpentElement = elementWaitByXpath("//*[@id=\"work-log\"]/ul/li[last()]");
        List timeSpentWebElement =  timeSpentElement.findElements(By.cssSelector(".time-spent"));
        String[] timeSpentText = timeSpent.split(",");
        for (int i = 0; i < timeSpentWebElement.size(); i++) {
            WebElement weComment = (WebElement) timeSpentWebElement.get(i);
            Assert.assertEquals(timeSpentText[i], weComment.getText());
        }
    }

    @Допустим("^я создал и принял задачу на себя$")
    public void я_создал_и_принял_задачу_на_себя() throws Throwable {
        commonDefinitions.открываю_главную_страницу_тракера();
        создаю_новый_тикет_со_статусом("accept");
    }

    @И("^в окне \"([^\"]*)\" вижу затраченное время в часах \"([^\"]*)\" в минутах \"([^\"]*)\"$")
    public void в_окне_вижу_затраченное_время_в_часах_в_минутах(String window_name, String hours, String mins) throws Throwable {
        WebElement foundName = elementWaitByXpath("//*[@class='tt-header']");
        WebElement foundHours = elementWaitByCss(".tt-hours");
        WebElement foundMinutes = elementWaitByCss(".tt-minutes");

        Assert.assertEquals(window_name, foundName.getText());
        Assert.assertEquals(hours, foundHours.getText());
        Assert.assertEquals(mins, foundMinutes.getText());
    }

    @И("^открываю вкладку Tracker$")
    public void открываю_вкладку_Tracker() throws Throwable {
        commonDefinitions.открываю_главную_страницу_тракера();
        перехожу_во_вкладку("Tracker");
    }

    @То("^вижу на странице количество полос активности \"([^\"]*)\"$")
    public void вижу_на_странице_количество_полос_активности(Integer feeds) throws Throwable {
        List<WebElement> foundFeeds = Driver.getServerInstance().findElements(By.xpath("//*[@class='feed']//*[@id='activity-feed']"));
        Integer countFeeds = foundFeeds.size();
        Assert.assertEquals(feeds, countFeeds);
    }

    @И("^внутри полосы активности вижу что номер тикета равен последнему созданному тикету$")
    public void внутри_полосы_активности_вижу_что_номер_тикета_равен_последнему_созданному_тикету() throws Throwable {
        WebElement foundId = elementWaitByCss(".ticket-id");
        String lastId = CommonData.latestTicketId;
        Assert.assertEquals(lastId, foundId.getText());
    }

    @И("^внутри полосы активности вижу рабочую запись \"([^\"]*)\"$")
    public void внутри_полосы_активности_вижу_рабочую_запись(String comment) throws Throwable {
        WebElement foundComment = elementWaitByCss(".comment-content");
        Assert.assertEquals(comment, foundComment.getText());
    }

    @Если("^в полосе активности кликаю по номеру тикета$")
    public void в_полосе_активности_кликаю_по_номеру_тикета() throws Throwable {
        WebElement ticketLink = elementWaitByCss(".ticket-link");
        ticketLink.click();
    }

    @То("^вижу рабочий журнал \"([^\"]*)\" на открывшейся странице кликаемого тикета$")
    public void вижу_рабочий_журнал_на_открывшейся_странице_кликаемого_тикета(String WorkLog) throws Throwable {
        WebElement foundElement = elementWaitByCss("#no1");
        Assert.assertEquals(WorkLog, foundElement.getText().substring(0,8));
    }

    @И("^вижу в рабочем журнале запись \"([^\"]*)\"$")
    public void вижу_в_рабочем_журнале_запись(String comment) throws Throwable {
        WebElement foundComment = Driver.getServerInstance().findElement(By.xpath("//*[@class='item']//*[@class='comment']"));
        Assert.assertEquals(comment, foundComment.getText());
    }

    @Допустим("^зашел в трак под пользователем \"([^\"]*)\"$")
    public void зашел_под_пользователем(String user) throws Throwable {
        commonDefinitions.setUsername(user);
        commonDefinitions.открываю_главную_страницу_тракера();
    }

    @И("^создал тикет под названием \"([^\"]*)\" и запустил клиент под пользователем \"([^\"]*)\"$")
    public void создал_тикет_под_названиеми_запустил_клиент(String ticketName, String user) throws Throwable {
        создать_новый_тикет_под_названием(ticketName);
        commonDefinitions.setUsername(user);
        commonDefinitions.запускаю_клиентское_приложение();
        clientDefinitions.выбираю_последний_созданный_тикет();
    }

    @Когда("^через вкладку \"([^\"]*)\" кликаю по ссылке \"([^\"]*)\"$")
    public void через_вкладку_кликаю_по_ссылке(String tab, String link) throws Throwable {
        перехожу_во_вкладку(tab);
        открываю_отчет_со_ссылкой(link);
    }

    @Тогда("^вижу страницу с названием отчета \"([^\"]*)\" за последний месяц \"([^\"]*)\"$")
    public void вижу_страницу_с_названием_отчета_за_последний_месяц(String report, String monthYear) throws Throwable {
        String day_month_year = get_new_format(monthYear);
        String currentUrl = Driver.getServerInstance().getCurrentUrl();
        Driver.getServerInstance().navigate().to(""+currentUrl+"?fromDMY="+day_month_year+"");
        WebElement foundReport = elementWaitByXpath("//*[@class='report-label'][contains(text(), '"+report+"')]");
        WebElement foundMonthYear = elementWaitByCss(".date-display");
        Assert.assertEquals(report, foundReport.getText().replaceAll("\\s+\\(.*?\\)",""));
        Assert.assertEquals(monthYear, foundMonthYear.getText());
    }

    @То("^вижу страницу с отчетом за месяц \"([^\"]*)\"$")
    public void вижу_страницу_с_отчетом_за_месяц(String monthYear) throws Throwable {
        WebElement foundMonthYear = elementWaitByCss(".date-display");
        Assert.assertEquals(monthYear, foundMonthYear.getText());
    }

    @То("^вижу страницу с названием отчета за период \"([^\"]*)\"$")
    public void вижу_страницу_с_названием_отчета_за_период(String reportName) throws Throwable {
        WebElement foundReport = elementWaitByXpath("//*[@class='report-label']");
        Assert.assertEquals(reportName, foundReport.getText().replaceAll("\\s+\\(.*?\\)",""));
    }

    @И("^вижу вкладку для пользователя \"([^\"]*)\"$")
    public void вижу_вкладку_для_пользователя(String user) throws Throwable {
        WebElement foundName = elementWaitByXpath("//*[@class='report-result'][contains(text(), '"+user+"')]");
        Assert.assertEquals(user, foundName.getText().replaceAll("\\s+\\(.*?\\)",""));
    }

    @Если("^кликаю по вкладке \"([^\"]*)\"$")
    public void кликаю_по_вкладке(String user) throws Throwable {
        WebElement foundName = elementWaitByXpath("//*[@class='report-result'][contains(text(), '"+user+"')]");
        foundName.click();
    }

    @То("^вижу таблицу с названием тикета \"([^\"]*)\", затраченным временем \"([^\"]*)\" и общим временем \"([^\"]*)\"$")
    public void вижу_таблицу_с_названием_тикета_и_затраченным_временем(String ticketName, String ticketTime, String totalTime) throws Throwable {
        WebElement foundName = elementWaitByXpath("//*[@class='summary']//*[contains(text(), '"+ticketName+"')]");
        Assert.assertEquals(ticketName, foundName.getText());
        WebElement foundTime = elementWaitByXpath("//*[@class='ticket-time'][contains(text(), '"+ticketTime+"')]");
        Assert.assertEquals(ticketTime, foundTime.getText());
        WebElement foundTotal = elementWaitByXpath("//*[@class='tickets-time'][contains(text(), '"+totalTime+"')]");
        Assert.assertEquals(totalTime, foundTotal.getText());
    }

    @Если("^на переключателе даты кликаю на стрелку \"([^\"]*)\"$")
    public void на_переключателе_даты_кликаю_на_стрелку(String arrow) throws Throwable {
        WebElement foundArrow = elementWaitByXpath("//*[@id='date-switcher']//*[contains(text(), '"+arrow+"')]");
        foundArrow.click();
    }

    @Если("^вижу страницу с формой для построения отчета \"([^\"]*)\"$")
    public void вижу_страницу_с_формой_для_построения_отчета(String formName) throws Throwable {
        WebElement foundName = elementWaitByXpath("//p[contains(text(), '"+formName+"')]");
        Assert.assertEquals(formName, foundName.getText());
    }

    @Если("^указываю период с \"([^\"]*)\" по \"([^\"]*)\"$")
    public void указываю_период_с(String from,String to) throws Throwable {
        WebElement fromDate = elementWaitByCss("#fromDMY");
        fromDate.clear();
        fromDate.sendKeys(from);
        fromDate.sendKeys();
        WebElement toDate = elementWaitByCss("#toDMY");
        toDate.clear();
        toDate.sendKeys(to);
        toDate.sendKeys();
        WebElement foundReport = elementWaitByXpath("//*[@class='report-label']");
        foundReport.click();
    }

    @И("^нажимаю на кнопку \"([^\"]*)\"$")
    public void нажимаю_на_кнопку(String button) throws Throwable {
        WebElement trackerCalendarMakeReportButton = elementWaitByXpath("//ul[@id='nav']//input[@value='"+button+"']");
        trackerCalendarMakeReportButton.click();
    }
    public void создать_новый_тикет_под_названием(String ticketName) throws Throwable{
        перехожу_во_вкладку("New Ticket");
        Driver.getServerInstance().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement summary = Driver.getServerInstance().findElement(By.xpath("//*[@id=\"field-summary\"]"))    ;
        summary.sendKeys(ticketName);
        Driver.getServerInstance().findElement(By.name("submit")).click();
        CommonData.latestTicketId = Driver.getServerInstance().findElement(By.className("trac-id")).getText();
        CommonData.latestTicketSummary = ticketName;
        указываю_статус_редактируемому_тикету("accept");
    }

    public String get_new_format(String monthYear){
        String day_month_year="";
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        try{
            Date date = formatter.parse(monthYear);
            day_month_year = new SimpleDateFormat("dd-MM-yyyy").format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return day_month_year;
    }
}
