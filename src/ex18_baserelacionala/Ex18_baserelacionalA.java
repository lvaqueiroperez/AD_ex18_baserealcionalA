package ex18_baserelacionala;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 Seguir lo que dice el documento hasta:
 Crear tabla produtos:
        
 create table produtos(codigo varchar2(3) primary key, descricion varchar2(15), prezo integer);

 Dende esta aplicacion java ...:
 */
public class Ex18_baserelacionalA {

    Connection conn;

    /*
     crear un metodo de nome 'conexion'  que permita conectarse a base orcl
     mediante o usuario hr password hr 
     */
    public void Conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

        conn = DriverManager.getConnection(url);

    }

    public void Cerrar() throws SQLException {

        conn.close();
    }

    /*
     crear un metodo de nome 'insireProduto'  que permita inserir na taboa produtos
     unha fila pasandolle como parametros o codigo o nome e o prezo dun produto
     */
    public void InsertarProduto(String codigo, String desc, int prezo) throws SQLException {

        try {
            //ACORDARSE DE PONER LAS COMILLAS NECESARIAS, TODO DEBE SER UN STRING
            //NO HACE FALTA PONER EL ";" AL FINAL
            //EN ESTOS MÉTODOS, HACE FALTA DISTINGUIR LAS MAYUS (?)
            //PONER MEJOR LOS INTERROGANTES ANTES QUE LA STRING
            //OJO, "createStatement()" solo podemos insertar valores directamente, sin interrogaciones
            //OJO, "prepareStatement()" ya permite usar las interrogaciones
            conn.prepareStatement("INSERT INTO produtos VALUES(?,?,?)");
            conn.commit();
            System.out.println("Insertado !");

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la insercción");

        }

    }

    public void ListaProductos() {

    }

    public void ActualizaPre() {

    }

    public void BorrarFila() {

    }

    public void MostrarFila() {

    }

    //Recordar que para localizar mejor los errores, en vez del throws
    //son mejores los try-catch (buscar diferencias)
    public static void main(String[] args) throws SQLException {

        //Creamos objeto de la Clase que tiene los métodos para trabajar con ella:
        Ex18_baserelacionalA obj = new Ex18_baserelacionalA();

        obj.Conexion();
        //SI INSERTAMOS UNA PRIMARY KEY REPETIDA, DARÁ ERROR
        //obj.InsertarProduto("p1", "parafusos", 3);
        //obj.InsertarProduto("p2", "cravos", 4);
        //obj.InsertarProduto("p3", "tachas", 6);
        obj.InsertarProduto("p5", "hola", 13);
        obj.Cerrar();
    }

}
