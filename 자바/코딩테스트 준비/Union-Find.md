- Union-Find는 두 객체가 같은 집합에 속하는지, 다른 집합에 속하는지 찾는데 탁월한 [[_코테를 위한 알고리즘|알고리즘]]이다.
- Union은 두 객체를 같은 집합에 속하도록 합치는 과정이다.
- Find는 한 객체의 집합을 조회하는 과정이다.
```java
int[] parent = new int[N];

for(int i = 0; i < N; i++) {
	parent[i] = i;
}

public int find(int node) {
	if(parent[node] != node) {
		parent[node] = find(node);
	}
	return parent[node];
}

public void union(int node1, int node2) {
	int node1Parent = find(node1);
	int node2Parent = find(node2);

	if(node1Parent != node2Parent) {
		parent[node1Parent] = node2Parent;
	}
}
```
