- 이더리움은 `탈중앙화 서비스`를 만드는데 사용된다.
- `Smart Contract`를 베이스로 작동한다.
	- `Smart Contract`는 **특정 조건을 만족했을 때 자동으로 실행되는 코드**이다.
- 이더리움은 Turing Complete한 기계를 분산하여 저장한다.
- 즉, 세계적으로 분산되어 있는 `Smart Contract`를 실행할 수 있는 컴퓨터 인프라라고 볼 수 있다.
# Bitcoin VS Ethereum
### 공통점
- 둘 다 참여자들을 잇는 P2P 네트워크이다.
- 서명이나 해쉬 같은 암호화 기법을 사용한다.
- 디지털화폐를 사용한다.
### 차이점
- 이더리움의 목적은 지불 시스템이 아니다.
- 이더리움의 화폐인 이더는 이더리움이라는 컴퓨터를 사용하는 비용이다.
- 코드(스크립트) 차이점
	- 비트코인의 코드
		- 지불 조건을 평가하는데에만 사용된다.
		- 안전성과 효율성을 위해 의도적으로 단순하게 설계되었다.
	- 이더리움의 코드
		- 이더리움은 범용적으로 프로그래밍 가능한 블록체인
		- Turing Complete하게 설계되어, 임의 복잡도의 코드를 실행할 수 있다.
	- Turing Complete하면 무한대로 실행될 수 있는 것 아닌가? -> Gas를 통해 해결한다.
- 상태의 차이점
	- 둘 다 **분산된 상태 머신**으로 작동한다.
	- 둘 다 트랜잭션을 통해 상태가 바뀐다.
	- 하지만 무엇을 상태로 갖는지가 매우 다르다.
	- 비트코인의 상태
		- 비트코인의 상태는 해당 비트코인을 누가 소유한지를 추적한다.
		- 즉, 트랜잭션은 비트코인의 소유권 변경을 나타낸다.
	- 이더리움의 상태
		- 일반적인 데이터 저장소의 상태를 추적한다.
		- 키-값 형태로 저장 가능한 임의의 데이터를 저장하고 그 상태 전이를 추적한다.
	- 이더리움의 메모리
		- 따라서 이더리움의 메모리는 코드 뿐만 아니라 데이터를 저장할 수 있다.
		- 트랜잭션이나 `Smart Contract`의 실행 과정에서 이 데이터가 수정될 수 있으며, 이 변화를 블록체인에 기록하며 추적한다.
# Gas
- 이더리움의 코드는 Turing Complete하기 때문에 얼마나 오래, 얼마나 리소스를 많이 잡아먹으며 실행되는지 알 수 없다.
	- 심지어는 영원히 실행될 수 있다.
	- 이 문제를 해결하기 위하여 `Gas`가 도입되었다.
- EVM은 `Smart Contract`를 실행할 때, 각 명령어의 비용(`Gas`)를 세밀하게 추적한다.
	- 각 명령어는 정해진 값의 비용을 가진다.
- 각 트랜잭션에는 `Gas`한도가 정해져 있으며, 만약 이 한도를 초과하면 실행을 중지한다.
- 이를 통해 불필요하게 자원을 낭비하는 `Smart Contract`의 실행을 방지한다.
- `Gas`는 EOA가 트랜잭션에 포함시켜 CA에 넘겨주는 형식이다.
- CA가 `Gas`를 사용하며 트랜잭션을 실행시킨 후, 남은 `Gas`는 EOA에게 다시 돌려준다.
	- 이때, 만약 `Gas`를 다 사용해서 더 이상 실행시키지 못한다면, 해당 상태 변화는 롤백된다.
	- 또한 네트워크 사용료 개념으로 EOA에게 `Gas`를 돌려주지 않는다.
- 트랜잭션 가격을 계산해보자.
	- A가 B에게 1ETH를 보내고 싶다고 하자.
	- A는 `maxPriorityFeePerGas`를 10wei로 설정했다.
	- 현재 이더리움 네트워크에서 `BaseFeePerGas`는 190wei다.
	- 따라서 1가스당 200wei가 필요하다.
	- 이때, 전송이 21000가스가 필요하다고 가정하면, 21000가스 필요 X 200wei = 4,200,000wei 이다.
	- A는 1ETH + 0.0042ETH를 내야한다.
	- B는 1ETH를 받는다.
	- 중간 Validator는 21000가스 계산 X 10wei = 210,000wei 를 받는다.
	- 이때 BaseFee는 소각된다. (21000가스 X 190wei = 3,990,000wei) <- 디플레이션 방지
