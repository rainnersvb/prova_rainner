package teste;

import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.*;

public class TesteBase {
    public static WebDriver driver = null;

    // Organiza as preferências de inicialização do ChromeDriver, coloquei algumas genéricas.
    // Os testes podem ser realizados sem interface gráfica (ideal) caso preferir, a linha foi comentada
    public static void init() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--disable-notifications", "--disable-infobars","--no-sandbox");
        //options.addArguments("headless"); // Descomentar caso prefira os testes sem interface gráfica
        TesteBase.driver = new ChromeDriver(options);
    }

    // Utiliza método para organizar o WebDriver utilizado. Serve para todos os navegadores.
    public static void setupDriver() {
        try{
            WebDriverManager.globalConfig().setTimeout(5);
            WebDriverManager.chromedriver().setup();
        }catch (WebDriverManagerException e){
            System.out.println("Ocorreu um problema ao gerenciar o ChromeDriver!");
        }
    }

    // Função para que o WebDriver fique buscando pela visibilidade de um seletor.
    public static WebElement esperaVisibilidade(String seletor){
        boolean elementoVisivel = false;
        while (!elementoVisivel){
            try{
                if (driver.findElement(By.cssSelector(seletor)).isDisplayed()){
                    elementoVisivel = true;
                }
            }catch (Exception e){} // Faça nada com a exceção, continaur buscando no laço
        }
        return driver.findElement(By.cssSelector(seletor));
    }

    // Função para filtrar elementos de uma lista por visibilidade
    public static List<WebElement> $(String seletor, WebElement pattern, boolean visivel) {  //
        List<WebElement> retorno = new ArrayList<WebElement>();
        List<WebElement> list = $(seletor, pattern);
        if (visivel) {
            for (WebElement element : list) {
                if ((!element.getCssValue("display").equals("none")
                        && !element.getAttribute("class").contains("oculto")
                        && element.isDisplayed())) {
                    retorno.add(element);
                }
            }
        }
        return (visivel) ? retorno : list;
    }

    // Função para selecionar elementos numa lista
    public static List<WebElement> $(String seletor, WebElement patern) {
        if (patern != null) {
            return patern.findElements(By.cssSelector(seletor));
        }
        return driver.findElements(By.cssSelector(seletor));
    }

    // Função para aguardar dinamicamente pelo carregamento da página
    public static WebElement espere(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.time.Duration.ofSeconds(15)) // 15 s limite

                .pollingEvery(java.time.Duration.ofSeconds(1)) // tentando a cada 1s

                .ignoring(org.openqa.selenium.ElementNotVisibleException.class) // mesmo se não estiver visível

                .ignoring(org.openqa.selenium.NoSuchElementException.class); // mesmo se não estiver listado
        WebElement foo = wait.until(
                new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(locator);
                    }
                }
        );
        return foo;
    }
}