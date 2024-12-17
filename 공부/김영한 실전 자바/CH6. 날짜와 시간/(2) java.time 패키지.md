![[Pasted image 20241218023830.png]]
# Local~
- LocalDate : 날짜(연 월 일)
- LocalTime : 시간(시 분초 )
- LocalDateTime : 날짜시간(연 월 일 시 분 초)
왜 Local일까? -> 세계 시간대를 고려하지 않고(타임존) 특정 지역의 날짜와 시간만을 고려하기 때문이다.
# 시간대를 고려한 것들
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