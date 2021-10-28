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
                    ResultSet resultSet = procesar.obtener_empleados();
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
       }
        
        
        
        
        
        
        
        
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        HttpSession sesion = req.getSession();
        sesion.setAttribute("usuario", "Ele Angel");
        try {
            Obtener_Datos datos = new Obtener_Datos();
            ResultSet resultSet = datos.obtener_empleados();
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
