<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/bsf.tld" prefix="bsf" %>

<html>
<body>
<bsf:scriptlet language="lua">
    for i=1,11 do
        out:println("isso é um teste de um scriptlet em lua dentro de um jsp..."..i.."<br>")
    end
</bsf:scriptlet>
<bsf:expression language="lua">
 "agora &eacute; um expression" 
</bsf:expression>
</body>
</html>