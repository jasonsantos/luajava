/*
* Copyright (C) 2005 Kepler Project.
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

package org.keplerproject.luajava.luabsf;

import java.util.Vector;

import org.apache.bsf.BSFDeclaredBean;
import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.bsf.util.BSFEngineImpl;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

/**
 * This class implements a Engine for bsf to run Lua
 * 
 * @author Thiago Ponte
 */
public class LuaEngine extends BSFEngineImpl
{
  /** state used to execute Lua */
  private LuaState L;
  
	/**
	 * This method is used to initialize the engine right after construction.
	 */
  public void initialize(BSFManager mgr, String lang, Vector declaredBeans)
  	throws BSFException
  {
    super.initialize(mgr, lang, declaredBeans);
    
    L = LuaStateFactory.newLuaState();
    
  	for (int i = 0; i < declaredBeans.size(); i++)
  	{
  	  declareBean ((BSFDeclaredBean) declaredBeans.elementAt(i));
  	}
  }
  
  /**
   * Terminates the engine.
   */
  public void terminate()
  {
    super.terminate();
    L.close();
  }

  /**
   * This is used by an application to execute some script.
   * 
	 * @param source   (context info) the source of this expression
	 *                 (e.g., filename)
	 * @param lineNo   (context info) the line number in source for expr
	 * @param columnNo (context info) the column number in source for expr
	 * @param script   the script to execute
   */
  public void exec(String source, int lineNo, int columnNo, Object script)
  	throws BSFException
  {
    if (script instanceof String)
      L.doString((String) script);
    
    else if (script instanceof LuaObject)
    {
      LuaObject obj = (LuaObject) script;
      try
      {
	      if(obj.isFunction() || obj.isUserdata() || obj.isTable())
	        obj.call(new Object[] {});
      }
      catch(LuaException e)
      {
        throw new BSFException(e.getMessage());
      }
    }
  }
  
  /**
   * This is used by an application to evaluate an expression.
   * 
   * @param source   (context info) the source of this expression
	 *                 (e.g., filename)
	 * @param lineNo   (context info) the line number in source for expr
	 * @param columnNo (context info) the column number in source for expr
	 * @param expr     the expression to evaluate

   */
  public Object eval(String source, int lineNo, int columnNo, Object expr)
  	throws BSFException
  {
    if (expr instanceof String)
    {
      L.doString("return (" + (String) expr + ")");
      try
      {
        Object obj = L.toJavaObject(-1);
        L.pop(1);
        return obj;
      }
      catch (LuaException e)
      {
        throw new BSFException("Couldn't eval source code at line " + lineNo + " and column " + columnNo); 
      }
    }
    
    else if (expr instanceof LuaObject)
    {
      LuaObject obj = (LuaObject) expr;
      try
      {
	      if(obj.isFunction() || obj.isUserdata() || obj.isTable())
	        return obj.call(new Object[] {});
      }
      catch(LuaException e)
      {
        throw new BSFException("Couldn't eval source code at line " + lineNo + " and column " + columnNo);
      }
    }
    
    return null;
  }

  /**
   * Only used on OO languages.
   */
  public Object call (Object object, String method, Object[] args) throws BSFException
  {
    return null;
  }

  /**
   * Declare a bean after the engine has been started. Declared beans
	 * are beans that are named and which the engine must make available
	 * to the scripts it runs in the most first class way possible.
	 * 
	 * @param bean bean to declare
   */
  public void declareBean (BSFDeclaredBean bean) throws BSFException
  {
    try
    {
      L.pushObjectValue(bean.bean);
    }
    catch (LuaException e)
    {
      throw new BSFException(e.getMessage());
    }
    L.setGlobal(bean.name);
  }
  
	/**
	 * Undeclare a previously declared bean.
	 *
	 * @param bean the bean to undeclare
	 */
  public void undeclareBean (BSFDeclaredBean bean) throws BSFException
  {
    L.pushNil();
    L.setGlobal(bean.name);
  }
}
