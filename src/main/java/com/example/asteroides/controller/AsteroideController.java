package com.example.asteroides.controller;

import com.example.asteroides.dto.FormularioRequest;
import com.example.asteroides.model.AsteroideVista;
import com.example.asteroides.service.AsteroideService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class AsteroideController {

    private final AsteroideService servicioAsteroide;

    public AsteroideController(AsteroideService servicioAsteroide) {
        this.servicioAsteroide = servicioAsteroide;
    }

    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal OAuth2User user, Model model) {

        if (!model.containsAttribute("busqueda")) {
            model.addAttribute("busqueda", new FormularioRequest());
        }

        if (user != null) {
            model.addAttribute("given_name", user.getAttribute("given_name"));
            model.addAttribute("email", user.getAttribute("email"));
            model.addAttribute("picture", user.getAttribute("picture"));
        }

        return "index";
    }

    @PostMapping("/buscar")
    public String buscar(
            @Valid @ModelAttribute("busqueda") FormularioRequest formularioRequest,
            BindingResult errores,
            @AuthenticationPrincipal OAuth2User user,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (errores.hasErrors()) {
            addUserAttributes(user, model);
            return "index";
        }

        LocalDate fecha = formularioRequest.getFecha();
        String fechaFormateada = fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try {
            List<AsteroideVista> listaAsteroides = servicioAsteroide.obtenerAsteroides(fecha);
            redirectAttributes.addFlashAttribute("asteroides", listaAsteroides);
            redirectAttributes.addFlashAttribute("fechaConsultada", fechaFormateada);
            return "redirect:/resultados";

        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            errores.rejectValue("fecha", "error", "No hay datos para esa fecha.");
        } catch (org.springframework.web.client.ResourceAccessException e) {
            errores.rejectValue("fecha", "error", "No se puede conectar con la API de la NASA ahora mismo.");
        } catch (org.springframework.web.client.RestClientException e) {
            errores.rejectValue("fecha", "error", "Error al consultar la API de la NASA.");
        }

        addUserAttributes(user, model);
        return "index";
    }

    private void addUserAttributes(OAuth2User user, Model model) {
        if (user != null) {
            model.addAttribute("given_name", user.getAttribute("given_name"));
            model.addAttribute("email", user.getAttribute("email"));
            model.addAttribute("picture", user.getAttribute("picture"));
        }
    }

    @GetMapping("/resultados")
    public String resultados() {
        return "resultados";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    @ResponseBody
    public Map<String, Object> profile(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
}
