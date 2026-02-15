package com.example.asteroides.service;

import com.example.asteroides.client.NeowsClient;
import com.example.asteroides.dto.AsteroideResponse;
import com.example.asteroides.model.Asteroide;
import com.example.asteroides.model.AsteroideVista;
import com.example.asteroides.model.DatosAsteroide;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AsteroideService {

    private final NeowsClient neowsClient;

    public AsteroideService(NeowsClient neowsClient) {
        this.neowsClient = neowsClient;
    }

    public List<AsteroideVista> obtenerAsteroides(LocalDate fecha) {
        AsteroideResponse response = neowsClient.obtenerAsteroides(fecha);
        String fechaString = fecha.toString();

        List<Asteroide> listaDia = response.getNear_earth_objects().getOrDefault(fechaString, List.of());

        return listaDia.stream()
                .filter(asteroide -> asteroide.isPotentiallyHazardous()) // tu nombre actual
                .map(this::toView)
                .toList();
    }

    private AsteroideVista toView(Asteroide asteroide) {
        AsteroideVista asteroideVista = new AsteroideVista();
        asteroideVista.setId(asteroide.getId());
        asteroideVista.setName(asteroide.getName());
        asteroideVista.setPotentiallyHazardous(asteroide.isPotentiallyHazardous());

        // diámetro máx en metros
        double maxMeters = 0;
        if (asteroide.getEstimated_diameter() != null) {
            Map<String, Double> meters = asteroide.getEstimated_diameter().get("meters");
            if (meters != null && meters.get("estimated_diameter_max") != null) {
                maxMeters = meters.get("estimated_diameter_max");
            }
        }
        asteroideVista.setDiameterMaxMeters(maxMeters);

        // datos del primer acercamiento (si hay)
        String missKm = null;
        String velKmh = null;

        if (asteroide.getClose_approach_data() != null && !asteroide.getClose_approach_data().isEmpty()) {
            DatosAsteroide ca = asteroide.getClose_approach_data().get(0);

            if (ca.getMiss_distance() != null) {
                missKm = ca.getMiss_distance().get("kilometers");
            }
            if (ca.getRelative_velocity() != null) {
                velKmh = ca.getRelative_velocity().get("kilometers_per_hour");
            }
        }

        asteroideVista.setMissDistanceKm(missKm);
        asteroideVista.setVelocityKmh(velKmh);

        return asteroideVista;
    }
}
