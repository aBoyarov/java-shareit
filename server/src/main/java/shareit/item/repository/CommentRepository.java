package shareit.item.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shareit.item.model.Comment;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByItemId(long itemId);
}
