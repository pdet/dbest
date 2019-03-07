/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import benchmark.Benchmark;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import sgbd.SGBD;

/**
 *
 * @author Lucas
 */
public class CriadorDeBancoDeDados {

    private Connection connection;

    public CriadorDeBancoDeDados(SGBD sgbd, String nomeDoBancoJaExistente, String ip) throws SQLException, ClassNotFoundException {
        String url = gerarURL(sgbd, nomeDoBancoJaExistente, ip);
        String driver = sgbd.getDriver();
        conectarAoBanco(url, sgbd.getUsuario(), sgbd.getSenha(), driver);
    }

    public static String gerarURL(SGBD sgbd, String nomeDoBanco, String ip) {
        String url = sgbd.getUrl();

        url = url.replace("ip", ip);
        url = url.replace("nomeDoBanco", nomeDoBanco);
        return url;
    }

    public void conectarAoBanco(String url, String usuario, String senha, String driver) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, usuario, senha);
    }

    public void fecharConexao() throws SQLException {
        this.connection.close();
    }

    public void criarNovoBanco(String nomeDoBanco) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("create database " + nomeDoBanco);
        statement.close();
    }

    public void criarTabelasDoBanco(SGBD sgbd, String nomeDoBanco, String ip, Benchmark benchmark) throws SQLException, ClassNotFoundException, IOException {
        String url = gerarURL(sgbd, nomeDoBanco, ip);
        conectarAoBanco(url, sgbd.getUsuario(), sgbd.getSenha(), sgbd.getDriver());
        Statement statement = this.connection.createStatement();
        String localizacaoArquivoDeCriacaoDasTabelas = benchmark.getLocalizacaoDosArquivosDeCriacaoDasTabelas().get(sgbd.getId());
        Properties arquivoScriptDeConsultas = getArquivoDePropriedades(localizacaoArquivoDeCriacaoDasTabelas);

        int numeroTotalDeTabelas = Integer.parseInt(arquivoScriptDeConsultas.getProperty("numeroTotalDeTabelas"));

        for (int i = 1; i <= numeroTotalDeTabelas; i++) {
            String tabelaParaCriar = arquivoScriptDeConsultas.getProperty("tabela" + i);
            statement.executeUpdate(tabelaParaCriar);
        }
        statement.close();
    }

    public void povoarTabelaDoBanco(String localizacaoDoArquivoScript) throws FileNotFoundException, IOException, SQLException {
        BufferedReader arquivoScript = new BufferedReader(new FileReader(localizacaoDoArquivoScript));
        String clausulaSqlParaInserirUmaTuplaNaTabela;
        Statement statement = this.connection.createStatement();
        while (arquivoScript.ready()) {
            clausulaSqlParaInserirUmaTuplaNaTabela = arquivoScript.readLine();
            if (clausulaSqlParaInserirUmaTuplaNaTabela.toLowerCase().contains("insert")) {
                statement.executeUpdate(clausulaSqlParaInserirUmaTuplaNaTabela);
            }
        }
        statement.close();
        arquivoScript.close();
    }

    public Properties getArquivoDePropriedades(String localizacaoDoArquivo) throws IOException {
        Properties arquivoDePropriedades = new Properties();
        FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);
        arquivoDePropriedades.load(arquivo);
        return arquivoDePropriedades;
    }
}
