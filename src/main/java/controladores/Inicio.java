/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Conexion;
import modelos.Obtener_Datos;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author rivas
 */
public class Inicio extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        
        response.setContentType("application/json;charset=utf-8"); 
        //response.setContentType( "text/html; charset=iso-8859-1" );
	PrintWriter out = response.getWriter();
        String filtro = request.getParameter("consultar_datos");
        
         
        switch(filtro){
        case "si_consultalos" :
            JSONArray array_tabla_usuarios = new JSONArray();
            JSONObject tabla_html = new JSONObject();
                try {
                    
                    Obtener_Datos procesar = new Obtener_Datos();
                    ResultSet resultSet = procesar.obtener_empleados("");
                    int i = 0;
                    String tabla= "";
                    tabla="<table id='tabla_empleados' class='table table-striped table-bordered'>";
                        tabla+="<thead>";
                            tabla+="<tr>";
                                 
                                tabla+="<th>ID</th>"; 
                                tabla+="<th>Nombre</th>";
                                tabla+="<th>Apellido</th>";
                                tabla+="<th>email</th>";
                                tabla+="<th>Teléfono</th>";
                                tabla+="<th>Fecha</th>";
                                tabla+="<th>Salario</th>";
                                tabla+="<th>Acciones</th>";
                            tabla+="</tr>";
                        tabla+="</thead>";
                        tabla+="<tbody>";
                    while(resultSet.next()){
                       i++;
                        tabla+="<tr>";

                            tabla+="<td>"+resultSet.getString("employee_id")+"</td>";
                            tabla+="<td>"+resultSet.getString("first_name")+"</td>";
                            tabla+="<td>"+resultSet.getString("last_name")+"</td>";

                            tabla+="<td>"+resultSet.getString("email")+"</td>";
                            tabla+="<td>"+resultSet.getString("phone_number")+"</td>";

                            tabla+="<td>"+resultSet.getString("hire_date")+"</td>";
                            tabla+="<td>"+resultSet.getString("salary")+"</td>";
                            tabla+="<td>";
                                tabla+="<button type='button' class='btn btn-success' id='boton_editar' data-id='"+resultSet.getString("employee_id")+"'>Editar</button>"; 
                                tabla+="<button type='button' class='btn btn-warning' id='boton_eliminar' data-id='"+resultSet.getString("employee_id")+"'>Eliminar</button>";


                            tabla+="</td>";
                        tabla+="</tr>";
                    }
                        tabla+="</tbody>";
                    tabla+="</table>";
                    tabla_html.put("la_tabla_html", tabla);
                    tabla_html.put("EJEMPLO", "PROGRAMANDO");
                    array_tabla_usuarios.put(tabla_html); 
            } catch (Exception ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                response.getWriter().write(array_tabla_usuarios.toString());//reotorna datos
            break;
            
            
        case "si_eliminalo":
            JSONArray array_eliminado = new JSONArray();
            JSONObject objeto_retornos = new JSONObject();
                try {
                    
                    Obtener_Datos procesar = new Obtener_Datos();
                    String resultado= procesar.eliminar_datos(request.getParameter("id"));
                    objeto_retornos.put("resultado",resultado);
                    objeto_retornos.put("proceso","actualizar");
                    array_eliminado.put(objeto_retornos);
                    
                }catch(Exception e){
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
                }
                response.getWriter().write(array_eliminado.toString());//reotorna datos
            break;
            
            
            
            
         case "si_actualizalo":
            JSONArray array_actualizado = new JSONArray();
            JSONObject objeto_actualizado = new JSONObject();
                try {
                    
                    Obtener_Datos procesar = new Obtener_Datos();
                    String resultado= procesar.update_datos(request.getParameter("llave_persona"), request.getParameter("nombre"), 
                        request.getParameter("apellido"), request.getParameter("email"), 
                        request.getParameter("telefono"), request.getParameter("fecha1"), 
                        request.getParameter("salario"));
                    objeto_actualizado.put("resultado",resultado);
                    objeto_actualizado.put("proceso","actualizar");
                    array_actualizado.put(objeto_actualizado);
                    
                }catch(Exception e){
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
                }
                response.getWriter().write(array_actualizado.toString());//reotorna datos
            break;
            
            
            
            
        case "si_registro":
            JSONArray array_insertar = new JSONArray();
            JSONObject objeto_insertar = new JSONObject();
                try {
                    
                    Obtener_Datos procesar = new Obtener_Datos();
                    String resultado= procesar.insetar_datos(request.getParameter("nombre"), 
                        request.getParameter("apellido"), request.getParameter("email"), 
                        request.getParameter("telefono"), request.getParameter("fecha1"), 
                        request.getParameter("salario"));
                    
                    objeto_insertar.put("resultado",resultado);
                    objeto_insertar.put("proceso","insertar");
                    objeto_insertar.put("nombre","Elenilson");
                    array_insertar.put(objeto_insertar);
                    
                }catch(Exception e){
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
                }
                response.getWriter().write(array_insertar.toString());//reotorna datos
            break;
            
        case "traer_especifico":
            JSONArray array_traer = new JSONArray();
            JSONObject objeto_traer = new JSONObject();
            try{
                Obtener_Datos procesar = new Obtener_Datos();
                ResultSet resultSet = procesar.obtener_empleados(request.getParameter("employee_id"));
                
                int contador=0;
                while(resultSet.next()){
                    objeto_traer.put("employee_id",resultSet.getString("employee_id"));
                    objeto_traer.put("first_name",resultSet.getString("first_name"));
                    objeto_traer.put("last_name",resultSet.getString("last_name"));
                    objeto_traer.put("email",resultSet.getString("email"));
                    objeto_traer.put("phone_number",resultSet.getString("phone_number"));
                    objeto_traer.put("hire_date",resultSet.getString("hire_date"));
                    objeto_traer.put("salary",resultSet.getString("salary"));
                    
                }
                objeto_traer.put("proceso","consultar_especifico"); 
                array_traer.put(objeto_traer);
            }catch(Exception e){
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, e);
            }
            
            response.getWriter().write(array_traer.toString());//reotorna datos
            
            
            break;
       }
        
        
        
        
        
        
        
        
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        HttpSession sesion = req.getSession();
        sesion.setAttribute("usuario", "Ele Angel");
        try {
            Obtener_Datos datos = new Obtener_Datos();
            ResultSet resultSet = datos.obtener_empleados("");
            while(resultSet.next()){
                System.out.println("Nombre: "+resultSet.getString("first_name")+resultSet.getString("last_name"));
            }
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //resp.sendRedirect("index.jsp");
        
    }
    
    
    
}
