------------------------------------------------------
--    Creates a lot of objects to check if the
--    used memory keeps stable
------------------------------------------------------


local runtime = luajava.bindClass("java.lang.Runtime"):getRuntime()

function doGc(event, lineNumber)
    if event == "count" then
        collectgarbage()
        print(gcinfo())
        runtime:gc()
        print(tostring(runtime:totalMemory()/1024).."   "..tostring(runtime:freeMemory()/1024).."     "..tostring(runtime:maxMemory()/1024))
    end
end

debug.sethook(doGc, "l", 20000)

print ("-----------------------------")
print("testing newInstance")
for i=1,20000 do
    luajava.newInstance("java.lang.Object")
    local s = luajava.newInstance("java.awt.Frame", "test"..i)
    s:toString()
end

doGc()

print ("-----------------------------")
print("testing bindClass")
for i=1,40000 do
    luajava.bindClass("java.awt.BorderLayout")
end

doGc()

debug.sethook(doGc, "l", 10)

print ("-----------------------------")
print("testing createProxy")
local t = {run = function() print("run") end }
for i=1,40000 do
    luajava.createProxy("java.lang.Runnable", t)
end


doGc()