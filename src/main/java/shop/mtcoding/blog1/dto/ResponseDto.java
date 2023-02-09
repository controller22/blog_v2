package shop.mtcoding.blog1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ResponseDto<T> {
    private int code;
    private String msg;
    private T data;
}
