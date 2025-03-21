package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.JwtResponse;
import dev.shetel.kushik.dto.LoginRequest;
import dev.shetel.kushik.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token).build();
    }
}