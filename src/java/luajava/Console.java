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

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * LuaJava console
 * @author Thiago Ponte
 */
public class Console
{

  /**
   * Creates a console for user interaction
   * @param args - names of the lua files to be executed
   */
	public static void main(String[] args)
	{
		try
		{
			LuaState L = LuaStateFactory.newLuaState();
			L.openBasicLibraries();
			L.openDebug();
			L.openLoadLib();

			if (args.length > 0)
			{
				for (int i = 0; i < args.length; i++)
				{
					int res = L.doFile(args[i]);
					if (res != 0)
					{
					  String str;
			      if (res == LuaState.LUA_ERRRUN.intValue())
			      {
			        str = "Runtime error. ";
			      }
			      else if (res == LuaState.LUA_ERRMEM.intValue())
			      {
			        str = "Memory allocation error. ";
			      }
			      else if (res == LuaState.LUA_ERRERR.intValue())
			      {
			        str = "Error while running the error handler function. ";
			      }
			      else
			      {
			        str = "Lua Error code " + res + ". ";
			      }

					  throw new LuaException(str + "Error on file " + args[i]);
					}
				}

				return;
			}

			System.out.println("API Lua Java - console mode.");

			BufferedReader inp =
				new BufferedReader(new InputStreamReader(System.in));

			String line;

		  System.out.print("> ");
			while ((line = inp.readLine()) != null && !line.equals("exit"))
			{
				int ret = L.doString(line);
				if (ret != 0)
				{
				  String str;
		      if (ret == LuaState.LUA_ERRRUN.intValue())
		      {
		        str = "Runtime error. ";
		      }
		      else if (ret == LuaState.LUA_ERRMEM.intValue())
		      {
		        str = "Memory allocation error. ";
		      }
		      else if (ret == LuaState.LUA_ERRERR.intValue())
		      {
		        str = "Error while running the error handler function. ";
		      }
		      else
		      {
		        str = "Lua Error code " + ret + ". ";
		      }
					System.out.println(str + line);
				}
			  System.out.print("> ");
			}
			
			L.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
