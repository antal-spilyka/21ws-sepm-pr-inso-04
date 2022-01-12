package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto applicationUserToUserDto(ApplicationUser user);

    PaymentInformationDto paymentInformationToPaymentInformationDto(PaymentInformation paymentInformation);

    PaymentInformation paymentInformationDtoToPaymentInformation(PaymentInformationDto paymentInformationDto);

    ApplicationUser userEditDtoToApplicationUser(UserEditDto userEditDto);
}
