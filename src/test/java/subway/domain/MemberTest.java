package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberTest {
    @Autowired
    MemberRepository members;

    @Autowired
    FavoriteRepository favorites;

    @Test
    void save() {
        Member expected = new Member("jason");
        expected.addFavorite(favorites.save(new Favorite()));
        Member actual = members.save(expected);
        members.flush(); // transaction commit
    }

}