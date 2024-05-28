package id.ten.authapi.mappers;

import id.ten.authapi.model.User;
import id.ten.authapi.records.RegisterUserRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMappers {
    User toUser(RegisterUserRecord registerUserRecord);
}
