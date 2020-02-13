package model;

/**
 *
 * @author cleolaptop
 */
public class Cliente extends Usuario {
    String nome, rg, cpf, email, pais, estado, cidade, organizacao, unidadeOrg;

    public Cliente(String login) {
        this.login = login;
    }

    public Cliente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // getters e setters:    
    public String getNome () {
        return nome;
    }
    
    public void setNome (String nome) {
        this.nome = nome;
    }
    
    public String getRG () {
        return rg;
    }
    
    public void setRG (String rg) {
        this.rg = rg;
    }
    
    public String getCPF () {
        return cpf;
    }
    
    public void setCPF (String cpf) {
        this.rg = cpf;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getPais () {
        return pais;
    }
    
    public void setPais (String pais) {
        this.pais = pais;
    }
    
    public String getEstado () {
        return estado;
    }
    
    public void setEstado (String estado) {
        this.estado = estado;
    }

    public String getCidade () {
        return cidade;
    }
    
    public void setCidade (String cidade) {
        this.cidade = cidade;
    }

    public String getOrganizacao () {
        return organizacao;
    }
    
    public void setOrganizacao (String organizacao) {
        this.organizacao = organizacao;
    }

    public String getUnidadeOrg () {
        return unidadeOrg;
    }
    
    public void setUnidadeOrg (String unidadeOrg) {
        this.unidadeOrg = unidadeOrg;
    }
}
