package ru.dubna.kts.models.auth.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookieInfoDto {
	private UUID id;

	private String username;
}
