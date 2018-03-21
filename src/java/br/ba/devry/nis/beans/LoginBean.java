package br.ba.devry.nis.beans;

import br.ba.devry.nis.dao.LoginDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     *
     */
    private String usuario;
    private String senha;
    private String mensagem = "";

    public String confirmLogin() {
        FacesMessage message = null;
        LoginDAO validateLogin = new LoginDAO();
        validateLogin.connect();
        if (validateLogin.isConnected()) {
            if (usuario.equals("") || senha.equals("")) {
                if (usuario.equals("") && senha.equals("")) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de usuário e senha estão vazios");

                } else {
                    if (usuario.equals("")) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de usuário está vazio");
                    }
                    if (senha.equals("")) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao logar", "Campo de senha está vazio");
                    }
                }
            } else {

                if (validateLogin.userExists(usuario)) {
                    if (validateLogin.confirmPassword(senha)) {
                        mensagem = "Acesso Autorizado";
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
