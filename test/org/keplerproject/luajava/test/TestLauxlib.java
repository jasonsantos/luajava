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

import java.io.FileInputStream;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import junit.framework.TestCase;

/**
 * Tests the use of lauxlib functions.
 * Requires junit.
 * 
 * @author thiago
 */
public class TestLauxlib extends TestCase
{
  /**
   * Tests the load functions
   */
  public void testLoadRun() throws Exception
  {
    // test loadfile
    LuaState L = LuaStateFactory.newLuaState();
    L.openLibs();
    
    int loadRes = L.LloadFile("wrongLuaFile.lua");
    assertEquals(loadRes, LuaState.LUA_ERRSYNTAX.intValue());
    System.out.println(L.toString(-1));
    L.pop(1);
    
    loadRes = L.LloadFile("simpleLuaFile.lua");
    assertEquals(loadRes, 0);
    L.pcall(0, 0, 0);

    // test loadbuffer
    FileInputStream input = new FileInputStream("wrongLuaFile.lua");
    byte[] bytes = new byte[input.available()];
    input.read(bytes);
    input.close();
    
    loadRes = L.LloadBuffer(bytes, "test");
    assertEquals(LuaState.LUA_ERRSYNTAX.intValue(), loadRes);
    System.out.println(L.toString(-1));
    
    input = new FileInputStream("simpleLuaFile.lua");
    bytes = new byte[input.available()];
    input.read(bytes);
    input.close();
    
    loadRes = L.LloadBuffer(bytes, "test2");
    assertEquals(0, loadRes);
    L.pcall(0, 0, 0);
    
    L.close();
  }
  
  /**
   * Test the check functions.
   */
  public void testChecks()
  {
    LuaState L = LuaStateFactory.newLuaState();
    L.openLibs();
    
    String testCheckStr = "testCheck";
    
    L.pushString(testCheckStr);
    assertEquals(testCheckStr, L.LcheckString(-1));
    assertEquals(testCheckStr, L.LoptString(-1, "test"));
    
    L.pushNumber(1.0);
    L.LcheckNumber(-1);
    
    assertTrue(L.LoptNumber(2, 2.0) == 1.0);
    assertTrue(L.LcheckNumber(2) == 1.0);
    
    L.LcheckAny(2);
    
    L.LcheckType(1, LuaState.LUA_TSTRING.intValue());
    
    L.close();
  }
  
  /**
   * Checks the metamethods and metatable functions.
   */
  public void testMeta() throws LuaException
  {
    LuaState L = LuaStateFactory.newLuaState();
    L.openLibs();
    
    L.newTable();
    L.newTable();
    L.pushString("__index");
    L.LdoString("return function()" +
    		"io.write( 'metatest\\n') io.stdout:flush() " +
    		"return 'foo' " +
    		"end");
    L.setTable(-3);
    L.setMetaTable(-2);
    
    L.LcallMeta(-1, "__index");
    System.out.println(L.toString(-1));
    L.pop(1);
    L.LgetMetaField(-1, "__index");
    L.call(0, 1);
    System.out.println(L.toString(-1));
    L.pop(1);
    L.pushString("testTable");
    L.pushValue(-2);
    L.setTable(LuaState.LUA_GLOBALSINDEX.intValue());
    L.pop(1);
    L.LdoString("str = testTable.ff; print(str..'fromLua');" +
    		" io.stdout:flush()");
    
    L.close();
  }
}
