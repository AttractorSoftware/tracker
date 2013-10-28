package net.itattractor.features;

import cucumber.api.java.After;
import cucumber.api.java.ru.*;
import cucumber.runtime.PendingException;
import net.itattractor.Config;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerDefinitions {

    private WebElement screenshotBlock;

    @И("^Перехожу во вкладку \"([^\"]*)\"$")
    public void Перехожу_во_вкладку(String tab) throws Throwable {
        Driver.getServerInstance().findElement(By.xpath("//a[text()='" + tab + "']")).click();
    }

    @Тогда("^Увижу скриншот юзера \"([^\"]*)\" с количеством кликаний мышью \"([^\"]*)\" и нажатием клавиатуры \"([^\"]*)\" раз$")
    public void Увижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(String username, String clickCount, String pressCount) throws Throwable {
        List<WebElement> elements = Driver.getServerInstance().findElements(By.className("tracker-image"));
        WebElement element = elements.get(elements.size() - 1);

        WebElement mouse_count_cont = element.findElement(By.cssSelector(".mouse-event-count"));
        WebElement keyboard_count_cont = element.findElement(By.cssSelector(".keyboard-event-count"));

        Assert.assertEquals(mouse_count_cont.getText(), clickCount);
        Assert.assertEquals(keyboard_count_cont.getText(), pressCount);
    }

    @Если("^Открою отчет с ссылкой \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void Открою_отчет_с_ссылкой_пользователя(String report_name, String author) throws Throwable {
        List<WebElement> users = Driver.getServerInstance().findElements(By.cssSelector("#content ul"));
        if (!users.get(0).getText().equals("No users found")) {
            Driver.getServerInstance().findElement(By.xpath("//li[contains(@author,'" + author + "')]/a[text()='" + report_name + "']")).click();
        }
    }

    @Тогда("^Должен увидеть в меню вкладку \"([^\"]*)\"$")
    public void Должен_увидеть_в_меню_вкладку(String tab) throws Throwable {
        List<WebElement> items = Driver.getServerInstance().findElements(By.cssSelector("#mainnav a"));
        boolean contains = false;
        for (WebElement item : items) {
            if (tab.equals(item.getText())) {
                contains = true;
            }
        }
        Assert.assertTrue(contains);
    }

    @И("^Вижу этот добавленный комментарий$")
    public void Вижу_этот_добавленный_комментарий() throws Throwable {
        Driver.getServerInstance().navigate().to("http://127.0.0.1:8000/trac-env/ticket/" + CommonData.latestTicketId);
        List<WebElement> change = Driver.getServerInstance().findElements(By.cssSelector(".comment p"));
        Assert.assertEquals(CommonData.comment, (change.get(change.size() - 1)).getText());
    }

    @Когда("^Заполняю поля нового тикета и сохраняю его$")
    public void Заполняю_поля_нового_тикета_и_сохраняю_его() throws Throwable {
        Driver.getServerInstance().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement summary = Driver.getServerInstance().findElement(By.xpath("//*[@id=\"field-summary\"]"))    ;
        String ticketSummary = new Date().toString();
        summary.sendKeys(ticketSummary);
        Driver.getServerInstance().findElement(By.name("submit")).click();
        CommonData.latestTicketId = Driver.getServerInstance().findElement(By.className("trac-id")).getText();
        CommonData.latestTicketSummary = ticketSummary;
    }

    @И("^Указываю статуст \"([^\"]*)\" редактируемому тикету$")
    public void Указываю_статуст_редактируемому_тикету(String status) throws Throwable {
        WebElement modifyBtn = Driver.getServerInstance().findElement(By.cssSelector("#no4"));
        modifyBtn.click();
        Driver.getServerInstance().findElement(By.id("action_" + status)).click();
        Driver.getServerInstance().findElement(By.name("submit")).click();
    }

    @То("^Увижу заголовок \"([^\"]*)\"$")
    public void Увижу_заголовок(String header) throws Throwable {
        WebElement reportFor = Driver.getServerInstance().findElement(By.xpath("//ul[@id='nav']/p"));
        Assert.assertEquals(header, reportFor.getText());
    }

    @Допустим("^Создаю новый тикет со статусом \"([^\"]*)\"$")
    public void Создаю_новый_тикет_со_статусом(String ticketStatus) throws Throwable {
        Перехожу_во_вкладку("New Ticket");
        Заполняю_поля_нового_тикета_и_сохраняю_его();
        Указываю_статуст_редактируемому_тикету(ticketStatus);
    }


    @И("^Укажу период с \"([^\"]*)\" по \"([^\"]*)\"$")
    public void Укажу_период_(String from,String to) throws Throwable {
        WebElement fromDate = Driver.getServerInstance().findElement(By.cssSelector("#fromDMY"));
        fromDate.clear();
        fromDate.sendKeys(from);
        fromDate.sendKeys();
        WebElement toDate = Driver.getServerInstance().findElement(By.cssSelector("#toDMY"));
        toDate.clear();
        toDate.sendKeys(to);
        toDate.sendKeys();
        WebElement trackerCalendarMakeReportButton = Driver.getServerInstance().findElement(By.xpath("//ul[@id='nav']//input[@value='Построить отчет']"));
        trackerCalendarMakeReportButton.click();
    }
    @И("^Указываю дату \"([^\"]*)\"$")
    public void Указываю_дату(String date) throws Throwable {
        WebElement selectdate = Driver.getServerInstance().findElement(By.xpath("//form[@id='tracker-filter']//input[@id='tracker-date']"));
        selectdate.clear();
        selectdate.sendKeys(date);
        WebElement updateButton = Driver.getServerInstance().findElement(By.xpath("//div[@class='buttons']//input[@value='Update']"));
        updateButton.click();
    }

    @Тогда("^Вижу у последнего созданного тикета \"([^\"]*)\" наработанных минут$")
    public void Вижу_у_последнего_созданного_тикета_наработанных_минут(String mins) throws Throwable {
        Thread.sleep(5000);
        WebElement min_cont = Driver.getServerInstance().findElement(By.xpath("//div[@id='content']//div[@id='" + CommonData.latestTicketSummary + "']//b[@id='min']"));
        Assert.assertEquals(min_cont.getText(), mins);
    }

    @Если("^Кликаю по первому скриншоту$")
    public void Кликаю_по_первому_скриншоту() throws Throwable {
        screenshotBlock = Driver.getServerInstance().findElement(By.cssSelector(".tracker-image"));
        screenshotBlock.findElement(By.xpath("a/img")).click();
    }

    @То("^Должен увидеть модальное окно скриншота$")
    public void Должен_увидеть_модальное_окно_скриншота() throws Throwable {
        WebElement modalWindow = screenshotBlock.findElement(By.xpath("//./div[@id='boxes']/div"));
        String display = modalWindow.getCssValue("display");
        Assert.assertEquals(display, "block");
    }

    @After
    public void closeBrowser() throws Exception {
        Driver.closeServerInstance();
    }

    @И("^Открою отчет с ссылкой \"([^\"]*)\" за \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void Открою_отчет_с_ссылкой_за_пользователя(String link_text, String date, String author) throws Throwable {
        List<WebElement> users = Driver.getServerInstance().findElements(By.cssSelector("#content ul"));
        if (!users.get(0).getText().equals("No users found")) {
            Driver.getServerInstance().findElement(By.xpath("//li[contains(@author,'" + author + "')]/a[text()='" + link_text + "']")).click();
        }
        Driver.getServerInstance().findElement(By.xpath("//*[@id=\"tracker-date\"]")).clear();
        Driver.getServerInstance().findElement(By.xpath("//*[@id=\"tracker-date\"]")).sendKeys(date);
        Driver.getServerInstance().findElement(By.xpath("//*[@id=\"tracker-filter\"]/div[2]/input")).click();
    }
}
