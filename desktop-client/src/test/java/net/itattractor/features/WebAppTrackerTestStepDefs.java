package net.itattractor.features;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class WebAppTrackerTestStepDefs {

    WebDriver webDriver;
    private String url;
    private String username;
    private String password;

    @Given("^I'm working with \"([^\"]*)\" and my username is \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_m_working_with_and_my_username_is_and_password(String url, String usernmae, String password) throws Throwable {
        this.url = url;
        this.username = usernmae;
        this.password = password;
    }

    @Given("^I open browser$")
    public void I_open_browser() throws Throwable {
        webDriver = new FirefoxDriver();
    }


    @When("^navigate to trac instance$")
    public void navigate_to_trac_instance() throws Throwable {
        webDriver.navigate().to("http://" + username + ":" + password + "@" + url);
    }

    @Then("^should see tracker tab in main menu$")
    public void should_see_tracker_tab_in_main_menu() throws Throwable {
        List<WebElement> items = webDriver.findElements(By.cssSelector("#mainnav a"));
        for (WebElement item : items) {
            if ("Tracker".equals(item.getText())) {
                Assert.assertEquals("Tracker", item.getText());

            }
        }
        webDriver.quit();
    }

    @Given("^I'm on main page of trac$")
    public void I_m_on_main_page_of_tracker() throws Throwable {
        I_open_browser();
        navigate_to_trac_instance();
    }


    @When("^click on Tracker menu link$")
    public void click_on_Tracker_menu_link() throws Throwable {
        webDriver.findElement(By.xpath("//a[text()='Tracker']")).click();

    }

    @Then("^should see list of users and choose first$")
    public void should_see_list_of_users_and_choose_first() throws Throwable {
        WebElement user = webDriver.findElement(By.cssSelector("#nav li a"));
        user.click();

    }

    @And("^should see screenshot work flow by date \"([^\"]*)\"$")
    public void should_see_screenshot_work_flow_by_date(String date) throws Throwable {
        WebElement trackerDate = webDriver.findElement(By.cssSelector("#tracker-date"));
        trackerDate.clear();
        trackerDate.sendKeys(date);

        WebElement trackerCalendarUpdateButton = webDriver.findElement(By.cssSelector("input[name=update]"));
        trackerCalendarUpdateButton.click();

        WebElement image = webDriver.findElement(By.cssSelector(".tracker-image img"));
        Assert.assertTrue(image.isDisplayed());
        webDriver.quit();
    }
}
