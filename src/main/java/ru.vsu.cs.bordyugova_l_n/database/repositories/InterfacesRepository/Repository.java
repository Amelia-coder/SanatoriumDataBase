package ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository;

import java.util.*;

// Generic Repository Interface
public interface Repository<T> {
    void add(T entity);
    void update(T entity);
    void delete(Long id);
    T getById(Long id);
    List<T> getAll();
}