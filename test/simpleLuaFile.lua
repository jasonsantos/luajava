-- this file is a very simple lua file

function test()
    if true then
        io.write("test\n")
    end
end


for i=1,10 do
    io.write("test"..i.."\n")
end

io.stdout:flush()
