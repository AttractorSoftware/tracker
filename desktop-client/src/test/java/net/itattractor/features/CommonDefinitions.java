package net.itattractor.features;

import cucumber.api.java.ru.Допустим;
import net.itattractor.features.helper.Driver;
import org.uispec4j.Window;


public class CommonDefinitions {

    private Window loginWindow;

    private String url = "http://127.0.0.1:8000/trac-env";

    private String username = "tester";
    private String password = "tester";
    @Допустим("^запускаю клиентское приложение$")
    public void запускаю_клиентское_приложение() throws Throwable {
        loginWindow = Driver.getClientInstance().getMainWindow();
        loginWindow.getInputTextBox("url").setText(url);
        loginWindow.getInputTextBox("username").setText(username);
        loginWindow.getPasswordField().setPassword(password);
        loginWindow.getButton("submit").click();
    }

    @Допустим("^открываю главную страницу тракера$")
    public void открываю_главную_страницу_тракера() throws Throwable {
        Driver.getServerInstance().navigate().to("http://" + username + ":" + password + "@" + url.substring("http://".length()) + "/login");
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    void waitForScreenshotToBeSended() throws InterruptedException {
        int milisecondsToWait = 2000;
        Thread.sleep(milisecondsToWait);
    }
}

