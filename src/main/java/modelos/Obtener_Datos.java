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
    
    public ResultSet obtener_empleados(){
        Connection con = null; 
        ResultSet resultSet = null;
        try{ 
            Conexion cone = new Conexion();
            con = cone.AbrirConexion(); 
            String sql = "SELECT * FROM employees";
            PreparedStatement ps = con.prepareStatement(sql); 
            resultSet = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return resultSet;
    }
    
}
