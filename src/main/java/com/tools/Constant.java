package com.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Optional;


public class Constant {

    //Hazelcast
    private final static String KEY = "key";
    private final static String VECTOR = "vector";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String PAGE_INDEX_DOCTOR = "pageIndexDoctor";
    private final static String PAGE_INDEX_MODEL_DEVELOPER = "pageIndexModelDeveloper";

    //String matches
    private final static String PHONEREG = "[+]{0,1}[0-9]{1,15}";
    private final static String EMAILREG = "[a-zA-Z0-9]+[@][a-z]+[.][a-z]{2,3}";

    //Icons
    private final static String SIGN_IN_BUTTON_ICON = "/img/icons/signIn.png";
    private final static String APPLICATION_ICON = "img/icons/icon.png";

    //FXML root
    private final static String MAIN_MENU_ROOT = "fxml/menu/mainMenu.fxml";
    private final static String LOGIN_MENU_ROOT = "fxml/admin/loginMenu.fxml";

    //Miscellaneous
    private static final int OBJECT_ON_PAGE = 30;
    private final static String BORDER_COLOR_INHERIT = "-fx-border-color: inherit";
    private final static String BORDER_COLOR_RED = "-fx-border-color: red";

    public static String[] getAuth() {
        String[] auth = new String[2];
        String login = Encryptor.decrypt(HazleCastMap.getMapByName(HazleCastMap.getKeyMapName()).get(KEY).toString(),
                HazleCastMap.getMapByName(HazleCastMap.getKeyMapName()).get(VECTOR).toString(), HazleCastMap.getMapByName(HazleCastMap.getUserMapName()).get(LOGIN).toString());
        String password = Encryptor.decrypt(HazleCastMap.getMapByName(HazleCastMap.getKeyMapName()).get(KEY).toString(),
                HazleCastMap.getMapByName(HazleCastMap.getKeyMapName()).get(VECTOR).toString(), HazleCastMap.getMapByName(HazleCastMap.getUserMapName()).get(PASSWORD).toString());
        auth[0] = login;
        auth[1] = password;
        return auth;
    }

    public static String responseToString(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    public static int getObjectOnPage() {
        return OBJECT_ON_PAGE;
    }


    public static void getAlert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean questionOkCancel(String questionText) {
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert questionOfCancellation = new Alert(Alert.AlertType.WARNING, questionText, ok, cancel);
        questionOfCancellation.setHeaderText(null);
        Optional<ButtonType> result = questionOfCancellation.showAndWait();
        if (result.orElse(cancel) == ok) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPHONEREG() {
        return PHONEREG;
    }

    public static String getEMAILREG() {
        return EMAILREG;
    }

    public static String getKEY() {
        return KEY;
    }

    public static String getVECTOR() {
        return VECTOR;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getPageIndexDoctor() {
        return PAGE_INDEX_DOCTOR;
    }

    public static String getPageIndexModelDeveloper() {
        return PAGE_INDEX_MODEL_DEVELOPER;
    }

    public static String getSignInButtonIcon() {
        return SIGN_IN_BUTTON_ICON;
    }

    public static String getMainMenuRoot() {
        return MAIN_MENU_ROOT;
    }

    public static String getBorderColorInherit() {
        return BORDER_COLOR_INHERIT;
    }

    public static String getBorderColorRed() {
        return BORDER_COLOR_RED;
    }

    public static String getLoginMenuRoot() {
        return LOGIN_MENU_ROOT;
    }

    public static String getApplicationIcon() {
        return APPLICATION_ICON;
    }
}
