package mx.edu.ittepic.a58calllogcontent;

public class TDACall {
    String tipo, numero, duracion;

    public TDACall(String tipo, String numero, String duracion){
        this.tipo = tipo;
        this.numero = numero;
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
