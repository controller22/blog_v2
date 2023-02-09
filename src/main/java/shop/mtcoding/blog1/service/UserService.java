package shop.mtcoding.blog1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog1.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog1.handler.ex.CustomException;
import shop.mtcoding.blog1.model.User;
import shop.mtcoding.blog1.model.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {
        User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        if (sameUser != null) {
            throw new CustomException("동일한 username이 존재합니다");
        }
        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }
    }
}
