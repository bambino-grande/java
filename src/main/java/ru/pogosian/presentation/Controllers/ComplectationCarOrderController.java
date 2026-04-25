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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pogosian.business.orders.complectationCarOrder.ComplectationCarOrder;
import ru.pogosian.business.orders.inStockCarOrder.InStockCarOrder;
import ru.pogosian.business.services.CarService;
import ru.pogosian.business.services.OrderService;
import ru.pogosian.presentation.DTO.request.*;
import ru.pogosian.presentation.DTO.response.CarDetailResponse;
import ru.pogosian.presentation.DTO.response.ComplectationCarOrderResponse;
import ru.pogosian.presentation.DTO.response.InStockCarOrderResponse;
import ru.pogosian.presentation.mapper.CarFilterMapper;
import ru.pogosian.presentation.mapper.CarMapper;
import ru.pogosian.presentation.mapper.ComplectationCarOrderMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/complectation-car-order")
@AllArgsConstructor
@Tag(name = "complectation-orders", description = "операции на заказазы автомобиля с выборной комплектацией")
public class ComplectationCarOrderController {
    private final ComplectationCarOrderMapper complectationCarOrderMapper;
    private final OrderService orderService;

    @GetMapping("/find-all")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @Operation(summary = "получить список заказов", description = "возвращает список заказов на автомобили с конкретноей комплектацией")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список заказов", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComplectationCarOrderResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<ComplectationCarOrderResponse> findAllComplectationCarOrders(Pageable pageable) {
        return orderService.viewAllComplectationCarOrders(pageable).stream().map(complectationCarOrderMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "создать заказ", description = "создаёт заказ на автомобиль с конкретной комплектацией.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный заказ", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse createComplectationCarOrder(@RequestBody CreateComplectationCarOrderRequest createComplectationCarOrderRequest) {
        return complectationCarOrderMapper.toDto(orderService.createComplectationCarOrder(createComplectationCarOrderRequest.carId(), createComplectationCarOrderRequest.clientId()));
    }

    @PostMapping("/clients")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "создание юзером заказа в наличии", description = "создаёт заказ на автомобиль с комплектацией. айди клиента связанного с заказом явзяется пользователь")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный заказ", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse createUsrsComplectationCarOrder(@RequestBody CreateComplectationCarOrderForUserRequest createComplectationCarOrderForUserRequest) {
        return complectationCarOrderMapper.toDto(orderService.createUsrsComplectationCarOrder(createComplectationCarOrderForUserRequest.carId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "обновить заказ", description = "обновляет заказ на автомобиль с конкретной комплектацию.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "обновлённый заказ", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse updateComplectationCarOrder(@RequestBody UpdateComplectationCarOrderRequest createOrUpdateCarDetailRequest, @PathVariable UUID id) {
        return complectationCarOrderMapper.toDto(orderService.updateComplectationCarOrder(complectationCarOrderMapper.toDomain(createOrUpdateCarDetailRequest, id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удалить заказ", description = "выполняет мягкое удаление заказа автомобиля с конкретной комплектацей.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public void deleteComplectationCarOrder(@PathVariable UUID id) {
        orderService.deleteComplectationCarOrder(id);
    }


    @PostMapping("/{id}/move")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "перевести заказ на следующий статус", description = "ADMIN может переходить из любового этапа, MANAGER может переходить из всех этапов связанных с менеджером, WAREHOUSE_ADMIN может переходить из всех этапов связанных с администратором склада")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "удаленный хаках", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse moveComplectationCarOrder(@PathVariable UUID id) {
        return complectationCarOrderMapper.toDto(orderService.moveComplectationCarOrder(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "отмена заказа", description = "USER может отменить свой заказ, ADMIN можнт отменить любоюй заказ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ отменен", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse cancelComplectationCarOrder(@PathVariable UUID id) {
        return complectationCarOrderMapper.toDto(orderService.cancelComplectationCarOrder(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @Operation(summary = "получить заказ", description = "возвращает заказ по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ получен", content = @Content(schema = @Schema(implementation = ComplectationCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ComplectationCarOrderResponse getComplectationCarOrder(@PathVariable UUID id) {
        return complectationCarOrderMapper.toDto(orderService.getComplectationCarOrder(id));
    }


}

