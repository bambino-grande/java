package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.orders.inStockCarOrder.*;
import ru.pogosian.presentation.DTO.Types.InStockCarOrderStage;
import ru.pogosian.presentation.DTO.request.CreateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.request.UpdateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.response.InStockCarOrderResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InStockCarOrderStageMapper {
    @Mapping(target = "stage", source = "state", qualifiedByName = "toDtoStage")
    InStockCarOrderResponse toDto(InStockCarOrder inStockCarOrder);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "state", source = "request.stage", qualifiedByName = "toDomainStage")
    InStockCarOrder toDomain(UpdateInStockCarOrderRequest request, UUID orderId);

    @Named("toDtoStage")
    default InStockCarOrderStage toDtoStage(InStockCarOrderStatusState state){
        if(state instanceof InStockCarOrderApprovedByManager)
            return InStockCarOrderStage.ApprovedByManager;
        if(state instanceof InStockCarOrderAwaitingForPaymen)
            return InStockCarOrderStage.AwaitingForPayment;
        if(state instanceof InStockCarOrderCancelled)
            return InStockCarOrderStage.Cancelled;
        if(state instanceof InStockCarOrderCompleted)
            return InStockCarOrderStage.Completed;
        if(state instanceof InStockCarOrderIsReadyForPickingUp)
            return InStockCarOrderStage.ReadyForPickingUp;
        if(state instanceof InStockCarOrderPayed)
            return InStockCarOrderStage.Payed;
        if(state instanceof InStockCarOrderPlaced)
            return InStockCarOrderStage.Placed;
        throw  new IllegalArgumentException("Unknown state type: " + state.getClass());
    }

    @Named("toDomainStage")
    default InStockCarOrderStatusState toDomainStage(InStockCarOrderStage inStockCarOrderStage){
        if(inStockCarOrderStage == InStockCarOrderStage.ApprovedByManager)
            return new InStockCarOrderApprovedByManager();
        if(inStockCarOrderStage == InStockCarOrderStage.AwaitingForPayment)
            return new InStockCarOrderAwaitingForPaymen();
        if(inStockCarOrderStage == InStockCarOrderStage.Cancelled)
            return new InStockCarOrderCancelled();
        if(inStockCarOrderStage ==  InStockCarOrderStage.Completed)
            return new InStockCarOrderCompleted();
        if(inStockCarOrderStage == InStockCarOrderStage.ReadyForPickingUp)
            return new InStockCarOrderIsReadyForPickingUp();
        if(inStockCarOrderStage == InStockCarOrderStage.Payed)
            return new InStockCarOrderPayed();
        if(inStockCarOrderStage == InStockCarOrderStage.Placed)
            return new InStockCarOrderPlaced();
        throw new IllegalArgumentException("inStockCarOrderStage not implemented");
    }
}
