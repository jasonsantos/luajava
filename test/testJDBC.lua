
do
-- tests if object Statement is nil
if st == nil then
    print("Error. st object is nil")
    return
end

local st = st
_G.st = nil

function createSQLProxy(t)

    local tableName = t
    
    local function i (t,k)
    
        local id = tonumber(k)
        if not id then
            return nil
        end
        
        local function mi (t,k)
        
            local sql = "select "..k.." from "..tableName.." where id="..id
            local rs = st:executeQuery(sql)
            if not rs:next() then
                rs:close()
                return nil
            end
            
            local res = rs:getString(1)
            rs:close()
            
            return res
        end
        
        local res = {}
        setmetatable(res, {__index = mi})
        return res
    end
    
    local res = {}
    setmetatable(res, {__index = i })
    return res
end

end


proxy = createSQLProxy("luatest")

print(proxy[1].str)
print(proxy[1].number)

