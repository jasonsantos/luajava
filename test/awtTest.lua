
frame = luajava.newInstance("java.awt.Frame", "Lua Java Console")
console = luajava.newInstance("java.awt.TextArea")
buttons_pn = luajava.newInstance("java.awt.Panel")
execute_bt = luajava.newInstance("java.awt.Button", "Execute")
clear_bt = luajava.newInstance("java.awt.Button", "Clear")
exit_bt = luajava.newInstance("java.awt.Button", "Exit")

frame:setSize(600,300)

buttons_pn:add(execute_bt)
buttons_pn:add(clear_bt)
buttons_pn:add(exit_bt)

BorderLayout = luajava.bindClass("java.awt.BorderLayout")

frame:add(BorderLayout.NORTH, console)
frame:add(BorderLayout.SOUTH, buttons_pn)
frame:pack()
frame:show()

--
-- Listeners
--

execute_cb = { 
actionPerformed = function(ev)
  print("execute")
  pcall(loadstring(console:getText()))
end
}

jproxy = luajava.createProxy("java.awt.event.ActionListener",execute_cb)

execute_bt:addActionListener(jproxy)


clear_cb = {actionPerformed= function (ev)
  print("clear");
  console:setText("");
end
 }

jproxy = luajava.createProxy("java.awt.event.ActionListener" ,clear_cb)
clear_bt:addActionListener(jproxy) 

exit_cb = { actionPerformed=function (ev)
 print("exit")
  frame:setVisible(false)
  frame:dispose()
end
}


jproxyb = luajava.createProxy("java.awt.event.ActionListener" ,exit_cb)

exit_bt:addActionListener(jproxyb)

close_cb = {  }
function close_cb.windowClosing(ev)
  print("close")
  frame:setVisible(false)
  frame:dispose()
end

function close_cb.windowActivated(ev)
  print("act")
end

jproxy = luajava.createProxy("java.awt.event.WindowListener", close_cb)
frame:addWindowListener(jproxy)
