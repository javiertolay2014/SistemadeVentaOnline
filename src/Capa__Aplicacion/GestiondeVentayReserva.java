/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa__Aplicacion;


import static CapaInterfaz.InterfazPrincipal.deskPricipal;
import CapaInterfaz.InterfazVenta_Reserva;
import CapaInterfaz.InterfazBuscarProducto;
import CapaInterfaz.InterfazLiquidacion;
import CapaInterfaz.PaneldePrenda;
import CapadeGestionDeBD.DetalleVenta_Reserva;
import CapadeGestionDeBD.Producto;
import CapadeGestionDeBD.Venta;
import CapadeGestionDeBD.Persona;
import CapadeGestionDeBD.conexion;
import ds.desktop.notify.DesktopNotify;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author javier
 */
public class GestiondeVentayReserva {
       
        private final conexion mysql = new conexion();
        private final Connection cn = mysql.conectar();
        ArrayList<DetalleVenta_Reserva> carrito = new ArrayList();
        private double total=0;
        int codigoventa;

    public GestiondeVentayReserva() {

    }
      

 public void EventobotonNuevo() {                                          
       // InterfazVenta_Reserva.txtDescuento.setText("");
        InterfazVenta_Reserva.dcFecha_venta.setDate(null);
       // InterfazVenta_Reserva.txtDescuento.setEditable(true);
       // InterfazVenta_Reserva.txtTotal_venta.setText("");
        InterfazVenta_Reserva.Cod_producto.setText("");
        InterfazVenta_Reserva.txtNombre_cliente.setText("");
        InterfazVenta_Reserva.txtDNIcliente.setText("");
        InterfazVenta_Reserva.txtSubtotal.setText("");
       //InterfazVenta_Reserva.txtSubtotal.setEnabled(false);
       InterfazVenta_Reserva.txtSubtotal.setEditable(false);
        InterfazVenta_Reserva.jlabelcarrito.setText("0 PRODUTO/S EN EL CARRITO");
        InterfazVenta_Reserva.txtTelefono.setText("");
        InterfazVenta_Reserva.btnbuscarProducto.setEnabled(true);
        InterfazVenta_Reserva.btnAgregarProducto.setEnabled(true);
//        InterfazVenta_Reserva.cboTipodeRegistro.setEnabled(true);
        InterfazVenta_Reserva.btnPrint.setEnabled(true);
        InterfazVenta_Reserva.talle.setEnabled(true);
        InterfazVenta_Reserva.cantidad.setEnabled(true);
        InterfazVenta_Reserva.buscarcodigo.setEnabled(true);
        InterfazVenta_Reserva.btnNuevo1.setEnabled(false); 
        InterfazVenta_Reserva.listmodel.clear();
        InterfazVenta_Reserva.dcFecha_venta.setEnabled(true);
    }  
        
 
 
 
  public void EventobotonCancelar() {                                          
//        InterfazVenta_Reserva.txtDescuento.setText("");
        InterfazVenta_Reserva.dcFecha_venta.setDate(null);
        //InterfazVenta_Reserva.txtDescuento.setEditable(false);
        InterfazVenta_Reserva.btnBuscarCliente.setEnabled(false);
       // InterfazVenta_Reserva.txtTotal_venta.setText("");
        InterfazVenta_Reserva.Cod_producto.setText("");
        InterfazVenta_Reserva.txtNombre_cliente.setText("");
        InterfazVenta_Reserva.txtDNIcliente.setText("");
        InterfazVenta_Reserva.txtSubtotal.setText("");
        InterfazVenta_Reserva.jlabelcarrito.setText("0 PRODUCTO/S EN EL CARRITO");
        InterfazVenta_Reserva.txtTelefono.setText("");
        InterfazVenta_Reserva.btnbuscarProducto.setEnabled(false);
        InterfazVenta_Reserva.btnAgregarProducto.setEnabled(false);
//        InterfazVenta_Reserva.cboTipodeRegistro.setEnabled(true);
        InterfazVenta_Reserva.btnPrint.setEnabled(false);
        
        InterfazVenta_Reserva.talle.setEnabled(false);
        InterfazVenta_Reserva.btnGuardarPedido.setEnabled(false);
        InterfazVenta_Reserva.cantidad.setEnabled(false);
        InterfazVenta_Reserva.buscarcodigo.setEnabled(false);
        InterfazVenta_Reserva.btnNuevo1.setEnabled(true); 
        InterfazVenta_Reserva.listmodel.clear();
        total =0;
        carrito.clear();
        InterfazVenta_Reserva.dcFecha_venta.setEnabled(false);
    }
 
 
 
 
 
 
 public void EventoBuscarProducto() {                                                  
        InterfazBuscarProducto form = new InterfazBuscarProducto();
        deskPricipal.add(form);
        form.toFront();
        form.setVisible(true);
    } 
  public void EventoIngresarcodigo() {                                                  
      Producto prenda = new Producto();
      
      if ( InterfazVenta_Reserva.Cod_producto.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Este campo debe llevar un valor");
            InterfazVenta_Reserva.Cod_producto.requestFocus();
            return;
        }
 prenda  = prenda.BuscarPorCodigo(InterfazVenta_Reserva.Cod_producto.getText());
                if ( prenda.getCod_producto()== 0) {
                JOptionPane.showMessageDialog(null, "NO EXISTE PRODUCTO");
                InterfazVenta_Reserva.Cod_producto.setText("");
              
      } else {
                
                InterfazVenta_Reserva.txtprenda.setText("Producto : "+prenda.getDescripcion_producto());
                InterfazVenta_Reserva.txttipo.setText("Tipo de Prenda : "+prenda.getTipodeprenda());
                InterfazVenta_Reserva.txtsexo.setText("Sexo : "+ prenda.getSexo());
                InterfazVenta_Reserva.Cod_producto.setText(String.valueOf(prenda.getCod_producto()));
                if (!"Pantalon".equals(prenda.getTipodeprenda())) {
                    InterfazVenta_Reserva.talle.addItem("XS");
                    InterfazVenta_Reserva.talle.addItem("S");
                    InterfazVenta_Reserva.talle.addItem("M");
                    InterfazVenta_Reserva.talle.addItem("L");
                    InterfazVenta_Reserva.talle.addItem("XL");
                    InterfazVenta_Reserva.talle.addItem("2XL");
                    InterfazVenta_Reserva.talle.addItem("3XL");
                } else {
                    InterfazVenta_Reserva.talle.addItem("36");
                    InterfazVenta_Reserva.talle.addItem("38");
                    InterfazVenta_Reserva.talle.addItem("40");
                    InterfazVenta_Reserva.talle.addItem("42");
                    InterfazVenta_Reserva.talle.addItem("44");
                    InterfazVenta_Reserva.talle.addItem("46");
                    InterfazVenta_Reserva.talle.addItem("48");
                }
                
                
                
                
                }
                
                
                  
                
                
    } 
 
  
  
  
  
  
  
