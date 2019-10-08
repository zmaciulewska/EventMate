package com.eventmate.serviceImpl;

import com.eventmate.dto.AbstractDto;
import com.eventmate.entity.AbstractEntity;
import com.eventmate.service.AbstractService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class AbstractServiceImpl<T extends AbstractDto, S extends AbstractEntity> implements AbstractService<T> {

    public abstract JpaRepository<S, Long> getRepository();

    public abstract T convert(S entity);

    public abstract S convert(T dto);

    public abstract List<T> entitiesToDtos(List<S> entities);

    public abstract List<S> dtosToEntities(List<T> dtos);

    @Override
    public List<T> getAll() {
        Sort orderBy = new Sort(Sort.Direction.ASC, "id");
        return entitiesToDtos(getRepository().findAll(orderBy));
    }

    @Override
    public T getOne(Long id) {
        S entity = getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Object with given id: " + id + " not found."));
        return convert(entity);
    }

    @Override
    public T create(T t) {
        return convert(getRepository().save(convert(t)));
    }

    @Override
    public void delete(Long id) {
        S entity = getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Object with given id: " + id + " not found."));
        getRepository().delete(entity);
    }

    @Override
    public T update(T t, Long id) {
        if (getRepository().findById(id).isPresent()) {
            S entity = getRepository().findById(id).get();
            return convert(getRepository().save(entity));
        } else {
            return null;
        }
    }
}
