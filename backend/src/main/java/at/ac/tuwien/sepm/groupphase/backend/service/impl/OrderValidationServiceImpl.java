package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CodeReturnDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.OrderValidationInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TicketMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Order;
import at.ac.tuwien.sepm.groupphase.backend.entity.OrderValidation;
import at.ac.tuwien.sepm.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.OrderValidationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.OrderValidationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class OrderValidationServiceImpl implements OrderValidationService {
    private PasswordEncoder passwordEncoder;
    private OrderValidationRepository orderValidationRepository;
    private OrderRepository orderRepository;
    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;
    private AddressMapper addressMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public OrderValidationServiceImpl(
        PasswordEncoder passwordEncoder,
        OrderValidationRepository orderValidationRepository,
        OrderRepository orderRepository,
        TicketRepository ticketRepository,
        TicketMapper ticketMapper,
        AddressMapper addressMapper) {
        this.passwordEncoder = passwordEncoder;
        this.orderValidationRepository = orderValidationRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    @Override
    public OrderValidationDto validate(OrderValidationInquiryDto orderValidationInquiryDto) {
        LOGGER.debug("Validating");
        try {
            Order order = orderRepository.getById(orderValidationInquiryDto.getId());

            if (!orderValidationRepository.existsByOrder(order)) {
                return OrderValidationDto.OrderValidationDtoBuilder
                    .anOrderValidationDto()
                    .withValid(false)
                    .withComment("This Order does not exist!")
                    .build();
            }

            OrderValidation orderValidation = orderValidationRepository.findByOrder(order);

            if (!passwordEncoder.matches(orderValidation.getHash(), orderValidationInquiryDto.getHash())
                || order.isRefunded() || !order.isBought()) {
                return OrderValidationDto.OrderValidationDtoBuilder
                    .anOrderValidationDto()
                    .withValid(false)
                    .withComment("The QR Code was manipulated or the Order was refunded!")
                    .build();
            }

            ApplicationUser applicationUser = order.getUser();
            List<TicketDto> tickets = ticketMapper.ticketToTicketDto(ticketRepository.findTicketsByOrder(order));

            return OrderValidationDto.OrderValidationDtoBuilder
                .anOrderValidationDto()
                .withFirstName(applicationUser.getFirstName())
                .withLastName(applicationUser.getLastName())
                .withTickets(tickets)
                .withValid(true)
                .withDate(order.getPerformance().getStartTime())
                .withPerformanceName(order.getPerformance().getName())
                .withComment("This Order has been validated.")
                .build();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void createValidation(Order order) {
        LOGGER.debug("Create Validation");
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        String hash = passwordEncoder.encode(order.hashCode() + generatedString);
        OrderValidation orderValidation = new OrderValidation();
        orderValidation.setOrder(order);
        orderValidation.setHash(hash);

        try {
            orderValidationRepository.save(orderValidation);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public CodeReturnDto generateCode(Long id) {
        try {
            LOGGER.debug("generating QR code.");
            Order order = orderRepository.getById(id);
            if (!order.isBought() || order.isRefunded()) {
                throw new ConflictException("Order wasn't bought or was refunded!");
            }
            OrderValidation orderValidation = orderValidationRepository.findByOrder(order);

            byte[] image = new byte[0];

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String hash = passwordEncoder.encode(orderValidation.getHash());
            System.out.println(hash);
            String url = String.format("http://localhost:4200/#/validation/%s?hash=%s", order.getId(), hash);
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 100, 100);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFFF);

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
            byte[] pngData = pngOutputStream.toByteArray();
            CodeReturnDto codeReturnDto = new CodeReturnDto();
            codeReturnDto.setImage(Base64.getEncoder().encodeToString(pngData));
            codeReturnDto.setAddress(addressMapper.entityToDto(order.getPerformance().getEvent().getEventPlace().getAddress()));
            codeReturnDto.setUserName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
            codeReturnDto.setTickets(ticketMapper.ticketToTicketDto(order.getTickets()));
            return codeReturnDto;

        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (WriterException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
