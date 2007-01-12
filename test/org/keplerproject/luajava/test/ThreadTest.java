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

import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

/**
 * Tests LuaJava running as a multithreaded application.<br>
 * The objective of the test is to see that LuaJava behaves
 * properly when being executed from diferent threads.
 * 
 * @author thiago
 */
public class ThreadTest
{
	private static String lua = "function run() io.write('test\\n');" +
			"io.stdout:flush();" +
			"luajava.bindClass('java.lang.Thread'):sleep(100);" +
			" end;tb={run=run}";
	
	public static void main(String[] args) throws Exception
	{
		LuaState L = LuaStateFactory.newLuaState();
		L.openBase();
		L.openIo();
		//L.openLibs();
		
		L.LdoString(lua);
		
		for(int i = 0 ;i < 100; i++)
		{
			LuaObject obj = L.getLuaObject("tb");
			Object runnable = obj.createProxy("java.lang.Runnable");
		  	Thread thread = new Thread((Runnable) runnable);
			thread.start();
		}
		System.out.println("end main");
	}
}
