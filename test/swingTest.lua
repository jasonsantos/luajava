
frame = luajava.newInstance("javax.swing.JFrame", "Lua Java Console")
console = luajava.newInstance("javax.swing.JTextArea")
buttons_pn = luajava.newInstance("javax.swing.JPanel")
execute_bt = luajava.newInstance("javax.swing.JButton", "Execute")
clear_bt = luajava.newInstance("javax.swing.JButton", "Clear")
exit_bt = luajava.newInstance("javax.swing.JButton", "Exit")

console:setSize(600,300)

buttons_pn:add(execute_bt)
buttons_pn:add(clear_bt)
buttons_pn:add(exit_bt)

BorderLayout = luajava.bindClass("java.awt.BorderLayout")

frame:setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

frame:getContentPane():add(console, BorderLayout.NORTH);
frame:getContentPane():add(buttons_pn, BorderLayout.SOUTH);
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

