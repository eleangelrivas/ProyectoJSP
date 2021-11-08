/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Obtener_Datos;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author elenilsonangel
 */
public class Login extends HttpServlet {

   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        JSONArray array_login = new JSONArray();
        JSONObject objeto_login = new JSONObject();
         
           
        response.setContentType("application/json;charset=utf-8"); 
        //response.setContentType( "text/html; charset=iso-8859-1" );
	PrintWriter out = response.getWriter();
        String filtro = request.getParameter("filtro_accion");   
        switch(filtro){
           case "si_loguear":
               
             
            try {
                Obtener_Datos procesar = new Obtener_Datos();
                String resultado = "";
                String contrabd="";
                String nombre="";
                String usuario="";
                ResultSet resultSet = procesar.obtener_empleados(request.getParameter("login-email"),"porusuario");
                
                int contador=0;
                if(resultSet==null){
                    objeto_login.put("resultado","Errodatosnoexisten");
                }else{
                     
                     while(resultSet.next()){
                        objeto_login.put("employee_id",resultSet.getString("employee_id"));
                        objeto_login.put("first_name",resultSet.getString("first_name"));
                        objeto_login.put("last_name",resultSet.getString("last_name"));
                        objeto_login.put("email",resultSet.getString("email"));
                        objeto_login.put("phone_number",resultSet.getString("phone_number"));
                        objeto_login.put("hire_date",resultSet.getString("hire_date"));
                        objeto_login.put("salary",resultSet.getString("salary"));
                        contrabd = resultSet.getString("contrasenia");
                        nombre = resultSet.getString("first_name");
                        usuario = resultSet.getString("usuario");

                    }

                    if(BCrypt.checkpw(request.getParameter("login-password"),contrabd)){
                        sesion.setAttribute("usuario", usuario);
                        sesion.setAttribute("nombre", nombre);
                        objeto_login.put("resultado","Exito");

                    }else{
                        objeto_login.put("resultado","ErrorContrasenia");
                    }
                }
                 
                array_login.put(objeto_login);
            } catch (Exception ex) {
                 
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
                
                
           break;
        }
        response.getWriter().write(array_login.toString());
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();
        sesion.invalidate();
        req.getRequestDispatcher("modulos/login/index.jsp").forward(req, resp);
    }
    
    
    
}
