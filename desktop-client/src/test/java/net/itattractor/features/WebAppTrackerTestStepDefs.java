package net.itattractor.features;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.itattractor.Ticket;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.uispec4j.Window;
import org.uispec4j.assertion.Assertion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WebAppTrackerTestStepDefs {
    Adapter adapter;
    private Window loginWindow;
    private Window tasksWindow;
    private Window recordWindow;

    protected String url;
    protected String username;
    protected String password;

    WebDriver webDriver;
    private String ticketSummary;
    private String ticketId;
    private String sentComment;


    DateFormat dateFormat;
    Date date;
    public Integer minutes;


    protected void initWebDriver() {
        if (webDriver == null)
            webDriver = new FirefoxDriver();
    }


    @Given("^I open browser$")
    public void I_open_browser() throws Throwable {
        webDriver = new FirefoxDriver();
    }


    @Given("^I'm working with \"([^\"]*)\" and my username is \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_m_working_with_and_my_username_is_and_password(String url, String usernmae, String password) throws Throwable {
        this.url = url;
        this.username = usernmae;
        this.password = password;
    }

    @Given("^The browser is run$")
    public void The_browser_is_run() throws Throwable {
        initWebDriver();
    }

    protected void navigateBrowser(String host) {
        webDriver.navigate().to("http://" + username + ":" + password + "@" + host);
    }

    @When("^navigate to trac instance$")
    public void navigate_to_trac_instance() throws Throwable {
        webDriver.navigate().to("http://" + username + ":" + password + "@" + url.substring(url.indexOf(':') + 3));
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

    @When("^I create new ticket$")
    public void I_create_new_ticket() throws Throwable {
        webDriver.findElement(By.xpath("//a[text()='New Ticket']")).click();
        WebElement summary = webDriver.findElement(By.id("field-summary"));
        ticketSummary = new Date().toString();
        summary.sendKeys(ticketSummary);
        webDriver.findElement(By.name("submit")).click();
        ticketId = webDriver.findElement(By.className("trac-id")).getText();
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


    @And("^close browser$")
    public void close_browser() throws Throwable {
        webDriver.quit();
    }


    @Given("^First user from user list on tracker main page is chosen$")
    public void First_user_from_user_list_on_tracker_main_page_is_chosen() throws Throwable {
        I_m_on_main_page_of_tracker();
        click_on_Tracker_menu_link();
        should_see_list_of_users_and_choose_first();
    }

    @When("^click on that user$")
    public void click_on_that_user() throws Throwable {

    }

    @And("^choose date in \"([^\"]*)\" calendar$")
    public void choose_date_in_calendar(String date) throws Throwable {
        WebElement trackerDate = webDriver.findElement(By.cssSelector("#tracker-date"));
        trackerDate.clear();
        trackerDate.sendKeys(date);
    }

    @And("^push update button$")
    public void push_update_button() throws Throwable {
        WebElement trackerCalendarUpdateButton = webDriver.findElement(By.cssSelector("input[name=update]"));
        trackerCalendarUpdateButton.click();

    }

    @Then("^should see screensots$")
    public void should_see_screensots() throws Throwable {
        WebElement image = webDriver.findElement(By.cssSelector(".tracker-image img"));
        Assert.assertTrue(image.isDisplayed());
        webDriver.quit();
    }

    @Given("^I'm on tasks form of application$")
    public void I_on_tasks_form_of_application() throws Throwable {
        adapter = new Adapter();
        loginWindow = adapter.getMainWindow();
        loginWindow.getInputTextBox("url").setText(url);
        loginWindow.getInputTextBox("username").setText(username);
        loginWindow.getPasswordField().setPassword(password);
        loginWindow.getButton("submit").click();
        tasksWindow = adapter.getTasksWindow();
    }

    @Then("^I check if ticket doesn't exist in combobox$")
    public void I_check_if_ticket_doesn_t_exist_in_combobox() throws Throwable {
        Assertion contains = tasksWindow.getComboBox().contains(ticketId + ": " + ticketSummary);
        Assert.assertEquals(false, contains.isTrue());
    }

    @And("^I click refresh button$")
    public void I_click_refresh_button() throws Throwable {
        tasksWindow.getButton("Refresh").click();
    }

    @And("^I check if ticket exists in combobox$")
    public void I_check_if_ticket_exists_in_combobox() throws Throwable {
        Assertion contains = tasksWindow.getComboBox().contains(ticketId + ": " + ticketSummary);
        Assert.assertEquals(true, contains.isTrue());
    }

    @And("^I close ticket$")
    public void I_close_ticket() throws Throwable {
        webDriver.findElement(By.id("action_resolve")).click();
        webDriver.findElement(By.name("submit")).click();
    }

    @Then("^I should see ticket exist in tasks form$")
    public void I_should_see_ticket_exist_in_tasks_form() throws Throwable {
        Assertion contains = tasksWindow.getComboBox().contains(ticketId + ": " + ticketSummary);
        Assert.assertEquals(true, contains.isTrue());
    }

    @Then("^I shouldn't see created ticket in tasks list$")
    public void I_shouldnt_see_created_ticket_in_tasks_list() throws Throwable {
        Assertion contains = tasksWindow.getComboBox().contains(ticketId + ": " + ticketSummary);
        Assert.assertEquals(false, contains.isTrue());
    }

    @And("^I've accepted ticket on trac$")
    public void I_ve_accepted_ticket_on_trac() throws Throwable {
        I_create_new_ticket();
        WebElement modify=webDriver.findElement(By.cssSelector("#no4"));
        modify.click();
        webDriver.findElement(By.id("action_accept")).click();
        webDriver.findElement(By.name("submit")).click();

    }

    @When("^press \"([^\"]*)\" button$")
    public void press_button(String buttonName) throws Throwable {
        tasksWindow.getButton(buttonName).click();
    }

    @And("^I've accepted ticket with name \"([^\"]*)\" on trac$")
    public void I_ve_accepted_ticket_with_name_on_trac(String string) throws Throwable {

    }

    @Then("^I should see created ticket in tasks list$")
    public void I_should_see_created_ticket_in_tasks_list() throws Throwable {
        Assertion contains = tasksWindow.getComboBox().contains(ticketId + ": " + ticketSummary);
        Assert.assertEquals(true, contains.isTrue());
    }

//    @After
//    public void after_scenario(){
//        if (webDriver != null) {
//            webDriver.close();
//        }
//    }

    @And("^I've closed ticket on trac$")
    public void I_ve_closed_ticket_on_trac() throws Throwable {
        I_ve_accepted_ticket_on_trac();
        I_close_ticket();
    }

    @And("^I've chosen one ticket$")
    public void I_ve_chosen_one_ticket() throws Throwable {
        String selectedTicket = tasksWindow.getComboBox().getAwtComponent().getSelectedItem().toString();
        tasksWindow.getComboBox().select(selectedTicket);
        ticketId = selectedTicket.substring(1, selectedTicket.indexOf(':'));
        ticketSummary = selectedTicket.substring(selectedTicket.indexOf(':') + 2);
        tasksWindow.getButton("start").click();
    }

    @And("^Start following$")
    public void start_following() throws Throwable {
        recordWindow = adapter.getRecordWindow();
        recordWindow.getButton("ok").click();
    }



    @When("^I wrote comment \"([^\"]*)\" and submit$")
    public void I_wrote_comment_and_submit(String comment) throws Throwable {
        sentComment = comment;
        recordWindow = adapter.getRecordWindow();
        recordWindow.getInputTextBox("Label").setText(comment);
        recordWindow.getButton("ok").click();
    }

    @Then("^I see in file \"([^\"]*)\" that this comment exists$")
    public void I_see_in_file_that_this_comment_exists(String filename) throws Throwable {
        File file = new File(System.getProperty("user.home") + "/" + filename);
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            Node tasks = document.getFirstChild();
            Node task = tasks.getLastChild();
            String ticketId = task.getAttributes().getNamedItem("id").getNodeValue();
            String ticketSummary = task.getAttributes().getNamedItem("name").getNodeValue();
            Node description = task.getChildNodes().item(1);
            String textContent = description.getTextContent();

            Assert.assertEquals(this.ticketId, ticketId);
            Assert.assertEquals(this.ticketSummary, ticketSummary);
            Assert.assertEquals(sentComment, textContent);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @And("^stay for a while \"([^\"]*)\" seconds$")
    public void stay_for_a_while(Integer seconds) throws Throwable {
        // Express the Regexp above with the code you wish you had
        System.out.println("WebAppTrackerTestStepDefs.stay_for_a_while");

        Date start = new Date();
        Date end = new Date();

        while(end.getTime() - start.getTime() < seconds * 1000){
            end = new Date();
        }
    }

    @And("^Emulate mouse click \"([^\"]*)\" and keyboard press \"([^\"]*)\"$")
    public void Emulate_mouse_click_and_keyboard_press(Integer click_count, Integer press_count) throws Throwable {
        Robot robot = new Robot();

        for(int i = 0; i < click_count; i++){
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        for(int j =0; j < press_count; j++) {

            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);
        }
    }

    @Given("^I've made \"([^\"]*)\" keyboard events and \"([^\"]*)\" mouse events$")
    public void I_ve_made_keyboard_events_and_mouse_events(String keyboardEventCoutn, String mouseEventCount) throws Throwable {

    }

    @When("^I open day report$")
    public void I_open_day_report() throws Throwable {
        The_browser_is_run();
        navigate_to_trac_instance();
        WebElement tabTracker = webDriver.findElement(By.linkText("Tracker"));
        tabTracker.click();
        WebElement reportLink = webDriver.findElement(By.linkText("Ежедневный отчет"));
        reportLink.click();
    }


    @And("^Find frame by user \"([^\"]*)\" with \"([^\"]*)\" clicks and \"([^\"]*)\" presses$")
    public void Find_frame_by_user_with_clicks_and_presses(String username, String clicks, String presses) throws Throwable {
        WebElement frame = webDriver.findElement(By.cssSelector("div[author='" + username + "']"));
        WebElement mouse_count_cont = webDriver.findElement(By.cssSelector("div[author='"+username+"'] .mouse-event-count"));
        WebElement keyboard_count_cont = webDriver.findElement(By.cssSelector("div[author='"+username+"'] .keyboard-event-count"));

        Assert.assertEquals(username, frame.getAttribute("author"));
        Assert.assertEquals(mouse_count_cont.getText(), clicks);
        Assert.assertEquals(keyboard_count_cont.getText(), presses);
    }

    @Given("^I'm on Tracker page$")
    public void I_m_on_tracker_page() throws Throwable {
        navigate_to_trac_instance();
        click_on_Tracker_menu_link();
    }

    @When("^I click on link Report of user \"([^\"]*)\"$")
    public void I_click_on_link_report_of_user(String username) throws Throwable {
        WebElement user = webDriver.findElement(By.cssSelector("li[author='"+username+"'] .report"));
        user.click();
    }

    @Then("^should see choose date$")
    public void should_see_choose_date() throws Throwable {
        WebElement reportFor = webDriver.findElement(By.xpath("//ul[@id='nav']/p"));
        Assert.assertEquals("Please choose dates from and to", reportFor.getText());
        webDriver.quit();
    }

    @Given("^I'm on page Report of user \"([^\"]*)\"$")
    public void I_m_on_page_report_of_user(String username) throws Throwable {
        I_m_on_tracker_page();
        I_click_on_link_report_of_user(username);
    }

    @And("^choose end date \"([^\"]*)\" in calendar$")
    public void choose_end_date_in_calendar(String date) throws Throwable {
        WebElement trackerDate = webDriver.findElement(By.cssSelector("#toDMY"));
        trackerDate.clear();
        trackerDate.sendKeys(date);
    }

    @And("^push submit button$")
    public void push_submit_button() throws Throwable {
        WebElement trackerCalendarMakeReportButton = webDriver.findElement(By.xpath("//ul[@id='nav']//input[@value='Построить отчет']"));
        trackerCalendarMakeReportButton.click();

    }

    @Then("^I should see working hours plus 10 min$")
    public void I_should_see_working_hours_plus_10_min() throws Throwable {
        WebElement min = webDriver.findElement(By.xpath("//div[@id='content']//b[@id='min']"));
        if (minutes==50)
                minutes=0;
        else
            minutes+=10;
        Assert.assertEquals(min.getText(),minutes.toString());
        webDriver.quit();
    }

    @When("^choose todays date in calendar$")
    public void choose_todays_date_in_calendar() throws Throwable {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date();
        WebElement trackerDate = webDriver.findElement(By.cssSelector("#fromDMY"));
        trackerDate.clear();
        trackerDate.sendKeys(dateFormat.format(date));
        trackerDate.sendKeys();
    }

    @And("^working for 10 sec$")
    public void working_for_10_sec() throws Throwable {
        Emulate_mouse_click_and_keyboard_press(13,5);
        stay_for_a_while(10);
    }

    @And("^choose the ticket$")
    public void choose_the_ticket() throws Throwable {
        I_on_tasks_form_of_application();
        I_ve_chosen_one_ticket();
        start_following();
    }

    @When("^I check working hours$")
    public void I_check_working_hours() throws Throwable {
        choose_todays_date_in_calendar();
        choose_end_date_in_calendar("10-09-2015");
        push_submit_button();
        WebElement min = webDriver.findElement(By.xpath("//div[@id='content']//b[@id='min']"));
        minutes=Integer.parseInt(min.getText());
    }

    @Given("^I'm working on new ticket for 10 sec$")
    public void I_am_working_on_new_ticket_for_10_sec() throws Throwable {
        navigate_to_trac_instance();
        autorize_user();
//        I_create_new_ticket();
        I_ve_accepted_ticket_on_trac();

        I_on_tasks_form_of_application();
        I_click_refresh_button();
        I_choose_new_ticket();
        start_following();
        Emulate_mouse_click_and_keyboard_press(13,5);
        stay_for_a_while(10); }


    @When("^I go to report table of user \"([^\"]*)\"$")
    public void I_go_to_report_table(String username) throws Throwable {
        I_m_on_page_report_of_user(username);
        choose_todays_date_in_calendar();
        choose_end_date_in_calendar("10-09-2015");
        push_submit_button();
    }

    @Then("^I should see 10 min of new ticket")
    public void I_should_see_10_min_of_new_ticket() throws Throwable {
        WebElement min = webDriver.findElement(By.xpath("//div[@id='content']//div[@id='"+ticketSummary+"']//b[@id='min']"));
        Assert.assertEquals(min.getText(),"10");
        webDriver.quit();
    }

    @And("^I choose new ticket$")
    public void I_choose_new_ticket() throws Throwable {
        int anIndex = tasksWindow.getComboBox().getAwtComponent().getItemCount() - 1;
        Ticket selectedTicket = (Ticket) tasksWindow.getComboBox().getAwtComponent().getModel().getElementAt(anIndex);
        String ticketValue = selectedTicket.toString();
        tasksWindow.getComboBox().select(ticketValue);
        ticketId = ticketValue.substring(1, ticketValue.indexOf(':'));
        ticketSummary = ticketValue.substring(ticketValue.indexOf(':') + 2);
        tasksWindow.getButton("start").click();
    }

    public void autorize_user() throws Throwable {
        WebElement element = webDriver.findElement(By.linkText("Login"));
        element.click();

    }
}

