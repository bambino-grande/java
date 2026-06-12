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
import ru.pogosian.business.services.AssemblyOrderService;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateAssemblyOrderRequest;
import ru.pogosian.presentation.DTO.response.AssemblyOrderResponse;
import ru.pogosian.presentation.mapper.AssemblyOrderMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assembly-orders")
@AllArgsConstructor
@Tag(name = "assembly-orders", description = "внутренние складские заказы на сборку")
public class AssemblyOrderController {
    private final AssemblyOrderService assemblyOrderService;
    private final AssemblyOrderMapper assemblyOrderMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "создать запрос на сборку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "запрос на сборку успешно создан", content = @Content(schema = @Schema(implementation = AssemblyOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public AssemblyOrderResponse createAssemblyOrder(@RequestBody CreateOrUpdateAssemblyOrderRequest createOrUpdateAssemblyOrderRequest) {
        return assemblyOrderMapper.toDto(assemblyOrderService.addAssemblyOrder(assemblyOrderMapper.toDomain(createOrUpdateAssemblyOrderRequest)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "получить заказ на сборку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "запрос на сборку успешно создан", content = @Content(schema = @Schema(implementation = AssemblyOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public AssemblyOrderResponse getAssemblyOrder(@PathVariable UUID id) {
        return assemblyOrderMapper.toDto(assemblyOrderService.viewAssemblyOrder(id));
    }

    @GetMapping("/find-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "получить список заказов на сборку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "запрос на сборку успешно создан", content = @Content(schema = @Schema(implementation = AssemblyOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public List<AssemblyOrderResponse> findAllDetails(Pageable pageable) {
        return assemblyOrderService.viewAllAssemblyOrder(pageable).stream().map(assemblyOrderMapper::toDto).collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "обновить заказ на сборку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "запрос на сборку успешно создан", content = @Content(schema = @Schema(implementation = AssemblyOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public AssemblyOrderResponse assemblyOrderUpdate(@PathVariable UUID id,@RequestBody CreateOrUpdateAssemblyOrderRequest createOrUpdateAssemblyOrderRequest) {
        return assemblyOrderMapper.toDto(assemblyOrderService.updateAssemblyOrder(id, assemblyOrderMapper.toDomain(createOrUpdateAssemblyOrderRequest)));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_ADMIN')")
    @Operation(summary = "удалить заказ на сборку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "заказ удален"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Конфликт данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для выполнения операции")
    })
    public void deleteCar(@PathVariable UUID id) {
        assemblyOrderService.deleteAssemblyOrder(id);
    }
}