  /* public void EventoTipodeRegistro() {                                                  
        if (InterfazVenta_Reserva.cboTipodeRegistro.getSelectedItem() == "RESERVA") {
       habilitar();
       InterfazVenta_Reserva.dcFecha_venta.setEnabled(true);
       InterfazVenta_Reserva. btnBuscarCliente.setEnabled(true);
       } else {
            InterfazVenta_Reserva.dcFecha_venta.setEnabled(false);
       InterfazVenta_Reserva. btnBuscarCliente.setEnabled(false);
       inhabilitar();
       }
    } */
   
   
   
   
   
 
    public void EventoTalle() {                                      
        InterfazVenta_Reserva.cantidad.removeAllItems();
        
        if (!"".equals(InterfazVenta_Reserva.Cod_producto.getText())) {
        Producto prend = new Producto();
        int stock=prend.BuscarStock(InterfazVenta_Reserva.Cod_producto.getText(), InterfazVenta_Reserva.talle.getSelectedItem().toString());
        if (stock!=0) {
        for (int i = 1; i <=stock ; i++) {
        InterfazVenta_Reserva.cantidad.addItem(String.valueOf(i));
                }
        } else {
        InterfazVenta_Reserva.cantidad.addItem(String.valueOf(0));
        } 
               
        }
        
            
        
        
    }

