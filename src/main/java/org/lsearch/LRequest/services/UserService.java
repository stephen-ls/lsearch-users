package org.lsearch.LRequest.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lsearch.LRequest.dto.user.PatchUserDto;
import org.lsearch.LRequest.dto.user.RegisterUserDto;
import org.lsearch.LRequest.exceptions.ResourceNotFoundException;
import org.lsearch.LRequest.models.User;
import org.lsearch.LRequest.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

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

    public User getUserById(UUID id) {
        var userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    public User getUserByProviderId(String providerId) {
        var userOpt = userRepository.findByProviderId(providerId);
        return userOpt.orElse(null);
    }

    public User register(RegisterUserDto userDto) {
        var providerId = userDto.getProviderId();
        var user = this.getUserByProviderId(providerId);
        if (user == null) {
            user = new User(providerId, userDto.getEmail(), getRandomName());
            return userRepository.save(user);
        }

        return user;
    }

    public User updateUser(UUID id, PatchUserDto userDto) {
        User user = this.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return this.updateUser(user, userDto.getName(), userDto.getEmail());
    }

    public User updateUser(User user, String name, String email) {
        if (name != null) user.setName(name);
        if (email != null) user.setName(name);
        userRepository.save(user);

        return user;
    }
}
