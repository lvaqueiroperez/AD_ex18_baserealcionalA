package ex18_baserelacionala;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 Seguir lo que dice el documento hasta:
 Crear tabla produtos:
        
 create table produtos(codigo varchar2(3) primary key, descricion varchar2(15), prezo integer);

 Dende esta aplicacion java ...:
 */
public class Ex18_baserelacionalA {

    //en cada método nos conectamos y desconectamos
    Connection conn;

    /*
     crear un metodo de nome 'conexion'  que permita conectarse a base orcl
     mediante o usuario hr password hr 
     */
    //OJO, TENEMOS QUE ESTAR COMO HR PARA QUE VAYA FUNCIONANDO TODO !!!
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
            //PONER MEJOR LOS INTERROGANTES ANTES QUE LAS STRING POR TEMAS DE SEGURIDAD
            //OJO, "createStatement()" solo podemos insertar valores directamente, sin interrogaciones
            //OJO, "prepareStatement()" ya permite usar las interrogaciones
            //Usamos el método de createStatement() normal en este ejercicio (es decir, sin interrogantes):
            Statement stm = conn.createStatement();

            stm.executeUpdate("insert into produtos values('" + codigo + "','" + desc + "','" + prezo + "')");

            //AL ESTAR EL AUITOCOMMIT ACTIVO, NO DEJA HACER COMMITS
            //conn.commit();
            System.out.println("Insertado !");

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la insercción");

        }

    }
    /*
     crear un metodo chamdo 'listaProdutos' que amose o contido dos rexistros que
     hai na base  (debe usarse crearse un resulSet e volcar o contido do mesmo ) 
     */

    public void ListaProductos() {

        try {

            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select * from produtos");

            //PODEMOS OBTENER LA METADATA DE UNA TABLA Y USARLA PARA UTILIDADES
            //COMO SABER EL NOMBRE DE LAS COLUMNAS, EL NUM DE COLUMNAS ETC
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int numColumnas = rsmd.getColumnCount();
            System.out.println("Codigo\t" + "Desc\t" + "Prezo");

            while (rs.next()) {
                //las columnas se cuentan a partir de 1, NO de 0
                //en este caso sabemos que hay 3
                for (int i = 1; i <= 2; i++) {

                    System.out.print(rs.getString(i) + "\t");

                }
                System.out.print(rs.getInt(3) + "\t");
                System.out.println();

            }

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     Crear un método de nome 'actualizaPre' tal que pasandolle o codigo e prezo 
     dun rexistro actualize o campo  prezo do rexistro  que corresponde a dito  
     codigo. 
     */

    public void ActualizaPre(String codigo, int prezoNovo) {

        try {

            Statement st = conn.createStatement();

            st.executeUpdate("update produtos set prezo='" + prezoNovo + "' where codigo='" + codigo + "'");

            System.out.println("Actualizado!");

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     borrar mediante o metodo borrarFila un producto.
     */

    public void BorrarFila(String codigo) {

        try {

            Statement st = conn.createStatement();

            st.executeUpdate("delete from produtos where codigo = '" + codigo + "'");

            System.out.println("Fila borrada !");

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     Crear un método de nome ‘amosarFila’ tal que pasandolle o codigo dunha fila
     amose o contido dos seus campos
     */

    public void MostrarFila(String codigo) {

        try {
            //resultset forward_only read_only ???

            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select * from produtos where codigo='" + codigo + "'");

            System.out.println("Codigo\t" + "Desc\t" + "Prezo");

            while (rs.next()) {
                //las columnas se cuentan a partir de 1, NO de 0
                //en este caso sabemos que hay 3
                for (int i = 1; i <= 3; i++) {
                    //aunque sea int lo pasa a String directamente??? (prezo)
                    System.out.print(rs.getString(i) + "\t");

                }
                System.out.println();

            }

        } catch (SQLException ex) {
            Logger.getLogger(Ex18_baserelacionalA.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Recordar que para localizar mejor los errores, en vez del throws
    //son mejores los try-catch (buscar diferencias)
    public static void main(String[] args) throws SQLException {

        //Creamos objeto de la Clase que tiene los métodos para trabajar con ella:
        Ex18_baserelacionalA obj = new Ex18_baserelacionalA();

        obj.Conexion();

        //EN VEZ DE USAR ESTE MÉTODO, MEJOR NOS CONECTAREMOS A LA BASE CADA
        //VEZ QUE LLAMEMOS A UN MÉTODO, Y LA CERRAREMOS DE LA MISMA MANERA???
        //
        //SI INSERTAMOS UNA PRIMARY KEY REPETIDA, DARÁ ERROR
//        obj.InsertarProduto("p1", "parafusos", 3);
//        obj.InsertarProduto("p2", "cravos", 4);
//        obj.InsertarProduto("p3", "tachas", 6);
        obj.ListaProductos();
//        obj.ActualizaPre("p1", 555);
//        
//        obj.BorrarFila("p1");
//        obj.MostrarFila("p2");

        obj.Cerrar();

    }

}
