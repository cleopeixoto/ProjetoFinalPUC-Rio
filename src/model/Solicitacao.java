package model;

/**
 *
 * @author Cleo Peixoto
 */
public class Solicitacao {
    private int id, id_cliente;
    private String data, tipo, status, serial;
//  String dataSol = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    
    public Solicitacao (int id, int id_cliente, String data, String tipo, String status, String serial) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.data = data;
        this.tipo = tipo;
        this.status = status;
        this.serial = serial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    
}
