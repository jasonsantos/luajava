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

package org.keplerproject.luajava.test.node;

import java.util.ArrayList;
import java.util.List;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;


/**
 * Example that ilustrate the usage of Lua as a configuration file.
 * 
 * @author thiago
 */
public class LuaNode
{
  private static LuaState L;
  private static IConfig configLib;
  private LuaObject configFile;
  
  private static final String configFileName = "testConfig.lua";
  
  /**
   * Parses the Lua file to be used.
   * 
   * @param fileName The Lua file
   */
  public static LuaNode proccessFile(String fileName) throws LuaException
  {
    try
    {
      return new LuaNode(fileName);
    }
    catch (ClassNotFoundException e)
    {
      throw new LuaException(e);
    }
  }
  
  protected LuaNode(String fileName) throws ClassNotFoundException, LuaException
  {
    if (L == null)
    {
      L = LuaStateFactory.newLuaState();
      L.openLibs();
      int err = L.LdoFile(configFileName);
      if (err != 0)
      {
        switch (err)
        {
          case 1 :
            throw new LuaException("Runtime error. " + L.toString(-1));
          case 2 :
            throw new LuaException("File not found. " + L.toString(-1));
          case 3 :
            throw new LuaException("Syntax error. " + L.toString(-1));
          case 4 :
            throw new LuaException("Memory error. " + L.toString(-1));
          default :
            throw new LuaException("Error. " + L.toString(-1));
        }
      }
      configLib = (IConfig) L.getLuaObject("configLib").createProxy("test.node.IConfig");
    }
    
    configFile = configLib.processConfigFile(fileName);
  }
  
  protected LuaNode(LuaObject obj)
  {
    configFile = obj;
  }
  
  /**
   * Name of the node represented by <code>this</code>.
   */
  public String getName()
  {
    return configLib.getName(configFile);
  }

  /**
   * Returns a LuaNode representing the child of the current node
   * with the name <code>childName</code>
   */
  public LuaNode getChild(String childName)
  {
    return new LuaNode(configLib.getChild(configFile, childName));
  }
  
  /**
   * Returns a list with all the node children of the current node.
   */
  public List getChildren()
  {
    LuaObject obj = configLib.getChildren(configFile);
    List list = new ArrayList();

    int i = 1;
    obj.push();
    L.pushNumber(i);
    L.getTable(-2);
    while(!L.isNil(-1))
    {
      if(L.isTable(-1))
      {
        list.add(new LuaNode(L.getLuaObject(-1)));
      }
      L.pop(1);
      
      i++;
      L.pushNumber(i);
      L.getTable(-2);
    }
    
    L.pop(1);
   
    return list;
  }
  
  /**
   * Returns a list with all the children of the current node with the name 
   * <code>childName</code>.
   */
  public List getChildren(String childName)
  {
    LuaObject obj = configLib.getChildren(configFile, childName);
    List list = new ArrayList();

    int i = 1;
    obj.push();
    L.pushNumber(i);
    L.getTable(-2);
    while(!L.isNil(-1))
    {
      if(L.isTable(-1))
      {
        list.add(new LuaNode(L.getLuaObject(-1)));
      }
      L.pop(1);
      
      i++;
      L.pushNumber(i);
      L.getTable(-2);
    }
    
    L.pop(1);
   
    return list;
  }
  
  /**
   * Returns the attribute with name <code>attributeName</code>.
   */
  public String getAttribute(String attributeName)
  {
    return configLib.getAttribute(configFile, attributeName);
  }
  
  /**
   * Returns the value of the current node.
   */
  public String getValue()
  {
    return configLib.getValue(configFile);
  }
}