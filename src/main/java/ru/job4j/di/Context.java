package ru.job4j.di;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Map<String, Object> els = new HashMap<String, Object>();

    public void reg(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors(); // получаем все конструкторы у класса, переданного в параметры
        if (constructors.length > 1) {
            throw new IllegalStateException("Class has multiple constructors : " + cl.getCanonicalName());
        }
        Constructor con = constructors[0]; // получаем конкретный конструктор
        List<Object> args = new ArrayList<Object>();
        for (Class arg : con.getParameterTypes()) { // получаем классы/значения, которые этот конструктор принимает для создания объекта. Например он принимает String / Integer
            if (!els.containsKey(arg.getCanonicalName())) { // если map не содержит такой класс, например String/Integer, то вызываем исключение
                throw new IllegalStateException("Object doesn't found in context : " + arg.getCanonicalName());
            }
            args.add(els.get(arg.getCanonicalName())); // а если наш класс есть в мапе, то добавляем в лист
        }
        try {
            els.put(cl.getCanonicalName(), con.newInstance(args.toArray()));
        } catch (Exception e) {
            throw new IllegalStateException("Count create an instance of : " + cl.getCanonicalName(), e);
        }
    }

    public <T> T get(Class<T> inst) {
        return (T) els.get(inst.getCanonicalName());
    }
}