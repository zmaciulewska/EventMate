package com.eventmate.mapper;

import com.eventmate.dto.CostDto;
import com.eventmate.dto.form.CostFormDto;
import com.eventmate.entity.Cost;

public interface CostMapper extends AbstractMapper<CostDto, Cost> {
    Cost formToEntity(CostFormDto costFormDto);
    Cost convert(CostDto costDto);
    CostDto convert(Cost cost);
}
