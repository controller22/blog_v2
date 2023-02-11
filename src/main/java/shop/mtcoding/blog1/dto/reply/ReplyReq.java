package shop.mtcoding.blog1.dto.reply;

import lombok.Getter;
import lombok.Setter;

public class ReplyReq {
    
    @Setter
    @Getter
    public static class ReplySaveReqDto {
        private String comment;
        private Integer boardId;
    }
    
}
