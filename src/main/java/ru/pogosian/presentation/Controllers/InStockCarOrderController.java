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
import ru.pogosian.business.services.OrderService;
import ru.pogosian.presentation.DTO.request.CreateInStockCarOrderForUserRequest;
import ru.pogosian.presentation.DTO.request.CreateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.request.UpdateInStockCarOrderRequest;
import ru.pogosian.presentation.DTO.response.CarResponse;
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
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @Operation(summary = "получить список заказов", description = "возвращает список заказов на автомобили в наличии до какого-то количества")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "список заказов", content = @Content(array = @ArraySchema(schema = @Schema(implementation = InStockCarOrderResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public List<InStockCarOrderResponse> findAllInStockCarOrders(Pageable pageable) {
        return orderService.viewAllInStockCarOrders(pageable).stream().map(inStockCarOrderMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "создание админом заказа в наличии", description = "создаёт заказ на автомобиль в наличии. админ сам указывает id клиента, с которым связан заказ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный заказ", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse createInStockCarOrder(@RequestBody CreateInStockCarOrderRequest createInStockCarOrderRequest) {
        return inStockCarOrderMapper.toDto(orderService.createInStockCarOrder(createInStockCarOrderRequest.carId(), createInStockCarOrderRequest.clientId()));
    }

    @PostMapping("/clients")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "создание юзером заказа в наличии", description = "создаёт заказ на автомобиль в наличии. айди клиента связанного с заказом явзяется пользователь")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "созданный заказ", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse createUsersInStockCarOrder(@RequestBody CreateInStockCarOrderForUserRequest createInStockCarOrderRequest) {
        return inStockCarOrderMapper.toDto(orderService.createUsersInStockCarOrder(createInStockCarOrderRequest.carId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "обновить заказ", description = "обновляет заказ на автомобиль в наличии.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "обновлённый заказ", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse updateInStockCarOrder(@RequestBody UpdateInStockCarOrderRequest createOrUpdateCarDetailRequest, @PathVariable UUID id) {
        return inStockCarOrderMapper.toDto(orderService.updateInStockCarOrder(inStockCarOrderMapper.toDomain(createOrUpdateCarDetailRequest, id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "удалить заказ", description = "выполняет мягкое удаление заказа на автомобиль в наличии.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public void deleteInStockCarOrder(@PathVariable UUID id) {
        orderService.deleteInStockCarOrder(id);
    }

    @PostMapping("/{id}/move")
    @PreAuthorize("@orderSecurityService.canMoveInStockCarOrder(#id, authentication)")
    @Operation(summary = "перевести заказ на следующий статус", description = "ADMIN может переходить из любового этапа, MANAGER может переходить из всех этапов связанных с менеджером")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "удаленный хаках", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse moveInStockCarOrder(@PathVariable UUID id) {
        return inStockCarOrderMapper.toDto(orderService.moveInStockCarOrder(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN') or @orderSecurityService.isInStockCarOrderOwner(#id, authentication)")
    @Operation(summary = "отмена заказа", description = "USER может отменить свой заказ, ADMIN можнт отменить любоюй заказ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ отменен", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Данные не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse cancelInStockCarOrder(@PathVariable UUID id) {
        return inStockCarOrderMapper.toDto(orderService.cancelInStockCarOrder(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @orderSecurityService.isInStockCarOrderOwner(#id, authentication)")
    @Operation(summary = "получить заказ", description = "возвращает заказ по идентификатору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ получен", content = @Content(schema = @Schema(implementation = InStockCarOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public InStockCarOrderResponse getInStockCarOrder(@PathVariable UUID id) {
        return inStockCarOrderMapper.toDto(orderService.getInStockCarOrder(id));
    }

}

