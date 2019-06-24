package com.controllers.windows.stack.entity;

import com.controllers.windows.menu.MenuController;
import com.entity.EmployeeStatusEntity;
import com.entity.ModelDeveloperEntity;
import com.tools.Constant;
import com.tools.GlobalMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.ArrayList;

public class ChangeModelDeveloperController extends MenuController {

    private MenuController menuController;
    private ModelDeveloperEntity modelDeveloperEntity;
    private ArrayList<EmployeeStatusEntity> employeeStatus;
    private ObservableList<EmployeeStatusEntity> employeeStatusEntities;
    private boolean change;
    private Tooltip tooltip_ErrorName;
    private Tooltip tooltip_ErrorSurname;
    private Tooltip tooltip_ErrorTelephone;
    private Tooltip tooltip_ErrorEmail;

    @FXML
    private Label label_PaneName;
    @FXML
    private TextField textField_Name;
    @FXML
    private Tooltip tooltip_Name;
    @FXML
    private TextField textField_Surname;
    @FXML
    private Tooltip tooltip_Surname;
    @FXML
    private TextField textField_Telephone;
    @FXML
    private Tooltip tooltip_Telephone;
    @FXML
    private TextField textField_Email;
    @FXML
    private Tooltip tooltip_Email;
    @FXML
    private ComboBox<EmployeeStatusEntity> comboBox_Status;
    @FXML
    private Button button_Save;
    @FXML
    private Button button_Cancel;

