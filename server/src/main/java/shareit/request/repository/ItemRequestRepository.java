package shareit.request.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shareit.request.model.ItemRequest;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByRequestorIdOrderByCreatedAsc(Long userId);

    Page<ItemRequest> findAll(Pageable pageable);
}
