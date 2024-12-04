package pl.pas.aplikacjamvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pas.aplikacjamvc.dto.RentDTO;
import pl.pas.aplikacjamvc.service.RentService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        return "home";
    }

    @PostMapping
    public String rentRoom(@RequestParam String clientUsername,
                           @RequestParam int roomNumber,
                           @RequestParam LocalDateTime beginTime) {
        RentDTO rentDTO = new RentDTO(clientUsername, roomNumber, beginTime, null);
        rentService.rentRoom(rentDTO);
        return "redirect:/home";
    }

    @PostMapping("/return")
    public String returnRoom(@RequestParam String id, @RequestParam LocalDateTime endTime) {
        rentService.returnRoom(id, endTime);
        return "redirect:/home";
    }

    @GetMapping("/getAllRents")
    public String getAllRents(Model model) {
        List<RentDTO> rentDTOs = rentService.getAllRents();
        model.addAttribute("rentDTOs", rentDTOs);
        return "rentList";
    }

    @GetMapping("/rents")
    public String showRents(Model model) {
        return "rents";
    }

    @GetMapping("/rents/rentRoom")
    public String showRentRoom(Model model) {
        return "rentRoom";
    }

    @GetMapping("/rents/returnRoom")
    public String showReturnRoom(Model model) {
        return "returnRoom";
    }

    @GetMapping("/rents/rentList")
    public String showRentList(Model model) {
        return "rentList";
    }
}
