![[Pasted image 20241228133852.png]]
- `Collection` 인터페이스 부터 `Iterable`을 `extends`하기 때문에, 모든 컬렉션은 이 순회방법을 제공하도록 구현되어 있다.
- `Map`들은 key-value 구조이기 때문에 같은 방법으로 순회할 수는 없다.
	- 단, `keySet()`, `values()`를 통해서 키와 값은 컬렉션으로 제공되기 때문에, 편하게 순회할 수 있다.
```java
// 리스트
List<Integer> list = new ArrayList<>();  
for (Integer i : list) {  
    System.out.println(i);  
}  

// 셋
Set<Integer> set = new HashSet<>();  
for (Integer i : set) {  
    System.out.println(i);  
}  

// 맵
Map<String, Integer> map = new HashMap<>();  
for (String i : map.keySet()) {  
    System.out.println(i);  
}  
for (Integer i : map.values()) {  
    System.out.println(i);  
}  
for (Map.Entry<String, Integer> entry : map.entrySet()) {  
    System.out.println(entry.getKey() + " " + entry.getValue());  
}

// ...
```