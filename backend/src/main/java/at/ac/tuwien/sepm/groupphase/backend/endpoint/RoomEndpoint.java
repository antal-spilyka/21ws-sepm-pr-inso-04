package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomEndpoint {

    private RoomService roomService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RoomEndpoint(RoomService roomService) {
        this.roomService = roomService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find room by search parameters.")
    public ResponseEntity findRooms(RoomSearchDto roomSearchDto) {
        ResponseEntity response = new ResponseEntity(roomService.findRoom(roomSearchDto).stream(), HttpStatus.OK);
        return response;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new room.")
    public ResponseEntity saveRoom(@RequestBody @Validated RoomInquiryDto roomInquiryDto) {
        try {
            ResponseEntity response = new ResponseEntity(roomService.save(roomInquiryDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room already exists:  " + e.getLocalizedMessage(), e);
        }
    }
}
