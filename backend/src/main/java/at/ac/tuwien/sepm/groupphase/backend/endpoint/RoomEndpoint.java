package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomEndpoint {

    private RoomService roomService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RoomEndpoint(RoomService roomService) {
        this.roomService = roomService;
    }

    @PermitAll
    @GetMapping
    @Operation(summary = "Find room by search parameters.")
    public ResponseEntity findRooms(RoomSearchDto roomSearchDto) {
        try {
            ResponseEntity response = new ResponseEntity(roomService.findRoom(roomSearchDto).stream(), HttpStatus.OK);
            return response;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }

    @PermitAll
    @PostMapping
    @Operation(summary = "persist new room.")
    public ResponseEntity saveRoom(@RequestBody RoomInquiryDto roomInquiryDto) {
        try {
            ResponseEntity response = new ResponseEntity(roomService.save(roomInquiryDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room with same id already exists.");
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }
}
