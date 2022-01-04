package ir.urmia.cloudservice.mapper;

import ir.urmia.cloudservice.base.mapper.BaseMapper;
import ir.urmia.cloudservice.domain.User;
import ir.urmia.cloudservice.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserDTOMapper extends BaseMapper<User, UserDTO> {
}
