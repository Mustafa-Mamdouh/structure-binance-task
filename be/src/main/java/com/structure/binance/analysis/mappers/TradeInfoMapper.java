package com.structure.binance.analysis.mappers;

import com.structure.binance.analysis.dtos.responses.TradeInfoDto;
import com.structure.binance.analysis.services.binance.TradeInfo;
import org.mapstruct.Mapper;

@Mapper
public interface TradeInfoMapper {
    TradeInfo asEntity(TradeInfoDto tradeInfoDto);
    TradeInfoDto asDto(TradeInfo tradeInfo);
}
