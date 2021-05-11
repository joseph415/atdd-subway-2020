package wooteco.subway.maps.line.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {
    @Override
    List<Line> findAll();

    @Query("select n from Line n where n.id in (:ids)")
    List<Line> findLinesIns(List<Long> ids);
}
