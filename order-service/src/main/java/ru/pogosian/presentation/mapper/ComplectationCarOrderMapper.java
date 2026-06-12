package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.orders.complectationCarOrder.*;
import ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage;
import ru.pogosian.presentation.DTO.request.UpdateComplectationCarOrderRequest;
import ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComplectationCarOrderMapper {
    @Mapping(target = "stage", source = "state", qualifiedByName = "toDtoStage")
    ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse toDto(ComplectationCarOrder complectationCarOrder);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "state", source = "request.stage", qualifiedByName = "toDomainStage")
    ComplectationCarOrder toDomain(ru.pogosian.presentation.DTO.request.UpdateComplectationCarOrderRequest request, UUID orderId);

    @Named("toDtoStage")
    default ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage toDtoStage(CompectationCarOrderStatusState state){
        if(state instanceof ComplectationCarOrderApprovedByWarehouseState)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.ApprovedByWarehouse;
        if(state instanceof ComplectationCarOrderAwaitingForPaymen)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.AwaitingForPayment;
        if (state instanceof ComplectationCarOrderAwaitingForShipping)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.AwaitingForShipping;
        if(state instanceof ComplectationCarOrderCancelled)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Cancelled;
        if(state instanceof ComplectationCarOrderCompleted)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Completed;
        if(state instanceof ComplectationCarOrderIsReadyForPickingUp)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.ReadyForPickingUp;
        if(state instanceof ComplectationCarOrderPayed)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Payed;
        if(state instanceof ComplectationCarOrderPlaced)
            return ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Placed;
        throw new IllegalArgumentException("Unknown state type: " + state.getClass());
    }

    @Named("toDomainStage")
    default CompectationCarOrderStatusState toDomainStage(ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage complectationCarOrderStage){
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.ApprovedByWarehouse)
            return new ComplectationCarOrderApprovedByWarehouseState();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.AwaitingForPayment)
            return new ComplectationCarOrderAwaitingForPaymen();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.AwaitingForShipping)
            return new ComplectationCarOrderAwaitingForShipping();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Cancelled)
            return new ComplectationCarOrderCancelled();
        if(complectationCarOrderStage ==  ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Completed)
            return new ComplectationCarOrderCompleted();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.ReadyForPickingUp)
            return new ComplectationCarOrderIsReadyForPickingUp();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Payed)
            return new ComplectationCarOrderPayed();
        if(complectationCarOrderStage == ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage.Placed)
            return new ComplectationCarOrderPlaced();
        throw new IllegalArgumentException("Unknown state type: " + complectationCarOrderStage.getClass());
    }
}
