/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consulta;

/**
 *
 * @author Lucas
 */
public class Consulta {

    private int id;
    private String textoConsulta;
    private long tempoDeExecucao;
    private int ordem;
    private char tipo;

    public Consulta(String textoDaConsulta) {
        textoConsulta = textoDaConsulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrdem(int ordemConsulta) {
        ordem = ordemConsulta;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setTempoDeExecucao(long tempo) {
        tempoDeExecucao = tempo;
    }

    public long getTempoDeExecucao() {
        return tempoDeExecucao;
    }

    public String getTextoConsulta() {
        return textoConsulta;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}
