map     = {}
local t = {}

function map.size()

    local i = 0
    for k,v in pairs(t) do
        i = i + 1
    end

    return i
end

function map.clear()

    t = {}
end

function map.isEmpty()

    local i = 0
    for k,v in pairs(t) do
        return false
    end
    return true
end

function map.containsKey(key)

    return t[key] ~= nil
end

function map.containsValue(value)

    for k,v in pairs(t) do
        if v == value then
            return true
        end
    end
    return false
end

function map.putAll(outMap)

    local i = outMap:keySet():iterator()

    while i:hasNext() do
        local key = i:next()
        map.put(key, outMap:get(key))
    end
end

function map.get(key)

    return t[key]
end

function map.remove(key)

    local obj = t[key]
    t[key] = nil
    
    return obj
end

function map.put(key, value)

    local obj = t[key]
    
    t[key] = value
    
    return obj
end

local startTime = os.clock()
for i=1,10000 do
    map.put(i, tostring(i))
end
for i=1,10000 do
    map.put(i, tostring(i+1))
end
for i=1,10000 do
    map.put(tostring(i), i)
end
for i=1,10000 do
    map.put(tostring(i), i+1)
end
local endTime = os.clock()

map.clear()

io.write("execution time in lua "..(endTime-startTime).."\n")io.flush()