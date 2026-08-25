package ru.pogosian.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.presentation.DTO.request.CreateOrUpdateTestDriveRequestRequest;
import ru.pogosian.presentation.DTO.response.TestDriveRequestResponse;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestDriveRequestMapper {
    @Mapping(target = "testDriveId", ignore = true)
    TestDriveRequest toDomain(CreateOrUpdateTestDriveRequestRequest request);

    TestDriveRequestResponse toDto(TestDriveRequest request);

    @Mapping(source = "testDriveId", target = "testDriveId")
    TestDriveRequest toDomain(CreateOrUpdateTestDriveRequestRequest request, UUID testDriveId);
}