
function getRun(i, param)
local i = i
local param = param
return function ()
--print("test");
--[[if i == 1 then
i = 0
print "aqui"
print(io.read())
end]]
--luajava.bindClass("java.lang.String")
print("teste2 "..i);
--luajava.bindClass("java.lang.Thread"):sleep(100)
luajava.newInstance("java.lang.String", "lalala");
--print("fim thread");
print(gcinfo());
print(param:toString());
end
end


for i=0,100 do
f = luajava.createProxy("java.lang.Runnable",{run=getRun(i, luajava.newInstance("java.lang.String", "str param"))});
--print(f:toString())

t = luajava.newInstance("java.lang.Thread", f);
print(t:toString())

t:start()
--t:run()
--t:join()
end

for i=0,5000 do
--luajava.bindClass("java.lang.String")
end
print("fim main");
--luajava.bindClass("java.lang.Thread"):sleep(100)
