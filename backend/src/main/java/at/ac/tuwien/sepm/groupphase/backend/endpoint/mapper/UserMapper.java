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
     * Maps ApplicationUser to userDto.
     *
     * @param user which has to be mapped to userDto
     * @return userDto
     */
    UserDto applicationUserToUserDto(ApplicationUser user);

    /**
     * Maps paymentInformation to paymentInformationDto.
     *
     * @param paymentInformation which has to be mapped to paymentInformationDto
     * @return paymentInformationDto
     */
    PaymentInformationDto paymentInformationToPaymentInformationDto(PaymentInformation paymentInformation);

    /**
     * Maps paymentInformationDto to paymentInformation.
     *
     * @param paymentInformationDto which has to be mapped to paymentInformation
     * @return paymentInformation
     */
    PaymentInformation paymentInformationDtoToPaymentInformation(PaymentInformationDto paymentInformationDto);

    /**
     * Maps userEditDto to ApplicationUser.
     *
     * @param userEditDto which includes edited user changes to be mapped to ApplicationUser
     * @return ApplicationUser
     */
    ApplicationUser userEditDtoToApplicationUser(UserEditDto userEditDto);
}
