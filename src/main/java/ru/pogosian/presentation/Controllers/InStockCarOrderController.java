package ru.pogosian.presentation.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.presentation.DTO.request.CreateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.request.UpdateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.response.InStockCarOrderResponse;
import ru.pogosian.presentation.mapper.InStockCarOrderStageMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/in-stock-car-order")
@AllArgsConstructor
@Tag(name = "in-stock-orders", description = "операции с заказами на автомобили в наличии")
public class InStockCarOrderController {
    private final InStockCarOrderStageMapper inStockCarOrderMapper;
    private final OrderService orderService;

    @GetMapping("/find-all")
    @Operation(summary = "получить список заказов", description = "возвращает список заказов на автомобили в наличии до какого-то количества")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список заказов", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InStockCarOrderResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<InStockCarOrderResponse> findAllInStockCarOrders(Pageable pageable) {
        return orderService.viewAllInStockCarOrders(pageable).stream().map(inStockCarOrderMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @Operation(summary = "создать заказ", description = "создаёт заказ на автомобиль в наличии.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный заказ", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public InStockCarOrderResponse createInStockCarOrder(@RequestBody CreateInStockCarOrderRequest createInStockCarOrderRequest) {
        return inStockCarOrderMapper.toDto(orderService.createInStockCarOrder(createInStockCarOrderRequest.carId(), createInStockCarOrderRequest.clientId(), createInStockCarOrderRequest.managerId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "обновить заказ", description = "обновляет заказ на автомобиль в наличии.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "обновлённый заказ", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public InStockCarOrderResponse updateInStockCarOrder(@RequestBody UpdateInStockCarOrderRequest createOrUpdateCarDetailRequest, @PathVariable UUID id) {
        return inStockCarOrderMapper.toDto(orderService.updateInStockCarOrder(inStockCarOrderMapper.toDomain(createOrUpdateCarDetailRequest, id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удалить заказ", description = "выполняет мягкое удаление заказа на автомобиль в наличии.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void deleteInStockCarOrder(@PathVariable UUID id) {
        orderService.deleteInStockCarOrder(id);
    }
}

