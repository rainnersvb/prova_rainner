package teste;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Teste para realizar validações na API, na página https://rapidapi.com/divad12/api/numbers-1
 *
 * Código comentado dos acontecimentos e uns system.out.print nas partes em que validações foram realizadas.
 * Utilizado mesmo e-mail e cadastros da questao11
 *
 * Os resultados das requisições testadas serão apenas exibidos no console.
 */

public class questao12 {
    public static void main(String[] args){
        TesteBase.setupDriver(); // Baixa o ChromeDriver válido para a estação que está executando o teste.
        TesteBase.init(); // Inicializa o ChromeDriver
        System.out.println("- Realizando validações na página: https://rapidapi.com/divad12/api/numbers-1");

        TesteBase.driver.get("https://rapidapi.com/divad12/api/numbers-1");
        TesteBase.driver.switchTo().defaultContent();
        WebElement btnTestEndpoint = TesteBase.driver.findElement(By.cssSelector("#endpoint-form button"));
        btnTestEndpoint.click();
        try{
            System.out.println("- Aguardando carregamento da página inicial...");
            Thread.sleep(3000);
        }catch (Exception e){}
        WebElement btnLogin = TesteBase.driver.findElement(By.cssSelector("button.ant-btn-link"));
        btnLogin.click();
        try{
            System.out.println("- Aguardando carregamento da página de login...");
            Thread.sleep(1000);
        }catch (Exception e){}
        // Identificando os campos
        WebElement campoEmail = TesteBase.driver.findElement(By.cssSelector("#login-form_email"));
        WebElement campoPassword = TesteBase.driver.findElement(By.cssSelector("#login-form_password"));
        WebElement buttonLogar = TesteBase.driver.findElement(By.cssSelector("button.login-form-button"));
        // Preenchendo os campos
        campoEmail.click();
        campoEmail.sendKeys("testerainner@gmail.com");
        campoPassword.click();
        campoPassword.sendKeys("Testerainner@1");
        buttonLogar.click();
        try{
            System.out.println("- Aguardando carregamento da página inicial...");
            Thread.sleep(10000);
        }catch (Exception e){}
        TesteBase.espere(By.cssSelector("button.endpoint-test-button"));
        WebElement apiApp = TesteBase.driver.findElements(By.cssSelector("div.application span")).get(2);
        WebElement apiKey = TesteBase.driver.findElements(By.cssSelector("div.ant-form-item-control-input")).get(1);
        // Exibe no console os valores
        System.out.println("* API App value: "+ apiApp.getText()+"\n* API Key value: "+ apiKey.getText());

        // Clicar em Test Endpoint na primeira requisição já selecionada, Get year fact
        WebElement buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();
        try{
            System.out.println("- Aguardando API trazer resposta para requisição 'Year fact'...");
            Thread.sleep(2000);
        }catch (Exception e){}
        WebElement ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Year fact' com valor padrão: \n" + ResultsBody.getText());

        // Clicar em Test Endpoint na segunda requisição, Get trivia fact
        List<WebElement> listaRequisicoes = TesteBase.driver.findElements
                (By.cssSelector("div.endpoints-list-collapse div"));
        listaRequisicoes.get(2).click();
        try{
            Thread.sleep(1000);
        }catch (Exception e){}
        buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();
        try{
            System.out.println("- Aguardando API trazer resposta para requisição 'Trivia fact'...");
            Thread.sleep(2000);
        }catch (Exception e){}
        ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Year fact' com valor padrão: \n" + ResultsBody.getText());

        // Informar número 10 no parâmetro Number (String)
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        List<WebElement> campoNumber = TesteBase.$("input.ant-input", null, true);
        campoNumber.get(1).click();
        // Depois de selecionar o campo, limpar ele
        Actions actions = new Actions(TesteBase.driver);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.build().perform();
        campoNumber.get(1).sendKeys("10");
        actions.sendKeys(Keys.TAB);
        actions.sendKeys(Keys.PAGE_UP);
        actions.build().perform();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        actions.sendKeys(Keys.PAGE_UP);
        actions.build().perform();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();

        // Mostrar no console os resultados
        ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Year fact' com valor '10' inserido: " +
                "\n" + ResultsBody.getText());

        // Clicar em Test Endpoint na terceira requisição, Get random fact
        listaRequisicoes = TesteBase.driver.findElements
                (By.cssSelector("div.endpoints-list-collapse div"));
        listaRequisicoes.get(4).click();
        try{
            Thread.sleep(1000);
        }catch (Exception e){}
        buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();
        try{
            System.out.println("- Aguardando API trazer resposta para requisição 'Random fact'...");
            Thread.sleep(2000);
        }catch (Exception e){}

        // Mostrar no console os resultados
        ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Random fact' com valor padrão inserido: " +
                "\n" + ResultsBody.getText());

        // Clicar em Test Endpoint na quarta requisição, Get math fact
        listaRequisicoes = TesteBase.driver.findElements
                (By.cssSelector("div.endpoints-list-collapse div"));
        listaRequisicoes.get(6).click();
        try{
            Thread.sleep(1000);
        }catch (Exception e){}
        buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();
        try{
            System.out.println("- Aguardando API trazer resposta para requisição 'Math fact'...");
            Thread.sleep(2000);
        }catch (Exception e){}

        // Mostrar no console os resultados
        ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Math fact' com valor padrão inserido: " +
                "\n" + ResultsBody.getText());

        // Informar número 3 seguido de ',' e o número 14 (3,14) para trazer duas requisições seguidas
        campoNumber = TesteBase.$("input.ant-input", null, true);
        campoNumber.get(1).click();
        // Depois de selecionar o campo, limpar ele, número 1729
        actions = new Actions(TesteBase.driver);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.build().perform();
        campoNumber.get(1).sendKeys("3,14");
        actions.sendKeys(Keys.TAB);
        actions.sendKeys(Keys.PAGE_UP); // Subindo a janela para não dar erro
        actions.build().perform();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        actions.sendKeys(Keys.PAGE_UP);
        actions.build().perform();
        try{
            Thread.sleep(2000);
        }catch (Exception e){}
        buttonTestEndpoint = TesteBase.driver.findElement(By.cssSelector("button.endpoint-test-button"));
        buttonTestEndpoint.click();

        // Mostrar no console os resultados
        ResultsBody = TesteBase.driver.findElement(By.cssSelector("div.react-json-view"));
        System.out.println("- Resultado da requisição por 'Math fact' com valor padrão inserido: " +
                "\n" + ResultsBody.getText());

        // Há também a requisição Date fact, mas é no mesmo estilo das anteriores.
        try{ // Fecha após 3 segundos para o driver não ficar gastando memória
            System.out.println("- Teste da funcionalidade da API sugerida concluída!");
            Thread.sleep(3000);
            TesteBase.driver.close();
        }catch (Exception e){}
    }
}
