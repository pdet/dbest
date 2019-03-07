/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbd;

/**
 *
 * @author Lucas
 */
public class SGBD {
    
    private int id;
    private String nome;
    private String url;
    private String driver;
    private String usuario;
    private String senha;

    public SGBD(int id, String nome, String url, String driver, String usuarioDefault, String senhaDefault) {
        this.id = id;
        this.nome = nome;
        this.url = url;
        this.driver = driver;
        this.usuario = usuarioDefault;
        this.senha = senhaDefault;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senhaDefault) {
        this.senha = senhaDefault;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuarioDefault) {
        this.usuario = usuarioDefault;
    }
}
