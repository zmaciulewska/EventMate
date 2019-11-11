package com.eventmate.serviceImpl;

import com.eventmate.dao.CostDao;
import com.eventmate.dto.CostDto;
import com.eventmate.entity.Cost;
import com.eventmate.mapper.CostMapper;
import com.eventmate.service.CostService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostServiceImpl extends AbstractServiceImpl<CostDto, Cost> implements CostService {

    private CostDao repository;
    private CostMapper mapper;

    public CostServiceImpl(CostDao repository, CostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public JpaRepository<Cost, Long> getRepository() {
        return repository;
    }

    @Override
    public CostDto convert(Cost entity) {
        return mapper.convert(entity);
    }

    @Override
    public Cost convert(CostDto dto) {
        return mapper.convert(dto);
    }

    @Override
    public List<CostDto> entitiesToDtos(List<Cost> entities) {
        return entities.stream().map(e -> mapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<Cost> dtosToEntities(List<CostDto> dtos) {
        return dtos.stream().map(e -> mapper.convert(e)).collect(Collectors.toList());
    }
}
