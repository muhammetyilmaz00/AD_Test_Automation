package pageObjects.interfaces;

import org.apache.commons.configuration.ConfigurationException;
import org.jetbrains.annotations.NotNull;
import utils.EmailUtilities;

import java.util.concurrent.TimeoutException;

public interface ADPageObjectsInterface {

    void navigateToMainPage();

    void clickInLoggenButton();

    void enterEmail(String email);

    void clickOnButton(String button);

    void enterPassword(String password);

    void checkLoggedIn();

    void checkWarning();

    void checkRequiredFieldWarning();

    void clickCodeButton();

    void clickFirstNews();

    void checkDetailsOnOpenedPage();

    void clickSearchButton();

    void writeSearchForm(String word);

    void clickZoekButton();

    void checkResultsTitle(String word);

    void checkNoResultsFoundTitle(String word);

    String contextCheck(@NotNull String input);

    String getEmail(
            EmailUtilities.Inbox.EmailField filter,
            String filterKey,
            Boolean print,
            Boolean save) throws TimeoutException;

    void acquireSaveEmail(EmailUtilities.Inbox.EmailField filterType, String filterKey) throws TimeoutException;

    void getHTML(String url);

    void saveLoginCode();

    void enterLoginCode();

    void clickForgotPassword();

    void enterNewPassword() throws ConfigurationException;
}
