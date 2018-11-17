package CapadeGestionDeBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

    public class Producto {
    
    private long cod_producto;
    private String sexo;
    private String tipodeprenda;
    private String estado;
    private String descripcion_producto;
    private Date fechaingreso ;
    private double precio_venta;
    private double precio_compra;
    private byte[] foto;   

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(long cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipodeprenda() {
        return tipodeprenda;
    }

    public void setTipodeprenda(String tipodeprenda) {
        this.tipodeprenda = tipodeprenda;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
        
        
        
        
    private conexion mysql = new conexion();
    private Connection conexion = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;

    
    
    public DefaultTableModel mostrar() {
        DefaultTableModel modelo;
        String[] titulos
                = {"Codigo",
                    "Descripcion","Sexo",
                    "Precio Venta",
                    "Precio Compra"};
        String[] registros = new String[5];
        
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from producto  order by codproducto desc";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                registros[0] = rs.getString("codproducto");
                registros[1] = rs.getString("descripcion");
                registros[2] = rs.getString("sexo");
                registros[3] = rs.getString("precioventa");
                registros[4] = rs.getString("preciocompra");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
        }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
    
    public DefaultTableModel mostraroferta() {
        DefaultTableModel modelo;
        String[] titulos
                = {"Codigo",
                    "Descripcion","Sexo",
                    "Precio Venta",
                    "Precio Compra"};
        String[] registros = new String[5];
        
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from producto   where estado= 'oferta'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                registros[0] = rs.getString("codproducto");
                registros[1] = rs.getString("descripcion");
                registros[2] = rs.getString("sexo");
                registros[3] = rs.getString("precioventa");
                registros[4] = rs.getString("preciocompra");
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

        sSQL = "insert into producto (codproducto , descripcion,tipodeprenda,sexo"
                + ", precioventa,preciocompra,fechaingreso,imagen)"
                + " values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setLong  (1, this.getCod_producto());
            pst.setString(2, this.getDescripcion_producto());
            pst.setString(3, this.getTipodeprenda());
            pst.setString(4, this.getSexo());
            pst.setDouble(5, this.getPrecio_venta());
            pst.setDouble(6, this.getPrecio_compra());
            pst.setDate(7, this.getFechaingreso());
            pst.setBytes(8,this.getFoto());
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

    public boolean editar() {
        if (this.getFoto()!=null) {
         sSQL = "update producto  set descripcion = ? , tipodeprenda=?, sexo = ?, precioventa= ? , preciocompra = ? ,fechaingreso=?,estado=?,imagen = ? "
         + "   where codproducto =? ";
        try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setString(1, this.getDescripcion_producto()); 
            pst.setString(2, this.getTipodeprenda());
            pst.setString(3, this.getSexo());
            pst.setDouble(4, this.getPrecio_venta());
            pst.setDouble(5, this.getPrecio_compra());
            pst.setDate(6, this.getFechaingreso());
            pst.setString(7,this.getEstado());
            pst.setBytes (8,this.getFoto());
            pst.setLong(9, this.getCod_producto());
            int N = pst.executeUpdate();
            return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
            }   
        } else {
        sSQL = "update producto  set descripcion = ? , tipodeprenda=?, sexo = ?, precioventa= ? , preciocompra = ? ,fechaingreso=?,estado=? "
         + "   where codproducto =? ";
        try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setString(1, this.getDescripcion_producto()); 
            pst.setString(2, this.getTipodeprenda());
            pst.setString(3, this.getSexo());
            pst.setDouble(4,this.getPrecio_venta());
            pst.setDouble(5, this.getPrecio_compra());
            pst.setDate(6, this.getFechaingreso());
            pst.setString(7,this.getEstado());
            pst.setLong(8, this.getCod_producto());
            int N = pst.executeUpdate();
            return N != 0;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
            }
        }
        

    }//cierre funcion


    public boolean eliminar() {
        sSQL = "delete from producto where codproducto = ?";
        try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);

            pst.setLong(1, this.getCod_producto());
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

    /**
     * ***** FUNCION STOCK ****
     * @param prenda
     * @return 
     */
    public boolean ModificarStockProductos(DetalleProducto prenda) 
           {
            sSQL = "update detalleproducto set cantidad = ? where codproducto = ? and talle = ?";
            try {
            PreparedStatement pst = conexion.prepareStatement(sSQL);
            pst.setInt(1, prenda.getCantidad());
            pst.setLong(2, prenda.getFKcod_producto());
            pst.setString(3, prenda.gettalle());
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


    public DefaultTableModel mostrarPorCodigo(String buscar) {

        DefaultTableModel modelo;
         String[] titulos
                = {"Codigo",
                    "Descripcion","Sexo",
                    "Precio Venta",
                    "Precio Compra"};
        String[] registros = new String[5];
        
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from producto where codproducto =" + buscar + " order by codproducto desc";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                registros[0] = rs.getString("codproducto");
                registros[1] = rs.getString("descripcion");
                registros[2] = rs.getString("sexo");
                registros[3] = rs.getString("precioventa");
                registros[4] = rs.getString("preciocompra");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
        }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
        
        
        
        
        
        
        
        
        
        
    }

    
    
    
    
    
    
    
    
     public Producto BuscarPorCodigo(String buscar) {
                Producto prenda = new Producto();
                
                totalRegistros = 0;
                sSQL = "select * from producto where codproducto ="+buscar ;
                try {
                     
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sSQL);
                
                while (rs.next()) {
                prenda.setCod_producto( rs.getLong("codproducto"));
                prenda.setDescripcion_producto(rs.getString("descripcion"));
                prenda.setSexo(rs.getString("sexo"));
                prenda.setPrecio_venta(rs.getDouble("precioventa"));
                prenda.setPrecio_compra(rs.getDouble("preciocompra"));
                prenda.setFoto(rs.getBytes("imagen"));
                prenda.setTipodeprenda(rs.getString("tipodeprenda"));
                prenda.setFechaingreso(rs.getDate("fechaingreso"));
                totalRegistros = totalRegistros + 1;
                
                }
                return prenda;
               
                }catch (SQLException e) {
                
                JOptionPane.showConfirmDialog(null, e);
                JOptionPane.showMessageDialog(null, "Error al buscar por codigo.....");
                return null;
                }
                }
    
    
    
    
       public  int BuscarStock(String producto,String talle) {
                int Stock =0;
                totalRegistros = 0;
                sSQL = "select * from detalleproducto where codproducto =" + producto + " and talle= '"+ talle +"'";
                try {
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sSQL);
                while (rs.next()) {
                Stock= rs.getInt("cantidad");
                }
                return Stock;
                }catch (SQLException e) {
               // JOptionPane.showConfirmDialog(null, e);
               // JOptionPane.showMessageDialog(null, "Error al buscar stock.....");
                return 0;
                }
                }  
    
    
     public int ContarModeloP(String producto,String talle) {

        sSQL = "select count(*)AS cantidad from detalleproducto where codproducto =" + producto + " and talle= '"+ talle +"'";

        try {
            int cantidad = 0;
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
            cantidad = rs.getInt("cantidad");
            }
            return cantidad;
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
            }

    }
    
    
    public long productoIgual(long codigo) {

        sSQL = "SELECT codproducto from producto where codproducto = " + codigo;

        try {
            long cod = 0;
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                cod = rs.getLong("codproducto");
            }

            return cod;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }

    }

    public DefaultTableModel mostrarExportar(String buscar) {

        DefaultTableModel modelo;

        String[] titulos
                = {"Codigo", "Nombre ",
                    "Descripcion", "Unidad  ",
                    "Precio Venta", "Stock", "Precio Compra,ubicacion bodega"};

        String[] registros = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "select cod_producto , nombre_producto , descripcion_producto , unidad_producto, precio_producto , stock_producto ,  precio_compra ,ubicacion_bodega from producto where nombre_producto like '%" + buscar + "%' order by cod_producto desc";

        try {

            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_producto");
                registros[1] = rs.getString("nombre_producto");
                registros[2] = rs.getString("descripcion_producto");
                registros[3] = rs.getString("unidad_producto");
                registros[4] = rs.getString("precio_producto");
                registros[5] = rs.getString("stock_producto");
                registros[6] = rs.getString("precio_compra");
                registros[7] = rs.getString("ubicacion_bodega");

                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public ArrayList<String> llenar_combo() {
        ArrayList<String> lista = new ArrayList<String>();
        sSQL = "select nombre_categoria from categoria";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                lista.add(rs.getString("nombre_categoria"));
              
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return lista;
    }

}
