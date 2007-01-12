/*
* Copyright (C) 2003-2007 Kepler Project.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
* IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
* CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
* TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
* SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package org.keplerproject.luajava.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;


/**
 * Uses JDBC statement to execute queries inside Lua.
 * Uses hsqldb.
 * @author thiago
 */
public class TestJDBC
{
  
  public static void main(String[] args) throws Exception
  {
    // gets a java.sql.Connection and creates a Statement
    Class.forName("org.hsqldb.jdbcDriver");
    Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9002","sa","");
    Statement st = con.createStatement();
    
    try
    {
      st.execute("DROP TABLE luatest");
    }
    catch (Exception ignore) {}
    
    st.execute("CREATE TABLE luatest (id int not null primary key, str varchar, number double)");
    
    for(int i = 0 ; i < 10 ; i++)
    {
      st.executeUpdate("INSERT INTO luatest (id, str, number) values(" + i + ", '" + 2*i + "', " + System.currentTimeMillis() + ")");
    }
    
    LuaState L = LuaStateFactory.newLuaState();
    L.openLibs();
    
    //L.pushString("st");
    L.pushObjectValue(st);
    //L.setTable(LuaState.LUA_GLOBALSINDEX.intValue());
    L.setGlobal("st");
    
    int err = L.LdoFile("testJDBC.lua");
    if (err != 0)
    {
      switch (err)
      {
        case 1 :
          System.out.println("Runtime error. " + L.toString(-1));
          break;

        case 2 :
          System.out.println("File not found. " + L.toString(-1));
          break;

        case 3 :
          System.out.println("Syntax error. " + L.toString(-1));
          break;
        
        case 4 :
          System.out.println("Memory error. " + L.toString(-1));
          break;
        
        default :
          System.out.println("Error. " + L.toString(-1));
          break;
      }
    }
    
    L.close();
    st.close();
    con.close();
  }
}