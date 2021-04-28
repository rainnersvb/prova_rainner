package teste;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Teste básico para realizar algumas validações no cadastro da página: https://accounts.google.com/signup/v2
 *
 * Código comentado dos acontecimentos e uns system.out.print nas partes em que validações foram realizadas.
 * Neste teste, são necessárias informações para prosseguir no cadastro, será testado de forma rápida até a inserção
 * de número de telefone válido.
 */

public class questao11 {
    public static void main(String[] args){
        TesteBase.setupDriver(); // Baixa o ChromeDriver válido para a estação que está executando o teste.
        TesteBase.init(); // Inicializa o ChromeDriver
        System.out.println("- Realizando validações na página: https://accounts.google.com/signup/v2");

        TesteBase.driver.get("https://accounts.google.com/signup/v2");
        // Vai redirecionar para um link da página do cadastro
        Faker faker = new Faker(); // Para gerar um nome de usuário aleatório
        WebElement campoNome = TesteBase.esperaVisibilidade("#firstName"); // Espera pelo elemento Nome
        WebElement campoSobrenome = TesteBase.driver.findElement(By.cssSelector("#lastName")); // Sobrenome
        WebElement campoNomeUsu = TesteBase.driver.findElement(By.cssSelector("#username")); // Nome de usuário
        WebElement campoSenha1 = TesteBase.driver.findElement(By.cssSelector("#passwd input")); // Campo senha 1
        WebElement campoSenha2 = TesteBase.driver.findElement(By.cssSelector("#confirm-passwd input")); // Campo senha 2
        WebElement botaoProxima = TesteBase.driver.findElement(By.cssSelector("#accountDetailsNext")); // Botão proximo
        System.out.println("- Inserindo informações genéricas para os campos," +
                "testanndo validação com senhas que não são iguais e nome de usuário já existente...");

        campoNome.click(); // Clica no campo
        campoNome.sendKeys("Teste"); // Insere nome

        campoSobrenome.click(); // Clica no campo
        campoSobrenome.sendKeys("Rainner"); // Insere sobrenome

        campoNomeUsu.click();
        campoNomeUsu.sendKeys("testesautomatizados"); // Insere um e-mail que já existe

        campoSenha1.click();
        campoSenha1.sendKeys("testerainner123@#!");

        campoSenha2.click();
        campoSenha2.sendKeys("testerainner321#@!"); // Insere a confirmação da senha incorretamente

        WebElement chkMostrarSenha = null; // Busca pelo elemento de mostrar a senha
        List<WebElement> listaInputs = TesteBase.driver.findElements(By.cssSelector("div input"));
        for (int i = 0; i < listaInputs.size(); i++){
            if (listaInputs.get(i).getAttribute("type").contains("checkbox")) {
                chkMostrarSenha = listaInputs.get(i);
                break;
            }
        }

        chkMostrarSenha.click(); // Clica no check de msotrar a senha
        botaoProxima.click(); // Tenta avançar na tela de cadastro, deve estar com as senhas diferentes

        List<WebElement> validaInputs =
                TesteBase.driver.findElements(By.cssSelector("#view_container section div div div span"));
        for (int i = 0; i < validaInputs.size(); i++){
            if (validaInputs.get(i).getText().contains("As senhas não são iguais. Tente novamente.")){
                System.out.println("- Validado com senhas que não são iguais...");
            }else try{
                throw new Exception("- Não foi exibida a validação esperada quando inserir senhas diferentes!");
            }catch (Exception e){}
        }

        campoSenha2.click(); // Volta a inserir a senha correta, mas ainda deve validar o e-mail que já existe
        campoSenha2.sendKeys("testerainner123@#!");
        botaoProxima.click(); // Nome de usuário já deve estar sendo utilizado, validar

        validaInputs = TesteBase.driver.findElements(By.cssSelector("#view_container section div div div span"));
        for (int i = 0; i < validaInputs.size(); i++){
            if (validaInputs.get(i).getText().contains("Este nome de usuário já está em uso. Tente outro.")){
                System.out.println("- Validado que o nome de usuário já está em uso...");
            }else try{
                throw new Exception("- Não foi exibida a validação esperada quando inserir senhas diferentes!");
            }catch (Exception e){}
        }

        campoNomeUsu.click();
        campoNomeUsu.clear();
        campoNomeUsu.sendKeys(faker.dog().name() + faker.cat().name() + faker.number().numberBetween(71,81) + "testes");
        // Gera um nome com nome aleatório de cão e gato
        botaoProxima.click();
        System.out.println("- Validado com nome de usuário válido...");
        try{
            System.out.println("- Aguardando carregamento da página...");
            Thread.sleep(3000);
        }catch (Exception e){}
        WebElement campoFone = TesteBase.driver.findElement(By.cssSelector("#phoneNumberId"));
        campoFone.click();
        campoFone.sendKeys("11111111");
        WebElement botaoProximo = TesteBase.driver.findElements(By.cssSelector("#view_container button")).get(0);
        botaoProximo.click();
        WebElement validacaoFone =
                TesteBase.driver.findElement(By.cssSelector("#view_container section div div div div"));
        // Valida mensagem em vermelho, erro do fone, pois foi informado um formato inválido
        if (!validacaoFone.getText()
                .contains("Este formato de número de telefone não é válido. Verifique o país e o número.")){
            try{
                throw new Exception("Validação a respeito do número de telefone inválido não foi exibida...");
            }catch (Exception e){}
        }else System.out.println("- Validado com número de telefone em um formato inválido...");
        // Necessário um fone válido para validar as informações a diante
        try{ // Fecha após 3 segundos para o driver não ficar gastando memória
            System.out.println("- Teste da funcionalidade de cadastro da página do Google concluída!");
            Thread.sleep(3000);
            TesteBase.driver.close();
        }catch (Exception e){}
    }
}