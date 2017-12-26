<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Todo lists</title>
</head>
<body>

<h3>Add new list</h3>

<form:form modelAttribute="todo_list" method="POST" action="/add-todo_list">
    <table>
        <tr>
            <td><label>Name:</label></td>
            <td><form:input type="text" path="name"/></td>
        </tr>
    </table>
    <input type="submit" value="add">
</form:form>

<span style="float:left;">

    <h3>Add new todo to list ${todo_list.getName()}</h3>

    <form:form modelAttribute="todo_list" method="POST" action="/add-todo">
        <table>
            <tr>
                <td><label>Name:</label></td>
                <td><input type="text" name="todo"/></td>
                <td><input type="submit" value="add"> </td>
            </tr>
        </table>
        <form:input type="hidden" path="name"/>
    </form:form>
</span>

<span style="float:left;">

    <h3>Select todo list</h3>

    <form:form modelAttribute="todo_list" method="GET" action="/get-todo_list">
        <table>
            <tr>
                <td>
                    <form:select path="name">
                        <form:option value="NONE">--SELECT--</form:option>
                        <form:options items="${lists_names}"></form:options>
                    </form:select>
                </td>
                <td><input type="submit" value="choose" onclick="form.action='/get-todo_list';"></td>
                <td><input type="submit" value="delete" onclick="form.action='/delete-todo_list';"></td>
            </tr>
        </table>
    </form:form>

    <form:form name="checkForm" modelAttribute="todo_list" method="POST" action="/set_done">

        <input type="checkbox" style="display:none" name="sid" value="-1" checked="checked"/>

        <table>
            <c:forEach var="todo" items="${todo_list.getTodoList()}">
                <tr>
                    <td>${todo.getDescription()}</td>
                    <td><input type="checkbox" name="sid" value="${todo.getId()}" onclick="document.checkForm.hB.click();"
                        <c:if test="${todo.getIsDone()}">checked="checked"</c:if>/></td>
                    <td> <input type="submit" value="delete"
                         onclick="form.action='/delete-todo';document.checkForm.toDelete.value=${todo.getId()};document.checkForm.toDelete.submit();"></td>
                 </tr>
            </c:forEach>

            <form:input type="hidden" path="name"/>
            <input type="hidden" name="toDelete"/>
            <input type="submit" style="display:none" name="hB" value="add"/>

        </table>
    </form:form>
</span>
</body>
</html>