package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;

class SubwayPathTest {

    private Line line3;
    private SubwayPath subwayPath;

    @BeforeEach
    void setup() {
        line3 = TestObjectUtils.createLine(3L, "3호선", "ORANGE");
        line3.addLineStation(new LineStation(1L, null, 0, 0));
        LineStation lineStation6 = new LineStation(4L, 1L, 1, 2);
        LineStation lineStation7 = new LineStation(3L, 4L, 50, 2);
        line3.addLineStation(lineStation6);
        line3.addLineStation(lineStation7);

        List<LineStationEdge> lineStations = Lists.newArrayList(
                new LineStationEdge(lineStation6, line3.getId()),
                new LineStationEdge(lineStation7, line3.getId())
        );
        subwayPath = new SubwayPath(lineStations);
    }

    @Test
    void 거리에_따른_요금을_계산하는_기능() {
        int distance = subwayPath.calculateDistance();

        assertThat(subwayPath.calculateFare(distance)).isEqualTo(2150d);
    }
}