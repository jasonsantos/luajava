package luajava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Console LuaJava
 */
public class Console
{

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
						throw new LuaException("Error on file " + args[i]);
				}

				return;
			}

			System.out.println("API Lua Java - console mode.");

			BufferedReader inp =
				new BufferedReader(new InputStreamReader(System.in));

			String line;

			while ((line = inp.readLine()) != null && !line.equals("exit"))
			{
				int ret = L.doString(line);
				if (ret != 0)
					System.out.println("Invalid Input : " + line);
				//throw new LuaException("Invalid Input .");
			}
			
			L.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
