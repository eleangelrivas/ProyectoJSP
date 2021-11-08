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

/**
 *
 * @author elenilsonangel
 */
public class Home extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8"); 
        String filtro = request.getParameter("consultar_datos");
        
        JSONArray array_tabla_usuarios = new JSONArray();
        JSONObject tabla_html = new JSONObject();
        String tabla= "";
        String tabla2= "";
         
        switch(filtro){
        case "grafico_depto" :
           
                try {
                    
                    Obtener_Datos procesar = new Obtener_Datos();
                    ResultSet resultSet = procesar.obtener_salarios("");
                    int i = 0;
                    
                    tabla="<table id='tabla_salarios_depto' class='table table-striped table-bordered'>";
                        tabla+="<thead>";
                            tabla+="<tr>";
                                 
                                tabla+="<th></th>"; 
                                tabla+="<th>Salario</th>"; 
                            tabla+="</tr>";
                        tabla+="</thead>";
                        tabla+="<tbody>";
                    while(resultSet.next()){
                       i++;
                        tabla+="<tr>";

                            tabla+="<th>"+resultSet.getString("nombre_depto")+"</th>";
                            tabla+="<td>"+resultSet.getString("salario")+"</td>"; 

                            tabla+="</td>";
                        tabla+="</tr>";
                    }
                        tabla+="</tbody>";
                    tabla+="</table>";
                    tabla_html.put("la_tabla_html", tabla);
                    tabla_html.put("id_tabla1", "tabla_salarios_depto");
                    
                    
                    ResultSet resultSet2 = procesar.obtener_salarios("salario_promedio");
                    tabla2="<table id='tabla_salario_promedio' class='table table-striped table-bordered'>";
                        tabla2+="<thead>";
                            tabla2+="<tr>";
                                 
                                tabla2+="<th></th>"; 
                                tabla2+="<th>Salario</th>"; 
                            tabla2+="</tr>";
                        tabla2+="</thead>";
                        tabla2+="<tbody>";
                    while(resultSet2.next()){
                       i++;
                        tabla2+="<tr>";

                            tabla2+="<th>"+resultSet2.getString("nombre")+"</th>";
                            tabla2+="<td>"+resultSet2.getString("salary")+"</td>"; 

                            tabla2+="</td>";
                        tabla2+="</tr>";
                    }
                        tabla2+="</tbody>";
                    tabla2+="</table>";
                    
                    tabla_html.put("tabla_promedio", tabla2);
                    tabla_html.put("id_tabla2", "tabla_salario_promedio");
                    
                    tabla_html.put("tabla_empleados", "vacio");
                    tabla_html.put("proceso", "Exito");
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
        HttpSession sesion = req.getSession();
        if (sesion.getAttribute("usuario")==null) {
               System.out.println("Se vino el if");
               resp.sendRedirect("Login");
        }else{
            System.out.println("Se vino el else");
            req.getRequestDispatcher("/modulos/home/index.jsp").forward(req, resp);
        }
    }
    
    
    
}
