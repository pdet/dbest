/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import benchmark.Benchmark;
import consulta.Consulta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import db.ArmazenadorDeResultados;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sgbd.SGBD;

/**
 *
 * @author Lucas
 */
public class Testador {

    public static final int SEQUENCIAL = 1;
    public static final int ALEATORIO = 2;
    public static final int ALEATORIO_PRE_DEFINIDO = 3;
    private Connection connection = null;
    private ArmazenadorDeResultados armazenadorDeResultados;
    private static int i;
    private static boolean tempoEsgotado = false;
    private static boolean testeConcluido = false;
    private static boolean testeJaConcluiu = false;

    public Testador(SGBD sgbd, String nomeDoBanco, String nomeDoBancoParaArmazenarResultados, String ip) throws SQLException, ClassNotFoundException {
        String urlTestador = gerarURL(sgbd, nomeDoBanco, ip);
        String urlArmazenadorDeResultados = gerarURL(sgbd, nomeDoBancoParaArmazenarResultados, ip);
        String driver = sgbd.getDriver();
        conectarAoBanco(urlTestador, sgbd.getUsuario(), sgbd.getSenha(), driver);
        armazenadorDeResultados = new ArmazenadorDeResultados(urlArmazenadorDeResultados, sgbd.getUsuario(), sgbd.getSenha());
    }

    public Testador() {
    }

    public void fecharConexao() throws SQLException {
        this.connection.close();
    }

    public void setTempo() {
        tempoEsgotado = true;
    }

    public boolean testeConcluiu() {
        return testeConcluido;
    }

    public static String gerarURL(SGBD sgbd, String nomeDoBanco, String ip) {
        String url = sgbd.getUrl();

        url = url.replace("ip", ip);
        url = url.replace("nomeDoBanco", nomeDoBanco);
        return url;
    }
    
    public void pararOTeste() {
        this.testeJaConcluiu = true;
    }

    public void conectarAoBanco(String url, String usuario, String senha, String driver) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    /*ordemDoTeste pode ser Sequencial = 1 ou Randômico = 2  */
    public void testarPorIteracao(Benchmark benchmark, int numeroDeIteracoes, int ordemDoTeste) throws SQLException {
        armazenadorDeResultados.configurarBenchmarkNoBanco(benchmark);
        int idDoTeste = armazenadorDeResultados.inserirNovoTeste("Iteração", ordemDoTeste, benchmark.getId());
        long tempoInicio = System.currentTimeMillis();
        switch (ordemDoTeste) {
            case SEQUENCIAL:
                for (int iteracao = 1; iteracao <= numeroDeIteracoes; iteracao++) {
                    testarSequencialmente(benchmark, idDoTeste, iteracao);
                }
                break;
            case ALEATORIO:
                for (int iteracao = 1; iteracao <= numeroDeIteracoes; iteracao++) {
                    testarAleatorio(benchmark, idDoTeste, iteracao);
                }
                break;
            case ALEATORIO_PRE_DEFINIDO:
                ArrayList<Integer> ordemDasConsultas = sortearOrdemDasConsultas(benchmark.getConsultas());
                for (int iteracao = 1; iteracao <= numeroDeIteracoes; iteracao++) {
                    testarAleatorioPreDefinido(benchmark, ordemDasConsultas, idDoTeste, iteracao);
                }
                break;
        }
        long tempoFim = System.currentTimeMillis();
        long duracaoTotalDoTeste = tempoFim - tempoInicio;
        armazenadorDeResultados.atualizarDadosDoTesteConcluido(idDoTeste, duracaoTotalDoTeste, numeroDeIteracoes);
        testeConcluido = true;
    }

