package CapadeGestionDeBD;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DetalleVenta_Reserva {

    private int cantidad;
    private long cod_productoFK; 
    private int cod_ventaFK;
    private String talle,sexo;
    private byte [] imagen;
    private String nombre;
    private int NuevoStock;
    private double subtotal;
    
    
    
    private conexion mysql = new conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    
    public Integer totalRegistros;

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getCod_productoFK() {
        return cod_productoFK;
    }

    public void setCod_productoFK(long cod_productoFK) {
        this.cod_productoFK = cod_productoFK;
    }

    public int getCod_ventaFK() {
        return cod_ventaFK;
    }

    public void setCod_ventaFK(int cod_ventaFK) {
        this.cod_ventaFK = cod_ventaFK;
    }

    public String getTalle() {
        return talle;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNuevoStock() {
        return NuevoStock;
    }

    public void setNuevoStock(int NuevoStock) {
        this.NuevoStock = NuevoStock;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public DefaultTableModel mostrar(String cod_venta) {

        DefaultTableModel modelo;
        String[] titulos
                = {"Codigo detalle", "CODIGO", "NOMBRE ",
                    "PRECIO", "CANTIDAD",
                    "Cod Venta", "STOCK", "SUB TOTAL"};
        String[] registros = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "SELECT cod_detalle ,cod_productoFK,"
                + "(SELECT nombre_producto FROM producto WHERE cod_productoFK = cod_producto)AS productoNom, "
                + " precio_producto ,cantidad_detalle, cod_ventaFK ,"
                + "(SELECT stock_producto FROM producto WHERE cod_productoFK=cod_producto)As "
                + "stock ,  subtotal FROM detalle_venta WHERE cod_ventaFK = '" + cod_venta + "' ORDER BY cod_detalle ASC ";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_detalle");
                registros[1] = rs.getString("cod_productoFK");
                registros[2] = rs.getString("productoNom");
                registros[3] = rs.getString("precio_producto");
                registros[4] = rs.getString("cantidad_detalle");
                registros[5] = rs.getString("cod_ventaFK");
                registros[6] = rs.getString("stock");
                registros[7] = rs.getString("subtotal");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public boolean insertar() {

        sSQL = "insert into ventaproducto ( codventa"
                + ", codproducto, talle ,cantidad ,subtotal) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1,   this.getCod_ventaFK());
            pst.setLong(2,  this.getCod_productoFK()); 
            pst.setString(3,this.getTalle());
            pst.setInt(4,   this.getCantidad());
            pst.setDouble(5,this.getSubtotal());
         

            int N = pst.executeUpdate();
            if (N != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }//cierre funcion
 public boolean insertarPedido() {

        sSQL = "insert into pedidoproducto ( codpedido"
                + ", codproducto, talle ,cantidad ,subtotal) values (?,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1,   this.getCod_ventaFK());
            pst.setLong(2,  this.getCod_productoFK()); 
            pst.setString(3,this.getTalle());
            pst.setInt(4,   this.getCantidad());
            pst.setDouble(5,this.getSubtotal());
         

            int N = pst.executeUpdate();
            if (N != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    public boolean eliminar(int cod) {

        sSQL = "delete from ventaproducto where cod_detalle = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, cod);
            int N = pst.executeUpdate();
            return N != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }//cierre funcion

//cierre funcion */



 public boolean ModificarStockProductos( ) 
           {
            sSQL = "update detalleproducto set cantidad = ? where codproducto = ? and talle = ?";
            try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, this.getNuevoStock());
            pst.setLong(2, this.getCod_productoFK());
            pst.setString(3, this.getTalle());
            int N = pst.executeUpdate();
                return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "Error al guardar datos en  producto talle ");
            return false;
            }
            }
   //cierre funcion
    public int ContarModeloP() {

        sSQL = "select count(*)AS cantidad from detalleproducto where codproducto =" + this.getCod_productoFK() + " and talle= '"+ this.getTalle()+"'";

        try {
            int cant = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
            cant = rs.getInt("cantidad");
            }
            return cant;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
             JOptionPane.showMessageDialog(null, "Error  ");
            return 0;
            }

    }
}
