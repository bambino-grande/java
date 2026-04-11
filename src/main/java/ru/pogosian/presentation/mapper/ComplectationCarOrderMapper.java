package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.orders.complectationCarOrder.*;
import ru.pogosian.presentation.DTO.Types.ComplectationCarOrderStage;
import ru.pogosian.presentation.DTO.request.CreateComplectationCarOrderRequest;
import ru.pogosian.presentation.DTO.request.UpdateComplectationCarOrderRequest;
import ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComplectationCarOrderMapper {
    @Mapping(target = "stage", source = "state", qualifiedByName = "toDtoStage")
    ComplectationCarOrderResponse toDto(ComplectationCarOrder complectationCarOrder);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "state", ignore = true)
    ComplectationCarOrder toDomain(CreateComplectationCarOrderRequest request);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "state", source = "request.stage", qualifiedByName = "toDomainStage")
    ComplectationCarOrder toDomain(UpdateComplectationCarOrderRequest request, UUID orderId);

    @Named("toDtoStage")
    default ComplectationCarOrderStage toDtoStage(CompectationCarOrderStatusState state){
        if(state instanceof ComplectationCarOrderApprovedByWarehouseState)
            return ComplectationCarOrderStage.ApprovedByWarehouse;
        if(state instanceof ComplectationCarOrderAwaitingForPaymen)
            return ComplectationCarOrderStage.AwaitingForPayment;
        if (state instanceof ComplectationCarOrderAwaitingForShipping)
            return ComplectationCarOrderStage.AwaitingForShipping;
        if(state instanceof ComplectationCarOrderCancelled)
            return ComplectationCarOrderStage.Cancelled;
        if(state instanceof ComplectationCarOrderCompleted)
            return ComplectationCarOrderStage.Completed;
        if(state instanceof ComplectationCarOrderIsReadyForPickingUp)
            return ComplectationCarOrderStage.ReadyForPickingUp;
        if(state instanceof ComplectationCarOrderPayed)
            return ComplectationCarOrderStage.Payed;
        if(state instanceof ComplectationCarOrderPlaced)
            return ComplectationCarOrderStage.Placed;
        throw new IllegalArgumentException("Unknown state type: " + state.getClass());
    }

    @Named("toDomainStage")
    default CompectationCarOrderStatusState toDomainStage(ComplectationCarOrderStage complectationCarOrderStage){
        if(complectationCarOrderStage == ComplectationCarOrderStage.ApprovedByWarehouse)
            return new ComplectationCarOrderApprovedByWarehouseState();
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForPayment)
            return new ComplectationCarOrderAwaitingForPaymen();
        if(complectationCarOrderStage == ComplectationCarOrderStage.AwaitingForShipping)
            return new ComplectationCarOrderAwaitingForShipping();
        if(complectationCarOrderStage == ComplectationCarOrderStage.Cancelled)
            return new ComplectationCarOrderCancelled();
        if(complectationCarOrderStage ==  ComplectationCarOrderStage.Completed)
            return new ComplectationCarOrderCompleted();
        if(complectationCarOrderStage == ComplectationCarOrderStage.ReadyForPickingUp)
            return new ComplectationCarOrderIsReadyForPickingUp();
        if(complectationCarOrderStage == ComplectationCarOrderStage.Payed)
            return new ComplectationCarOrderPayed();
        if(complectationCarOrderStage == ComplectationCarOrderStage.Placed)
            return new ComplectationCarOrderPlaced();
        throw new IllegalArgumentException("Unknown state type: " + complectationCarOrderStage.getClass());
    }
}
