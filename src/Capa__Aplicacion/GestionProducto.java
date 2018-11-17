
package Capa__Aplicacion;

import CapaInterfaz.InterfazLiquidacion;
import CapaInterfaz.InterfazProducto;
import CapadeGestionDeBD.DetalleProducto;
import CapadeGestionDeBD.Producto;
import java.awt.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javier
 */
public class GestionProducto {
    
    private File file;
    private List<JTable> tabla;
    private List<String> nom_files;
    private final List<DetalleProducto> ListaDetalle = new ArrayList<>();
    private String ruta;

    public GestionProducto() {

    }
         public void CrearTabla()  {
            
           DefaultTableModel t;
            Producto funcionproducto = new Producto();
            t = funcionproducto.mostrar();
            
            
            
            InterfazProducto.jTabla.setModel(t);
              //mostrar(" ");
               InterfazProducto.jTabla.getColumnModel().getColumn(1).setPreferredWidth(220);
               //InterfazProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
               InterfazProducto.jTabla.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
              @Override
              public Component getTableCellRendererComponent(JTable table, Object value,
              boolean isSelected, boolean hasFocus, int row, int column) {
              JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //l.setBorder(new LineBorder(Color.black, 1));
              l.setBackground(new java.awt.Color(36, 33, 33));
              l.setForeground(new java.awt.Color(25, 118, 210));
              l.setFont(new java.awt.Font("Arial", 1, 12));
              return l;
               }
               });   
    } 
         
         
                  public void CrearTablaOferta()  {
            
           DefaultTableModel t;
            Producto funcionproducto = new Producto();
            t = funcionproducto.mostraroferta();
            
            
            
            InterfazLiquidacion.jTabla.setModel(t);
              //mostrar(" ");
               InterfazLiquidacion.jTabla.getColumnModel().getColumn(1).setPreferredWidth(220);
               //InterfazProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
               InterfazLiquidacion.jTabla.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
              @Override
              public Component getTableCellRendererComponent(JTable table, Object value,
              boolean isSelected, boolean hasFocus, int row, int column) {
              JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //l.setBorder(new LineBorder(Color.black, 1));
              l.setBackground(new java.awt.Color(36, 33, 33));
              l.setForeground(new java.awt.Color(25, 118, 210));
              l.setFont(new java.awt.Font("Arial", 1, 12));
              return l;
               }
               });   
    }
    
  public void EventoGuardar() {                                           
        DetalleProducto detallePrenda = new DetalleProducto();
        /*PARTE VALIDACION DE CAMPOS*/
        if (InterfazProducto.txtCod_producto.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Codigo.");
            InterfazProducto.txtCod_producto.requestFocus();
            return;
        }

        if (InterfazProducto.txtDescripcion.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Descripcion");
            InterfazProducto.txtDescripcion.requestFocus();
            return;
        }

        if (InterfazProducto.txtPrecioventa.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio de venta.");
            InterfazProducto.txtPrecioventa.requestFocus();
            return;
        }

        if (InterfazProducto.txtPrecio_compra.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio de compra");
            InterfazProducto.txtPrecio_compra.requestFocus();
            return;
        }

        
        Producto datos = new Producto();
        long verificarCodigo = Long.parseLong(InterfazProducto.txtCod_producto.getText());
        long cod_producto = datos.productoIgual(verificarCodigo);
        if (cod_producto == verificarCodigo) {
            JOptionPane.showMessageDialog(null, "YA EXISTE UN PRODUCTO CON EL MISMO CODIGO");
            InterfazProducto.txtCod_producto.requestFocus();
            return;
        }

        datos.setCod_producto(Long.valueOf(InterfazProducto.txtCod_producto.getText()));
        datos.setDescripcion_producto(InterfazProducto.txtDescripcion.getText());
        datos.setPrecio_venta(Double.valueOf(InterfazProducto.txtPrecioventa.getText()));
        datos.setPrecio_compra(Double.valueOf(InterfazProducto.txtPrecio_compra.getText()));
        datos.setSexo(InterfazProducto.ComboBoxSexo.getSelectedItem().toString());
        Calendar cal;
        int d, m, a;
        cal = InterfazProducto.dcFecha_venta.getCalendar();
        d = cal.get(Calendar.DAY_OF_MONTH);
        m = cal.get(Calendar.MONTH);
        a = cal.get(Calendar.YEAR) - 1900;
        datos.setFechaingreso(new Date(a, m, d));
        datos.setTipodeprenda(InterfazProducto.ComboBoxTipodePrenda.getSelectedItem().toString());
        detallePrenda.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
        if (ruta!=null) {
          
      
        File imagen = new File(ruta);
        try{

            byte[] icono = new byte[(int) imagen.length()];
            InputStream input = new FileInputStream(imagen);
            input.read(icono);
            datos.setFoto(icono);

        }catch(IOException e){
            datos.setFoto(null);
        }
} else {
      JOptionPane.showMessageDialog(null, " Ingrese una imagen.");
        
        }
        
        
        
        if (datos.insertar()) {

            switch (InterfazProducto.ComboBoxTipodePrenda.getSelectedItem().toString())
            {
                case "Pantalon":
                ControlarTallePantalon();

                for (int i = 0; i < ListaDetalle.size(); i++)
                {
                    ListaDetalle.get(i).insertarProductoTalle();
                }
                System.out.println ("Pantalon");
                break;
                default:
                ControlarTalle(detallePrenda);
                for (int i = 0; i < ListaDetalle.size(); i++)
                {
                    ListaDetalle.get(i).insertarProductoTalle();
                }
                System.out.println ("NADA"); break;
            }

            JOptionPane.showMessageDialog(null, "Producto Ingresado.");
            //mostrar("");

            DefaultTableModel tab=datos.mostrar();
            InterfazProducto.jTabla.setModel(tab);
            ruta =null;
            InterfazProducto.imagen.setIcon(null);
            inhabilitar();
            inhabilitarStock();
            inhabilitarStockPantalon();
        } else {
            JOptionPane.showMessageDialog(null, "Producto no Ingresado.");
            //mostrar("");

        }
    }  
    public void EventoEditar() {                                          
       
  DetalleProducto detallePrenda = new DetalleProducto();
        /*PARTE VALIDACION DE CAMPOS*/
        if (InterfazProducto.txtCod_producto.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Codigo.");
            InterfazProducto.txtCod_producto.requestFocus();
            return;
        }

        if (InterfazProducto.txtDescripcion.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Descripcion");
            InterfazProducto.txtDescripcion.requestFocus();
            return;
        }

        if (InterfazProducto.txtPrecioventa.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio de venta.");
            InterfazProducto.txtPrecioventa.requestFocus();
            return;
        }

        if (InterfazProducto.txtPrecio_compra.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Precio de compra");
            InterfazProducto.txtPrecio_compra.requestFocus();
            return;
        }
        
        Producto datos = new Producto();
        datos.setCod_producto(Long.valueOf(InterfazProducto.txtCod_producto.getText()));
        datos.setDescripcion_producto(InterfazProducto.txtDescripcion.getText());
        datos.setPrecio_venta(Double.valueOf(InterfazProducto.txtPrecioventa.getText()));
        datos.setPrecio_compra(Double.valueOf(InterfazProducto.txtPrecio_compra.getText()));
        datos.setSexo(InterfazProducto.ComboBoxSexo.getSelectedItem().toString());
        Calendar cal;
        int d, m, a;
        cal = InterfazProducto.dcFecha_venta.getCalendar();
        d = cal.get(Calendar.DAY_OF_MONTH);
        m = cal.get(Calendar.MONTH);
        a = cal.get(Calendar.YEAR) - 1900;
        datos.setFechaingreso(new Date(a, m, d));
        datos.setTipodeprenda(InterfazProducto.ComboBoxTipodePrenda.getSelectedItem().toString());
        detallePrenda.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
        if (ruta!=null) {
          File imagen = new File(ruta);
        try{
            byte[] icono = new byte[(int) imagen.length()];
            InputStream input = new FileInputStream(imagen);
            input.read(icono);
            datos.setFoto(icono);
            }catch(IOException e){
            datos.setFoto(null);
            }   
            } else 
            {
            datos.setFoto(null);
         }
       

        if (datos.editar()) {

            switch (InterfazProducto.ComboBoxTipodePrenda.getSelectedItem().toString())
            {
                case "Pantalon":
                ControlarTallePantalon();

                for (int i = 0; i < ListaDetalle.size(); i++)
                {
                    
                    if ( ListaDetalle.get(i).ModificarStockProductos()) {
                    } else {
                      ListaDetalle.get(i).insertarProductoTalle();
                    
                    }
                    
                }
              
                break;
                default:
                ControlarTalle(detallePrenda);
                for (int i = 0; i < ListaDetalle.size(); i++)
                {
                    if ( ListaDetalle.get(i).ModificarStockProductos()) {
                    } else {
                      ListaDetalle.get(i).insertarProductoTalle();
                    
                    }
                }
            break;
            }

            JOptionPane.showMessageDialog(null, "Producto Modificado");
            //mostrar("");

            DefaultTableModel tab=datos.mostrar();
            InterfazProducto.jTabla.setModel(tab);
            ruta =null;
            InterfazProducto.imagen.setIcon(null);
            inhabilitar();
            inhabilitarStock();
            inhabilitarStockPantalon();
        } else {
            JOptionPane.showMessageDialog(null, "Producto no Modificado.");
            //mostrar("");

        }
    }  
    
      public void EventoEliminar() {                                            

        if (InterfazProducto.txtCod_producto.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Registro de la Tabla");
            return;
        }
        int i = JOptionPane.showConfirmDialog(null, "Si elimina el Producto lo borrara de las ventas asociadas a el ¿Desea Eliminar?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (!InterfazProducto.txtCod_producto.getText().equals("")) {
                Producto datos = new Producto();
                datos.setCod_producto(Long.valueOf(InterfazProducto.txtCod_producto.getText()));
                datos.eliminar();
                
                mostrar("");
                InterfazProducto.imagen.setIcon(null);
                inhabilitar();
            } else {

                JOptionPane.showMessageDialog(null, "No se elimino el producto.");
            }

        }

    }   
    
    
     public void EventoImagen() {                                              
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        j.setFileFilter(fil);
        int s = j.showOpenDialog(null);
        if(s == JFileChooser.APPROVE_OPTION){
            ruta = j.getSelectedFile().getAbsolutePath();
            ImageIcon preview = new ImageIcon(ruta);
            if(preview != null){
                InterfazProducto.imagen.setText("");
                Icon icon = new ImageIcon(preview.getImage().getScaledInstance(InterfazProducto.imagen.getWidth(),InterfazProducto.imagen.getHeight(),50));
                InterfazProducto.imagen.setIcon(icon);
                

            }

        }        // TODO add your handling code here:
    }  
     public void mostrar(String buscar) {

        try {
            DefaultTableModel tabla1;
            Producto funcionproducto = new Producto();

            if (InterfazProducto.cboSelec.getSelectedItem() == "Codigo") {
                tabla1 = funcionproducto.mostrarPorCodigo(buscar);
            } else {
                tabla1 = funcionproducto.mostrar();
            }

            InterfazProducto.jTabla.setModel(tabla1);
           } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
          }
    }    
 public void EventoTablaMouseClicked(java.awt.event.MouseEvent evt) {                                    

      
     
        habilitar();
        InterfazProducto.btnEliminar.setEnabled(true);
        InterfazProducto.btnGuardar.setEnabled(false);
        InterfazProducto.btnEditar.setEnabled(true);
        InterfazProducto.btnNuevo.setEnabled(true);
        InterfazProducto.txtCod_producto.setEditable(false);
         int fila = InterfazProducto.jTabla.rowAtPoint(evt.getPoint());
        
         Producto prenda = new Producto();
         prenda = prenda.BuscarPorCodigo(InterfazProducto.jTabla.getValueAt(fila, 0).toString());
     
         try{
         byte[] bi = prenda.getFoto();
         BufferedImage image = null;
         InputStream in = new ByteArrayInputStream(bi);
         image = ImageIO.read(in);
         Icon icon = new ImageIcon(image.getScaledInstance(InterfazProducto.imagen.getWidth(),InterfazProducto.imagen.getHeight(),50));
         InterfazProducto.imagen.setIcon(icon);
         }catch(IOException ex){
         }
        InterfazProducto.txtCod_producto.setText(String.valueOf(prenda.getCod_producto()));
        InterfazProducto.txtDescripcion.setText(prenda.getDescripcion_producto());
        InterfazProducto.txtPrecioventa.setText(String.valueOf(prenda.getPrecio_venta()));
        InterfazProducto.txtPrecio_compra.setText(String.valueOf(prenda.getPrecio_compra()));
        InterfazProducto.ComboBoxSexo.setSelectedItem(prenda.getSexo());
        InterfazProducto.dcFecha_venta.setDate(prenda.getFechaingreso());
        if (prenda.getTipodeprenda() != null) {
        InterfazProducto.ComboBoxTipodePrenda.setSelectedItem(prenda.getTipodeprenda());   
        }
        DetalleProducto Talles = new DetalleProducto();
        switch (prenda.getTipodeprenda()) {
        case "Pantalon":
        InsertarStockTalleNU(Talles.BuscarTalles((int) prenda.getCod_producto()));
        break;
        default:
        InsertarStockTalleLE(Talles.BuscarTalles((int) prenda.getCod_producto()));
        break;
 }
    }     
     
    public void EventoTipodePrenda() {                                                     
        switch (InterfazProducto.ComboBoxTipodePrenda.getSelectedItem().toString())
        {
            case "Pantalon":
            inhabilitarStock();
            habilitarStockPantalon();
            break;
            default:
            habilitarStock();
            inhabilitarStockPantalon();
            break;
        }     // TODO add your handling code here:
    }   
  public void inhabilitarStock()
    {
    InterfazProducto.txtTalleXS.setEditable(false);
    InterfazProducto.txtTalleS.setEditable(false);
    InterfazProducto.txtTalleM.setEditable(false);
    InterfazProducto.txtTalleL.setEditable(false);
    InterfazProducto.txtTalleXL.setEditable(false);
    InterfazProducto.txtTalleXXL.setEditable(false);
    InterfazProducto.txtTalleXXXL.setEditable(false);
    }
      public void habilitarStock()
    {
    InterfazProducto.txtTalleXS.setEditable(true);
    InterfazProducto.txtTalleS.setEditable(true);
    InterfazProducto.txtTalleM.setEditable(true);
    InterfazProducto.txtTalleL.setEditable(true);
    InterfazProducto.txtTalleXL.setEditable(true);
    InterfazProducto.txtTalleXXL.setEditable(true);
    InterfazProducto.txtTalleXXXL.setEditable(true);
    }
      
        public void inhabilitarStockPantalon()
    {
    InterfazProducto.txtTalle36.setEditable(false);
    InterfazProducto.txtTalle38.setEditable(false);
    InterfazProducto.txtTalle40.setEditable(false);
    InterfazProducto.txtTalle42.setEditable(false);
    InterfazProducto.txtTalle44.setEditable(false);
    InterfazProducto.txtTalle46.setEditable(false);
    InterfazProducto.txtTalle48.setEditable(false);
    }
      public void habilitarStockPantalon()
    {
    InterfazProducto.txtTalle36.setEditable(true);
    InterfazProducto.txtTalle38.setEditable(true);
    InterfazProducto.txtTalle40.setEditable(true);
    InterfazProducto.txtTalle42.setEditable(true);
    InterfazProducto.txtTalle44.setEditable(true);
    InterfazProducto.txtTalle46.setEditable(true);
    InterfazProducto.txtTalle48.setEditable(true);
    } 
      
      
 
    public void inhabilitar() {

     InterfazProducto.txtCod_producto.setEditable(false);
     InterfazProducto.txtDescripcion.setEditable(false);
     InterfazProducto.txtPrecioventa.setEditable(false);
     InterfazProducto.txtPrecio_compra.setEditable(false);
     InterfazProducto.txtmarca.setEditable(false);
     InterfazProducto.ComboBoxSexo.setEnabled(false);
     InterfazProducto.ComboBoxTipodePrenda.setEnabled(false);
     InterfazProducto.txtBuscar.setEditable(false);
     InterfazProducto.btnBuscar.setEnabled(false);
     InterfazProducto.btnEditar.setEnabled(false);
     InterfazProducto.btnEliminar.setEnabled(false);
     InterfazProducto.btnGuardar.setEnabled(false);
     InterfazProducto.jButtonImagen.setEnabled(false);
     InterfazProducto.dcFecha_venta.setEnabled(false);
     InterfazProducto.btnNuevo.setEnabled(true);
     InterfazProducto.txtCod_producto.setText("");
     InterfazProducto.txtDescripcion.setText("");
     InterfazProducto.txtPrecioventa.setText("");
     InterfazProducto.txtBuscar.setText("");
     InterfazProducto.txtPrecio_compra.setText("");
     InterfazProducto.txtTalle36.setText("");
     InterfazProducto.txtTalle38.setText("");
     InterfazProducto.txtTalle40.setText("");
     InterfazProducto.txtTalle42.setText("");
     InterfazProducto.txtTalle44.setText("");
     InterfazProducto.txtTalle46.setText("");
     InterfazProducto.txtTalle48.setText("");
     InterfazProducto.txtTalleXS.setText("");
     InterfazProducto.txtTalleS.setText("");
     InterfazProducto.txtTalleM.setText("");
     InterfazProducto.txtTalleL.setText("");
     InterfazProducto.txtTalleXL.setText("");
     InterfazProducto.txtTalleXXL.setText("");
     InterfazProducto.txtTalleXXXL.setText("");
     InterfazProducto.txtmarca.setText("");
    }

    public void habilitar() {
     InterfazProducto.txtPrecio_compra.setEditable(true);
     InterfazProducto.txtCod_producto.setEditable(true);
     InterfazProducto.txtDescripcion.setEditable(true);
     InterfazProducto.txtPrecioventa.setEditable(true);
     InterfazProducto.txtBuscar.setEditable(true);
     InterfazProducto.btnBuscar.setEnabled(true);
     InterfazProducto.btnEliminar.setEnabled(true);
     InterfazProducto.btnGuardar.setEnabled(true);
     InterfazProducto.btnNuevo.setEnabled(false);
     InterfazProducto.txtCod_producto.setText("");
     InterfazProducto.txtDescripcion.setText("");
     InterfazProducto.txtPrecioventa.setText("");
     InterfazProducto.txtBuscar.setText("");
     InterfazProducto.txtPrecio_compra.setText("");
     InterfazProducto.txtTalle36.setText("");
     InterfazProducto.txtTalle38.setText("");
     InterfazProducto.txtTalle40.setText("");
     InterfazProducto.txtTalle42.setText("");
     InterfazProducto.txtTalle44.setText("");
     InterfazProducto.txtTalle46.setText("");
     InterfazProducto.txtTalle48.setText("");
     InterfazProducto.txtTalleXS.setText("");
     InterfazProducto.txtTalleS.setText("");
     InterfazProducto.txtTalleM.setText("");
     InterfazProducto.txtTalleL.setText("");
     InterfazProducto.txtTalleXL.setText("");
     InterfazProducto.txtTalleXXL.setText("");
     InterfazProducto.txtTalleXXXL.setText("");
     InterfazProducto.txtmarca.setText("");
     InterfazProducto.imagen.setIcon(null);
     InterfazProducto.txtmarca.setEditable(true);
     InterfazProducto.ComboBoxSexo.setEnabled(true);
     InterfazProducto.ComboBoxTipodePrenda.setEnabled(true);
     InterfazProducto.jButtonImagen.setEnabled(true);
     InterfazProducto.dcFecha_venta.setEnabled(true);
    }
 public void ControlarTallePantalon(){ 
        
            try {
             if (Integer.parseInt(InterfazProducto.txtTalle36.getText()) > 0 && InterfazProducto.txtTalle36.getText()!= null ) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle36.getText()));
            detalleP.settalle("36");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"36");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
            try {
            if (Integer.parseInt(InterfazProducto.txtTalle38.getText()) > 0 && InterfazProducto.txtTalle38.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle38.getText()));
            detalleP.settalle("38");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"38");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
       
            try {
            if (Integer.parseInt(InterfazProducto.txtTalle40.getText()) > 0 && InterfazProducto.txtTalle40.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle40.getText()));
            detalleP.settalle("40");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"40");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
            try {
             if (Integer.parseInt(InterfazProducto.txtTalle42.getText()) > 0 && InterfazProducto.txtTalle42.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle42.getText()));
            detalleP.settalle("42");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"42");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
           
            try {
            if (Integer.parseInt(InterfazProducto.txtTalle44.getText()) > 0  ) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle44.getText()));
            detalleP.settalle("44");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"44");
            ListaDetalle.add(detalleP);
            }
             } catch (NumberFormatException e) {
            }
            try {
            if (Integer.parseInt(InterfazProducto.txtTalle46.getText()) > 0 && InterfazProducto.txtTalle46.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle46.getText()));
            detalleP.settalle("46");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"46");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
            try {
            if (Integer.parseInt(InterfazProducto.txtTalle48.getText()) > 0 && InterfazProducto.txtTalle48.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalle48.getText()));
            detalleP.settalle("48");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"48");
            ListaDetalle.add(detalleP);
            }
            } catch (NumberFormatException e) {
            }
            
       }
    
      public void ControlarTalle(DetalleProducto detallePrenda){ 
          try {
            if (Integer.parseInt(InterfazProducto.txtTalleXS.getText()) > 0 && InterfazProducto.txtTalleXS.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleXS.getText()));
            detalleP.settalle("XS");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"XS");
            ListaDetalle.add(detalleP);
            }  
          } catch (NumberFormatException e) {
          }
          try {
           if (Integer.parseInt(InterfazProducto.txtTalleS.getText()) > 0 && InterfazProducto.txtTalleS.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleS.getText()));
            detalleP.settalle("S");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"S");
            ListaDetalle.add(detalleP);
            }   
          } catch (NumberFormatException e) {
          }
          try {
           if (Integer.parseInt(InterfazProducto.txtTalleM.getText()) > 0 && InterfazProducto.txtTalleM.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleM.getText()));
            detalleP.settalle("M");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"M");
            ListaDetalle.add(detalleP);
            }   
          } catch (NumberFormatException e) {
          }
         
           try {
             if (Integer.parseInt(InterfazProducto.txtTalleL.getText()) > 0 && InterfazProducto.txtTalleL.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleL.getText()));
            detalleP.settalle("L");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"L");
            ListaDetalle.add(detalleP);
            }  
          } catch (NumberFormatException e) {
          }
          try {
               if (Integer.parseInt(InterfazProducto.txtTalleXL.getText()) > 0 && InterfazProducto.txtTalleXL.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleXL.getText()));
            detalleP.settalle("XL");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"XL");
            ListaDetalle.add(detalleP);
            }  
          } catch (NumberFormatException e) {
          }
           try {
              if (Integer.parseInt(InterfazProducto.txtTalleXXL.getText()) > 0 && InterfazProducto.txtTalleXXL.getText()!= null) {
            DetalleProducto detalleP = new DetalleProducto();
            detalleP.setFKcod_producto(Integer.parseInt(InterfazProducto.txtCod_producto.getText()));
            detalleP.setCantidad(Integer.parseInt(InterfazProducto.txtTalleS.getText()));
            detalleP.settalle("XXL");
            detalleP.setCod_detalleProducto(InterfazProducto.txtCod_producto.getText()+"XXL");
            ListaDetalle.add(detalleP);
            } 
          } catch (NumberFormatException e) {
          }
            
         
    }
 
 public void InsertarStockTalleLE( ArrayList<DetalleProducto> Talles){
        for (int i = 0; i < Talles.size(); i++) {
        switch (Talles.get(i).gettalle()) {
        case "XS":
        InterfazProducto.txtTalleXS.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "S":
        InterfazProducto.txtTalleS.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "M":
        InterfazProducto.txtTalleM.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "L":
        InterfazProducto.txtTalleM.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "XL":
        InterfazProducto.txtTalleXL.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "XXL":
        InterfazProducto.txtTalleXXL.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "XXXL":
        InterfazProducto.txtTalleXXXL.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        default:
        break;
        }    
        }
        
 }    
 
 
 public void InsertarStockTalleNU( ArrayList<DetalleProducto> Talles){
        for (int i = 0; i < Talles.size(); i++) {
        switch (Talles.get(i).gettalle()) {
        case "36":
        InterfazProducto.txtTalle36.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "38":
        InterfazProducto.txtTalle38.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "40":
        InterfazProducto.txtTalle40.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "42":
        InterfazProducto.txtTalle42.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "44":
        InterfazProducto.txtTalle44.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "46":
        InterfazProducto.txtTalle46.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        case "48":
        InterfazProducto.txtTalle48.setText(String.valueOf(Talles.get(i).getCantidad()));
        break;
        default:
        break;
        }    
        }
 }    


}