# DApp
- DApp은 필수적인 두 컴포넌트를 지닌다.
	- 블록체인 상의 `Smart Contract`
	- 해당 `Smart contract`를 사용하기 쉽게 하는 UI
- `Smart Contract`를 실행하기 위해서는 이더가 필요하므로 사용자 부담이 있다.
- DApp은 기존 중앙화 앱에 비하여 다음과 같은 장점을 지닌다.
	- 복원력 : 일부 노드가 다운되더라도 나머지 노드가 최신 상태를 반영하고 있으므로 지속적으로 운영할 수 있다.
	- 투명성 : 코드가 전체 공개 되므로, 기능을 신뢰할 수 있다.
	- 검열 저항성 : 사용자가 중앙 통제 없이 상호작용 가능하다. 또한 일단 배포되면, 그 누구도 수정할 수 없다.
- DApp의 Backend
	- `Smart Contract`이다.
	- 비즈니스 로직과 관련 상태를 저장한다.
	- `Smart Contract`의 로직은 비용이 많이 드므로, 최소화해야 한다. (정확히는 `Gas`소비가 많으면 안된다.)
		- 이를 극복하기 위해 일부 DApp은 신뢰할 수 있는 써드파티 데이터소스, 오프체인 계산등을 활용하기도 한다.
- DApp의 FrontEnd
	- HTML, CSS, JS 등으로 만든다.
	- Web3.js 등의 라이브러리를 통해 이더리움과 연결한다.
	- MetaMask 등을 통하여 이더리움과 상호작용한다. (브라우저로 월렛 제공)
- DApp의 DB
	- 높은 `Gas` 비용 때문에 보통 off-chain 데이터 스토리지를 사용한다.
		- 중앙화 유형 : AWS 등
		- 탈중앙화 유형(P2P 네트워크 기반) : IPFS, 이더리움의 Swarm
- DApp의 Message Comunication
	- P2P 네트워크를 통하여 이루어진다.
		- Whisper
- DApp의 DNS
	- Ethereum Name Service(ENS)
	- 탈중앙화를 위한 탈중앙화로, NS를 제공해준다.
- 한번 만들어보자.
	1. 애플리케이션 코드를 Swarm이나 IPFS에 모두 넣는다.
		- Swarm 노드를 준비한다.
		- Swarm 노드에 코드를 배포한다.
	2. ENS를 통해 해당 Swarm 노드에 이름을 부여한다.
	3. 사용자가 사용한다.
		- 브라우저에 도메인명을 입력한다.
		- ENS가 리졸버 주소를 반환한다.
		- 리졸버 주소를 통해 Swarm 해시를 받는다.
		- Swarm 해시를 통해 Swarm 노드에 저장된 DApp 파일의 해시를 받는다.
		- 사용한다.
# 계정
- EOA(Externally Owned Account)
	- 실제 사용자의 계정이다.
	- 개인키에 의해 제어된다.
	- 값 전달(EOA -> EOA), `Smart Contract`호출(EOA -> CA) 등이 가능하다.
	- 실행 체인의 시작점이 될 수 있으므로 능동적이다.
	- 공개키와 비공개키에 의해 제어된다.
		- 비공개키로 해당 계정의 소유권을 주장할 수 있다.
		- 비공개키로 트랜잭션에 서명할 수 있다.
		- 공개키는 비공개키를 통해 생성되며, 서명검증에 사용되며, 계정주소로도 사용된다.
- CA(Contract Account)
	- `Smart Contract`가 실행되는 계정이다.
	- `Smart Contract`의 내용에 따라 해당 계정 내부의 상태데이터가 변화될 수 있다.
	- `Smart Contract`에 의해 트랜잭션을 자동으로 실행하거나, 다른 계정과 상호작용할 수 있다.
	- 혼자 실행될 수 없으므로 수동적이다.
![[Pasted image 20241207220052.png]]
# 계정 상태
- Nonce
	- 각 계정에서 발생한 트랜잭션의 순서 번호이다.
		- EOA의 경우 해당 계정에서 보낸 트랜잭션의 개수
		- CA의 경우 해당 계정에서 생성된 계약의 개수
	- 이를 통해 동일한 Nonce의 트랜잭션은 한번만 실행될 수 있도록 하여, `Replay Attack`등을 막는다.
- Balance : wei 잔고를 나타낸다.
- Storage Root
	- CA의 상태 저장소의 루트 해쉬값이다.
	- CA는 특정 데이터를 저장할 수 있기 때문에 필요하다.
	- CA에서 트랜잭션이 발생하면 이 변경 사항은 `Storage Root`를 통해서 추적된다.
