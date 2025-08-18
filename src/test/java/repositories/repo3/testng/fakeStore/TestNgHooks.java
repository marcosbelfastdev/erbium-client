package repositories.repo3.testng.fakeStore;

import org.testng.Reporter;
import org.testng.annotations.*;
import repositories.repo3.testng.testngFactories.TestNgMainFactory;

public class TestNgHooks {

    protected TestNgMainFactory testNgMainFactory;

    @BeforeSuite
    public void beforeSuite() {
        Reporter.log(">> Before Suite", true);
    }

    @AfterSuite
    public void afterSuite() {
        Reporter.log(">> After Suite", true);
    }

    @BeforeTest
    public void beforeTest() {

    }

    @AfterTest
    public void afterTest() {
        Reporter.log(">> After Test", true);
    }

    @BeforeClass
    public void beforeClass() {
        Reporter.log(">> Before Class", true);
    }

    @AfterClass
    public void afterClass() {
        Reporter.log(">> After Class", true);
    }

    @BeforeMethod
    public void beforeMethod() {
        Reporter.log(">> Before Method", true);
    }

    @AfterMethod
    public void afterMethod() {
        Reporter.log(">> After Method", true);
    }

}