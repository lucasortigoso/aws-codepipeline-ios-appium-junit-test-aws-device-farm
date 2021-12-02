import io.appium.java_client.AppiumDriver
import org.apache.commons.io.FileUtils
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.net.URL
import java.util.*

class iOSAppiumAWS {

    fun getEnvironmentVariable(name: String): String? {
        var value = System.getenv(name)
        if (value == null || value.isEmpty()) {
            value = System.getProperty(name)
        }
        return value
    }

    fun screenshot(driver: RemoteWebDriver, path_screenshot: String) {
        val srcFile: File = driver.getScreenshotAs(OutputType.FILE)
        val filename = UUID.randomUUID().toString()
        val targetFile = File("$path_screenshot$filename.jpg")
        FileUtils.copyFile(srcFile, targetFile)
    }

    @Test
    fun test() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability(
            CapabilityType.PLATFORM_NAME,
            getEnvironmentVariable("DEVICEFARM_DEVICE_PLATFORM_NAME") ?: "iOS"
        )
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("newCommandTimeout", 600);

        //create a AppiumDriver, the default port for Appium is 4723
        val driver = AppiumDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)

        val btnCadastrar = driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Cadastrar\"]"))
        btnCadastrar.click()

        Thread.sleep(1000)

        val labelSucesso =
            driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"Cadastro efetuado com sucesso\"]"))

        screenshot(driver, path_screenshot = getEnvironmentVariable("DEVICEFARM_SCREENSHOT_PATH") ?: "")

        assert(labelSucesso != null)

    }
}