package CapadeGestionDeBD;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class DetalleProducto 
{   private String cod_detalleProducto;
    private int FKcod_producto; 
    private String talle;
    private int cantidad;
    private ArrayList<DetalleProducto>Talles = new ArrayList<>();
    private final conexion mysql = new conexion();
    private final Connection conexion = mysql.conectar();
    private String sSQL = "";

    public String getCod_detalleProducto() {
        return cod_detalleProducto;
    }

    public void setCod_detalleProducto(String cod_detalleProducto) {
        this.cod_detalleProducto = cod_detalleProducto;
    }
    

       public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getFKcod_producto() {
        return FKcod_producto;
    }

    public void setFKcod_producto(int FKcod_producto) {
        this.FKcod_producto = FKcod_producto;
    }

    public String gettalle() {
        return talle;
    }

    public void settalle(String FKcod_talle) {
        this.talle = FKcod_talle;
    }
     public boolean insertarProductoTalle() {

        sSQL = "insert into detalleproducto ("
                + "coddetalle , codproducto ,"
                + " talle ,"
                + "cantidad)  values (?,?,?,?)";
        try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setString(1,this.getCod_detalleProducto());
            pst.setInt(2, this.getFKcod_producto());
            pst.setString(3, this.gettalle());
            pst.setInt(4, this.getCantidad());
            int N = pst.executeUpdate();
            if (N != 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar datos en  producto talle ");
                return false;
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "Error al guardar datos en  producto talle ");
            return false;
        }
    }//cierre 
 public boolean ModificarStockProductos() 
           {
            sSQL = "update detalleproducto set cantidad = ? where codproducto = ? and talle = ?";
            try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setInt(1, this.getCantidad());
            pst.setLong(2, this.getFKcod_producto());
            pst.setString(3, this.gettalle());
            int N = pst.executeUpdate();
                return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "Error al guardar datos en  producto talle ");
            return false;
            }
            }
 public  ArrayList<DetalleProducto> BuscarTalles(int codigo) {
        
        sSQL = "select * from detalleproducto  where codproducto="+codigo;
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
            DetalleProducto tallep =new DetalleProducto();
            tallep.setFKcod_producto(rs.getInt("codproducto"));
            tallep.settalle(rs.getString("talle"));
            tallep.setCantidad(rs.getInt("cantidad"));
            Talles.add(tallep);
        }
            return Talles;
        }   catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }  
    
}
