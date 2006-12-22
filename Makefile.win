#
# NMake makefile for the LuaJava Windows Distribution
#

include config.win


#
# Other variables.
#
CLASSES     = \
	src/java/org/keplerproject/luajava/CPtr.class \
	src/java/org/keplerproject/luajava/JavaFunction.class \
	src/java/org/keplerproject/luajava/LuaException.class \
	src/java/org/keplerproject/luajava/LuaInvocationHandler.class \
	src/java/org/keplerproject/luajava/LuaJavaAPI.class \
	src/java/org/keplerproject/luajava/LuaObject.class \
	src/java/org/keplerproject/luajava/LuaState.class \
	src/java/org/keplerproject/luajava/LuaStateFactory.class \
	src/java/org/keplerproject/luajava/Console.class
	
DOC_CLASSES = \
	src/java/org/keplerproject/luajava/JavaFunction.java \
	src/java/org/keplerproject/luajava/LuaException.java \
	src/java/org/keplerproject/luajava/LuaInvocationHandler.java \
	src/java/org/keplerproject/luajava/LuaObject.java \
	src/java/org/keplerproject/luajava/LuaState.java \
	src/java/org/keplerproject/luajava/LuaStateFactory.java \
	src/java/org/keplerproject/luajava/Console.java
	
OBJS        = src\c\luajava.obj
.SUFFIXES: .java .class

#
# Targets
#
run: build FORCE
	@echo ------------------
	@echo Build Complete
	@echo ------------------

#build: checkjdk $(CLASSES) $(JAR_FILE) apidoc $(SO_FILE) clean FORCE
build: checkjdk $(CLASSES) $(JAR_FILE) apidoc $(SO_FILE) clean FORCE

#
# Build .class files.
#
.java.class:
	"$(JDK)\bin\javac" -sourcepath ./src/java $*.java

#
# Create the JAR
#
$(JAR_FILE):
	cd src/java
	"$(JDK)\bin\jar" cvf ../../$(JAR_FILE) org/keplerproject/luajava/*.class
	cd ..
	cd ..
  
#
# Create the API Documentation
#
apidoc:
	"$(JDK)\bin\javadoc" -classpath "src/java/" -public -quiet -d "doc/us/API" $(DOC_CLASSES)

#
# Build .c files.
#
$(SO_FILE): $(OBJS)
	link  /dll /nodefaultlib:"msvcrt.lib" -out:$@ $? $(LUA_LIBDIR)\$(LIB_LUA)
#	link  /dll -out:$@ $? $(LUA_LIBDIR)\$(LIB_LUA)

src/c/luajava.c: src/c/luajava.h

src/c/luajava.h:
	"$(JDK)\bin\javah" -o src/c/luajava.h -classpath "$(JAR_FILE)" org.keplerproject.luajava.LuaState
  

#
# Check that the user has a valid JDK install.  This will cause a
# premature death if JDK is not defined.
#
checkjdk: "$(JDK)\bin\java.exe"

#
# Help deal with phony targets.
#
FORCE: ;


install: $(SO_FILE)
	IF NOT EXIST $(LUA_LIBDIR) mkdir $(LUA_LIBDIR)
	copy $(SO_FILE) $(LUA_LIBDIR)

#
# Cleanliness.
#
clean:
	-del src\java\org\keplerproject\luajava\*.class src\c\*.obj *.pdb *.exp *.lib *.exp *.ilk

# $Id: Makefile.win,v 1.1 2006-12-22 14:06:40 thiago Exp $
