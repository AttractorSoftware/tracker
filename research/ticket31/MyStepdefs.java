package itattractor;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.PendingException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created with IntelliJ IDEA.
 * User: esdp
 * Date: 8/1/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyStepdefs {
    private WebDriver driver = new FirefoxDriver();
    @Given("^Opened WebDriver \"([^\"]*)\"$")
    public void Opened_WebDriver(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        if(driver != null)
        {
            System.out.println("Opened_WebDriver:" + driver);
        }
    }

    @Then("^I see title of web page \"([^\"]*)\"$")
    public void The_site_says(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        if(arg1.compareTo("zakon") != -1){
            System.out.println("MyStepdefs.The_site_says");
        }
        else{
            throw new PendingException();
        }
    }

    @When("^I visit page \"([^\"]*)\" login \"([^\"]*)\" pass \"([^\"]*)\"$")
    public void I_visit_page_login_pass(String url, String username, String password) throws Throwable {
        url = url.replaceFirst("http://", "http://"+username+":"+password+"@");
        driver.navigate().to(url);
    }

    @And("^I see content description on page \"([^\"]*)\"$")
    public void I_see_content_description_on_page(String arg1) throws Throwable {
        // Express the Regexp above with the code you wish you had
        String text = new String();
        text = driver.findElement(By.id(arg1)).getText();
        if(arg1.compareTo(text) != -1){
            System.out.println("MyStepdefs.I_see_content_description_on_page");
        }
        else{
            throw new PendingException();
        }
    }
}