    public void testarSequencialmente(Benchmark benchmark, int idDoTeste, int iteracao) throws SQLException {
        ArrayList<Consulta> consultas = benchmark.getConsultas();

        for (int i = 0; i < consultas.size(); i++) {
            Consulta consulta = consultas.get(i);
            //Tirar
            System.out.println("Consulta" + (i + 1) + "executando....");
            long tempoInicial = System.currentTimeMillis();
            executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
            long tempoFinal = System.currentTimeMillis();
            consulta.setTempoDeExecucao(tempoFinal - tempoInicial);
            /*A ordem de execução de cada consulta é i+1 */
            consulta.setOrdem(i + 1);

            armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, iteracao);
        }
    }

    //Obs: A ordem de execução de cada consulta é numeroSorteado + 1
    public void testarAleatorio(Benchmark benchmark, int idDoTeste, int iteracao) throws SQLException {
        ArrayList<Consulta> consultas = benchmark.getConsultas();
        ArrayList<Integer> ordemDasConsultas = sortearOrdemDasConsultas(consultas);

        for (int i = 0; i < ordemDasConsultas.size(); i++) {
            int numeroDaConsultaAExecutar = ordemDasConsultas.get(i);
            Consulta consulta = consultas.get(numeroDaConsultaAExecutar);
            consulta.setOrdem(i + 1);
            //Tirar
            System.out.println("Consulta " + (numeroDaConsultaAExecutar + 1) + " executando");

            long tempoInicial = System.currentTimeMillis();
            executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
            long tempoFinal = System.currentTimeMillis();
            consulta.setTempoDeExecucao(tempoFinal - tempoInicial);

            armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, iteracao);
        }

    }

    public void testarAleatorioPreDefinido(Benchmark benchmark, ArrayList<Integer> ordemDasConsultas, int idDoTeste, int iteracao) throws SQLException {
        ArrayList<Consulta> consultas = benchmark.getConsultas();

        for (int i = 0; i < ordemDasConsultas.size(); i++) {
            int numeroDaConsultaAExecutar = ordemDasConsultas.get(i);
            Consulta consulta = consultas.get(numeroDaConsultaAExecutar);
            consulta.setOrdem(i + 1);
            //Tirar
            System.out.println("Consulta " + (numeroDaConsultaAExecutar + 1) + " executando");

            long tempoInicial = System.currentTimeMillis();
            executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
            long tempoFinal = System.currentTimeMillis();
            consulta.setTempoDeExecucao(tempoFinal - tempoInicial);

            armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, iteracao);
        }
    }

    public ArrayList<Integer> sortearOrdemDasConsultas(ArrayList<Consulta> consultas) {
        Random random = new Random();
        ArrayList<Integer> numerosSorteados = new ArrayList<Integer>();

        while (numerosSorteados.size() < consultas.size()) {
            /* Gera número aleatório entre 0 e o tamanho da lista de consultas - 1 */
            int numeroSorteado = random.nextInt(consultas.size());
            if (!numerosSorteados.contains(numeroSorteado)) {
                numerosSorteados.add(numeroSorteado);
            }
        }
        return numerosSorteados;
    }

    /* Método que envia a consulta para o banco
    @param query: String que contém o texto da consulta a ser enviado pro SGBD
    @param tipo: tipo de consulta: 'S' = Select e 'U' = Update*/
    public void executarConsulta(String textoDaConsulta, char tipo) throws SQLException {
        PreparedStatement preparedtatement = this.connection.prepareStatement(textoDaConsulta);
        preparedtatement.execute();
        preparedtatement.close();
    }

    public void testarTempo(Benchmark benchmark, int ordemDoTeste) throws SQLException {
        int idDoTeste = armazenadorDeResultados.inserirNovoTeste("Tempo", ordemDoTeste, benchmark.getId());
        ArrayList<Consulta> consultas = benchmark.getConsultas();
        long tempoInicio = System.currentTimeMillis();
        int j = 1;
        int contadorDeIteracoes = 0;
        Random random = new Random();
        ArrayList<Integer> numerosJaSorteados = new ArrayList<Integer>();
        while (true) {
            if (ordemDoTeste == SEQUENCIAL) {          
                for (i = 0; i < consultas.size(); i++) {
                         if (tempoEsgotado == true) {
                    break;
                }
                    Consulta consulta = consultas.get(i);
                    //Tirar
                    System.out.println("Consulta" + (i + 1) + "executando....");
                    long tempoInicial = System.currentTimeMillis();
                    executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
                    long tempoFinal = System.currentTimeMillis();
                    consulta.setTempoDeExecucao(tempoFinal - tempoInicial);
                    /*A ordem de execução de cada consulta é i+1 */
                    consulta.setOrdem(i + 1);
                    
                    armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, j);
                }

            } else if (ordemDoTeste == ALEATORIO) {
                while (numerosJaSorteados.size() < consultas.size()) {
                    if (tempoEsgotado == true) {
                        break;
                    }

                    /* Gera número aleatório entre 0 e o tamanho da lista de consultas - 1 */
                    int numeroSorteado = random.nextInt(consultas.size());

                    if (!numerosJaSorteados.contains(numeroSorteado)) {
                        numerosJaSorteados.add(numeroSorteado);
                        //Tirar
                        System.out.println(numeroSorteado + 1);

                        Consulta consulta = consultas.get(numeroSorteado);
                        /* A ordem de execução de cada consulta é o tamanho da lista que guarda os número já sorteados */
                        consulta.setOrdem(numerosJaSorteados.size());
                        long tempoInicial = System.currentTimeMillis();
                        executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
                        long tempoFinal = System.currentTimeMillis();
                        consulta.setTempoDeExecucao(tempoFinal - tempoInicial);

                        armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, j);
                    }
                }
            } else if (ordemDoTeste == ALEATORIO_PRE_DEFINIDO) {
                ArrayList<Integer> numerosSorteados = new ArrayList<Integer>();

                while (numerosSorteados.size() < consultas.size()) {
                    /* Gera número aleatório entre 0 e o tamanho da lista de consultas - 1 */
                    int numeroSorteado = random.nextInt(consultas.size());
                    if (!numerosSorteados.contains(numeroSorteado)) {
                        numerosSorteados.add(numeroSorteado);
                    }
                }
                
                for (int i = 0; i < numerosSorteados.size(); i++) {
                    if (tempoEsgotado == true) {
                        break;
                    }
                    int numeroDaConsultaAExecutar = numerosSorteados.get(i);
                    Consulta consulta = consultas.get(numeroDaConsultaAExecutar);
                    consulta.setOrdem(i + 1);
                    //Tirar
                    System.out.println("Consulta " + (numeroDaConsultaAExecutar + 1) + " executando");

                    long tempoInicial = System.currentTimeMillis();
                    executarConsulta(consulta.getTextoConsulta(), consulta.getTipo());
                    long tempoFinal = System.currentTimeMillis();
                    consulta.setTempoDeExecucao(tempoFinal - tempoInicial);

                    armazenadorDeResultados.inserirConsulta(consulta, idDoTeste, j);
                }

            }
            contadorDeIteracoes++;
            j++;
            if (tempoEsgotado == true) {
                break;
            }

        }
        long tempoFim = System.currentTimeMillis();
        long duracaoTotalDoTeste = tempoFim - tempoInicio;
        armazenadorDeResultados.atualizarDadosDoTesteConcluido(idDoTeste, duracaoTotalDoTeste, contadorDeIteracoes);
        testeConcluido = true;
    }
}