    public void init(MenuController menuController, ArrayList<EmployeeStatusEntity> employeeStatus) {
        this.menuController = menuController;
        this.employeeStatus = employeeStatus;
        employeeStatusEntities = FXCollections.observableList(this.employeeStatus);
        comboBox_Status.setItems(employeeStatusEntities);
        comboBox_Status.setCellFactory(new Callback<ListView<EmployeeStatusEntity>, ListCell<EmployeeStatusEntity>>() {
            @Override
            public ListCell<EmployeeStatusEntity> call(ListView<EmployeeStatusEntity> p) {
                ListCell cell = new ListCell<EmployeeStatusEntity>() {
                    @Override
                    protected void updateItem(EmployeeStatusEntity item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
        comboBox_Status.setButtonCell(new ListCell<EmployeeStatusEntity>() {
            @Override
            protected void updateItem(EmployeeStatusEntity t, boolean bln) {
                super.updateItem(t, bln);
                if (bln) {
                    setText("");
                } else {
                    setText(t.getName());
                }
            }
        });
        comboBox_Status.setVisibleRowCount(5);
        comboBox_Status.getSelectionModel().select(0);
        tooltip_ErrorName = new Tooltip("Required:\n- not empty\n- minimum 2 characters");
        tooltip_ErrorSurname = new Tooltip("Required:\n- not empty\n- minimum 2 characters");
        tooltip_ErrorTelephone = new Tooltip("Maximum 15 characters");
        tooltip_ErrorEmail = new Tooltip("");

        tooltip_Name.setText(tooltip_ErrorName.getText());
        tooltip_Surname.setText(tooltip_ErrorSurname.getText());
        tooltip_Telephone.setText(tooltip_ErrorTelephone.getText());
        tooltip_Email.setText(tooltip_ErrorEmail.getText());

        modelDeveloperEntity = new ModelDeveloperEntity();

        button_Save.setGraphic(Constant.okIcon());
        button_Cancel.setGraphic(Constant.cancelIcon());

    }

    public void save(ActionEvent event) {
        if (change) {
            if (checkFields()) {
                changeCurrent();
            }
        } else {
            if (checkFields()) {
                addNew();
            }
        }
    }

    private void changeCurrent() {
        int statusCode = modelDeveloperEntity.change(textField_Name.getText(), textField_Surname.getText(),
                textField_Telephone.getText(), textField_Email.getText(),
                comboBox_Status.getSelectionModel().getSelectedItem());
        if (statusCode != 0) {
            if (checkStatusCode(statusCode)) {
                TableView<ModelDeveloperEntity> tableView = (TableView<ModelDeveloperEntity>) this.menuController.getStage().getScene().lookup("#tableView_ModelDeveloper");
                for (ModelDeveloperEntity modelDeveloper : tableView.getItems()) {
                    if (modelDeveloper.getId() == modelDeveloperEntity.getId()) {
                        modelDeveloper.setName(modelDeveloperEntity.getName());
                        modelDeveloper.setSurname(modelDeveloperEntity.getSurname());
                        modelDeveloper.setTelephone(modelDeveloperEntity.getTelephone());
                        modelDeveloper.setEmail(modelDeveloperEntity.getEmail());
                        modelDeveloper.setEmployeeStatus(modelDeveloperEntity.getEmployeeStatus());
                    }
                }
                tableView.refresh();
                Constant.getAlert(null, "Model developer changed!", Alert.AlertType.INFORMATION);
                TextField textField = (TextField) this.menuController.getStage().getScene().lookup("#textField_Name");
                textField.clear();
                GlobalMap.getMiscMap().remove(Constant.getMODELDEVELOPER());
//                HazelCastMap.getMapByName(HazelCastMap.getMiscellaneousMapName()).delete("modelDeveloper");
                StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_ModelDeveloperChange");
                stackPane.setDisable(true);
                stackPane.setVisible(false);
            }
        }
    }

    private void addNew() {
        modelDeveloperEntity.addNew(textField_Name.getText(), textField_Surname.getText(),
                textField_Telephone.getText(), textField_Email.getText(),
                comboBox_Status.getSelectionModel().getSelectedItem());
        TableView<ModelDeveloperEntity> tableView = (TableView<ModelDeveloperEntity>) this.menuController.getStage().getScene().lookup("#tableView_ModelDeveloper");
        tableView.getItems().add(modelDeveloperEntity);
        StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_ModelDeveloperChange");
        stackPane.setDisable(true);
        stackPane.setVisible(false);
    }

    public void cancel() {
        clearFields();
        StackPane stackPane = (StackPane) this.menuController.getStage().getScene().lookup("#stackPane_ModelDeveloperChange");
        stackPane.setDisable(true);
        stackPane.setVisible(false);
    }

    private boolean checkFields() {
        if (textField_Name.getText() == null || textField_Name.getText().equals("") || textField_Name.getText().length() < 2) {
            setErrorTooltip(textField_Name, tooltip_ErrorName);
        } else {
            setDefaultTooltip(textField_Name, tooltip_Name);
        }
        if (textField_Surname.getText() == null || textField_Surname.getText().equals("") || textField_Surname.getText().length() < 2) {
            setErrorTooltip(textField_Surname, tooltip_ErrorSurname);

        } else {
            setDefaultTooltip(textField_Surname, tooltip_Surname);
        }
        if (!textField_Telephone.getText().matches(Constant.getPHONEREG()) && !textField_Telephone.getText().equals("")) {
            setErrorTooltip(textField_Telephone, tooltip_ErrorTelephone);
        } else {
            setDefaultTooltip(textField_Telephone, tooltip_Telephone);
        }
        if (!textField_Email.getText().matches(Constant.getEMAILREG()) && !textField_Email.getText().equals("")) {
            setErrorTooltip(textField_Email, tooltip_ErrorEmail);
        } else {
            setDefaultTooltip(textField_Email, tooltip_Email);
        }
        if (comboBox_Status.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
        if (textField_Name.getStyle().equals(Constant.getBorderColorInherit())
                && textField_Surname.getStyle().equals(Constant.getBorderColorInherit())
                && textField_Telephone.getStyle().equals(Constant.getBorderColorInherit())
                && textField_Email.getStyle().equals(Constant.getBorderColorInherit())) {
            return true;
        } else {
            return false;
        }
    }


    public void clearFields() {
        getTextField_Name().clear();
        getTextField_Surname().clear();
        getTextField_Telephone().clear();
        getTextField_Email().clear();
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public Label getLabel_PaneName() {
        return label_PaneName;
    }

    public void setLabel_PaneName(Label label_PaneName) {
        this.label_PaneName = label_PaneName;
    }

    public TextField getTextField_Name() {
        return textField_Name;
    }

    public void setTextField_Name(TextField textField_Name) {
        this.textField_Name = textField_Name;
    }

    public TextField getTextField_Surname() {
        return textField_Surname;
    }

    public void setTextField_Surname(TextField textField_Surname) {
        this.textField_Surname = textField_Surname;
    }

    public TextField getTextField_Telephone() {
        return textField_Telephone;
    }

    public void setTextField_Telephone(TextField textField_Telephone) {
        this.textField_Telephone = textField_Telephone;
    }

    public TextField getTextField_Email() {
        return textField_Email;
    }

    public void setTextField_Email(TextField textField_Email) {
        this.textField_Email = textField_Email;
    }

    public ComboBox<EmployeeStatusEntity> getComboBox_Status() {
        return comboBox_Status;
    }

    public void setComboBox_Status(ComboBox<EmployeeStatusEntity> comboBox_Status) {
        this.comboBox_Status = comboBox_Status;
    }

    public ArrayList<EmployeeStatusEntity> getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(ArrayList<EmployeeStatusEntity> employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

}
