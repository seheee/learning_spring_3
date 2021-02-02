package SpringBootTest;

import SpringBootTest.domain.Book;
import SpringBootTest.repository.BookRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;

@RunWith(SpringRunner.class)
@DataJpaTest
// @DataJpaTest : JPA 관련 테스트 설정만 로드함
// 내장형 데이터베이스 사용하여 실제 데이터베이스 사용하지 않고 테스트 데이터베이스로 테스트 가능
// 기본적으로 인메모리 임베디드 데이터베이스를 사용하며 @Entity 클래스를 스캔하여 스프링 데이터 JPA 저장소 구성
// 테스트 끝날때마다 자동으로 롤백

public class BookJpaTest {

    private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Book_Save_Test() {
        Book book = Book.builder()
                .title(BOOT_TEST_TITLE)
                .publishedAt(LocalDateTime.now())
                .build();
        testEntityManager.persist(book);
        assertThat(bookRepository.getOne(book.getIdx()), is(book));
    }

    @Test
    public void BookList_Save_Search_Test() {
        Book book1 = Book.builder()
                .title(BOOT_TEST_TITLE + "1")
                .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder()
                .title(BOOT_TEST_TITLE + "2")
                .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);

        Book book3 = Book.builder()
                .title(BOOT_TEST_TITLE + "3")
                .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book3);

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList, hasSize(3));
        assertThat(bookList, contains(book1, book2, book3));
    }

    @Test
    public void BookList_Save_Delete_Test() {
        Book book1 = Book.builder()
                .title(BOOT_TEST_TITLE + "1")
                .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder()
                .title(BOOT_TEST_TITLE + "2")
                .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);

        bookRepository.deleteAll();
        assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
    }
}
