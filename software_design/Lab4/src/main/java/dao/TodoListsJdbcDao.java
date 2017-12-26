package dao;

import model.Todo;
import model.TodoList;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by nikita on 09.11.16.
 */
public class TodoListsJdbcDao extends JdbcDaoSupport implements TodoListsDao {

    public TodoListsJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    public int addTodoList(TodoList todoList) {
        String sql = "INSERT INTO TodoLists (name) VALUES (?)";
        return getJdbcTemplate().update(sql, todoList.getName());
    }

    public int addTodoToList(int id, Todo todo) {
        String sql = "INSERT INTO Todos (list_id, description, is_done) VALUES (?, ?, ?)";
        return getJdbcTemplate().update(sql, id, todo.getDescription(), todo.getIsDone() ? 1 : 0);
    }

    public List<TodoList> getTodoLists() {
        String sql = "SELECT * FROM TodoLists";
        List<TodoList> result = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TodoList.class));
        sql = "SELECT * FROM Todos;";
        List<Todo> todos = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Todo.class));
        for (TodoList list: result) {
            int id = list.getId();
            for (Todo todo: todos) {
                if (todo.getListId() == id) {
                    list.getTodoList().add(todo);
                }
            }
        }
        return result;
    }

    public int setIsDone(int todoId, boolean isDone) {
        String sql = "UPDATE Todos SET is_done = " + (isDone ? 1 : 0) + " WHERE Todos.id = " + todoId;
        return getJdbcTemplate().update(sql);
    }

    public int deleteTodoList(TodoList todoList) {
        for (Todo todo: todoList.getTodoList()) {
            deleteTodo(todo.getId());
        }
        String sql = "DELETE FROM TodoLists WHERE id = " + todoList.getId();
        return getJdbcTemplate().update(sql);
    }

    public int deleteTodo(int todoId) {
        String sql = "DELETE FROM Todos WHERE id = " + todoId;
        return getJdbcTemplate().update(sql);
    }
}
