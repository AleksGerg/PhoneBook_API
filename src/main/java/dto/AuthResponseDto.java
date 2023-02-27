package dto;

import groovy.transform.ToString;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
@Builder
public class AuthResponseDto {
    private String token;
}
