package net.itattractor.features;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.Тогда;
import cucumber.runtime.PendingException;
import net.itattractor.features.helper.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ServerDefinitions {
    @Если("^Открою \"([^\"]*)\"$")
    public void Открою(String dailyReport) throws Throwable {
        Driver.getServerInstance().findElement(By.xpath("//a[text()='" + dailyReport + "']")).click();
    }

    @Тогда("^Увижу скриншот юзера \"([^\"]*)\" с количеством кликаний мышью \"([^\"]*)\" и нажатием клавиатуры \"([^\"]*)\" раз$")
    public void Увижу_скриншот_юзера_с_количеством_кликаний_мышью_и_нажатием_клавиатуры_раз(String username, String clickCount, String pressCount) throws Throwable {
        WebElement frame = Driver.getServerInstance().findElement(By.cssSelector("div[author='" + username + "']"));
        WebElement mouse_count_cont = Driver.getServerInstance().findElement(By.cssSelector("div[author='"+username+"'] .mouse-event-count"));
        WebElement keyboard_count_cont = Driver.getServerInstance().findElement(By.cssSelector("div[author='"+username+"'] .keyboard-event-count"));

        Assert.assertEquals(username, frame.getAttribute("author"));
        Assert.assertEquals(mouse_count_cont.getText(), clickCount);
        Assert.assertEquals(keyboard_count_cont.getText(), pressCount);
    }
}
