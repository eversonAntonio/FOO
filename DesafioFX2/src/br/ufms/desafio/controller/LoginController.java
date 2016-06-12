/*
 * Copyright (C) 2016 kleberkruger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufms.desafio.controller;

import br.ufms.desafio.app.DesafioFXApp2;
import br.ufms.desafio.conexao.Conexao;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author kleberkruger
 */
public class LoginController implements Initializable {
    
    protected ResultSet res;
    @FXML
    private TextField usuarioEdit;
    @FXML
    private PasswordField senhaEdit;
    @FXML
    private Label usuarioLabel;
    @FXML
    private Label senhaLabel;
    @FXML
    private Button entrarBtn;
    @FXML
    private Hyperlink cadastrarLabel;
    
    @FXML
    private void logar(ActionEvent event) {
        try {
            res = Conexao.consultaBanco("select senha from usuario where nomeUsuario='"+usuarioEdit.getText()+"'");
            res.next();
            if (res.getRow()==0){
                Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                dialogoErro.setTitle("Erro");
                dialogoErro.setContentText("Login inválido!");
                dialogoErro.setHeaderText("");
                dialogoErro.showAndWait();
            } else if (res.getString("senha").equals(senhaEdit.getText())){
                Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                dialogoErro.setTitle("Erro");
                dialogoErro.setContentText("Senha inválida!");
                dialogoErro.setHeaderText("");
                dialogoErro.showAndWait();
            } else {
                
            }
        } catch (SQLException ex){
            System.err.println(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void chamaCenaCadastro(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = (Parent) loader.load(getClass().getClassLoader().getResourceAsStream(
                "br/ufms/desafio/view/fxml/Cadastro.fxml"));
        } catch (IOException ex){
            System.out.println(ex);
        }
        Scene cena = new Scene(root);
        Stage janelaAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        janelaAtual.setScene(cena);
        janelaAtual.centerOnScreen();
    }
    
}
