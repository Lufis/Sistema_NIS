package br.ba.devry.nis.beans;

import br.ba.devry.nis.dao.LoginDAO;
import br.ba.devry.nis.model.Login;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daniel Melo
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private List<Login> listaLogin;
    private Login login = new Login();
    private LoginDAO validateLogin = new LoginDAO();

    public String confirmLogin() {
        FacesMessage message = null;
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

    public List listarLogin() {
        validateLogin.connect();
        if(validateLogin.isConnected()){
        listaLogin = validateLogin.getLoginList();
        return this.listaLogin;
        }
       return this.listaLogin;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }



}
