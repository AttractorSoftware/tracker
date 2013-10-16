package net.itattractor.features;

import cucumber.api.java.ru.Допустим;
import net.itattractor.features.helper.Driver;
import org.uispec4j.Window;


public class CommonDefinitions {

    private Window loginWindow;

    private String url = "http://127.0.0.1:8000/trac-env";
    private String username = "tester";
    private String password = "tester";

    @Допустим("^Запускаю клиентское приложение$")
    public void Запускаю_клиентское_приложение() throws Throwable {
        loginWindow = Driver.getClientInstance().getMainWindow();
        loginWindow.getInputTextBox("url").setText(url);
        loginWindow.getInputTextBox("username").setText(username);
        loginWindow.getPasswordField().setPassword(password);
        loginWindow.getButton("submit").click();
    }

    @Допустим("^Запускаю серверное приложение$")
    public void Запускаю_серверное_приложение() throws Throwable {
        Driver.getServerInstance().navigate().to("http://" + username + ":" + password + "@" + url.substring("http://".length()) + "/login");
    }

}

