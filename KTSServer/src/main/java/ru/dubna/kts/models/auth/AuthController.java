package ru.dubna.kts.models.auth;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.dubna.kts.config.security.CookieAuthenticationFilter;
import ru.dubna.kts.cookie.CookieUtils;
import ru.dubna.kts.exceptions.dtos.BadRequestExceptionPayload;
import ru.dubna.kts.exceptions.dtos.DefaultExceptionPayload;
import ru.dubna.kts.models.auth.dtos.CookieInfoDto;
import ru.dubna.kts.models.auth.dtos.CredentialsDto;
import ru.dubna.kts.models.auth.service.AuthService;
import ru.dubna.kts.models.user.dtos.UserOutputDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Авторизация/Регистрация")
@ApiResponse(responseCode = "400", content = {
		@Content(schema = @Schema(implementation = BadRequestExceptionPayload.class)) })
public class AuthController {
	private final AuthService authService;
	private final CookieUtils cookieUtils;

	@Autowired
	public AuthController(CookieUtils cookieUtils, @Qualifier("proxyAuthService") AuthService authService) {
		this.cookieUtils = cookieUtils;
		this.authService = authService;
	}

	private void createCookie(CookieInfoDto cookieInfo, HttpServletResponse servletResponse) {
		Cookie authCookie = new Cookie(CookieAuthenticationFilter.cookieName, cookieUtils.createToken(cookieInfo));
		authCookie.setHttpOnly(true);
		authCookie.setSecure(true);
		authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
		authCookie.setPath("/");

		servletResponse.addCookie(authCookie);
	}

	@PostMapping("/login")
	@Operation(summary = "Авторизация пользователя")
	@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CookieInfoDto.class)) })
	@ApiResponse(responseCode = "401", content = {
			@Content(schema = @Schema(implementation = DefaultExceptionPayload.class)) })
	public CookieInfoDto signIn(@Valid @RequestBody CredentialsDto credentials, HttpServletResponse servletResponse) {
		CookieInfoDto cookieInfo = authService.signIn(credentials);
		createCookie(cookieInfo, servletResponse);
		return cookieInfo;
	}

	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Регистрация пользователя")
	@ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserOutputDto.class)) })
	public UserOutputDto signUp(@Valid @RequestBody CredentialsDto credentialsDto) {
		return authService.signUp(credentialsDto);
	}
}
