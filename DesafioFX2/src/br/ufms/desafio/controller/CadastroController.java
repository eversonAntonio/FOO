/*
 * Copyright (C) 2016 Cliente
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

import br.ufms.desafio.conexao.Conexao;
import br.ufms.desafio.model.Endereco;
import br.ufms.desafio.model.Municipio;
import br.ufms.desafio.model.Responsavel;
import br.ufms.desafio.model.Telefone;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cliente
 */
public class CadastroController implements Initializable {
    
    protected ResultSet rs;
    @FXML
    private Button voltarBtn;
    @FXML
    private TextField idEdit;
    @FXML
    private ChoiceBox<?> usuarioTipo;
    @FXML
    private Label nascimentoLabel;
    @FXML
    private DatePicker nascimentoDataPicker;
    @FXML
    private TextField nomeEdit;
    @FXML
    private PasswordField senhaEdit;
    @FXML
    private TextField usuarioEdit;
    @FXML
    private TextField emailEdit;
    @FXML
    private TextField ufEdit;
    @FXML
    private TextField cidadeEdit;
    @FXML
    private TextField numeroEdit;
    @FXML
    private TextField logradouroEdit;
    @FXML
    private TextField bairroEdit;
    @FXML
    private TextField cepEdit;
    @FXML
    private TextField telefoneEdit;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            rs = Conexao.consultaBanco("select max(id) from usuario");
            rs.next();
            if (rs.getInt(1)!=0){
                idEdit.setText(Integer.toString(rs.getInt(1)+1));
            } else {
                idEdit.setText("1");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        usuarioTipo.setOnAction((ActionEvent event) -> {
            invisibilizarCampos();
            if (usuarioTipo.getValue().equals("Aluno")){

            } else if (usuarioTipo.getValue().equals("Escola")){

            } else if (usuarioTipo.getValue().equals("Professor")){

            } else {
                nascimentoLabel.setVisible(true);
                nascimentoDataPicker.setVisible(true);
            }
        });
        
    }    

    @FXML
    private void chamaCenaLogin(ActionEvent event) {
        
        if (usuarioTipo.getValue().equals("Aluno")){
            System.out.println("1");
        } else if (usuarioTipo.getValue().equals("Escola")){
            System.out.println("2");
        } else if (usuarioTipo.getValue().equals("Professor")){
            System.out.println("3");
        } else {
            System.out.println("4");
            if (!nascimentoDataPicker.getValue().equals("") && !emailEdit.getText().equals("") &&
                    !bairroEdit.getText().equals("") && !cepEdit.getText().equals("") &&
                    !ufEdit.getText().equals("") && !cidadeEdit.getText().equals("") &&
                    !logradouroEdit.getText().equals("") && !numeroEdit.getText().equals("") &&
                    !idEdit.getText().equals("") && !nomeEdit.getText().equals("") &&
                    !usuarioEdit.getText().equals("") && !senhaEdit.getText().equals("") &&
                    !telefoneEdit.getText().equals("")){
                Responsavel responsa = new Responsavel();
                responsa.setDataCriacao(LocalDate.now());
                responsa.setDataNascimento(nascimentoDataPicker.getValue());
                responsa.setEmail(emailEdit.getText());
                Endereco end = new Endereco();
                end.setBairro(bairroEdit.getText());
                end.setCep(cepEdit.getText());
                System.out.println("vai consultar");
                rs = Conexao.consultaBanco("select id from municipio where uf='"+ufEdit.getText().toUpperCase()+"'"
                        + "and nome='"+cidadeEdit.getText().toUpperCase()+"'");
                try {
                    rs.next();
                    end.setId(rs.getInt("id"));
                    System.out.println("consultou");
                } catch (Exception e) {
                    System.err.println(e);
                }
                Municipio mun = new Municipio();
                try {
                    mun.setId(rs.getInt("id"));
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                mun.setNome(cidadeEdit.getText());
                mun.setUf(ufEdit.getText());
                end.setIdMunicipio(mun);
                end.setLogradouro(logradouroEdit.getText());
                end.setNumero(Integer.parseInt(numeroEdit.getText()));
                responsa.setEndereco(end);
                responsa.setId(Integer.parseInt(idEdit.getText()));
                responsa.setIdUsuario(Integer.parseInt(idEdit.getText()));
                responsa.setNome(nomeEdit.getText());
                responsa.setNomeUsuario(usuarioEdit.getText());
                responsa.setSenha(senhaEdit.getText());
                Telefone tel = new Telefone();
                tel.setIdUsuario(Integer.parseInt(idEdit.getText()));
                tel.setNumero(Integer.parseInt(telefoneEdit.getText()));
                responsa.setTelefone(tel);
                responsa.setTipo('R');
                rs = Conexao.consultaBanco("insert into endereco values('"+end.getId()+"',"
                        + "'"+end.getNumero()+"', '"+end.getMunicipio().getId()+"',"
                        + "'"+end.getLogradouro()+"', '"+end.getBairro()+"',"
                        + "'"+end.getCep()+"')");
                rs = Conexao.consultaBanco("insert into usuario values('"+responsa.getId()+"',"
                        + "'"+responsa.getNome()+"', '"+responsa.getNomeUsuario()+"',"
                        + "'"+responsa.getSenha()+"', '"+responsa.getDataCriacao()+"',"
                        + "'"+responsa.getEmail()+"', '"+responsa.getEndereco().getId()+"',"
                        + "'"+responsa.getTipo()+"')");
                rs = Conexao.consultaBanco("insert into telefone values('"+tel.getNumero()+"',"
                        + "'"+tel.getIdUsuario()+"')");
                rs = Conexao.consultaBanco("insert into responsavel values('"+responsa.getId()+"',"
                        + "'"+responsa.getDataNascimento()+"')");
                Alert dialogoErro = new Alert(Alert.AlertType.INFORMATION);
                dialogoErro.setTitle("Sucesso");
                dialogoErro.setHeaderText(null);
                dialogoErro.setContentText("Data inserida no banco.");
                dialogoErro.showAndWait();
                FXMLLoader loader = new FXMLLoader();
                Parent root = null;
                try {
                    root = (Parent) loader.load(getClass().getClassLoader().getResourceAsStream(
                        "br/ufms/desafio/view/fxml/Login.fxml"));
                } catch (IOException ex){
                    System.err.println(ex);
                }
                Scene cena = new Scene(root);
                Stage janelaAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                janelaAtual.setScene(cena);
                janelaAtual.centerOnScreen();
            } else {
                Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
                dialogoErro.setTitle("Erro");
                dialogoErro.setContentText("Preencha todos os campos!");
                dialogoErro.setHeaderText("");
                dialogoErro.showAndWait();
            }
        }
    }
    
    /**
     * Método para deixar todos os componentes do gridpane da direita
     * invisíveis para que quando o tipo de usuário seja trocado
     * não haja componentes do antigo tipo de usuário visíveis.
     */
    private void invisibilizarCampos(){
        nascimentoLabel.setVisible(false);
        nascimentoDataPicker.setVisible(false);
    }
    
}