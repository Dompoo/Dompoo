![[Pasted image 20241218023830.png]]
# Local~
- LocalDate : 날짜(연 월 일)
- LocalTime : 시간(시 분초 )
- LocalDateTime : 날짜시간(연 월 일 시 분 초)
왜 Local일까? -> 세계 시간대를 고려하지 않고(타임존) 특정 지역의 날짜와 시간만을 고려하기 때문이다.
# Zoned, Offset
- ZonedDateTime : LocalDateTime에서 타임존을 추가한 것
- OffsetDateTime : LocalDateTime에서 UTC에서의 시간차이(오프셋)를 추가한 것(UTC+?)
# Year, Month, Day
- Year : 연
- Month : 월
- YearMonth : 연월
- MonthDay : 월일
- DayOfWeek : 월화수목금토일
# Instant
- Instant : 1970년 1월 1일 0시 0분 0초 UTC 기준으로 경과한 밀리초
# Period, Duration
- period : 두 날짜 사이의 간격(연 월 일)
- Duration : 두 시간 사이의 간격(시 분 초)
# 핵심 인터페이스
- Temporal : 어떠한 한 시각을 나타낸다.
	- 구현체로 LocalDateTime, ZonedDateTime, Instant 등이 포함된다.
	- 날짜와 시간을 조작하는 기능이 포함된다.
- TemporalAccesor : Temporal의 부모 인터페이스이다.
	- 날짜와 시간을 읽는 기능이 포함된다.
- TemporalAmount : 두 시각 사이 양, 시간을 나타낸다.
	- 1월 1일은 시각, 1월 1일 부터 1월 30일 사이 `30일`은 시간
	- 구현체로 Period, Duration이 포함된다.
	- 날짜와 시간 객체를 조정하여 그 간격을 읽거나 조작할 수 있다.
- TemporalUnit : 날짜와 시간을 측정하는 단위이다.
	- 60초 = 1분, 60분 = 1시간, 3600초 = 1시간 등으로 생각하면 된다.
	- 구현체로 열거형인 ChronoUnit이 있다.
	- NANOS, SECONDS, HOURS, DAYS, MONTHS, ...
- TemporalField : 날짜와 시간을 나타낼 때의 한 부분의 단위이다.
	- 12시 24분 30초의 `12시`, `24분`, `30초` 각각의 TemporalField라고 생각하면 된다.
	- 구현체로 열거형인 ChronoField가 있다.
	- YEAR, MONTH_OF_YEAR, DAY_OF_MONTH, DAY_OF_WEEK, ...