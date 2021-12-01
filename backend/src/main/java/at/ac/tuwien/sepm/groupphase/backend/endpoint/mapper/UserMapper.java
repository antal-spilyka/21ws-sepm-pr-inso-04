package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {
    UserDto applicationUserToUserDto(ApplicationUser user);

    PaymentInformationDto paymentInformationToPaymentInformationDto(PaymentInformation paymentInformation);

    PaymentInformation paymentInformationDtoToPaymentInformation(PaymentInformationDto paymentInformationDto);
}
