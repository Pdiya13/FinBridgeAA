package com.FinBridgeAA.Auth_service.Controller;

import com.FinBridgeAA.Auth_service.DTO.TokenInterospectResponse;
import com.FinBridgeAA.Auth_service.Services.AuthService;
import com.FinBridgeAA.Auth_service.Services.JwtService;
import com.FinBridgeAA.Auth_service.Services.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/token")
public class TokenInterospectController {
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthService authService;
    private final JwtService jwtService;
    public TokenInterospectController(TokenBlacklistService tokenBlacklistService, AuthService authService, JwtService jwtService) {
        this.tokenBlacklistService = tokenBlacklistService;
        this.authService = authService;
        this.jwtService =  jwtService;
    }
    @PostMapping("/interospect")
    public ResponseEntity<TokenInterospectResponse> interospect(@RequestHeader("Authorization") String authHeader) {
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.ok(
                    new TokenInterospectResponse(false, null, null, "INVALID_HEADER")
            );
        }
        String token = authHeader.substring(7);
        if(tokenBlacklistService.isBlacklisted(token)){
            return ResponseEntity.ok(
                    new TokenInterospectResponse(true, null, null, "TOKEN_BLACKLISTED")
            );
        }
        return ResponseEntity.ok(
                new TokenInterospectResponse(
                        true,
                        jwtService.extractUserId(token),
                        jwtService.extractRole(token),
                        null
                )
        );
    }
}
