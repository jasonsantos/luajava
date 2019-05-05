/*
 * $Id: LuaException.java,v 1.7 2007-04-17 23:47:50 thiago Exp $
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

package org.keplerproject.luajava;

/**
 * LuaJava exception
 *  
 * @author Thiago Ponte
 *
 */
public class LuaException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LuaException(String str)
	{
		super(str);
	}
	
	/**
	 * Will work only on Java 1.4 or later.
	 * To work with Java 1.3, comment the first line and uncomment the second one.
	 */
	public LuaException(Exception e)
	{
	   super(e);
		//super(e.getMessage());
	}
	
	public LuaException(String str, Exception e)
	{
		super(str, e);
	}
} 