package net.itattractor.features;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.PendingException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.uispec4j.Window;

import java.util.List;

public class MyStepdefs {
    Adapter adapter = new Adapter();
    private Window loginWindow;
    private Window tasksWindow;
    private Window recordWindow;
    private String sentComment;

    WebDriver webDriver;

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

    @And("^I click submit button$")
    public void I_click_submit_button() throws Throwable {
        loginWindow.getButton("submit").click();
    }

    @Then("^I should see tasks form$")
    public void I_should_see_tasks_form() throws Throwable {
        tasksWindow = adapter.getTasksWindow();
        Assert.assertEquals(tasksWindow.getTitle(), "tasks form");
    }

    @And("^I click \"([^\"]*)\" button$")
    public void I_click_button(String name) throws Throwable {
         tasksWindow.getButton(name).click();
    }

    @And("^I should see \"([^\"]*)\"$")
    public void I_should_see(String expected) throws Throwable {
        recordWindow = adapter.getRecordWindow();
        String actual = recordWindow.getTitle();
        Assert.assertEquals(expected, actual);
    }

    @And("^I send comment \"([^\"]*)\" and submit$")
    public void I_send_comment_and_submit(String comment) throws Throwable {
        sentComment = comment;
        recordWindow.getInputTextBox("Label").setText(comment);
        recordWindow.getButton("ok").click();
    }


    @And("^login to trac in url \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void login_to_trac_in_url_and_username_and_password(String url, String username, String password) throws Throwable {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("http://"+username+":"+password+"@"+url);
    }
//
//    @And("^I go to track \"([^\"]*)\" and username \"([^\"]*)\" and password \"([^\"]*)\"$")
//    public void I_go_to_track_and_username_and_password(String url, String username, String password) throws Throwable {
//        webDriver = new FirefoxDriver();
//        String newUrl = "http://" + username + ":" + password + "@" + url;
//        webDriver.navigate().to(newUrl);
//        List<WebElement> change = webDriver. findElements(By.cssSelector(".comment p"));
//        Assert.assertEquals(sentComment, (change.get(change.size()-1)).getText());
//    }

    @And("^I go to track \"([^\"]*)\"$")
    public void I_go_to_track(String url) throws Throwable {
        webDriver.navigate().to(url);
        List<WebElement> change = webDriver. findElements(By.cssSelector(".comment p"));
        Assert.assertEquals(sentComment, (change.get(change.size()-1)).getText());
    }
    /*
    @Given("^I launch application with url \"([^\"]*)\", username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_launch_application_with_url_username_and_password(String url, String username, String password) throws Throwable {
        I_run_application();
        I_input_url_and_username_and_password(url, username, password);
        I_click_submit_button();
        I_should_see_tasks_form();

    }

    @Then("^I check if ticket doesn't exist in combobox$")
    public void I_check_if_ticket_doesn_t_exist_in_combobox() throws Throwable {
        tasksWindow.getComboBox().contains()
    }
    */
}