- CodeHash
	- 각 CA에서 실행되는 EVM 코드의 해시값이다.
- World State
	- 이더리움 네트워크의 전역 상태를 나타낸다.
	- 이더리움 네트워크의 모든 계정의 계정주소-계정상태를 저장한다.
# 트랜잭션
- 트랜잭션에 의해 World State는 다음 State로 넘어가게 된다.
- 이렇게 하기 위해 어떤 노드든 트랜잭션은 BroadCast 할 수 있다.
	- Validator에서 실행된 후 (EVM 실행)
	- 이 실행 결과(상태변화)가 다른 노드로 전파된다.
	- 이 상태변화가 Validated Block에 포함되어야 World State에 기록된다.
- 각 트랜잭션은 아래 요소들을 가지고 있다.
	- from : 트랜잭션에 서명하고 전송한 계정의 주소(무조건 EOA)
	- to : 트랜잭션의 수신 주소
		- EOA일 경우 이더 전송
		- CA일 경우 `Smart Contract` 호출
	- signature : 발신자의 서명
	- nonce : 발신 계정의 트랜잭션 번호(카운터)
	- value : 전송하려는 이더의 양(wei 단위)
	- inputdata : `Smart Contract`에서 args 처럼 사용되는 값
	- gasLimit : 트랜잭션 실행에 사용될 수 있는 최대 가스
	- maxPriorityFeePerGas : 검증자에게 팁으로 제공될 수 있는 가스당 최대 가격
	- maxFeePerGas : 수신자가 최대로 부담할 수 있는 가스당 가격
- 트랜잭션에도 여러 종류가 있다.
	- message calls : `internal transaction`이다. 다른 계정에게 메시지를 보내는 것
	- contract creation : `Smart Contract`를 생성하는 트랜잭션이다.
		- 누군가에게 보내는 것이 아니므로 to가 비어있다.
	- contract run : `Smart Contract`를 실행하는 트랜잭션이다.
		- to는 해당 `Smart Contract`의 주소이다.
- 트랜잭션 제출 후에는 다음과 같은 일들이 벌어진다.
	1. 트랜잭션의 해시가 암호학적으로 생성된다.
	2. 네트워크에 전파되어 대기 중인 트랜잭션 풀에 등록된다.
	3. 검증자가 해당 트랜잭션을 블록에 포함시켜 검증된다.(successful)
	4. 해당 블록이 시간이 지나 justified -> finalized 상태까지 변화된다. (더욱 더 core가 되가는 것)
	5. finalized 단계까지 접어들면 외부 공격에 의해서만 수정될 수 있다.
- 이런 트랜잭션은 여러개가 하나의 블록으로 묶이는데, 이 과정은 PoS로 진행된다.
# Proof Of Stake 프로토콜
- 검증자는 32ETH를 예치하여 나쁜 행동에 대한 담보로 삼는다.
- 매 슬롯(12초)마다 무작위의 검증자가 block proposer로 선정된다.
	- 이들은 트랜잭션을 묶어 실행하고, 새로운 상태를 결정한다.
	- 이것을 블록으로 만들고, 다른 검증자에게 전달한다.
	- 때때로 block proposer가 오프라인 상태가 되면, 슬롯이 비게될 수도 있다.
- 이렇게 전파된 블록에 대하여 검증자가 검증한 후, 글로벌 상태 변화에 동의한다면 DB에 등록한다.
- 만약 동일 슬롯에 대하여 상충되는 두 블록을 같이 만나면, fork-choice 알고리즘을 통해서 선택한다.
# 블록 크기
- 이더리움은 1500만 `Gas`라는 목표 블록 크기를 가지고 있다.
- 또한 3000만 `Gas`라는 최대 블록 크기를 가지고 있다.
	- 블록내의 모든 트랜잭션에서 소비되는 총 가스는 3000만을 넘기지 못한다.
- 이는 성능이 낮은 노드들이 네트워크를 따라가지 못하는 현상을 방지하기 위함이다.

### 블록 생성 과정
![[Pasted image 20241208223618.png]]
1. EVM이 블록체인과 트랜잭션들을 로드한다. (각각 네트워크, mempool에서)
2. EVM이 각 트랜잭션을 처리하여 블록을 만든다.
3. 해당 블록을 네트워크 상에 전파한다.
4. 네트워크에서 블록이 approve 되면 블록체인에 추가된 것이다.
# Smart Contract
- 간단히 말해, EVM에 의해 실행 가능한 코드이다.
	- 모든 노드에 대하여 같은 결과로 실행된다.
	- 모든 노드는 각자의 EVM에서 이를 실행하지만, 처음 상태가 같기 때문에 그렇다.
