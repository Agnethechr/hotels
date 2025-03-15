package app.dtos;

import app.entities.Hotel;
import app.entities.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDTO {
    @JsonIgnore
    private Integer id;
    private String name;
    private String address;
    private Set<RoomDTO> rooms;

    public HotelDTO(Hotel hotel){
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.rooms = hotel.getRooms().stream()
                .map(RoomDTO::new)
                .collect(Collectors.toSet());
    }
}
