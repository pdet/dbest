/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbd.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import sgbd.SGBD;

/**
 *
 * @author Lucas
 */
public class SGBDFactory {

    /*public Properties getArquivoDePropriedades(String localizacaoDoArquivo) throws IOException {
    Properties arquivoDePropriedades = new Properties();
    FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);
    arquivoDePropriedades.load(arquivo);
    return arquivoDePropriedades;
    }*/
    public Properties getArquivoDePropriedades() throws IOException {
        Properties arquivoDePropriedades = new Properties();
        //FileInputStream arquivo = new FileInputStream(localizacaoDoArquivo);     

        arquivoDePropriedades.load(getClass().getResourceAsStream("sgbd.properties"));
        return arquivoDePropriedades;
    }

    public ArrayList<SGBD> getSGBDs() throws IOException {
        ArrayList<SGBD> sgbds = new ArrayList<SGBD>();
        Properties arquivoDePropriedades = getArquivoDePropriedades();
        int numeroDeSGBDs = Integer.parseInt(arquivoDePropriedades.getProperty("numeroTotalDeSGBDs"));

        for (int i = 1; i <= numeroDeSGBDs; i++) {
            int idDoSGBD = i;
            String nomeDoSGBD = arquivoDePropriedades.getProperty("nome" + i);
            String url = arquivoDePropriedades.getProperty("url" + i);
            String usuarioDefault = arquivoDePropriedades.getProperty("usuarioDefault" + i);
            String senhaDefault = arquivoDePropriedades.getProperty("senhaDefault" + i);
            String driver = arquivoDePropriedades.getProperty("driver" + i);
            SGBD sgbd = new SGBD(idDoSGBD, nomeDoSGBD, url, driver, usuarioDefault, senhaDefault);
            sgbds.add(sgbd);
        }
        return sgbds;
    }
}
