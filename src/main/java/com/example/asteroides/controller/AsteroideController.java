package com.example.asteroides.controller;

import com.example.asteroides.dto.FormularioRequest;
import com.example.asteroides.model.AsteroideVista;
import com.example.asteroides.model.ConsultaHistorial;
import com.example.asteroides.service.AsteroideService;
import jakarta.servlet.http.HttpSession;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controlador web para mostrar formularios, resultados y perfil de usuario.
 */
@Controller
public class AsteroideController {

    /**
     * Servicio que contiene la lógica de negocio de asteroides.
     */
    private final AsteroideService servicioAsteroide;

    /**
     * Crea el controlador con su servicio principal.
     *
     * @param servicioAsteroide servicio para consultar asteroides
     */
    public AsteroideController(AsteroideService servicioAsteroide) {
        this.servicioAsteroide = servicioAsteroide;
    }

    /**
     * Muestra la pantalla principal con el formulario y el historial guardado.
     *
     * @param user usuario autenticado
     * @param model datos que se envían a la vista
     * @param session sesión HTTP para leer historial
     * @return nombre de la plantilla principal
     */
    @GetMapping("/")
    public String inicio(@AuthenticationPrincipal OAuth2User user, Model model, HttpSession session) {

        if (!model.containsAttribute("busqueda")) {
            model.addAttribute("busqueda", new FormularioRequest());
        }

        if (user != null) {
            model.addAttribute("given_name", user.getAttribute("given_name"));
            model.addAttribute("email", user.getAttribute("email"));
            model.addAttribute("picture", user.getAttribute("picture"));
        }

        // Pasar historial a la vista
        List<ConsultaHistorial> historial = (List<ConsultaHistorial>) session.getAttribute("historial");
        if (historial != null) {
            model.addAttribute("historial", historial);
        }

        return "index";
    }

    /**
     * Procesa la búsqueda de asteroides por fecha.
     *
     * @param formularioRequest datos del formulario
     * @param errores posibles errores de validación
     * @param user usuario autenticado
     * @param model datos de la vista
     * @param redirectAttributes datos temporales para la redirección
     * @param session sesión HTTP para guardar historial
     * @return vista de origen o redirección a resultados
     */
    @PostMapping("/buscar")
    public String buscar(
            @Valid @ModelAttribute("busqueda") FormularioRequest formularioRequest,
            BindingResult errores,
            @AuthenticationPrincipal OAuth2User user,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        if (errores.hasErrors()) {
            addUserAttributes(user, model);
            return "index";
        }

        LocalDate fecha = formularioRequest.getFecha();
        String fechaFormateada = fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try {
            List<AsteroideVista> listaAsteroides = servicioAsteroide.obtenerAsteroides(fecha);

            // Guardar en historial
            List<ConsultaHistorial> historial = (List<ConsultaHistorial>) session.getAttribute("historial");
            if (historial == null) {
                historial = new ArrayList<>();
            }
            historial.add(new ConsultaHistorial(fechaFormateada, listaAsteroides.size()));
            session.setAttribute("historial", historial);

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

    /**
     * Añade datos básicos del usuario al modelo si existe sesión iniciada.
     *
     * @param user usuario autenticado
     * @param model datos de la vista
     */
    private void addUserAttributes(OAuth2User user, Model model) {
        if (user != null) {
            model.addAttribute("given_name", user.getAttribute("given_name"));
            model.addAttribute("email", user.getAttribute("email"));
            model.addAttribute("picture", user.getAttribute("picture"));
        }
    }

    /**
     * Muestra la pantalla de resultados.
     *
     * @return nombre de la plantilla de resultados
     */
    @GetMapping("/resultados")
    public String resultados() {
        return "resultados";
    }

    /**
     * Muestra la pantalla de login.
     *
     * @return nombre de la plantilla de login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Devuelve en JSON los datos del usuario autenticado.
     *
     * @param user usuario autenticado
     * @return mapa con los atributos del perfil
     */
    @GetMapping("/profile")
    @ResponseBody
    public Map<String, Object> profile(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
}
