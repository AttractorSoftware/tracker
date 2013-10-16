package net.itattractor.features;

import cucumber.api.java.ru.*;
import cucumber.runtime.PendingException;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServerDefinitions {

    @И("^Перехожу во вкладку \"([^\"]*)\"$")
    public void Перехожу_во_вкладку(String tab) throws Throwable {
        Driver.getServerInstance().findElement(By.xpath("//a[text()='" + tab + "']")).click();
    }

    @Тогда("^Увижу скриншот юзера \"([^\"]*)\" с количеством кликаний мышью \"([^\"]*)\" и нажатием клавиатуры \"([^\"]*)\" раз$")
    public void Увижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(String username, String clickCount, String pressCount) throws Throwable {
        List<WebElement> elements = Driver.getServerInstance().findElements(By.className("tracker-image"));
        for (WebElement element : elements) {
            System.out.println("1--");
            System.out.println(element.getText());
            System.out.println("--1");
            System.out.println(element.getTagName());
            System.out.println("!!!");
        }
        WebElement element = elements.get(elements.size() - 1);


        WebElement mouse_count_cont = element.findElement(By.cssSelector(".mouse-event-count"));
        WebElement keyboard_count_cont = element.findElement(By.cssSelector(".keyboard-event-count"));


        Assert.assertEquals(mouse_count_cont.getText(), clickCount);
        Assert.assertEquals(keyboard_count_cont.getText(), pressCount);
    }

    @Если("^Открою отчет с ссылкой \"([^\"]*)\" пользователя \"([^\"]*)\"$")
    public void Открою_отчет_с_ссылкой_пользователя(String report_name, String author) throws Throwable {
        Driver.getServerInstance().findElement(By.xpath("//li[contains(@author,'"+author+"')]/a[text()='"+report_name+"']")).click();
    }

    @Когда("^Заполняю поля нового тикета и сохраняю его$")
    public void Заполняю_поля_нового_тикета_и_сохраняю_его() throws Throwable {

        WebElement summary = Driver.getServerInstance().findElement(By.id("field-summary"));
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
        Driver.getServerInstance().findElement(By.id("action_"+status)).click();
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

    @И("^Укажу период с сегодняшнего по завтрашний день$")
    public void Укажу_период_с_сегодняшнего_по_завтрашний_день() throws Throwable {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = cal.getTime();

        WebElement fromDate = Driver.getServerInstance().findElement(By.cssSelector("#fromDMY"));
        fromDate.clear();
        fromDate.sendKeys(dateFormat.format(date));
        fromDate.sendKeys();


        WebElement toDate = Driver.getServerInstance().findElement(By.cssSelector("#toDMY"));
        toDate.clear();
        toDate.sendKeys(dateFormat.format(tomorrow));
        toDate.sendKeys();

        WebElement trackerCalendarMakeReportButton = Driver.getServerInstance().findElement(By.xpath("//ul[@id='nav']//input[@value='Построить отчет']"));
        trackerCalendarMakeReportButton.click();
    }

    @Тогда("^Вижу у последнего созданного тикета \"([^\"]*)\" наработанных минут$")
    public void Вижу_у_последнего_созданного_тикета_наработанных_минут(String mins) throws Throwable {
        WebElement min_cont = Driver.getServerInstance().findElement(By.xpath("//div[@id='content']//div[@id='"+CommonData.latestTicketSummary+"']//b[@id='min']"));
        Assert.assertEquals(min_cont.getText(),mins);
    }
}
