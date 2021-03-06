/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rivas
 */
public class Obtener_Datos {
    
    private final Connection con;
     
    public Obtener_Datos() throws SQLException, Exception {
        Conexion cone = new Conexion();
        this.con = cone.AbrirConexion();
    }
    
    
    public ResultSet obtener_salarios(String tipo){
     Connection con = null; 
        ResultSet resultSet = null;
        String sql = "";
        if(tipo=="salario_promedio"){
            sql = "SELECT \n" +
                    "concat(first_name,' ',last_name) as nombre,\n" +
                    "salary\n" +
                    "from employees\n" +
                    "WHERE salary >=(SELECT avg(salary) from employees)";
        }else{
            sql = "SELECT\n" +
                        "	sum(salary) as salario,\n" +
                        "	CASE\n" +
                        "		WHEN e.department_id is null then 'SIN DEPARTAMENTO'\n" +
                        "		ELSE \n" +
                        "		(select department_name FROM departments WHERE department_id = e.department_id)\n" +
                        "	END AS nombre_depto\n" +
                        "FROM\n" +
                        "	employees as e\n" +
                        "GROUP BY department_id";
        }
        try{ 
            Conexion cone = new Conexion();
            con = cone.AbrirConexion();  
            System.out.println("el sql: "+sql);
            PreparedStatement ps = con.prepareStatement(sql); 
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return resultSet;
    
    
    
    }
    public ResultSet obtener_empleados(String id, String tipo){
        String where="";
        if(tipo=="porid"){
            where = (id=="") ? "":"WHERE employee_id="+id;
        }else{
            where = "WHERE (usuario ='"+id+"' OR email = '"+id+"')";
        }
        
        Connection con = null; 
        ResultSet resultSet = null;
        try{ 
            Conexion cone = new Conexion();
            con = cone.AbrirConexion(); 
            String sql = "SELECT * FROM employees "+where;
            System.out.println("el sql: "+sql);
            PreparedStatement ps = con.prepareStatement(sql); 
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return resultSet;
    }
    
    
    public String eliminar_datos(String id){
        String resultado;
        try{ 
            String sql = "DELETE FROM employees WHERE employee_id="+id;
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            resultado = "exito";
        } catch (SQLException e) {
             resultado = "error";
             System.out.println("El error es: "+e);
        }
        
        return resultado;
    }
    
    
    public String update_datos(String id, String nombre, String apellido, String email, 
            String telefono, String fecha, String Salario){
        String resultado;
        
        try{
            String sql = "UPDATE employees SET first_name=?,last_name=?,email=?,"
                    + "phone_number=?,hire_date=?,salary=? "
                    + "WHERE employee_id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,String.valueOf(nombre));
            st.setString(2,String.valueOf(apellido));
            st.setString(3,String.valueOf(email));
            st.setString(4,String.valueOf(telefono));
            st.setString(5,String.valueOf(fecha));
            st.setFloat(6,Float.parseFloat(Salario));
            st.setInt(7,Integer.parseInt(id));
            st.executeUpdate();
            st.close();
            resultado = "actualizado";
               
        } catch (SQLException e) {
            resultado = "error";
            System.out.println("Error al eliminar: "+e);
            e.printStackTrace();
        }  
 
        return resultado;
    }
    
    
    public String insetar_datos(String nombre, String apellido, String email, 
            String telefono, String fecha, String Salario,String usuario, String contra){
        String resultado;
        
        try{
            String sql = "INSERT INTO employees(first_name,last_name,email,phone_number,hire_date,salary,usuario,contrasenia)values(?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,String.valueOf(nombre));
            st.setString(2,String.valueOf(apellido));
            st.setString(3,String.valueOf(email));
            st.setString(4,String.valueOf(telefono));
            st.setString(5,String.valueOf(fecha));
            st.setFloat(6,Float.parseFloat(Salario)); 
            st.setString(7,String.valueOf(usuario));
            st.setString(8,String.valueOf(contra));
            st.executeUpdate();
            st.close();
            resultado = "insertado";
               
        } catch (SQLException e) {
            resultado = "error";
            System.out.println("Error al guardar: "+e);
            e.printStackTrace();
        }  
 
        return resultado;
    }
    
    
}
