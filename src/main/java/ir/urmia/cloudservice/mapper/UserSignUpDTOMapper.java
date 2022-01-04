package ir.urmia.cloudservice.mapper;

import ir.urmia.cloudservice.base.mapper.BaseMapper;
import ir.urmia.cloudservice.domain.User;
import ir.urmia.cloudservice.service.dto.UserSignUpDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserSignUpDTOMapper extends BaseMapper<User, UserSignUpDTO> {
}
