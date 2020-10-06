package oss;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FieldWorkRepository extends PagingAndSortingRepository<FieldWork, Long> {

    List<FieldWork> findByOrderId(Long orderId);
}