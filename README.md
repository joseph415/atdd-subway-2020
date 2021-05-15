<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <img alt="license" src="https://img.shields.io/github/license/woowacourse/atdd-subway-2020">
</p>

<br>

# 레벨2 최종 미션 - 지하철 노선도 애플리케이션

## 🎯 요구사항

- [프론트엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/frontend-mission.md)
- [백엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/backend-mission.md)

## 🤔 미션 제출 방법

- 진행 방식은 오프라인 코딩 테스트와 동일하다.
- 저장소를 Fork하여 자신의 저장소에서 미션 구현을 완료하고, Pull Request를 통해 미션을 제출한다.
- Pull Request를 보낸 후 woowa_course@woowahan.com로 메일을 발송한다.

## 😌 레벨2 최종 미션을 임하는 자세

레벨2 과정을 스스로의 힘으로 구현했다는 것을 증명하는데 집중해라

- [x] 기능 목록을 잘 작성한다.
- [ ] 자신이 구현한 기능에 대해 인수 테스트를 작성한다.
- [x] 자신이 구현한 코드에 대해 단위 테스트를 작성한다.
- [x] TDD 사이클 이력을 볼 수 있도록 커밋 로그를 잘 작성한다.
- [ ] 사용자 친화적인 예외처리를 고민한다.
- [ ] 읽기 좋은 코드를 만든다.

## 🚀 Getting Started

### Install

#### npm 설치

```
cd frontend
npm install
```

> `frontend` 디렉토리에서 수행해야 합니다.

### Usage

#### webpack server 구동

```
npm run dev
```

#### application 구동

```
./gradlew bootRun
```

<br>

## 기능 요구 사항

### 백앤드

- [ ] 경로 조회 응답 결과에 요금 정보 추가
    - 요금 계산 기준을 참조하여 기존 응답에 요금정보도 함꼐 reponse 되도록 기능을 추가

  #### 거리별 요금
    - [x] 기본운임(10㎞ 이내) : 기본운임 1,250원
    - [x] 이용 거리초과 시 추가운임 부과
        - 10km초과 ∼ 50km까지(5km마다 100원)
        - 50km초과 시 (8km마다 100원)

  #### 노선별 추가 요금
    - [x] 추가 요금이 있는 노선을 이용 할 경우 측정된 요금에 추가
        - ex) 900원 추가 요금이 있는 노선 8km 이용 시 1,250원 -> 2,150원
        - ex) 900원 추가 요금이 있는 노선 12km 이용 시 1,350원 -> 2,250원
    - [x] 가장 높은 추가 요금만 적용
        - ex) 0원, 500원, 900원의 추가 요금이 있는 노선들을 경유하여 8km 이용 시 1,250원 -> 2,150원

  #### 연령별 할인 정책
    - [x] 로그인 사용자의 경우 연령별 요금으로 계산
        - 청소년(13세 이상~19세 미만): 운임에서 350원을 공제한 금액의 20%할인
        - 어린이(6세 이상~ 13세 미만): 운임에서 350원을 공제한 금액의 50%할

- [ ] 가장 빠른 도착 경로 타입 추가
    - 특정 시간 기준 `가장 빠른 도착` 조회 기능을 추가

## 📝 License

This project is [MIT](https://github.com/woowacourse/atdd-subway-2020/blob/master/LICENSE.md)
licensed.
