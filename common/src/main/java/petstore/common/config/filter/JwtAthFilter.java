package petstore.common.config.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import petstore.common.advice.exception.SystemException;
import petstore.common.utils.JwtUtil;

@Component
@RequiredArgsConstructor
public class JwtAthFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);

    if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer")) {
      throw new SystemException("Unauthorized Request");
    }
    // extract token and return userId
    final String jwtToken = authHeader.substring(7);
    final String userId = jwtUtil.extractUsername(jwtToken);

    // Build Security Context
    if (StringUtils.isNoneBlank(userId)
        && SecurityContextHolder.getContext().getAuthentication() == null) {

      var userDetails = userDetailsService.loadUserByUsername(userId);
      final boolean isTokenValid = jwtUtil.validateToken(jwtToken, userDetails);

      if (isTokenValid) {
        var authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    return StringUtils.containsAny(url, "/ath", "/health");
  }
}
