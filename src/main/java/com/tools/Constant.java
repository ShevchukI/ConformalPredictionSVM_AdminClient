package com.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    private final static String SIGN_IN_ICON = "/img/icons/signIn.png";
    private final static String APPLICATION_ICON = "img/icons/icon.png";
    private final static String ADD_ICON = "img/icons/add.png";
    private final static String CANCEL_ICON = "img/icons/cancel.png";
    private final static String DELETE_ICON = "img/icons/delete.png";
    private final static String INFO_ICON = "img/icons/info.png";
    private final static String OK_ICON = "img/icons/ok.png";
    private final static String RETURN_ICON = "img/icons/return.png";
    private final static String RUN_ICON = "img/icons/run.png";
    private final static String SEARCH_ICON = "img/icons/search.png";
    private final static String EDIT_ICON = "img/icons/edit.png";

    //FXML root
    private final static String MAIN_MENU_ROOT = "fxml/menu/mainMenu.fxml";
    private final static String LOGIN_MENU_ROOT = "fxml/admin/loginMenu.fxml";

    //Miscellaneous
    private static final String MODEL_DEVELOPER = "modelDeveloper";
    private static final String DOCTOR = "doctor";
    private static final int OBJECT_ON_PAGE = 30;
    private final static String BORDER_COLOR_INHERIT = "-fx-border-color: inherit";
    private final static String BORDER_COLOR_RED = "-fx-border-color: red";

    public static String[] getAuth() {
        String[] auth = new String[2];
        String login = Encryptor.decrypt(GlobalMap.getKeyMap().get(KEY),
                GlobalMap.getKeyMap().get(VECTOR),
                GlobalMap.getUserMap().get(LOGIN));
        String password = Encryptor.decrypt(GlobalMap.getKeyMap().get(KEY),
                GlobalMap.getKeyMap().get(VECTOR),
                GlobalMap.getUserMap().get(PASSWORD));
//        String login = Encryptor.decrypt(HazelCastMap.getMapByName(HazelCastMap.getKeyMapName()).get(KEY).toString(),
//                HazelCastMap.getMapByName(HazelCastMap.getKeyMapName()).get(VECTOR).toString(),
//                HazelCastMap.getMapByName(HazelCastMap.getUserMapName()).get(LOGIN).toString());
//        String password = Encryptor.decrypt(HazelCastMap.getMapByName(HazelCastMap.getKeyMapName()).get(KEY).toString(),
//                HazelCastMap.getMapByName(HazelCastMap.getKeyMapName()).get(VECTOR).toString(),
//                HazelCastMap.getMapByName(HazelCastMap.getUserMapName()).get(PASSWORD).toString());
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

    public static String[] getContent(HttpResponse response) {
        String[] strings = new String[10];
        String[] content = new String[3];
        try {
            strings = Constant.responseToString(response).split("\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] login = strings[3].split("_");
        content[0] = login[2];
        content[1] = strings[3];
        content[2] = strings[7];
        return content;
    }

    public static void getTextAreaAlert(String title, String header, String content, Alert.AlertType alertType) {
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(300);
        textArea.setMaxHeight(100);
        GridPane gridPane = new GridPane();
        gridPane.add(textArea, 0, 0);
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(gridPane);
        alert.showAndWait();
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

    public static String getMODELDEVELOPER() {
        return MODEL_DEVELOPER;
    }

    public static String getDOCTOR() {
        return DOCTOR;
    }

    public static String getPageIndexDoctor() {
        return PAGE_INDEX_DOCTOR;
    }

    public static String getPageIndexModelDeveloper() {
        return PAGE_INDEX_MODEL_DEVELOPER;
    }

    public static String getSignInIcon() {
        return SIGN_IN_ICON;
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

    public static String getAddIcon() {
        return ADD_ICON;
    }

    public static String getCancelIcon() {
        return CANCEL_ICON;
    }

    public static String getDeleteIcon() {
        return DELETE_ICON;
    }

    public static String getInfoIcon() {
        return INFO_ICON;
    }

    public static String getOkIcon() {
        return OK_ICON;
    }

    public static String getReturnIcon() {
        return RETURN_ICON;
    }

    public static String getRunIcon() {
        return RUN_ICON;
    }

    public static String getSearchIcon() {
        return SEARCH_ICON;
    }

    public static String getEditIcon() {
        return EDIT_ICON;
    }

    public static ImageView signInIcon() {
        return new ImageView(SIGN_IN_ICON);
    }

    public static ImageView applicationIcon() {
        return new ImageView(APPLICATION_ICON);
    }

    public static ImageView addIcon() {
        return new ImageView(ADD_ICON);
    }

    public static ImageView cancelIcon() {
        return new ImageView(CANCEL_ICON);
    }

    public static ImageView deleteIcon() {
        return new ImageView(DELETE_ICON);
    }

    public static ImageView infoIcon() {
        return new ImageView(INFO_ICON);
    }

    public static ImageView okIcon() {
        return new ImageView(OK_ICON);
    }

    public static ImageView returnIcon() {
        return new ImageView(RETURN_ICON);
    }

    public static ImageView runIcon() {
        return new ImageView(RUN_ICON);
    }

    public static ImageView searchIcon() {
        return new ImageView(SEARCH_ICON);
    }

    public static ImageView editIcon() {
        return new ImageView(EDIT_ICON);
    }
}