- 블록체인에 저장되는 덕분에 불변성과 분산성을 갖는다.
	- 단, 수정될 수는 없지만 삭제될 수는 있다. (EVM 연산코드 `SELFDESTRUCT`를 실행하면 가능하므로, 계약을 작성할 때 이를 고려해야 한다.)
	- 이 삭제는 리소스를 반환하는 행위이므로, 가스를 얻을 수 있다. (음의 가스를 갖는다.)
- `Smart Contract`는 Solidity같은 고수준 언어로 작성된다.
- `Smart Contract`는 실행되기 위해서는 EVM에 저수준 언어로 컴파일되어야 한다.
- `contract creation transaction`에 의하여 배포될 수 있다.
	- 이때 해당 트랜잭션의 to는 0x0이다. (없는 것)
- EOA에 의해 시작된 transaction chain에 의해서 호출되어 실행될 수 있다.
	- 이때 한 chain에서 여러 `Smart Contract`가 실행될 수 있는데, 이들은 모두 다 같이 성공하거나 다 같이 실패한다. (실패한 경우 롤백되어 `World State`는 그대로 남는다.)
# EVM
- 스택 기반으로 작동한다. (bitcoin보다는 좀 더 복잡하다.)
- 여러 명령어들이 존재한다.
	- REVERT : Gas Limit시 사용
	- MLOAD : 메모리 로드
	- SLOAD : 스택 로드
	- ...
# State
- `Smart Contract`에 의하여 여러 값들이 변하며 다음 State로 넘어가게 된다.
- Ethereum World State : Ethereum Address - Accounts의 매핑
	- Accounts는 balance, nonce, storage, code 등이 들어간다.
	- 즉 ,이 내부 값들이 바뀌면 Ethereum World State도 바뀌게 되는 것이다.
- EVM State
	- EVM은 현재 생성중인 블록과 현재 트랜잭션과 관련된 정보를 기반으로 초기화된다.
	- ROM : 호출된 `Smart Contract`로 초기화
	- PC, 메모리 : 0으로 초기화
	- 저장소 : `Smart Contract`의 저장소로 초기화
	- `Gas` : 지정된 값으로 초기화
	- `Smart Contract`는 `data`필드에 초기화 코드를 가지고 있는데, EVM이 이를 읽을 때 해당 코드로 초기화 한 후 실행하라는 뜻이다.
# Coin, Token
- Coin
	- 블록체인의 네이티브 화폐
	- PoW, PoS 등으로 획득할 수 있다.
	- ETH, BTC 등이 이에 해당한다.
- Token
	- 분산 프로젝트를 위해 생성된 자산이다.
	- `Smart Contract`를 통해 생성할 수 있다.
	- ERC 20 토큰 등이 이에 해당한다.
	- 플랫폼 내에서 자산을 나타내거나 결제 유틸리티 같은 것들을 수행한다.
	- Coin으로 토큰을 구매하고(Initial Coin Offering) 사용하게 된다. (게임 캐시 충전 같은 것)
# Consensus
- PoS(proof-of-stake) 방식으로 결정된다.
	- Validator는 32ETH를 stake 해야 한다.
	- Validator는 새로운 트랜잭션은 `mempool`에 계속 넣어둔다.
- PoS인 만큼, Proposer는 정말 랜덤으로 설정되어야 한다.
	- `RANDAO` 라는 방식으로 설정된다.
	- Proposer는 다음 일들을 처리한다.
		- 블록 제안(`mempool`에서 꺼내서 만듬)
		- 트랜잭션 처리 (실행)
		- 전파
- 또한 검증자들도 모두 검증하는 것이 아니라 위원회를 조직해서 검증된다.
	- `RANDAO` 라는 방식으로 설정된다.
	- 매 에폭(32슬롯, 1슬롯은 12초)마다 새로 조직된다.
	- 이들이 검증한다.
		- 전달된 블록을 재실행한다.
		- 확인되면 찬성투표를 한다.
- fork가 생기는 경우 가장 무거운 체인(투표한 검증자수와 그들의 스테이킹된 이더로 계산)이 선택된다.
	- 이더리움도 기존에 PoW를 썼을 때는 가장 긴 체인이 선택되었지만, 이제는 아니다.
