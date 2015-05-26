package org.keplerproject.luajava.test;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class ExceptionTest {
	public static void main(String[] args) throws LuaException {
		System.out.println("start " + System.getProperty("java.library.path"));
		LuaState L = LuaStateFactory.newLuaState();
		L.openLibs();
		L.LdoString("os.exit = function(...) print('EXIT',...) end");
		JF func = new JF(L);
		JPanic panic = new JPanic(L);
		L.pushJavaFunction(func);
		// L.atpanic(panic);
		int pcall = L.pcall(0, 0, 0);
		System.out.println("end " + pcall);
	}
}

class JF extends JavaFunction {

	public JF(LuaState L) {
		super(L);
	}

	@Override
	public int execute() throws LuaException {
		throw new NullPointerException("test exception");
		// return 0;
	}

}

class JPanic extends JavaFunction {

	public JPanic(LuaState L) {
		super(L);
	}

	@Override
	public int execute() throws LuaException {
		// throw new NullPointerException("test exception");
		return 0;
	}

}