       public void AgregarProducto() {               
        if ( InterfazVenta_Reserva.Cod_producto.getText().length() == 0|| Integer.parseInt(InterfazVenta_Reserva.Cod_producto.getText())<0 ) {
        JOptionPane.showMessageDialog(null, "Ingrese un codigo valido");
        InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
        return;
        }
        if ( InterfazVenta_Reserva.cantidad.getSelectedItem().toString().length() == 0|| Integer.parseInt(InterfazVenta_Reserva.cantidad.getSelectedItem().toString())<=0 ) {
        JOptionPane.showMessageDialog(null, "Ingrese una cantidad valida");
        InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
        return;
        }
        DetalleVenta_Reserva detalle= new  DetalleVenta_Reserva();
        Producto prenda = new Producto();
        prenda = prenda.BuscarPorCodigo(InterfazVenta_Reserva.Cod_producto.getText());
        int stock=prenda.BuscarStock(InterfazVenta_Reserva.Cod_producto.getText(), InterfazVenta_Reserva.talle.getSelectedItem().toString());
        total=total+prenda.getPrecio_venta()*Integer.valueOf(InterfazVenta_Reserva.cantidad.getSelectedItem().toString());
        detalle.setCod_productoFK(prenda.getCod_producto());
        detalle.setNombre(prenda.getDescripcion_producto());
        detalle.setSexo(prenda.getSexo());
        detalle.setImagen(prenda.getFoto());
        detalle.setCantidad(Integer.parseInt(InterfazVenta_Reserva.cantidad.getSelectedItem().toString()));
        detalle.setCod_ventaFK(12);
        detalle.setNuevoStock(stock-Integer.parseInt(InterfazVenta_Reserva.cantidad.getSelectedItem().toString()));
        detalle.setSubtotal(prenda.getPrecio_venta()*Integer.valueOf(InterfazVenta_Reserva.cantidad.getSelectedItem().toString()));
        detalle.setTalle(InterfazVenta_Reserva.talle.getSelectedItem().toString());
        InterfazVenta_Reserva.modelo.addElement(detalle);
        
        InterfazVenta_Reserva.ListadePrendas.setCellRenderer(new PaneldePrenda());
        carrito.add(detalle);
        InterfazVenta_Reserva.jlabelcarrito.setText(carrito.size()+" PRODUCTO/S EN EL CARRITO");
     //   InterfazVenta_Reserva.txtTotal_venta.setText("   "+String.valueOf(total));
        InterfazVenta_Reserva.txtSubtotal.setText("   "+String.valueOf(total));
        InterfazVenta_Reserva.Cod_producto.setText("");
        InterfazVenta_Reserva.cantidad.removeAllItems();
        InterfazVenta_Reserva.talle.removeAllItems();
        InterfazVenta_Reserva.btnGuardarPedido.setEnabled(true);
        habilitar();
        InterfazVenta_Reserva.btnBuscarCliente.setEnabled(true);
        InterfazVenta_Reserva.txtprenda.setText("Producto : ");
        InterfazVenta_Reserva.txttipo.setText("Tipo de Prenda : ");
        InterfazVenta_Reserva.txtsexo.setText("Sexo : ");
        
    }  
   public void EventoGuardar() { 
         if ( InterfazVenta_Reserva.txtNombre_cliente.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "INGRESE UN NOMBRE VALIDO");
            InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
            return;
        }
       
  if (InterfazVenta_Reserva.txtDNIcliente.getText().length() == 0 ||InterfazVenta_Reserva.txtDNIcliente.getText().length()>8) {
            JOptionPane.showMessageDialog(null, "INGRESE UN DNI VALIDO");
            InterfazVenta_Reserva.txtDNIcliente.requestFocus();
            return;} 
     
