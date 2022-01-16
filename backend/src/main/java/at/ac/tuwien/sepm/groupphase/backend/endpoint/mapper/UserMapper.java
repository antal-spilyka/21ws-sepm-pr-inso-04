package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PaymentInformationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.PaymentInformation;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    /**
     * Maps an application user to a user dto.
     *
     * @param user to map.
     * @return mapped user dto.
     */
    UserDto applicationUserToUserDto(ApplicationUser user);

    /**
     * Maps a payment information to a payment information dto.
     *
     * @param paymentInformation to map.
     * @return mapped payment information dto.
     */
    PaymentInformationDto paymentInformationToPaymentInformationDto(PaymentInformation paymentInformation);

    /**
     * Maps a payment information dto to a payment information.
     *
     * @param paymentInformationDto to map.
     * @return mapped payment information.
     */
    PaymentInformation paymentInformationDtoToPaymentInformation(PaymentInformationDto paymentInformationDto);

    /**
     * Maps a user dto to a user entity.
     *
     * @param userEditDto to map.
     * @return mapped user entity.
     */
    ApplicationUser userEditDtoToApplicationUser(UserEditDto userEditDto);
}
