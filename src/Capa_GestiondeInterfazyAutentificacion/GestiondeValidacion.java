/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capa_GestiondeInterfazyAutentificacion;
import CapaInterfaz.InterfazRegistrarAdmin;
import CapaInterfaz.InterfazPrincipal;
import CapaInterfaz.InterfazValidacion;
import CapaInterfaz.InterfazReserva;
import CapadeGestionDeBD.Persona;
import java.awt.HeadlessException;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;

/**
 *
 * @author javier
 */
public class GestiondeValidacion {
  
  static InterfazValidacion Validacion ; 
  static Persona usuario = new Persona();
  static InterfazRegistrarAdmin registrar;
  
  
    public void EventoValidarse() {                                            

       if (InterfazValidacion.txtLogin.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un UserName");
           InterfazValidacion.txtLogin.requestFocus();
           return;
       }

       if (InterfazValidacion.txtpassword.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una contraseña");
            InterfazValidacion.txtpassword.requestFocus();
            return;
        }

        try {
           
                
           
                if (usuario.Validacion(InterfazValidacion.txtLogin.getText(),InterfazValidacion.txtpassword.getText())) 
                   {
                Validacion.dispose();
                InterfazPrincipal ventanaprincipal = new InterfazPrincipal();
                ventanaprincipal.toFront();
                ventanaprincipal.setVisible(true);
                InterfazPrincipal.MenuPersonal.setEnabled(true);
                InterfazPrincipal.MenuProductos.setEnabled(true);
                InterfazPrincipal.MenuInformes.setEnabled(true);
           //     InterfazPrincipal.JmiHerramientas.setEnabled(true);
                InterfazPrincipal.JmIUsuarios.setEnabled(true);
                InterfazPrincipal.JmIClientes.setEnabled(true);
                InterfazReserva ventanaventa = new InterfazReserva();
                InterfazPrincipal.deskPricipal.add(ventanaventa); 
                    ventanaventa.toFront();
                    ventanaventa.setClosable(true);
                    ventanaventa.setIconifiable(true);
                    ventanaventa.setVisible(true);
                    ventanaventa.setMaximum(true);
                } else 
                {
                 JOptionPane.showMessageDialog(null," DATOS INCORRECTOS ");     
                }

            
            } catch (HeadlessException | PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, "ERROR2222222222222222 " + e);
            }
              
 
    } 
    
     public void EventoRegistrarAdmin() {                                           

        if (InterfazRegistrarAdmin.txtNombre_persona.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Nombre");
            InterfazRegistrarAdmin.txtNombre_persona.requestFocus();
            return;
        }

        if (InterfazRegistrarAdmin.txtDNI.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Direccion");
            InterfazRegistrarAdmin.txtDNI.requestFocus();
            return;
        }

      
        
        if (InterfazRegistrarAdmin.txtEmail.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un Email.");
            InterfazRegistrarAdmin.txtEmail.requestFocus();
            return;
        }
        
        if (InterfazRegistrarAdmin.txtLogin.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar Login");
            InterfazRegistrarAdmin.txtLogin.requestFocus();
            return;
        }

        if (InterfazRegistrarAdmin.txtPassword.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Debes ingresar una Contraseña");
            InterfazRegistrarAdmin.txtPassword.requestFocus();
            return;
        }

        Persona datos = new Persona();

        datos.setNombre_persona(InterfazRegistrarAdmin.txtNombre_persona.getText());
        datos.setCod_persona(InterfazRegistrarAdmin.txtDNI.getText());
        datos.setTelefono(InterfazRegistrarAdmin.txtTelefono.getText());
        datos.setUsuario(InterfazRegistrarAdmin.txtLogin.getText());
        datos.setPass(InterfazRegistrarAdmin.txtPassword.getText());
        datos.setTipo("Administrador");
        if (datos.CrearAdmistrador()) {
        Validacion = new InterfazValidacion();
        Validacion.toFront();
        Validacion.setVisible(true);
        
        
        } else {
        JOptionPane.showMessageDialog(null, "NO se ingresador Datos");
        }
    } 
    
     public void inhabilitar() {

      
        InterfazRegistrarAdmin.txtNombre_persona.setEnabled(false);
        InterfazRegistrarAdmin. txtDNI.setEnabled(false);
        InterfazRegistrarAdmin. txtTelefono.setEnabled(false);
        InterfazRegistrarAdmin.txtEmail.setEnabled(false);
        InterfazRegistrarAdmin.txtLogin.setEnabled(false);
        InterfazRegistrarAdmin.txtPassword.setEnabled(false);
        InterfazRegistrarAdmin.btnGuardar.setEnabled(false);
        InterfazRegistrarAdmin.btnNuevo.setEnabled(true);
        
        InterfazRegistrarAdmin.txtNombre_persona.setText("");
        InterfazRegistrarAdmin.txtDNI.setText("");
        InterfazRegistrarAdmin.txtTelefono.setText("");
        InterfazRegistrarAdmin.txtEmail.setText("");
        InterfazRegistrarAdmin.txtLogin.setText("");
        InterfazRegistrarAdmin.txtPassword.setText("");
        

    }

    public void habilitar() {

        InterfazRegistrarAdmin.txtNombre_persona.setEnabled(true);
        InterfazRegistrarAdmin.txtDNI.setEnabled(true);
        InterfazRegistrarAdmin.txtTelefono.setEnabled(true);
        InterfazRegistrarAdmin.txtEmail.setEnabled(true);
        InterfazRegistrarAdmin.txtLogin.setEnabled(true);
        InterfazRegistrarAdmin.txtPassword.setEnabled(true);
        InterfazRegistrarAdmin.btnGuardar.setEnabled(true);
        InterfazRegistrarAdmin.btnNuevo.setEnabled(false);
      
        InterfazRegistrarAdmin.txtNombre_persona.setText("");
        InterfazRegistrarAdmin.txtDNI.setText("");
        InterfazRegistrarAdmin.txtTelefono.setText("");
        InterfazRegistrarAdmin.txtEmail.setText("");
        InterfazRegistrarAdmin.txtLogin.setText("");
        InterfazRegistrarAdmin.txtPassword.setText("");
      

    }   
    
    public static void main(String args[]) {
        if (usuario.ContarUsuarios()==0) {
         registrar = new InterfazRegistrarAdmin();           
          registrar.toFront();
          registrar.setVisible(true);    
        } else {
                Validacion = new InterfazValidacion();
                Validacion.toFront();
                Validacion.setVisible(true);
        }
          
        
    }
}
