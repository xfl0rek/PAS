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

@Controller
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/home")
    public String showForm(Model model) {
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
}
