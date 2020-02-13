package model;

public class Usuario {
    
    String login;
    char[] senha;
    int id;
    
    public int getID () {
        return id;
    }
    
    public void setID (int id) {
        this.id = id;
    }
    
    public String getLogin () {
        return login;
    }
    
    public void setLogin (String login) {
        this.login = login;
    }
    
    public char[] getSenha () {
        return senha;
    }
    
    public void setSenha (char[] senha) {
        this.senha = senha;
    }

}
