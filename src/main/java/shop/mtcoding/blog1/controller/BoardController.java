package shop.mtcoding.blog1.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog1.dto.ResponseDto;
import shop.mtcoding.blog1.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog1.dto.board.BoardReq.BoardUpdateReqDto;
import shop.mtcoding.blog1.handler.ex.CustomApiException;
import shop.mtcoding.blog1.handler.ex.CustomException;
import shop.mtcoding.blog1.model.Board;
import shop.mtcoding.blog1.model.BoardRepository;
import shop.mtcoding.blog1.model.ReplyRepository;
import shop.mtcoding.blog1.model.User;
import shop.mtcoding.blog1.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpSession session;

    @GetMapping({ "/", "/board" })
    public String main(Model model) {
        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("boardDto", boardRepository.findByIdWithUser(id));
        model.addAttribute("replyDtos", replyRepository.findByBoardIdWithUser(id));
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto BoardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        if (BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title??? ??????????????????");
        }
        if (BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content??? ??????????????????");
        }
        if (BoardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title??? ????????? 100??? ???????????? ?????????");
        }

        boardService.?????????(BoardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "???????????????", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        boardService.???????????????(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "????????????", null), HttpStatus.OK);
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomException("?????? ???????????? ????????? ??? ????????????");
        }
        if (boardPS.getUserId() != principal.getId()) {
            throw new CustomException("???????????? ????????? ????????? ????????????", HttpStatus.FORBIDDEN);
        }
        model.addAttribute("board", boardPS);
        return "board/updateForm";
    }

    @PutMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateReqDto boardUpdateReqDto, HttpServletResponse response) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("????????? ?????? ???????????????", HttpStatus.UNAUTHORIZED);
        }
        if (boardUpdateReqDto.getTitle() == null || boardUpdateReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title??? ??????????????????");
        }
        if (boardUpdateReqDto.getContent() == null || boardUpdateReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content??? ??????????????????");
        }
        boardService.???????????????(id, boardUpdateReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "?????????????????????", null), HttpStatus.OK);
    }
}
