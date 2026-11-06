import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.*;

/**
 * Gère :
 *  1. La création du WebDriver Edge furtif
 *  2. La connexion au compte BGA
 *  3. L'attente qu'une partie de Connect 4 soit lancée (URL contient /connect4/)
 *  4. L'extraction automatique du TABLE_ID depuis l'URL
 */
public class BgaConnection {

    // ── Création du driver ────────────────────────────────────────────────────

    public WebDriver createDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--start-maximized");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriver driver = new EdgeDriver(options);
        ((JavascriptExecutor) driver).executeScript(
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        return driver;
    }

    // ── Connexion BGA ─────────────────────────────────────────────────────────

    public void connectBga(WebDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(BGAConfig.SELENIUM_TIMEOUT_S));
        driver.get("https://boardgamearena.com/account");

        // Fermer le bandeau RGPD s'il apparaît
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                By.id("didomi-notice-agree-button"))).click();
            Thread.sleep(500);
        } catch (Exception ignored) {}

        // Saisie e-mail — sélecteur exact issu du code original
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[name='email'][placeholder='Adresse e-mail ou nom d\\'utilisateur']")));
        emailField.sendKeys(BGAConfig.EMAIL);
        Thread.sleep(500);

        // Bouton "Suivant" — sélecteur exact issu du code original
        WebElement nextBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(@class, 'bga-button--blue') and text()='Suivant']")));
        nextBtn.click();
        Thread.sleep(500);

        // Saisie mot de passe
        WebElement pwdField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[type='password']")));
        pwdField.sendKeys(BGAConfig.PASSWORD);
        Thread.sleep(500);

        // Bouton "Se connecter" — sélecteur exact issu du code original
        WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[@class and contains(@class, 'bga-button--blue') and contains(normalize-space(.), 'Se connecter')]")));
        loginBtn.click();
        Thread.sleep(500);

        // Attendre que l'URL change (redirection post-login)
        wait.until(ExpectedConditions.not(
            ExpectedConditions.urlContains("/account")));

        System.out.println("[BgaConnection] Connecté ! URL : " + driver.getCurrentUrl());
    }

    // ── Attente du lancement de partie ────────────────────────────────────────

    /**
     * Bloque jusqu'à ce que le navigateur soit sur une page de jeu Connect 4.
     * L'utilisateur crée la table manuellement ; le bot attend et détecte l'URL.
     *
     * @return l'ID de table extrait de l'URL (ex. "863950")
     */
    public String waitForGameToStart(WebDriver driver) throws InterruptedException {
        System.out.println("[BgaConnection] En attente d'une partie Connect 4...");
        System.out.println("               -> Créez votre table sur BGA, le bot démarrera automatiquement.");

        while (true) {
            String url = driver.getCurrentUrl();

            // Patterns possibles :
            //   https://boardgamearena.com/table?table=863950   (lobby)
            //   https://boardgamearena.com/connect4?table=863950  (jeu lancé)
            //   https://boardgamearena.com/board?table=863950
            if (url.contains("connect4") || url.contains("/board")) {
                String tableId = extractTableId(url);
                if (tableId != null) {
                    System.out.println("[BgaConnection] Partie détectée ! TABLE_ID = " + tableId);
                    System.out.println("[BgaConnection] URL : " + url);
                    return tableId;
                }
            }

            // Si on est sur la page de table (lobby), attendre que la partie démarre
            if (url.contains("table=")) {
                String tableId = extractTableId(url);
                if (tableId != null) {
                    System.out.println("[BgaConnection] Table trouvée (" + tableId +
                                       "), attente du lancement...");
                }
            }

            Thread.sleep(BGAConfig.POLLING_INTERVAL_MS);
        }
    }

    // ── Utilitaires ───────────────────────────────────────────────────────────

    /**
     * Extrait l'ID de table depuis une URL BGA.
     * Exemples :
     *   ?table=863950   ->  "863950"
     */
    public static String extractTableId(String url) {
        int idx = url.indexOf("table=");
        if (idx >= 0) {
            int start = idx + 6;
            int end   = start;
            while (end < url.length() && Character.isDigit(url.charAt(end))) end++;
            if (end > start) return url.substring(start, end);
        }
        return null;
    }
}