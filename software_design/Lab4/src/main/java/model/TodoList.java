package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by nikita on 09.11.16.
 */
public class TodoList implements Iterable<Todo> {
    private List<Todo> todoList;
    private String name;
    private int id;

    public TodoList(int id, List<Todo> todoList, String name) {
        this.todoList = todoList;
        this.name = name;
        this.id = id;
    }

    public TodoList(int id, String name) {
        this.name = name;
        this.todoList = new ArrayList<Todo>();
        this.id = id;
    }

    public TodoList(String name) {
        this.name = name;
        this.todoList = new ArrayList<>();
    }

    public TodoList() {
        this.todoList = new ArrayList<Todo>();
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Iterator<Todo> iterator() {
        return todoList.iterator();
    }

    @Override
    public void forEach(Consumer<? super Todo> action) {
        todoList.forEach(action);
    }

    @Override
    public Spliterator<Todo> spliterator() {
        return todoList.spliterator();
    }
}
