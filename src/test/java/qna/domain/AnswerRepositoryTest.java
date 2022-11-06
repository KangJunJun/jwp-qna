package qna.domain;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    public Answer answerTest;
    public Question question;

    @BeforeEach
    void setUp() {
        User user = userRepository.save(UserTest.JAVAJIGI);
        question = questionRepository.save(new Question("title", "contents").writeBy(user));
        answerTest = new Answer(user, question, "Answers Contents1");
    }

    @Test
    @DisplayName("Answer 저장한 엔티티의 id로 조회한 경우 동일성 테스트")
    void find() {
        Answer saveAnswer = answerRepository.save(answerTest);
        Answer findAnswer = answerRepository.findById(saveAnswer.getId()).orElse(null);
        assertAll(
                () -> assertEquals(saveAnswer.getQuestion(), answerTest.getQuestion()),
                () -> assertEquals(saveAnswer.getWriter(), answerTest.getWriter()),
                () -> assertEquals(saveAnswer, findAnswer)
        );
    }

    @DisplayName("findByQuestionIdAndDeletedFalse 검증")
    @Test
    void findByQuestionIdAndDeletedFalseTest() {
        Answer saveAnswer = answerRepository.save(answerTest);
        List<Answer> answers1 = answerRepository.findByQuestionAndDeletedFalse(question);
        saveAnswer.setDeleted(true);
        List<Answer> answers2 = answerRepository.findByQuestionAndDeletedFalse(question);
        assertAll(
                () -> assertThat(answers1).hasSize(1),
                () -> assertThat(answers2).isEmpty()
        );
    }


}
