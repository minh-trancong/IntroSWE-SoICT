package hust.itep.quanlynhankhau.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    public List<T> getAll();
    public T get(Long id);
    public void save(T t);
    public void delete(T t);

    public void update(T t, HashMap<String, Object> columnMap);
}
