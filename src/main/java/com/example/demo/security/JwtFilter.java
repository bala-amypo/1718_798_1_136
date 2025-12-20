// package com.example.demo.security;

// import com.example.demo.service.UserService;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import java.io.IOException;

// @Component
// public class JwtFilter extends OncePerRequestFilter {
    
//     private final JwtUtil jwtUtil;
//     private final UserDetailsService userDetailsService;
//     private final UserService userService;
    
//     public JwtFilter(JwtUtil jwtUtil, 
//                      UserDetailsService userDetailsService,
//                      UserService userService) {
//         this.jwtUtil = jwtUtil;
//         this.userDetailsService = userDetailsService;
//         this.userService = userService;
//     }
    
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, 
//                                    HttpServletResponse response, 
//                                    FilterChain chain)
//             throws ServletException, IOException {
        
//         try {
//             final String authorizationHeader = request.getHeader("Authorization");
            
//             String jwt = null;
//             String email = null;
            
//             if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                 jwt = authorizationHeader.substring(7);
//                 email = jwtUtil.getEmail(jwt);
//             }
            
//             if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                 if (jwtUtil.validateToken(jwt)) {
//                     // Load user details from database
//                     UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    
//                     // Create authentication token
//                     UsernamePasswordAuthenticationToken authToken = 
//                         new UsernamePasswordAuthenticationToken(
//                             userDetails, 
//                             null, 
//                             userDetails.getAuthorities()
//                         );
                    
//                     authToken.setDetails(userDetails);
                    
//                     // Set authentication in context
//                     SecurityContextHolder.getContext().setAuthentication(authToken);
                    
//                     // Log for debugging
//                     logger.debug("Authenticated user: " + email);
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Cannot set user authentication: " + e.getMessage());
//         }
        
//         chain.doFilter(request, response);
//     }
    
//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) {
//         String path = request.getServletPath();
//         // Don't filter authentication endpoints and public endpoints
//         return path.startsWith("/auth/") || 
//                path.startsWith("/swagger-ui/") || 
//                path.startsWith("/v3/api-docs/") ||
//                path.equals("/status") ||
//                path.equals("/error") ||
//                path.equals("/favicon.ico");
//     }
// }