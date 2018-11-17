package CapadeGestionDeBD;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Pedido {
    private int cod_venta;
    private Date fecha_venta ;
     private Date fecha_vencimientopedido ;
    private Double total_venta ;
    private String cod_clienteFK;
    private Double descuento;
    
    private conexion mysql = new conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalRegistros;

    public Date getFecha_vencimientopedido() {
        return fecha_vencimientopedido;
    }

    public void setFecha_vencimientopedido(Date fecha_vencimientopedido) {
        this.fecha_vencimientopedido = fecha_vencimientopedido;
    }

    public int getCod_venta() {
        return cod_venta;
    }

    public void setCod_venta(int cod_venta) {
        this.cod_venta = cod_venta;
    }

    public Date getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(Date fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Double getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(Double total_venta) {
        this.total_venta = total_venta;
    }

    public String getCod_clienteFK() {
        return cod_clienteFK;
    }

    public void setCod_clienteFK(String cod_clienteFK) {
        this.cod_clienteFK = cod_clienteFK;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
    
    
    
    
    
    
public DefaultTableModel mostrar() {

        DefaultTableModel modelo;

        String[] titulos = {"Numero",
            "Fecha ", "Total", "COD USU",
            "Usuario", "COD CLIE", "Cliente", "Comprobante", "Numero", "Dscto"};

        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "select cod_venta , fecha_venta , Replace(Format(total_venta, 0), ',', '.') as total_venta  , cod_usuarioFK,"
                + "(select nombre_persona from persona where cod_persona = cod_usuarioFK)"
                + "as usuarioNom,cod_clienteFK ,"
                + "(select nombre_persona from persona where cod_persona = cod_clienteFK)"
                + "as clienteNom ,tipo_comprobante,num_factura,descuento from venta order by cod_venta DESC";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_venta");
                registros[1] = rs.getString("fecha_venta");
                registros[2] = rs.getString("total_venta");
                registros[3] = rs.getString("cod_usuarioFK");
                registros[4] = rs.getString("usuarioNom");
                registros[5] = rs.getString("cod_clienteFK");
                registros[6] = rs.getString("clienteNom");
                registros[7] = rs.getString("tipo_comprobante");
                registros[8] = rs.getString("num_factura");
                registros[9] = rs.getString("descuento");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }




    public int insertarPedido() {
          int cod=0;
        sSQL = "insert into pedido "
                + "(idcliente,total,fecha,fechavencimiento)"
                + "values(?,?,?,?)";

        try {
           
            PreparedStatement pst = cn.prepareStatement(sSQL,  Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, this.getCod_clienteFK());
            pst.setDouble(2, this.getTotal_venta());
            pst.setDate(3, this.getFecha_venta());
             pst.setDate(4, this.getFecha_vencimientopedido());
           
            int N = pst.executeUpdate();
             ResultSet rs = pst.getGeneratedKeys();
             if (rs.next()) {
             cod = rs.getInt(1);
             
             }
            if (N != 0) {
                return cod;
            } else {

                return 0;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }

    }




    public int insertar() {
          int cod=0;
        sSQL = "insert into venta "
                + "(total,fecha)"
                + "values(?,?)";

        try {
           
            PreparedStatement pst = cn.prepareStatement(sSQL,  Statement.RETURN_GENERATED_KEYS);

            
            pst.setDouble(1, this.getTotal_venta());
            pst.setDate(2, this.getFecha_venta());
            int N = pst.executeUpdate();
             ResultSet rs = pst.getGeneratedKeys();
             if (rs.next()) {
             cod = rs.getInt(1);
             
             }
            if (N != 0) {
                return cod;
            } else {

                return 0;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }

    }//cierre funcion

   /* public boolean editar(Dventa datos) {
        sSQL = "update venta set fecha_venta = ?, "
                + "total_venta = ? , cod_usuarioFK = ?  , cod_clienteFK = ? ,"
                + " tipo_comprobante =?,num_factura=? , descuento=? where cod_venta = ?";

        try {

            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setDate(1, datos.getFecha_venta());
            pst.setDouble(2, datos.getTotal_venta());
            pst.setInt(3, datos.getCod_usuarioFK());
            pst.setInt(4, datos.getCod_clienteFK());
            pst.setString(5, datos.getTipo_comprobante());
            pst.setInt(6, datos.getNum_factura());
            pst.setDouble(7, datos.getDescuento());
            pst.setInt(8, datos.getCod_venta());

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

    }//*/

    public boolean eliminar(int datos) {
        sSQL = "delete from venta where cod_venta = ?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos);
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

    
    

    /*++++++++++FUNCIONES ADICIONALES+++++++++++++++++*/
    public DefaultTableModel Buscar(String buscar) {

        DefaultTableModel modelo;

        String[] titulos = {"Numero",
            "Fecha ", "Total", "COD USU",
            "usuario", "COD CLIE", "Cliente", "Comprobante", "Dscto"};

        String[] registros = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        sSQL = "select cod_venta , fecha_venta , Replace(Format(total_venta, 0), ',', '.') as total_venta  ,"
                + " cod_usuarioFK, (select nombre_persona from persona "
                + "where cod_persona = cod_usuarioFK) as usuarioNom,"
                + " cod_clienteFK , (select nombre_persona from persona"
                + " where cod_persona = cod_clienteFK) as clienteNom ,tipo_comprobante,num_factura,descuento from venta where"
                + " (select nombre_persona from persona where cod_persona = cod_clienteFK)"
                + " LIKE '%" + buscar + "%' and cod_usuarioFK = "
                + "(select cod_usuario from usuario WHERE cod_usuario = cod_usuarioFK )"
                + "order by cod_venta DESC ";

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_venta");
                registros[1] = rs.getString("fecha_venta");
                registros[2] = rs.getString("total_venta");
                registros[3] = rs.getString("cod_usuarioFK");
                registros[4] = rs.getString("usuarioNom");
                registros[5] = rs.getString("cod_clienteFK");
                registros[6] = rs.getString("clienteNom");
                registros[7] = rs.getString("tipo_comprobante");
                registros[8] = rs.getString("num_factura");
                registros[9] = rs.getString("descuento");
                totalRegistros = totalRegistros + 1;
                modelo.addRow(registros);
            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public int BuscarCodigoVenta() {

        sSQL = "SELECT cod_venta from venta order by cod_venta DESC limit 1 ";

        try {
            int codigo_venta = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                codigo_venta = rs.getInt("cod_venta");
            }

            return codigo_venta;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }

    }

   

    public int BuscarNfacturas() {

        sSQL = "SELECT COUNT(*) as Nfactura FROM venta WHERE tipo_comprobante ='Factura'";

        try {
            int Nfactura = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                Nfactura = rs.getInt("Nfactura");
            }

            return Nfactura;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return 0;
        }

    }

}

/*

 public boolean eliminar(Dventa datos) {

        int codigo = Integer.parseInt(FrmMostrarVentas.txtCod_venta.getText());

        sSQL2 = "select cod_productoFK,sum(cantidad_detalle) as cantidad_detalle1 from detalle_venta where cod_ventaFK ="+codigo+" group by cod_productoFK";
        try {
            int codigo_producto = 0;
            int cantidad_detalle = 0;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL2);
            while (rs.next()) {
                codigo_producto = rs.getInt("cod_productoFK");
                cantidad_detalle = rs.getInt("cantidad_detalle1");
                JOptionPane.showMessageDialog(null,"Codigo Prod "+ codigo_producto);
                JOptionPane.showMessageDialog(null, "Cantidad "+cantidad_detalle);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        sSQL = "delete from venta where cod_venta = ?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, datos.getCod_venta());
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
*/
