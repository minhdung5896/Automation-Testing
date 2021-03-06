package Demo.com.junit;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Demo.com.configs.Configs;

public class iOSWebTest {
	public static IOSDriver<WebElement> driver = null;
	String wrongUsernameMsg = "Your username is invalid!";
	String wrongPasswordMsg = "Your password is invalid!";
	String successMsg = "You logged into a secure area!";

	@Before
	public void Setup() {
		driver = new IOSDriver<WebElement>(Configs.DemoServerUrl(), Configs.desiredCapabilitiesiOSWeb());
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@After
	public void Teardown() {
		try {
			if (driver != null)
				driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIFixitIOSWeb() {
		// Keep the test cases in order
		testInvalidUsername();
		testInvalidPassword();
		testLoginSuccessfully();
	}

	public void testInvalidUsername() {
		System.out.println("should return error when we input wrong username");
		login("foo", "SuperSecretPassword!");
		Assert.assertTrue(getMessage().contains(wrongUsernameMsg));
	}

	public void testInvalidPassword() {
		System.out.println("should return error when we input wrong password");
		login("tomsmith", "SuperSecretPassword");
		Assert.assertTrue(getMessage().contains(wrongPasswordMsg));
	}

	public void testLoginSuccessfully() {
		System.out.println("should run test successfully with correct username and password");
		login("tomsmith", "SuperSecretPassword!");
		Assert.assertTrue(getMessage().contains(successMsg));
	}

	public void login(String userName, String password) {
		driver.get("http://the-internet.herokuapp.com/login");
		driver.findElementById("username").sendKeys(userName);
		driver.findElementById("password").sendKeys(password);
		driver.findElementByXPath("//form[@name='login']").submit();
	}

	public String getMessage() {
		return driver.findElementById("flash").getText();
	}
}
