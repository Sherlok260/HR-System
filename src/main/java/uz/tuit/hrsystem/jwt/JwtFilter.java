package uz.tuit.hrsystem.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.tuit.hrsystem.entity.Token;
import uz.tuit.hrsystem.repository.EmployeeRepository;
import uz.tuit.hrsystem.repository.TokenRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    public static String getEmail;
    public static String getToken;
    public static String getRole;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            getToken = token;

            Optional<Token> token1 = tokenRepository.findByToken(token);

            if (token1.isPresent() && jwtProvider.validateToken(token) && token1.get().getLevel().equals("valid")) {
                String email = jwtProvider.getEmailFromToken(token);
                getEmail = email;
                getRole = employeeRepository.findByUsername(email).get().getRoles().name();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
