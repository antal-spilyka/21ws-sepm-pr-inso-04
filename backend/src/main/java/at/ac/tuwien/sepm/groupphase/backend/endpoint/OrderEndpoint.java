package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CodeReturnDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderRefundDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SetOrderToBoughtDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserEditDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ValidOrderInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.OrderMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderService;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderEndpoint {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderValidationService orderValidationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/orders";

    @Autowired
    public OrderEndpoint(OrderService orderService, OrderMapper orderMapper, OrderValidationService orderValidationService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderValidationService = orderValidationService;
    }

    @GetMapping("/{email}/reserved")
    @PermitAll
    public List<OrderDto> getReservedOrders(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/reserved", email);
        return this.orderMapper.orderToOrderDto(orderService.getAllReserved(email));
    }

    @GetMapping("/{email}/bought")
    @PermitAll
    public List<OrderDto> getBoughtOrders(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/bought", email);
        return this.orderMapper.orderToOrderDto(orderService.getAllBought(email));
    }

    @GetMapping("/{email}")
    @PermitAll
    public List<OrderDto> getAllOrders(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}", email);
        return this.orderMapper.orderToOrderDto(orderService.getAll(email));
    }

    @PutMapping()
    @PermitAll
    public ResponseEntity<String> setToBought(@RequestBody SetOrderToBoughtDto setOrderToBoughtDto) {
        LOGGER.info("PUT " + BASE_URL + "/{}", setOrderToBoughtDto);
        orderService.setOrderToBought(setOrderToBoughtDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/refund")
    @PermitAll
    public ResponseEntity<String> setRefund(@RequestBody OrderRefundDto orderRefundDto) {
        LOGGER.info("PUT " + BASE_URL + "refund: {}", orderRefundDto);
        orderService.refund(orderRefundDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/validation/{id}")
    @PermitAll
    public OrderValidationDto validateOrder(@PathVariable Long id, @RequestBody ValidOrderInquiryDto validOrderInquiryDto) {
        LOGGER.info("POST " + BASE_URL + "/validation: {}, {}", id, validOrderInquiryDto.getHash());
        OrderValidationInquiryDto orderValidationInquiryDto = new OrderValidationInquiryDto();
        orderValidationInquiryDto.setId(id);
        orderValidationInquiryDto.setHash(validOrderInquiryDto.getHash());

        return orderValidationService.validate(orderValidationInquiryDto);
    }

    @GetMapping("/validation/{id}")
    @PermitAll
    public CodeReturnDto getQrcode(@PathVariable Long id) {
        LOGGER.info("GET " + BASE_URL + "/validation code: {}", id);
        OrderValidationInquiryDto orderValidationInquiryDto = new OrderValidationInquiryDto();
        orderValidationInquiryDto.setId(id);
        CodeReturnDto codeReturnDto = orderValidationService.generateCode(id);
        return codeReturnDto;
    }
}
