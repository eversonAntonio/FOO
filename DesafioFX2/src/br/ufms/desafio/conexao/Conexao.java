/*
 * Copyright (C) 2016 Cliente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufms.desafio.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Cliente
 */
public class Conexao {
    
    protected static Statement stmt = null;
    protected static Connection c = null;
    protected static final String drive = "org.postgresql.Driver";
    protected static final String url = "jdbc:postgresql://localhost:5432/plataformaeducacional";
    protected static final String usuario = "postgres";
    protected static final String senha = "123";
    
    public static Connection conectar(){
        try {
            Class.forName(drive);
            c = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException | ClassNotFoundException ex){
            System.out.println(ex);
        }
        return c;
    }
    
    public static ResultSet consultaBanco(String sql){
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
}
