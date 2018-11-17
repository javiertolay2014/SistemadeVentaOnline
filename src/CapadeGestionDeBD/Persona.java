/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapadeGestionDeBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javier
 */
public class Persona {
private String cod_persona;
private String nombre_persona  ;
private String telefono;
private String tipo;
private String usuario;
private String pass;
private conexion mysql = new conexion(); //Instanciando la clase conexion
    private Connection cn = mysql.conectar();
    private String sSQL = ""; //Sentencia SQL
    public Integer totalRegistros; // Obtener los re
    public String getCod_persona() {
        return cod_persona;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setCod_persona(String cod_persona) {
        this.cod_persona = cod_persona;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
   

public boolean insertar( ) {

        sSQL = "insert into persona (idpersona,nombre,telefono,tipo)"
                + " values (?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, this.getCod_persona());
            pst.setString(2, this.getNombre_persona());
            pst.setString(3, this.getTelefono());
            pst.setString(4, this.getTipo());
            int N = pst.executeUpdate();
            return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
            }

            }
public boolean CrearAdmistrador( ) {

        sSQL = "insert into persona (idpersona,nombre,telefono,tipo,usuario,password)"
                + " values (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, this.getCod_persona());
            pst.setString(2, this.getNombre_persona());
            pst.setString(3, this.getTelefono());
            pst.setString(4, this.getTipo());
            pst.setString(5, this.getUsuario());
            pst.setString(6, this.getPass());
            int N = pst.executeUpdate();
            return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
            }

            }

 public boolean Validacion (String login, String password) {
        sSQL = "select * from persona  where idpersona =" + login 
                + " and password =" + password ;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            return true;
        } catch (SQLException e) {
          //  JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
 public boolean Buscar (String dni) {
      
           sSQL = "select count(*)AS cantidad from persona where idpersona="+dni;
        try {
            int codigo_venta = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
            codigo_venta = rs.getInt("cantidad");
            }
            if (codigo_venta!=0) {
                return true;
            } else {
                return false;
            }
            
            
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
            }
        
        
    }
    public int ContarUsuarios() {

        sSQL = "select count(*)AS cantidad from persona";

        try {
            int codigo_venta = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
            codigo_venta = rs.getInt("cantidad");
            }
            return codigo_venta;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
            }

    }

    public DefaultTableModel mostrar() {

        DefaultTableModel modelo;

        String[] titulos
                = {"   DNI   ", "Nombre",
                     "Telefono"
                    };

        String[] registros = new String[3];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "select idpersona ,nombre,telefono from persona";
               

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("idpersona");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("telefono");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }
}
