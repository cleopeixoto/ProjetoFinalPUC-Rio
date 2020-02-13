package model;

/**
 *
 * @author Cleo Peixoto
 */
public class Certificado {
    //"ID", "Versão", "Validade", "Emissor", "Status"
    private int id;
    private String versao, emissor, validade, nomeComum, status;
    
    public Certificado (int id, String versao, String emissor, String validade, String nomeComum, String status) {
        this.id = id;
        this.versao = versao;
        this.emissor = emissor;
        this.validade = validade;
        this.nomeComum =  nomeComum;
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
   
    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getEmissor () {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }
    
    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum (String nomeComum) {
        this.nomeComum = nomeComum;
    }    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
