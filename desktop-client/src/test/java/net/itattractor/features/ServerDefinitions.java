package net.itattractor.features;

import cucumber.api.java.After;
import cucumber.api.java.ru.*;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerDefinitions {
    private final CommonDefinitions commonDefinitions;
    private final ClientDefinitions clientDefinitions;

    public ServerDefinitions(CommonDefinitions commonDefinitions, ClientDefinitions clientDefinitions) {
        this.commonDefinitions = commonDefinitions;
        this.clientDefinitions = clientDefinitions;
    }

    @И("^перехожу во вкладку \"([^\"]*)\"$")
    public void перехожу_во_вкладку(String tab) throws Throwable {
        Driver.getServerInstance().findElement(By.xpath("//a[text()='" + tab + "']")).click();
    }

    @То("^вижу скриншот юзера \"([^\"]*)\" с количеством кликаний мышью \"([^\"]*)\" и нажатием клавиатуры \"([^\"]*)\" раз$")
    public void вижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(String username, String clickCount, String pressCount) throws Throwable {
        List<WebElement> elements = Driver.getServerInstance().findElements(By.className("tracker-image"));
        WebElement element = elements.get(elements.size() - 1);

        WebElement mouse_count_cont = element.findElement(By.cssSelector(".mouse-event-count"));
        WebElement keyboard_count_cont = element.findElement(By.cssSelector(".keyboard-event-count"));

        Assert.assertEquals(mouse_count_cont.getText(), clickCount);
        Assert.assertEquals(keyboard_count_cont.getText(), pressCount);
    }

    @Если("^открою отчет с ссылкой \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void открою_отчет_с_ссылкой_пользователя(String report_name, String author) throws Throwable {
        List<WebElement> users = Driver.getServerInstance().findElements(By.cssSelector("#content ul"));
        if (!users.get(0).getText().equals("No users found")) {
            Driver.getServerInstance().findElement(By.xpath("//li[contains(@author,'" + author + "')]/a[text()='" + report_name + "']")).click();
        }
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
    @И("^перехожу на страницу данного тикета$")
    public void перехожу_на_страницу_данного_тикета() throws Throwable {
        Driver.getServerInstance().navigate().to("http://127.0.0.1:8000/trac-env/ticket/" + CommonData.latestTicketId);
    }

    @И("^вижу этот добавленный комментарий$")
    public void вижу_этот_добавленный_комментарий() throws Throwable {
        List<WebElement> change = Driver.getServerInstance().findElements(By.cssSelector(".comment p"));
        Assert.assertEquals(CommonData.comment, (change.get(change.size() - 1)).getText());
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
        Thread.sleep(5000);
        WebElement element = elementWaitByXpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']//b[@id='min']");
        Assert.assertEquals(element.getText(), mins);
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
        WebElement report = elementWaitByXpath("//li[contains(@author,'" + username + "')]/a[@class=\"daily_report\"]");
        Assert.assertEquals(link, report.getText());
    }

    private WebElement elementWaitByXpath(String xpathExpression) throws Exception {
        WebDriverWait wait = new WebDriverWait(Driver.getServerInstance(), 10);

        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
    }

    private WebElement elementWaitByCss(String expression) throws Exception {
        WebDriverWait wait = new WebDriverWait(Driver.getServerInstance(), 10);

        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(expression)));
    }
}
