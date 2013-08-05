package net.itattractor.features;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.uispec4j.Window;

public class MyStepdefs {
    Adapter adapter = new Adapter();
    private Window loginWindow;
    private Window tasksWindow;
    private Window recordWindow;
    private String sentComment;

    WebDriver webDriver;
    private String lastComment;

    @Given("^I run application$")
    public void I_run_application() throws Throwable {
        loginWindow = adapter.getMainWindow();
        Assert.assertEquals(loginWindow.getTitle(), "login form");
    }

    @cucumber.api.java.en.When("^I input url \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_input_url_and_username_and_password(String url, String username, String password) throws Throwable {
        loginWindow.getInputTextBox("url").setText(url);
        loginWindow.getInputTextBox("username").setText(username);
        loginWindow.getPasswordField().setPassword(password);
    }

    @And("^I click \"([^\"]*)\" button$")
    public void I_click_submit_button(String arg1) throws Throwable {
        loginWindow.getButton(arg1).click();
    }

    @Then("^I should see \"([^\"]*)\"$")
    public void I_should_see_tasks_form(String arg1) throws Throwable {
        tasksWindow = adapter.getTasksWindow();
        Assert.assertEquals(tasksWindow.getTitle(), arg1);
    }

}
