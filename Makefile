#
# Makefile for LuaJava Linux Distribution
#

#
# Change this to reflect your setting.
#
#JDK     = C:\j2sdk1.4.1_01
JDK	= $(JAVA_HOME)

LUA5=/home/t/tecgraf/lib/lua5
LUA5INC=$(LUA5)/include
LUA5LIB=$(LUA5)/lib/Linux24

CC=/usr/bin/gcc

#
# Other variables.
#
VERSION	= 1.0b3
LUAJAVA_JAR	= luajava-$(VERSION).jar
LUAJAVA_SO	= libluajava-$(VERSION).so

CLASSES     = \
	src/java/luajava/CPtr.class \
	src/java/luajava/JavaFunction.class \
	src/java/luajava/LuaException.class \
	src/java/luajava/LuaInvocationHandler.class \
	src/java/luajava/LuaJavaAPI.class \
	src/java/luajava/LuaObject.class \
	src/java/luajava/LuaState.class \
	src/java/luajava/LuaStateFactory.class \
	src/java/luajava/Console.class

DOC_CLASSES	= \
	src/java/luajava/JavaFunction.java \
	src/java/luajava/LuaException.java \
	src/java/luajava/LuaInvocationHandler.java \
	src/java/luajava/LuaObject.java \
	src/java/luajava/LuaState.java \
	src/java/luajava/LuaStateFactory.java \
	src/java/luajava/Console.java

OBJS        = src/c/luajava.o
CFLAGS      = -I$(JDK)/include -I$(JDK)/include/linux -I$(LUA5INC)
.SUFFIXES: .java .class

#
# Targets
#
run: build
	@echo ------------------
	@echo Build Complete
	@echo ------------------

build: checkjdk $(LUAJAVA_JAR) apidoc $(LUAJAVA_SO)

#
# Build .class files.
#
.java.class:
	$(JDK)/bin/javac src/java/luajava/*.java
	
#
# Create the JAR
#
$(LUAJAVA_JAR): $(CLASSES)
	cd src/java; \
	$(JDK)/bin/jar cvf ../../$(LUAJAVA_JAR) luajava/*.class; \
	cd ../..;
  
#
# Create the API Documentation
#
apidoc:
	$(JDK)/bin/javadoc -public -classpath src/java/ -quiet -d "doc/API" $(DOC_CLASSES)

#
# Build .c files.
#
$(LUAJAVA_SO): $(OBJS)
	$(CC) -shared -o$@ $? $(LUA5LIB)/liblua5.a $(LUA5LIB)/liblualib5.a

src/c/luajava.c: src/c/luajava.h

src/c/luajava.h:
	$(JDK)/bin/javah -o src/c/luajava.h -classpath "$(LUAJAVA_JAR)" luajava.LuaState
  

## regras implicitas para compilacao

$(OBJDIR)/%.o:  %.c
	$(CC) -c $(CFLAGS) -o $@ $<

#
# Check that the user has a valid JDK install.  This will cause a
# premature death if JDK is not defined.
#
checkjdk: $(JDK)/bin/java

#
# Cleanliness.
#
clean:
	rm -f $(LUAJAVA_JAR)
	rm -f $(LUAJAVA_SO)
	rm -rf doc/API
	rm -f src/java/luajava/*.class src/c/*.o src/c/luajava.h

