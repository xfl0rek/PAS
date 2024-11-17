//package pl.pas.aplikacjarest.converter;
//
//import pl.pas.aplikacjarest.dto.RentDTO;
//import pl.pas.aplikacjarest.model.Rent;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class RentConverter {
//    public RentConverter() {}
//
//    public List<RentDTO> convertRentListToDTO(List<Rent> rentList) {
//        return rentList.stream()
//                .map(this::convertRentToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public RentDTO convertRentToDTO(Rent rent) {
//        return new RentDTO(
//                rent.getClient().getUsername(),
//                rent.getRoom().getRoomNumber(),
//                rent.getBeginTime(),
//                rent.getEndTime()
//        );
//    }
//
//    public Rent convertDTOToRent(RentDTO rentDTO) {
//        return new Rent(
//                rentDTO.getClientUsername(),
//                rentDTO.getRoomNumber(),
//                rentDTO.getBeginTime()
//        );
//    }
//
//}
