package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CodeReturnDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderValidationService {

    /**
     * Checks if an order is valid.
     *
     * @param orderValidationInquiryDto contains information required to validate
     * @return OrderValidationDto containing information about order and its validity
     */
    OrderValidationDto validate(OrderValidationInquiryDto orderValidationInquiryDto);

    /**
     * Creates validation entry on order buy.
     *
     * @param order to be validated in the future
     */
    void createValidation(Order order);

    /**
     * Generate validation QR code.
     *
     * @param id of the order
     * @return CodeReturnDto with the generated code
     */
    CodeReturnDto generateCode(Long id);
}
