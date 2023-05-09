package ru.tlnts.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tlnts.oauth.api.model.CreateUserRequest;
import ru.tlnts.oauth.api.model.UserInfo;
import ru.tlnts.oauth.storage.user.entity.User;

/**
 * @author mamedov
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "dateTimeCreate", ignore = true)
	@Mapping(target = "password", source = "password")
	public abstract User map(CreateUserRequest request, String password);

	public abstract UserInfo map(User user);

}
