/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark;

import consulta.Consulta;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Lucas
 */
public class Benchmark {
    
    private int id;
    private String nome;
    private HashMap<Integer,String> nomesDosBancosDeDados = new HashMap<Integer,String>();
    private HashMap<Integer,String> nomesDosBancosDeDadosParaArmazenarResultados = new HashMap<Integer,String>();
    private ArrayList<Integer> idsDosSgbds;
    private HashMap<Integer, String> localizacaoDosArquivosDeConsultas = new HashMap<Integer, String>();
    private HashMap<Integer, String> localizacaoDosArquivosDeCriacaoDasTabelas = new HashMap<Integer, String>();
    private ArrayList<Consulta> consultas = new ArrayList<Consulta>();

    public Benchmark(int id, String nome, HashMap<Integer,String> nomesDosBancoDeDados, 
            HashMap<Integer,String> nomesDosBancosParaArmazenarResultados, ArrayList<Integer> idsDosSgbds, 
            HashMap<Integer, String> localizacaoDosArquivosDeConsultas, HashMap<Integer, String> localizacaoDosArquivosDeCriacaoDasTabelas) {
        this.id = id;
        this.nome = nome;
        this.nomesDosBancosDeDados = nomesDosBancoDeDados;
        this.nomesDosBancosDeDadosParaArmazenarResultados = nomesDosBancosParaArmazenarResultados;
        this.idsDosSgbds = idsDosSgbds;
        this.localizacaoDosArquivosDeConsultas = localizacaoDosArquivosDeConsultas;
        this.localizacaoDosArquivosDeCriacaoDasTabelas = localizacaoDosArquivosDeCriacaoDasTabelas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public HashMap<Integer, String> getNomesDosBancosDeDados() {
        return nomesDosBancosDeDados;
    }

    public void setNomesDosBancosDeDados(HashMap<Integer, String> nomeDoBancoDeDados) {
        this.nomesDosBancosDeDados = nomeDoBancoDeDados;
    }

    public HashMap<Integer, String> getNomesDosBancosDeDadosParaArmazenarResultados() {
        return nomesDosBancosDeDadosParaArmazenarResultados;
    }

    public void setNomesDosBancosDeDadosParaArmazenarResultados(HashMap<Integer, String> nomeDoBancoDeDadosParaArmazenarResultados) {
        this.nomesDosBancosDeDadosParaArmazenarResultados = nomeDoBancoDeDadosParaArmazenarResultados;
    }

    public ArrayList<Integer> getIdsDosSgbds() {
        return idsDosSgbds;
    }

    public void setIdsDosSgbds(ArrayList<Integer> sgbds) {
        this.idsDosSgbds = sgbds;
    }

    public void addSgbdId(int idDoSgbd) {
        this.idsDosSgbds.add(idDoSgbd);
    }

    public HashMap<Integer, String> getLocalizacaoDosArquivosDeConsultas() {
        return localizacaoDosArquivosDeConsultas;
    }

    public void addLocalizacaoDoArquivoDeConsulta(int idDoSgbd, String localizacaoDoArquivoDeConsultas) {
        this.localizacaoDosArquivosDeConsultas.put(idDoSgbd, localizacaoDoArquivoDeConsultas);
    }

    public void addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public ArrayList<Consulta> getConsultas() {
        return this.consultas;
    }

    public HashMap<Integer, String> getLocalizacaoDosArquivosDeCriacaoDasTabelas() {
        return localizacaoDosArquivosDeCriacaoDasTabelas;
    }

    public void setLocalizacaoDosArquivosDeCriacaoDasTabelas(HashMap<Integer, String> localizacaoDosArquivosDeCriacaoDasTabelas) {
        this.localizacaoDosArquivosDeCriacaoDasTabelas = localizacaoDosArquivosDeCriacaoDasTabelas;
    }
    
}
