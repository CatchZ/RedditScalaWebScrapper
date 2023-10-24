import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.chrome.ChromeDriver
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elements
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions




object RedditScraper {

  val entrysToCollect = 300
  val crawlingTargetUrl = "https://www.reddit.com/r/all/hot/"
  val webDriverLocation = "C:\\Users\\Max\\chromedriver-win64\\chromedriver.exe"
  val webDriverType = "webdriver.chrome.driver"
  val scrappingTargetElement ="a[href^='/r/']"
  val waitForContantTimeout = 30



  def main(args: Array[String]): Unit = {
    System.setProperty(webDriverType, webDriverLocation)

    val driver: WebDriver = new ChromeDriver()

    driver.get(crawlingTargetUrl)

    var previousHeight: Long = 0
    var currentHeight: Long = driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
      .executeScript("return document.body.scrollHeight")
      .asInstanceOf[Long]


    while (getNumberOfEntries(driver) < entrysToCollect&& previousHeight != currentHeight) {
      driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
        .executeScript("window.scrollTo(0, document.body.scrollHeight);")

      Thread.sleep(waitForContantTimeout)

      previousHeight = currentHeight
      currentHeight = driver.asInstanceOf[org.openqa.selenium.JavascriptExecutor]
        .executeScript("return document.body.scrollHeight")
        .asInstanceOf[Long]
    }

    val pageSource = driver.getPageSource
    driver.close()

    val browser = JsoupBrowser()
    val doc = browser.parseString(pageSource)


    val subredditLinks = (doc >> elements(scrappingTargetElement)).toList
    val subreddits: List[String] = subredditLinks.map(element => element.attr("href"))
    val distinctSubreddits = subreddits.distinct




    distinctSubreddits.take(entrysToCollect).foreach(println)
  }

  def getNumberOfEntries(driver: WebDriver): Int = {
    driver.findElements(By.cssSelector(scrappingTargetElement)).size()
  }
}
