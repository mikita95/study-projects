package dao;

import model.Todo;
import model.TodoList;

import java.util.List;

/**
 * Created by nikita on 09.11.16.
 */
public interface TodoListsDao {
    int addTodoList(TodoList todoList);
    int addTodoToList(int id, Todo todo);

    List<TodoList> getTodoLists();

    int setIsDone(int todoId, boolean isDone);

    int deleteTodoList(TodoList todoList);
    int deleteTodo(int todoId);


}