        if ( carrito.isEmpty()) {
            JOptionPane.showMessageDialog(null, "CARRITO VACIO ..INGRESE PRODUCTOS!!!!!!");
            InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
            return;
        }
     
       
        if (InterfazVenta_Reserva.txtTelefono.getText().length() == 0 || InterfazVenta_Reserva.txtTelefono.getText().length()>19) {
            JOptionPane.showMessageDialog(null, "INGRESE UN TELEFONO VALIDO");
            InterfazVenta_Reserva.txtTelefono.requestFocus();
            return;
        }
/*            switch (InterfazVenta_Reserva.cboTipodeRegistro.getSelectedItem().toString()) 
            {
            case "VENTA":
              Venta venta = new Venta();
              
              int d, m, a;
              Calendar cal = new GregorianCalendar();
              d = cal.get(Calendar.DAY_OF_MONTH);
              m = cal.get(Calendar.MONTH);
              a = cal.get(Calendar.YEAR) - 1900;
              venta.setFecha_venta(new Date(a, m, d));
              venta.setTotal_venta(Double.valueOf(InterfazVenta_Reserva.txtTotal_venta.getText()));
              venta.setCod_clienteFK(InterfazVenta_Reserva.txtDNIcliente.getText());
              codigoventa = venta.insertar();
            if (codigoventa!=0) {
           
            for (int i = 0; i < carrito.size(); i++) {
            carrito.get(i).setCod_ventaFK(codigoventa);
            carrito.get(i).insertar();
            
            carrito.get(i).ModificarStockProductos();
            }
            JOptionPane.showMessageDialog(null, "Se registro venta");
               //************************* MENSAJE PARA LIQUIDAR *************************
            int liquidar=0;
             for (int i = 0; i < carrito.size(); i++)
            {
                if (carrito.get(i).getNuevoStock()==1) {
                    
                    if (carrito.get(i).ContarModeloP()==1 ){
                     Producto prenda =new Producto();
                     prenda = prenda.BuscarPorCodigo(String.valueOf(carrito.get(i).getCod_productoFK()));
                     prenda.setEstado("oferta");
                        System.out.println("estado"+prenda.getEstado());
                     prenda.editar();
                     liquidar++;
                     
                     
                    }
                
                
                
                }
            }
             
            if (liquidar!=0) {
            this.Notificacion();
                        
                    }
            
           Limpiarventa();
            
            } else 
            {
            JOptionPane.showMessageDialog(null, "No se Ingreso venta");
            
            }
            break;*/
            
              
              Venta pedido = new Venta();
              int di, me, an,dia,mes,año;
               Calendar c = new GregorianCalendar();
             
              di = c.get(Calendar.DAY_OF_MONTH);
              me = c.get(Calendar.MONTH);
              an = c.get(Calendar.YEAR) - 1900;
             
              if (InterfazVenta_Reserva.dcFecha_venta.getDate() == null) {
              JOptionPane.showMessageDialog(null, ".........INGRESE UNA FECHA VALIDA !!.......");
              InterfazVenta_Reserva.dcFecha_venta.requestFocus();
             return;} 
 
              
              Calendar  ca = InterfazVenta_Reserva.dcFecha_venta.getCalendar();
              dia = ca.get(Calendar.DAY_OF_MONTH);
              mes = ca.get(Calendar.MONTH);
              año = ca.get(Calendar.YEAR) - 1900;
              pedido.setFecha_venta(new Date(an, me, di));
              
              
              if (an==año&&me>mes) {
              JOptionPane.showMessageDialog(null, ".........INGRESE UNA FECHA VALIDA !!.......");
              return; 
              }
              if (an==año&& me==mes&&di>dia) {
              JOptionPane.showMessageDialog(null, ".........INGRESE UNA FECHA VALIDA !!.......");
              return; 
              }
               if (an==año&& me==mes&&di==dia) {
              JOptionPane.showMessageDialog(null, ".........INGRESE UNA FECHA VALIDA !!.......");
              return; 
              }
              if (an>año) {
              JOptionPane.showMessageDialog(null, ".........INGRESE UNA FECHA VALIDA !!.......");
              return; 
              }
            pedido.setFecha_vencimientopedido(new Date(año, mes, dia));   
            pedido.setTotal_venta(Double.valueOf(InterfazVenta_Reserva.txtSubtotal.getText()));
            
