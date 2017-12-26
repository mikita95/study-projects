package controller;

import dao.TodoListsDao;
import logic.TodoListsLogic;
import model.Todo;
import model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * Created by nikita on 09.11.16.
 */
@Controller
public class TodoListController {
    @Autowired
    private TodoListsDao todoListsDao;

    @RequestMapping(value = "/get-lists", method = RequestMethod.GET)
    public String getTodoLists(ModelMap map) {
        prepareModelMap(map, todoListsDao.getTodoLists(), new TodoList());
        return "index";
    }

    @RequestMapping(value = "/get-todo_list", method = RequestMethod.GET)
    public String getTodoList(@RequestParam String name, ModelMap map) {
        Optional<TodoList> list = TodoListsLogic.getTodoListByName(todoListsDao.getTodoLists(), name);
        prepareModelMap(map, todoListsDao.getTodoLists(), list.isPresent() ? list.get() : new TodoList());
        return "index";
    }

    @RequestMapping(value = "/delete-todo_list", method = RequestMethod.GET)
    public String deleteTodoList(@RequestParam String name, ModelMap map) {
        Optional<TodoList> list = TodoListsLogic.getTodoListByName(todoListsDao.getTodoLists(), name);
        if (list.isPresent()) {
            todoListsDao.deleteTodoList(list.get());
        }
        prepareModelMap(map, todoListsDao.getTodoLists(), new TodoList());
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/add-todo_list", method = RequestMethod.POST)
    public String addTodoList(@ModelAttribute("todo_list") TodoList list, ModelMap map) {
        todoListsDao.addTodoList(list);
        prepareModelMap(map, todoListsDao.getTodoLists(), list);
        return "redirect:/get-todo_list?name=" + list.getName();
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodoList(@RequestParam String name, @RequestParam("todo") String todo, ModelMap map) {
        Optional<TodoList> list = TodoListsLogic.getTodoListByName(todoListsDao.getTodoLists(), name);
        if (list.isPresent()) {
            todoListsDao.addTodoToList(list.get().getId(), new Todo(todo));
            prepareModelMap(map, todoListsDao.getTodoLists(),
                    TodoListsLogic.getTodoListByName(todoListsDao.getTodoLists(), name).get());
        }
        return "redirect:/get-todo_list?name=" + name;
    }

    @RequestMapping(value = "/set_done", method = RequestMethod.POST)
    public String setTodoDone(@RequestParam String name, @RequestParam("sid") String[] selected, ModelMap map) {
        TodoListsLogic.setDone(name, selected, todoListsDao);
        prepareModelMap(map, todoListsDao.getTodoLists(), new TodoList());
        return "redirect:/get-todo_list?name=" + name;
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.POST)
    public String deleteTodo(@RequestParam String name, @RequestParam("toDelete") String toDelete, ModelMap map) {
        todoListsDao.deleteTodo(Integer.parseInt(toDelete));
        prepareModelMap(map, todoListsDao.getTodoLists(), new TodoList());
        return "redirect:/get-todo_list?name=" + name;
    }

    private void prepareModelMap(ModelMap map, List<TodoList> lists, TodoList list) {
        map.addAttribute("lists", lists);
        map.addAttribute("lists_names", TodoListsLogic.getListsNames(lists));
        map.addAttribute("todo_list", list);
    }
}

