/*
* Copyright (C) 2004 Kepler Project.
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

package luajava;

/**
 * This interface represents a `function' that can be called from Lua. Any
 * object that implements this interface can be passed as a function to Lua.
 * The method <code>foo</code> is called when Lua tries to execute the
 * function.
 */
public abstract class JavaFunction
{
	
	/**
	 * This is the state in which this function will exist.
	 */
	protected LuaState L;
	
	/**
	 * This method is called from Lua. Any parameters can be taken with
	 * <code>getParam</code>. A reference to the JavaFunctionWrapper itself is
	 * always the first parameter received. Values passed back as results
	 * of the function must be pushed onto the stack.
	 * @return The number of values pushed onto the stack.
	 */
	public abstract int foo();
	
	/**
	 * Constructor that receives the current LuaState.
	 * @param L
	 */
	public JavaFunction(LuaState L)
	{
		this.L = L;
	}

	/**
	 * Returns a parameter received from Lua. Parameters are numbered from 1.
	 * A reference to the JavaFunction itself is always the first parameter
	 * received (the same as <code>this</code>).
	 * @param idx Index of the parameter.
	 * @return Reference to parameter.
	 * @see LuaObject
	 */
	public LuaObject getParam(int idx)
	{
		return L.getLuaObject(idx);
	}

	/**
	 * Register a JavaFunction with a given name. This method registers in a
	 * global variable the JavaFunction specified.
	 * @param name The name of the function.
	 */
	public void register(String name) throws LuaException
	{
	  synchronized (L)
	  {
			L.pushString(name);
			L.pushJavaFunction(this);
			L.setGlobal();
	  }
	}
}