            pedido.setCod_clienteFK(InterfazVenta_Reserva.txtDNIcliente.getText());
            int codpedido = pedido.insertarPedido();    
            Persona cliente = new Persona();
            if (!cliente.Buscar(InterfazVenta_Reserva.txtDNIcliente.getText())) {
            cliente.setCod_persona(InterfazVenta_Reserva.txtDNIcliente.getText());
            cliente.setNombre_persona(InterfazVenta_Reserva.txtNombre_cliente.getText());
            cliente.setTelefono(InterfazVenta_Reserva.txtTelefono.getText());
            cliente.setTipo("cliente");
            cliente.insertar();
                
                
                }
            
            
            
            
            if (codpedido!=0) {

            for (int i = 0; i < carrito.size(); i++)
            {
            carrito.get(i).setCod_ventaFK(codpedido);
            carrito.get(i).insertarPedido();
            carrito.get(i).ModificarStockProductos();
            }
            
            //************************* MENSAJE PARA LIQUIDAR *************************
            int liquidar=0;
             for (int i = 0; i < carrito.size(); i++)
            {
                if (carrito.get(i).getNuevoStock()==1) {
                    
                    if (carrito.get(i).ContarModeloP()==1 ){
                     Producto prenda =new Producto();
                     prenda = prenda.BuscarPorCodigo(String.valueOf(carrito.get(i).getCod_productoFK()));
                     prenda.setEstado("oferta");
                     prenda.editar();
                     liquidar++;
                     
                     
                    }
                
                
                
                }
            }
             
            if (liquidar!=0) {
//            this.Notificacion();
                        
                    }
            //***********************************************************************
 
            JOptionPane.showMessageDialog(null, "Se registro pedido");
            InterfazVenta_Reserva.listmodel.clear();
            carrito.clear();
            total=0;
            EventobotonNuevo();
            inhabilitar();
            inhabilitarRegistro();
            } else 
            {
            JOptionPane.showMessageDialog(null, "No se Ingreso pedido");
            }
          
            
            InterfazVenta_Reserva.Cod_producto.setEditable(true);
            InterfazVenta_Reserva.Cod_producto.requestFocus();
            InterfazVenta_Reserva.btnNuevo1.setEnabled(true);
    }            
    
 public  void habilitar() {
       InterfazVenta_Reserva.txtDNIcliente.setEditable(true);
       InterfazVenta_Reserva.txtNombre_cliente.setEditable(true);
       InterfazVenta_Reserva.txtTelefono.setEditable(true);
 }
  public  void ControlarDatosPedido() {
         if (InterfazVenta_Reserva.txtDNIcliente.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "INGRESE UN DNI VALIDO");
            InterfazVenta_Reserva.txtDNIcliente.requestFocus();
            return;} 
       if ( InterfazVenta_Reserva.txtNombre_cliente.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "INGRESE UN DOCUMENTO VALIDO!!!!!!!!");
            InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
            return;
        }
        if ( carrito.isEmpty()) {
            JOptionPane.showMessageDialog(null, "CARRITO VACIO ..INGRESE PRODUCTOS!!!!!!");
            InterfazVenta_Reserva.txtNombre_cliente.requestFocus();
            return;
        }
     
       
        if (InterfazVenta_Reserva.txtTelefono.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "INGRESE UN TELEFONO VALIDO");
            InterfazVenta_Reserva.txtTelefono.requestFocus();
            return;
        }
       
 
  }
 
    public void inhabilitarRegistro() {                                          
    //    InterfazVenta_Reserva.txtDescuento.setText("");
     //   InterfazVenta_Reserva.txtDescuento.setEditable(false);
     //   InterfazVenta_Reserva.txtTotal_venta.setText("");
        InterfazVenta_Reserva.Cod_producto.setText("");
        InterfazVenta_Reserva.txtNombre_cliente.setText("");
        InterfazVenta_Reserva. btnbuscarProducto.setEnabled(false);
        InterfazVenta_Reserva.btnAgregarProducto.setEnabled(false);
  //      InterfazVenta_Reserva.cboTipodeRegistro.setEnabled(false);
        InterfazVenta_Reserva.btnGuardarPedido.setEnabled(false);
        InterfazVenta_Reserva.btnPrint.setEnabled(false);
        InterfazVenta_Reserva.btnBuscarCliente.setEnabled(false);
        InterfazVenta_Reserva.talle.setEnabled(false);
        InterfazVenta_Reserva.cantidad.setEnabled(false);
        InterfazVenta_Reserva.dcFecha_venta.setEnabled(false);
        InterfazVenta_Reserva.buscarcodigo.setEnabled(false);
        InterfazVenta_Reserva.btnNuevo1.setEnabled(true);    
    }
    
    
     public void Limpiarventa() {                                          
        InterfazVenta_Reserva. btnbuscarProducto.setEnabled(false);
        InterfazVenta_Reserva.btnAgregarProducto.setEnabled(false);
//        InterfazVenta_Reserva.cboTipodeRegistro.setEnabled(false);
        InterfazVenta_Reserva.btnGuardarPedido.setEnabled(false);
        InterfazVenta_Reserva.btnPrint.setEnabled(true);
        InterfazVenta_Reserva.btnBuscarCliente.setEnabled(false);
        InterfazVenta_Reserva.talle.setEnabled(false);
        InterfazVenta_Reserva.cantidad.setEnabled(false);
        InterfazVenta_Reserva.dcFecha_venta.setEnabled(false);
        InterfazVenta_Reserva.buscarcodigo.setEnabled(false);
        InterfazVenta_Reserva.btnNuevo1.setEnabled(false);    
    }  
    
    
    
