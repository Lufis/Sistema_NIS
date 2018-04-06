/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ba.devry.nis.rn;

import br.ba.devry.nis.dao.LoginDAO;
import br.ba.devry.nis.model.Login;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daniel Melo
 */
public class LoginRN {

    public String confirmLogin(Login login) {
        FacesMessage message = null;
        LoginDAO validateLogin = new LoginDAO();
        validateLogin.connect();
        if (validateLogin.isConnected()) {
            if (login.getUsuario().equals("") || login.getSenha().equals("")) {
                if (login.getUsuario().equals("") && login.getSenha().equals("")) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de usuário e senha estão vazios");

                } else {
                    if (login.getUsuario().equals("")) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de usuário está vazio");
                    }
                    if (login.getSenha().equals("")) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de senha está vazio");
                    }
                }
            } else {

                if (validateLogin.findUser(login.getUsuario())) {
                    if (validateLogin.confirmPassword(login.getSenha())) {

                        validateLogin.closeConnection();
                        return "menu.xhtml?faces-redirect=true";

                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao logar", "Senha incorreta");
                        validateLogin.closeConnection();
                    }

                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro ao logar", "O nome de usuário ou senha estão incorretos");
                    validateLogin.closeConnection();

                }

            }
        }

        FacesContext.getCurrentInstance()
                .addMessage(null, message);

        return null;
    }
}
