package com.web;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

    private final String boardTestTitle = "test";
    private final String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void init() {
        User user = userRepository.save(User.builder()
        .name("sehee")
        .password("test")
        .email(email)
        .createdDate(LocalDateTime.now())
        .build());

        boardRepository.save(Board.builder()
        .title(boardTestTitle)
        .subTitle("sub title")
        .content("content")
        .boardType(BoardType.free)
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .user(user)
        .build());
    }

    @Test
    public void save_test() {
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("sehee"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getEmail(), is("test@gmail.com"));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getTitle(), is(boardTestTitle));
        assertThat(board.getSubTitle(), is("sub title"));
        assertThat(board.getContent(), is("content"));
        assertThat(board.getBoardType(), is(BoardType.free));
    }
}
