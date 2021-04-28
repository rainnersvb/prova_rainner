package teste;

/**
 * E-mail válido criado para o teste: testerainner@gmail.com
 *
 * Teste básico para realizar algumas validações no login da página: https://accounts.google.com/
 * 1 - E-mail inválido (valida mensagem)
 * 2 - Número de telefone inválido (valida mensagem)
 * 3 - E-mail válido, senha incorreta (valida mensagem)
 * 4 - E-mail válido, senha correta (continuação do 3, valida mensagem)
 *
 * Código comentado dos acontecimentos e uns system.out.print nas partes em que validações foram realizadas.
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class questao10 {
    public static void main(String[] args){
        TesteBase.setupDriver(); // Baixa o ChromeDriver válido para a estação que está executando o teste.
        TesteBase.init(); // Inicializa o ChromeDriver
        System.out.println("- Realizando validações na página: https://accounts.google.com/\n" +
                "1 - E-mail inválido (valida mensagem)\n" +
                "2 - Número de telefone inválido (valida mensagem)\n" +
                "3 - E-mail válido, senha incorreta (valida mensagem)\n" +
                "4 - E-mail válido, senha correta (valida mensagem)");

        TesteBase.driver.get("https://accounts.google.com/"); // Vai redirecionar para um link da página de login
        // Espera pela visibilidade do campo E-mail ou Telefone, e já atribui para uma variável
        WebElement campoLogin = TesteBase.esperaVisibilidade("#identifierId");
        campoLogin.click(); // Clica no campo

        /**
         * 1 - E-mail inválido (valida mensagem)
         */
        System.out.println("- Validando com e-mail inválido...");
        campoLogin.sendKeys("testerainnererrado@gmail.com"); // Insere um e-mail incorreto
        WebElement botaoProxima = TesteBase.driver.findElement(By.cssSelector("#identifierNext")); // Identifica botao
        botaoProxima.click(); // Clica no proximo
        try{ // Aguarde 3 segundos para carregamento da página
            System.out.println("- Aguardando carregamento da página...");
            Thread.sleep(3000);
        }catch (Exception e){}
        WebElement validacaoTexto = TesteBase.driver.findElement(By.cssSelector("#view_container"));
        // Textos para validar
        if (!validacaoTexto.getText().contains("Não foi possível encontrar sua Conta do Google")){
            try { // Valida se contêm o erro esperado, caso contário este teste falhará
                throw new Exception("A validação está incorreta! " +
                        "Não foi exibido o texto esperado na validação do e-mail inválido!");
            } catch (Exception e) {}
        }
        try{ // Aguarde 3 segundos para começar próximo teste
            System.out.println("- 1 - Validação com e-mail inválido concluída!");
            Thread.sleep(3000);
        }catch (Exception e){}

        /**
         * 2 - Número de telefone inválido (valida mensagem)
         */
        System.out.println("- Validando com número inválido...");
        campoLogin.click(); // Clica no campo
        campoLogin.clear(); // Limpa o e-mail inválido
        campoLogin.sendKeys("11111111"); // Insere um número inválido
        botaoProxima = TesteBase.driver.findElement(By.cssSelector("#identifierNext")); // Identifica botao
        botaoProxima.click(); // Clica no proximo
        try{ // Aguarde 3 segundos para carregamento da página
            System.out.println("- Aguardando carregamento da página...");
            Thread.sleep(3000);
        }catch (Exception e){}
        validacaoTexto = TesteBase.driver.findElement(By.cssSelector("#view_container")); // Atualiza elemento das views
        if (!validacaoTexto.getText().contains("Digite um e-mail ou número de telefone válido")){
            try { // Valida se contêm o erro esperado, caso contário este teste falhará
                throw new Exception("A validação está incorreta!" +
                        " Não foi exibido o texto esperado na validação do e-mail inválido!");
            } catch (Exception e) {}
        }
        try{ // Aguarde 3 segundos para começar próximo teste
            System.out.println("- 2 - Validação com número inválido concluída!");
            Thread.sleep(3000);
        }catch (Exception e){}

        /**
         * 3 - E-mail válido, senha incorreta (valida mensagem)
         */
        System.out.println("- Validando com e-mail válido e senha incorreta...");
        campoLogin.click(); // Clica no campo
        campoLogin.clear(); // Limpa o número inválido
        campoLogin.sendKeys("testerainner@gmail.com"); // Insere um e-mail válido
        botaoProxima = TesteBase.driver.findElement(By.cssSelector("#identifierNext")); // Identifica botao
        botaoProxima.click(); // Clica no proximo
        try{ // Aguarde 3 segundos para carregamento da página
            System.out.println("- Aguardando carregamento da página...");
            Thread.sleep(3000);
        }catch (Exception e){}
        // Identifica campo senha
        WebElement campoSenha = TesteBase.driver.findElement(By.cssSelector("#password input"));
        campoSenha.click(); // Clica no campo senha
        campoSenha.sendKeys("testerainner@2"); // Insere a senha incorreta no campo
        // Captura todos os elementos do tipo input na view
        List<WebElement> camposInput = TesteBase.driver.findElements(By.cssSelector("#view_container div input"));
        WebElement btnMostrarSenha = null; // Variável para armazenar Checkbox de Mostrar Senha
        for (int i = 0; i < camposInput.size(); i++){
            if (camposInput.get(i).getAttribute("type").contains("checkbox")){
                btnMostrarSenha = camposInput.get(i);
                break;
            }
        }
        btnMostrarSenha.click(); // Clica no check para visualizar a senha inserida
        try{ // Aguarde 1 segundos para visualizar a senha incorreta inserida
            Thread.sleep(1000);
            System.out.println("- E-mail válido permitiu ir para tela de senha...");
        }catch (Exception e){}
        // Identifica o novo botão de próximo
        botaoProxima = TesteBase.driver.findElement(By.cssSelector("#passwordNext"));
        botaoProxima.click(); // Clica no proximo
        try{ // Aguarde 3 segundos para carregamento da página
            System.out.println("- Aguardando carregamento da página...");
            Thread.sleep(3000);
        }catch (Exception e){}
        validacaoTexto = TesteBase.driver.findElement(By.cssSelector("#view_container")); // Atualiza elemento das views
        if (!validacaoTexto.getText().contains("Senha incorreta. Tente novamente")){
            try { // Valida se contêm o erro esperado, caso contário este teste falhará
                throw new Exception("A validação está incorreta!" +
                        " Não foi exibido o texto esperado na da senha inválida!");
            } catch (Exception e) {}
        }
        try{ // Aguarde 3 segundos para começar próximo teste
            System.out.println("- 3 - Validação com senha inválida concluída!");
            Thread.sleep(3000);
        }catch (Exception e){}

        /**
         * 4 - E-mail válido, senha correta (valida mensagem)
         */
        System.out.println("- Validando com e-mail válido e senha correta...");
        campoSenha.sendKeys("testerainner@1"); // Insere a senha correta no campo
        botaoProxima = TesteBase.driver.findElement(By.cssSelector("#passwordNext"));
        botaoProxima.click(); // Clica no proximo
        try{ // Aguarde 5 segundos para página carregar
            Thread.sleep(5000);
        }catch (Exception e){}
        WebElement headerBemVindo = TesteBase.driver.findElement(By.cssSelector("div header h1"));
        if (!headerBemVindo.getText().contains("Bem-vindo, teste rainner")){
            try{
                throw new Exception("O teste falhou, não exibiu a mensagem esperada no header da tela de boas-vindas");
            }catch (Exception e){}
        }else System.out.println("- 4 - Validação com senha válida concluída!");
        try{ // Fecha após 3 segundos para o driver não ficar gastando memória
            Thread.sleep(3000);
            System.out.println("- Teste da funcionalidade de login da página de Login do Google concluída!");
            TesteBase.driver.close();
        }catch (Exception e){}
    }
}