public  void inhabilitar() {
       InterfazVenta_Reserva.txtDNIcliente.setEditable(false);
       InterfazVenta_Reserva.txtNombre_cliente.setEditable(false);
       InterfazVenta_Reserva.txtTelefono.setEditable(false);
 }
public void Imprimir() throws JRException{ 
 InterfazVenta_Reserva.listmodel.clear();
 carrito.clear();
 total=0;
           EventobotonNuevo();
            inhabilitar();
            inhabilitarRegistro(); 
          Map parametro = new HashMap();
          parametro.put("venta",codigoventa);
          JasperReport j = (JasperReport) JRLoader.loadObject("C:\\Users\\javier\\Documents\\NetBeansProjects\\Sistema_Venta_de_Ropa\\src\\Imagenes\\menu\\comprobante.jasper");
          JasperPrint jp = JasperFillManager.fillReport(j, parametro,cn);
          JasperViewer jv = new JasperViewer(jp, false);
          jv.show();
           
}
/*
public  void Notificacion() {
         DesktopNotify.showDesktopMessage("AVISO DE LIQUIDACION", 
             ""
                     + "Se detecto nuevos productos para liquidar.."
                     + ""
                     + " haga clik para ver  ", 
             DesktopNotify.INFORMATION,20000L, new ActionListener(){@Override public void actionPerformed(ActionEvent evt){
            //Podemos utilizar un formulario Frame o cualquier otro
          InterfazLiquidacion form = new InterfazLiquidacion();
          deskPricipal.add(form);
          form.toFront();
          form.setVisible(true);
            
            //Tambien podemos mostrar mensajes simples, sin iconos
       
            
            //Tambien puede mostrar un Mensaje de Dialogo
          
        }});
 }
*/
}
