this is a fork from jasonsantos/luajava by duzc2.
changes:
* integration with luajit 2.0.3
* wildfly module impl
* better work within Linux(old luajava can't load .so module in Linux like System,because of the difference of JDK impl)

luajava
=======

LuaJava is a scripting tool for Java. The goal of this tool is to allow scripts written in Lua to manipulate components developed in Java. 

It allows Java components to be accessed from Lua using the same syntax that is used for accessing Lua`s native objects, without any need 
for declarations or any kind of preprocessing.  LuaJava also allows Java to implement an interface using Lua. This way any interface can be
implemented in Lua and passed as parameter to any method, and when called, the equivalent function will be called in Lua, and it's result 
passed back to Java.
