package org.lsearch.LRequest.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lsearch.LRequest.dto.user.RegisterUserDto;
import org.lsearch.LRequest.enums.UserRole;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@Service
@ApplicationScope
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ArrayList<String> names = new ArrayList<>(
            Arrays.asList(
                    "Core",
                    "Neuron",
                    "Mindscape",
                    "Cogni",
                    "Datawave",
                    "Axon",
                    "DeepMind",
                    "Evolve",
                    "Alpha",
                    "Omega",
                    "Synthetica",
                    "Infini",
                    "Sentient",
                    "Optima",
                    "MindForge",
                    "Nerve",
                    "Intuit",
                    "Fusion",
                    "Spark",
                    "MetaNet",
                    "Cortex",
                    "Prime",
                    "QuantumNet",
                    "Intellecta",
                    "Symmetry",
                    "MatrixLab",
                    "Synaptic",
                    "Horizon",
                    "NexusAI",
                    "Eureka",
                    "OmniMind",
                    "Virtuo",
                    "HyperLoop",
                    "Genesis",
                    "LogicWave",
                    "Converge",
                    "Axial",
                    "Echelon",
                    "EchoMind",
                    "Ethos",
                    "NeuroLink",
                    "Spectra",
                    "AetherNet",
                    "Hyperion",
                    "CoreLink",
                    "Chronos",
                    "Wisdom",
                    "ArkAI",
                    "Orbit"
            )
    );;
    private final Random random = new Random();

    private String getRandomName() {
        int index = random.nextInt(names.size());
        long timestamp = System.currentTimeMillis();
        return names.get(index) + "." + timestamp;
    }

    public boolean register(RegisterUserDto userDto) {
        var providerId = userDto.getProviderId();
        var user = userRepository.getUserByProviderId(providerId);
        if (user == null) {
            int result = userRepository.createUser(providerId, getRandomName(), userDto.getEmail(), UserRole.USER);
            return result > 0;
        }

        return true;
    }
}
