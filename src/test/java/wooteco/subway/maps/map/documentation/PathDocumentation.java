package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static wooteco.subway.maps.map.domain.PathType.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.line.dto.LineStationResponse;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {
    @Autowired
    private MapController mapController;
    @MockBean
    private MapService mapService;

    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;

    @BeforeEach
    public void setUp(
            WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        station1 = TestObjectUtils.createStation(1L, "교대역");
        station2 = TestObjectUtils.createStation(2L, "강남역역");
        station3 = TestObjectUtils.createStation(3L, "양재역");
        station4 = TestObjectUtils.createStation(4L, "남부터미널역");
    }

    @Test
    void findPath() {
        station1 = TestObjectUtils.createStation(1L, "교대역");
        station3 = TestObjectUtils.createStation(3L, "양재역");
        station4 = TestObjectUtils.createStation(4L, "남부터미널역");
        PathResponse pathResponse = new PathResponse(
                Arrays.asList(StationResponse.mockResponse(station1),
                        StationResponse.mockResponse(station3),
                        StationResponse.mockResponse(station4)),
                11, 4, 1350d);

        when(mapService.findPath(1L, 3L, DISTANCE)).thenReturn(pathResponse);

        given().log().all().
                param("source", 1L).
                param("target", 3L).
                param("type", DISTANCE).
                when().
                get("/paths").
                then().
                log().all().
                apply(document("paths/findPath",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("source").description("출발역 아이디"),
                                parameterWithName("target").description("도착역 아이디"),
                                parameterWithName("type").description("경로 찾는 기준")
                        ),
                        responseFields(
                                fieldWithPath("stations[].id").type(JsonFieldType.NUMBER)
                                        .description("역 아이디"),
                                fieldWithPath("stations[].name").type(JsonFieldType.STRING)
                                        .description("역 이름"),
                                fieldWithPath("stations[].createdDate").type(
                                        JsonFieldType.STRING)
                                        .description("역 만들어진 시간"),
                                fieldWithPath("stations[].modifiedDate").type(
                                        JsonFieldType.STRING)
                                        .description("역 수정된 시간"),
                                fieldWithPath("duration").type(JsonFieldType.NUMBER)
                                        .description("시간 정보 / 최단 거리요청 시 의미 최단시간이 아님 "),
                                fieldWithPath("distance").type(JsonFieldType.NUMBER)
                                        .description("거리 정보 / 최단 시간요청 시 의미 최단거리가 아님"),
                                fieldWithPath("fare").type(JsonFieldType.NUMBER)
                                        .description("요금")))).
                extract();
    }

    @Test
    void findMap() {
        station1 = TestObjectUtils.createStation(1L, "교대역");
        station2 = TestObjectUtils.createStation(2L, "강남역역");
        station3 = TestObjectUtils.createStation(3L, "양재역");
        station4 = TestObjectUtils.createStation(4L, "남부터미널역");

        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN", 0);
        line1.addLineStation(new LineStation(1L, null, 0, 0));
        line1.addLineStation(new LineStation(2L, 1L, 2, 2));

        Line line2 = TestObjectUtils.createLine(2L, "신분당선", "RED", 500);
        line2.addLineStation(new LineStation(2L, null, 0, 0));
        line2.addLineStation(new LineStation(3L, 2L, 2, 1));

        Line line3 = TestObjectUtils.createLine(3L, "3호선", "ORANGE", 900);
        line3.addLineStation(new LineStation(1L, null, 0, 0));
        line3.addLineStation(new LineStation(4L, 1L, 1, 2));
        line3.addLineStation(new LineStation(3L, 4L, 2, 2));

        MapResponse mapResponse = new MapResponse(
                Arrays.asList(LineResponse.mockResponse(line1, Arrays.asList(
                        LineStationResponse.of(line1.getId(), new LineStation(1L, null, 0, 0),
                                StationResponse.mockResponse(station1)),
                        LineStationResponse.of(line1.getId(), new LineStation(2L, 1L, 2, 2),
                                StationResponse.mockResponse(station2))
                        )),
                        LineResponse.mockResponse(line2, Arrays.asList(
                                LineStationResponse.of(line2.getId(),
                                        new LineStation(2L, null, 0, 0),
                                        StationResponse.mockResponse(station2)),
                                LineStationResponse.of(line2.getId(), new LineStation(3L, 2L, 2, 1),
                                        StationResponse.mockResponse(station3))
                        )),
                        LineResponse.mockResponse(line3, Arrays.asList(
                                LineStationResponse.of(line3.getId(),
                                        new LineStation(1L, null, 0, 0),
                                        StationResponse.mockResponse(station1)),
                                LineStationResponse.of(line3.getId(), new LineStation(4L, 1L, 1, 2),
                                        StationResponse.mockResponse(station4)),
                                LineStationResponse.of(line3.getId(), new LineStation(3L, 4L, 2, 2),
                                        StationResponse.mockResponse(station4))
                                )
                        )));

        when(mapService.findMap()).thenReturn(mapResponse);

        given().log().all().
                when().
                get("/maps").
                then().
                log().all().
                apply(document("paths/findMap",
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("lineResponses[].id").type(JsonFieldType.NUMBER)
                                        .description("호선 아이디"),
                                fieldWithPath("lineResponses[].name").type(JsonFieldType.STRING)
                                        .description("호선 이름"),
                                fieldWithPath("lineResponses[].color").type(JsonFieldType.STRING)
                                        .description("호선 색깔"),
                                fieldWithPath("lineResponses[].startTime").type(
                                        JsonFieldType.STRING)
                                        .description("호선 첫차 시간"),
                                fieldWithPath("lineResponses[].endTime").type(JsonFieldType.STRING)
                                        .description("호선 막차 시간"),
                                fieldWithPath("lineResponses[].intervalTime").type(
                                        JsonFieldType.NUMBER)
                                        .description("배차 간격"),
                                fieldWithPath("lineResponses[].stations").type(JsonFieldType.ARRAY)
                                        .description("호선의 역 연결 정보를 담은 배열"),
                                fieldWithPath("lineResponses[].stations[].station").type(
                                        JsonFieldType.OBJECT).description("역 정보를 담은 객체"),
                                fieldWithPath("lineResponses[].stations[].station.id").type(
                                        JsonFieldType.NUMBER).description("역 아이디"),
                                fieldWithPath("lineResponses[].stations[].station.name").type(
                                        JsonFieldType.STRING).description("역 이름"),
                                fieldWithPath(
                                        "lineResponses[].stations[].station.createdDate").type(
                                        JsonFieldType.STRING).description("역 생성 시간"),
                                fieldWithPath(
                                        "lineResponses[].stations[].station.modifiedDate").type(
                                        JsonFieldType.STRING).description("역 수정 시간"),
                                fieldWithPath("lineResponses[].stations[].preStationId").type(
                                        JsonFieldType.NUMBER).description("이전 역 아이디").optional(),
                                fieldWithPath("lineResponses[].stations[].lineId").type(
                                        JsonFieldType.NUMBER).description("호선 역 아이디"),
                                fieldWithPath("lineResponses[].stations[].distance").type(
                                        JsonFieldType.NUMBER).description("역 사이의 거리"),
                                fieldWithPath("lineResponses[].stations[].duration").type(
                                        JsonFieldType.NUMBER).description("역 사이의 시간"),
                                fieldWithPath("lineResponses[].createdDate").type(
                                        JsonFieldType.STRING)
                                        .description("생성된 시간"),
                                fieldWithPath("lineResponses[].modifiedDate").type(
                                        JsonFieldType.STRING).description("수정된 시간")))).
                extract();
    }
}
