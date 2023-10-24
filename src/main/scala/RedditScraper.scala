import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elements
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions




object RedditScraper {

  def main(args: Array[String]): Unit = {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Max\\chromedriver-win64\\chromedriver.exe")

    val driver: WebDriver = new ChromeDriver()

    driver.get("https://www.reddit.com/r/all/hot/")

    var previousHeight: Long = 0
    var currentHeight: Long = driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
      .executeScript("return document.body.scrollHeight")
      .asInstanceOf[Long]

    val wait = new WebDriverWait(driver, 15)


    while (getNumberOfEntries(driver) < 300 && previousHeight != currentHeight) {
      driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
        .executeScript("window.scrollTo(0, document.body.scrollHeight);")

      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Post")))

      previousHeight = currentHeight
      currentHeight = driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
        .executeScript("return document.body.scrollHeight")
        .asInstanceOf[Long]
    }

    val pageSource = driver.getPageSource
    driver.close()

    val browser = JsoupBrowser()
    val doc = browser.parseString(pageSource)


    val subredditLinks = (doc >> elements("a[href^='/r/']")).toList
    val subreddits: List[String] = subredditLinks.map(element => element.attr("href"))
    val distinctSubreddits = subreddits.distinct




    subreddits.take(300).foreach(println)
  }

  def getNumberOfEntries(driver: WebDriver): Int = {
    driver.findElements(By.cssSelector(".Post")).size()
  }
}
