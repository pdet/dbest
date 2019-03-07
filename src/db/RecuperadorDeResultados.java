/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import sgbd.SGBD;

/**
 *
 * @author Lucas
 */
public class RecuperadorDeResultados {

    private Connection connection;

    public RecuperadorDeResultados(SGBD sgbd, String nomeDoBancoParaArmazenarResultados, String ip) throws SQLException, ClassNotFoundException {
        String url = gerarURL(sgbd, nomeDoBancoParaArmazenarResultados, ip);
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

    public ArrayList getDadosDoTesteCadastrado(int idTeste) throws SQLException {
        ArrayList dadosDoTeste = new ArrayList();
        String clausulaSqlParaSelecionarDadosDoTeste = "Select * from teste where id = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaSelecionarDadosDoTeste);
        preparedStatement.setInt(1, idTeste);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            dadosDoTeste.add(resultSet.getInt("benchmark"));
            dadosDoTeste.add(resultSet.getString("tipo"));
            dadosDoTeste.add(resultSet.getString("ordem"));
            dadosDoTeste.add(resultSet.getString("numeroDeIteracoes"));
            dadosDoTeste.add(resultSet.getString("datainicio"));
            dadosDoTeste.add(resultSet.getString("datafim"));
            dadosDoTeste.add(resultSet.getLong("duracao"));
        }
        preparedStatement.close();
        resultSet.close();
        return dadosDoTeste;
    }

    public ArrayList<Integer> getIdDeTodosOsTestes() throws SQLException {
        ArrayList<Integer> listaDeIds = new ArrayList<Integer>();
        String clausulaSqlParaObterIdDeTodosOsTestes = "Select id from teste";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(clausulaSqlParaObterIdDeTodosOsTestes);

        while (resultSet.next()) {
            listaDeIds.add(resultSet.getInt("id"));
        }
        statement.close();
        resultSet.close();
        return listaDeIds;
    }

    public ArrayList getConsultasTeste(int idTeste) throws SQLException {
        ArrayList dadosDasConsultas = new ArrayList();
        String clausulaSqlParaObterDadosDasConsultasDeUmTeste = "Select * from consulta where idteste = ? order by iteracao,id";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaObterDadosDasConsultasDeUmTeste);
        preparedStatement.setInt(1, idTeste);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            dadosDasConsultas.add(resultSet.getInt("id"));
            dadosDasConsultas.add(resultSet.getInt("iteracao"));
            dadosDasConsultas.add(resultSet.getInt("ordem"));
            dadosDasConsultas.add(resultSet.getLong("duracao"));
        }
        preparedStatement.close();
        resultSet.close();
        return dadosDasConsultas;
    }

    public int getIdTesteAtual() throws SQLException {
        int idAtual = 0;
        Statement statement = this.connection.createStatement();
        String clausulaSqlParaRecuperarIdDoTesteAtual = "SELECT MAX(id) as maxId FROM teste";
        ResultSet resultset = statement.executeQuery(clausulaSqlParaRecuperarIdDoTesteAtual);

        if (resultset.next()) {
            idAtual = resultset.getInt("maxId");
        }
        statement.close();
        resultset.close();
        return idAtual;

    }

    public HashMap<Integer, String> getConsultasBenchmark(int idDoBenchmark) throws SQLException {
        HashMap<Integer, String> textoDasConsultas = new HashMap<Integer, String>();
        String clausulaSqlParaObterTextoDaConsulta = "select idConsulta,sqlConsulta from consultasBenchmark where idBenchmark = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaObterTextoDaConsulta);
        preparedStatement.setInt(1, idDoBenchmark);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int idDaConsulta = resultSet.getInt("idConsulta");
            String textoDaConsulta = resultSet.getString("sqlConsulta");
            textoDasConsultas.put(idDaConsulta, textoDaConsulta);
        }
        preparedStatement.close();
        resultSet.close();
        return textoDasConsultas;
    }

    public String getNomeDoBenchmark(int idDoBenchmark) throws SQLException {
        String clausulaSqlParaObterNomeDoBenchmark = "select nome from benchmark where id = " + idDoBenchmark;
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(clausulaSqlParaObterNomeDoBenchmark);
        String nomeDoBenchmark = "";
        if (resultSet.next()) {
            nomeDoBenchmark = resultSet.getString("nome");
        }
        statement.close();
        resultSet.close();
        return nomeDoBenchmark;
    }

    public HashMap<Integer, Integer> getDuracaoMediaDasConsultas(int idDoTeste) throws SQLException {
        HashMap<Integer, Integer> duracaoMediaDasConsultas = new HashMap<Integer, Integer>();
        String clausulaSqlParaObterDuracaoMediaDasConsultas = "select id,avg(duracao) as duracaoMedia from consulta where idteste = ? group by id";
        PreparedStatement preparedStatement = this.connection.prepareStatement(clausulaSqlParaObterDuracaoMediaDasConsultas);
        preparedStatement.setInt(1, idDoTeste);
       
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int idDaConsulta = resultSet.getInt("id");
            int duracaoMedia = resultSet.getInt("duracaoMedia");
            duracaoMediaDasConsultas.put(idDaConsulta, duracaoMedia);
        }
        preparedStatement.close();
        resultSet.close();
        return duracaoMediaDasConsultas;
    }
}
