/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmark.factory;

import benchmark.Benchmark;
import java.util.Properties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Lucas
 */
/* SGBDS:
 * 1 = Oracle
 * 2 = Postgres
 * 3 = SQL Server
 */
public class BenchmarkFactory {

    public Properties getArquivoDePropriedades() throws IOException {
        Properties arquivoDePropriedades = new Properties();
        //FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);     
        
        arquivoDePropriedades.load(getClass().getResourceAsStream("benchmark.properties"));
        return arquivoDePropriedades;
    }

    public ArrayList<Benchmark> getBenchmarks() throws IOException {
        ArrayList<Benchmark> benchmarks = new ArrayList<Benchmark>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        int numeroDeBenchmarks = Integer.parseInt(arquivoDePropriedades.getProperty("numeroTotalDeBenchmarks"));

        for (int i = 1; i <= numeroDeBenchmarks; i++) {
            String nomeDoBenchmark = arquivoDePropriedades.getProperty("benchmark" + i);
            
            int idDoBenchmark = i;
            int numeroDeSgbds = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + i + ".numeroDeSGBDs"));
            ArrayList<Integer> idsDosSgbds = getIdsDosSgbds(i, numeroDeSgbds);
            HashMap<Integer, String> nomesDosBancosDeDados = getNomesDosBancosDeDados(i, numeroDeSgbds);
            HashMap<Integer, String> nomesDosBancosParaArmazenarResultados = getNomesDosBancosDeDadosParaArmazenarResultados(i, numeroDeSgbds);
            HashMap<Integer, String> localizacoesDosArquivosDeConsultas = getLocalizacoesDosArquivosDeConsultas(i, numeroDeSgbds);
            HashMap<Integer, String> localizacoesDosArquivosDeCriacaoDasTabelas = getLocalizacoesDosArquivosDeCriacaoDasTabelas(i, numeroDeSgbds);

            Benchmark benchmark = new Benchmark(idDoBenchmark, nomeDoBenchmark, nomesDosBancosDeDados, nomesDosBancosParaArmazenarResultados,idsDosSgbds, localizacoesDosArquivosDeConsultas, localizacoesDosArquivosDeCriacaoDasTabelas);
            benchmarks.add(benchmark);
        }
        return benchmarks;
    }

    public ArrayList<Integer> getIdsDosSgbds(int idDoBenchmark, int numeroDeSgbds) throws IOException {
        ArrayList<Integer> idsDosSgbds = new ArrayList<Integer>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        for (int i = 1; i <= numeroDeSgbds; i++) {
            int idDoSgbd = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i));
            idsDosSgbds.add(idDoSgbd);
        }
        return idsDosSgbds;
    }

    public HashMap<Integer, String> getNomesDosBancosDeDados(int idDoBenchmark, int numeroDeSgbds) throws IOException {
        HashMap<Integer, String> nomesDosBancosDeDados = new HashMap<Integer, String>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        for (int i = 1; i <= numeroDeSgbds; i++) {
            int idDoSgbd = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i));
            String nomeDoBancoDeDados = arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i + ".nomeDoBancoDeDados");
            nomesDosBancosDeDados.put(idDoSgbd, nomeDoBancoDeDados);
        }
        return nomesDosBancosDeDados;
    }

    public HashMap<Integer, String> getNomesDosBancosDeDadosParaArmazenarResultados(int idDoBenchmark, int numeroDeSgbds) throws IOException {
        HashMap<Integer, String> nomesDosBancosParaArmazenarResultados = new HashMap<Integer, String>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        for (int i = 1; i <= numeroDeSgbds; i++) {
            int idDoSgbd = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i));
            String nomeDoBancoParaArmazenarResultados = arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i + ".nomeDoBancoDeDadosParaArmazenarResultados");
            nomesDosBancosParaArmazenarResultados.put(idDoSgbd, nomeDoBancoParaArmazenarResultados);
        }
        return nomesDosBancosParaArmazenarResultados;
    }

    public HashMap<Integer, String> getLocalizacoesDosArquivosDeConsultas(int idDoBenchmark, int numeroDeSgbds) throws IOException {
        HashMap<Integer, String> localizacoesDosArquivosDeConsultas = new HashMap<Integer, String>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        for (int i = 1; i <= numeroDeSgbds; i++) {
            int idDoSgbd = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i));
            String localizacaoDoArquivoDeConsulta = arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i + ".arquivoDeConsultas");
            localizacoesDosArquivosDeConsultas.put(idDoSgbd, localizacaoDoArquivoDeConsulta);
        }
        return localizacoesDosArquivosDeConsultas;
    }

    public HashMap<Integer, String> getLocalizacoesDosArquivosDeCriacaoDasTabelas(int idDoBenchmark, int numeroDeSgbds) throws IOException {
        HashMap<Integer, String> localizacoesDosArquivosDeCriacaoDasTabelas = new HashMap<Integer, String>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        for (int i = 1; i <= numeroDeSgbds; i++) {
            int idDoSgbd = Integer.parseInt(arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i));
            String localizacaoDoArquivoDeCriacaoDasTabelas = arquivoDePropriedades.getProperty("benchmark" + idDoBenchmark + ".sgbd" + i + ".arquivoDeCriacaoDasTabelas");
            localizacoesDosArquivosDeCriacaoDasTabelas.put(idDoSgbd, localizacaoDoArquivoDeCriacaoDasTabelas);
        }
        return localizacoesDosArquivosDeCriacaoDasTabelas;
    }
}