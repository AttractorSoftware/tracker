package net.itattractor.features;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.itattractor.helpers.SendScreenshotHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TrackerPluginStepDefinitions {

    private String host;
    private String username;
    private String password;

    private WebDriver webDriver;

    private WebElement usernameWebElement;
    private WebElement trackerFormUpdateButton;
    private WebElement chosenImage;
    private WebElement modalWindow;
    private WebElement divContainer;
    private WebElement tabTracker;

    private void initConfigurations(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }


    private void setHost(String host) {
        this.host = host;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void navigateBrowser(String host) {
        webDriver.navigate().to("http://" + username + ":" + password + "@" + host);
    }


    private void findUserByUsername(String username) {
        List<WebElement> elements = webDriver.findElements(By.xpath("//./div[@id='content']/ul/li/a"));

        username = username.length() > 0 ? username : this.username;

        for (WebElement element : elements) {
            if (element.getText().equals(username)) {

                usernameWebElement = element;
                break;
            }
        }
    }

    @After
    public void quitFromBrowser() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Given("^I have host \"([^\"]*)\" username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_have_host_username_and_password(String host, String username, String password) throws Throwable {
        initConfigurations(host, username, password);
    }

    @Then("^I should see tab \"([^\"]*)\"$")
    public void I_should_see_tab(String expectedTab) throws Throwable {
        WebElement item = webDriver.findElement(By.xpath("//./div[@id='mainnav']/ul/li/a[contains(., 'Tracker')]"));

        String actualTab = item.getText();
        Assert.assertEquals(expectedTab, actualTab);
    }

    @When("^I open host \"([^\"]*)\"$")
    public void I_open_host(String host) throws Throwable {
        navigateBrowser(host);
    }

    @Then("^I should see status \"([^\"]*)\"$")
    public void I_should_see_status(String expectedStatus) throws Throwable {
        WebElement webElement = webDriver.findElement(By.cssSelector("pre"));
        String actualStatus = webElement.getText();

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Given("^I have a page which displays a list of users$")
    public void I_have_a_page_which_displays_a_list_of_users() throws Throwable {
        webDriver.get("");
    }

    @And("^find user \"([^\"]*)\"$")
    public void find_user(String expectedUsername) throws Throwable {

        findUserByUsername(expectedUsername);
        String actualUsername = usernameWebElement.getText();

        Assert.assertEquals(expectedUsername, actualUsername);
    }

    @And("^click on the user$")
    public void click_on_the_user() throws Throwable {
        usernameWebElement.click();
    }

    @Then("^I should see text \"([^\"]*)\"$")
    public void I_should_see_text(String expectedText) throws Throwable {
        WebElement element = webDriver.findElement(By.xpath("//./div[@id='content']/h2"));
        String actualText = element.getText();

        Assert.assertEquals(expectedText, actualText);

    }

    @And("^click \"([^\"]*)\" button$")
    public void click_button(String expectedButtonText) throws Throwable {
        trackerFormUpdateButton = webDriver.findElement(By.cssSelector("input[value=" + expectedButtonText + "]"));
        trackerFormUpdateButton.click();
    }

    @Then("^I should see screenshots$")
    public void I_should_see_screenshots() throws Throwable {
        WebElement image = webDriver.findElement(By.cssSelector(".tracker-image img"));
        Assert.assertTrue(image.isDisplayed());
    }

    @Given("^I have been sent several screenshots$")
    public void user_has_been_sent_several_screenshots() throws Throwable {
        SendScreenshotHelper screenshotHelper = new SendScreenshotHelper();
        for (int i = 0; i < 1; i++) {
            screenshotHelper.sendScreenshot("http://" + this.host, this.username, this.password);
        }
    }

    @And("^find default user$")
    public void find_default_user() throws Throwable {
        findUserByUsername("");
        Assert.assertEquals(usernameWebElement.getText(), this.username);
    }

    @And("^choose today date in calendar$")
    public void choose_today_date_in_calendar() throws Throwable {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String today = sdf.format(date);

        WebElement trackerDate = webDriver.findElement(By.cssSelector("#tracker-date"));
        trackerDate.clear();
        trackerDate.sendKeys(today);
    }

    @And("^choose one image$")
    public void choose_one_image() throws Throwable {
        divContainer = webDriver.findElement(By.cssSelector(".tracker-image"));
        chosenImage = divContainer.findElement(By.xpath("//./a/img"));

        Assert.assertTrue(chosenImage.isDisplayed());
    }

    @And("^click on image$")
    public void click_on_image() throws Throwable {
        chosenImage.click();
    }

    @Then("^I should see modal window$")
    public void I_should_see_modal_window() throws Throwable {
        WebElement modalWindow = divContainer.findElement(By.xpath("//./div[@id='boxes']/div"));
        String display = modalWindow.getCssValue("display");

        Assert.assertEquals(display, "block");
    }

    @And("^input ticket title \"([^\"]*)\"$")
    public void input_ticket_title(String title) throws Throwable {
        WebElement element = webDriver.findElement(By.cssSelector("input[id=field-summary]"));
        element.sendKeys(title);
    }

    @And("^input ticket description \"([^\"]*)\"$")
    public void input_ticket_description(String description) throws Throwable {
        WebElement element = webDriver.findElement(By.cssSelector("textarea[id=field-description]"));
        element.sendKeys(description);
    }

    @And("^input ticket default owner$")
    public void input_ticket_default_owner() throws Throwable {
        WebElement element = webDriver.findElement(By.cssSelector("input[id=field-owner]"));
        element.sendKeys(this.username);
    }

//    @And("^click \"([^\"]*)\"$")
//    public void click(String value) throws Throwable {
//        WebElement element = webDriver.findElement(By.cssSelector("input[value=" + value + "]"));
//        element.click();
//    }

    @Then("^I should see ticket \"([^\"]*)\"$")
    public void I_should_see_ticket(String expectedTicket) throws Throwable {
        List<WebElement> elements = webDriver.findElements(By.xpath("."));
        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
    }

}