- 51% 공격을 막는 방법
	- PoW : 막대한 해시파워를 투자해야 함
	- PoS : 막대한 스테이크를 투자해야 함, 적발될 경우 다 잃음
	- PoW는 담보를 맡길 필요가 없고, 오랜 시간 검증되었다는 장점이 있다.
	- 하지만, 에너지를 너무 많이 투자해야 한다.
- `Gasper` 합의 메커니즘
	- 두가지 매커니즘을 결합한 형태
    - `Casper FFG` : 이더리움의 Proof-of-Stake 시스템으로, 검증자들이 블록을 제안하고 검증하는 방식
	    - 중복 투표 방식에 제한이 있다. (S1, T1), (S2, T2) 쌍을 투표할 때
		    - 더 아래에 있는 T1, T2의 높이가 같거나
		    - S1 T1이 S2 T2를 감싸는 형식이면 안된다. (S1 - S2 - T2 - T1)
    - `LMD-GHOST`: 체인 선택을 위해 사용되는 **가중치 기반**의 포크 선택 규칙
- Jutification / Finalization
	- Jutification
		- 최종화되기 위해서는 정당화가 먼저 되어야 한다.
		- 스테이킹된 이더의 2/3 이상이 해당 블록의 정상 체인 참여에 찬성해야 한다.
	- Finalization
		- 정당화된 다른 블록이 정당화된 블록 위에 얹어지면 자동으로 최종화된다.
	- 이것은 매번 일어나는 것이 아니라 1에폭(32슬롯)마다 실행된다.
		- 매 에폭의 첫번째 블록이 체크포인트가 되며, 해당 체크포인트에 대해서만 작동한다.
	- 이 과정은 체크포인트의 쌍으로 이루어진다. (A체크포인트, B체크포인트)
		- A가 B의 조상중 하나고, A가 정당화 되어있었다면, B는 정당화된다.
		- A가 B의 직계조상이고, A가 정당화 되어있었다면, A는 최종화되고, B는 정당화된다.
	- 서로 충돌하는 두 블록은 같이 최종화될 수 없다. (안정성 증명)
		- 만약 두 블록의 높이가 같다면, `Casper FFG`의 높이가 같으면 안된다는 조건을 어긴 것이 된다.
		- 한 블록의 높이가 더 높다면, 그 블록이 최종화되기 위해 이전 블록들도 최종화가 되었을 텐데, 그 중 하나는 높이가 같은 것이며, 이는 `Casper FFG`의 높이가 같으면 안된다는 조건을 어긴 것이 된다.
- PoS를 공격해보자.
	- 합의 공격에는 크게 두가지 방법이 있다.
		- 2/3 스테이크를 소유하는 방법
			- 돈이 매우 많이 드므로 방어된다.
		- 2/3 스테이크 투표가 많이 생기는 방법
			- 중복 투표이다.
			- 중복 투표한 사람들은 1/3 이더를 잃는다.
	- 또한 비활성 유출이라는 방법도 있다.
		- 검증자가 일부로 블록을 검증하지 않아서 chain을 비활성화할 수 있다.
		- 이때 4 에폭 이상 비활성화될 경우 비활성화했던 검증자들은 이더를 점점 잃는다.
		- 2/3까지 잃을 수 있다. -> 결국엔 활성화된다.
- 결론적으로 PoS는 다음과 같은 장점을 지닌다.
	- PoW에 비해 에너지 효율성이 좋다.(해시 계산 X)
	- 막대한 해시 파워를 투입할 필요가 없으므로 진입 장벽이 낮다.
	- 잘못된 행동에 대해 경제적 처벌을 줄 수 있다.
# Applications
- NFT : 구분가능한 토큰, 어떠한 것에 대한 등기권리증이다.
	- 이 어떠한 것을 NFT 로 만드는 과정을 `mintting`이라고 한다.
	- `Smart Contract`와 연결하여 어떠한 것을 거래할 수 있도록 하는 장점을 지닌다.
	- 단 몇몇 단점을 지닌다.
		- 저작권이 아닌 소유권을 거래하는 것이다. -> 몰래 NFT화 하면 저작인격권 침해, 복제권 침해이다.
		- NFT의 대상인 원본이 소실될 수 있다.
		- 원본이 디지털 대상이므로 무단으로 복제되어 희소성이 떨어질 수 있다.
		- PoW를 사용하는 경우 에너지 소비가 막대하다.
- Metaverse : 증강현실, NFT와 같이 많이 사용된다.
- 블록체인은 탈중앙화를 위해 인센티브가 필수적이다.