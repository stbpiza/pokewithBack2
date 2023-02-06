# pokewithBack2

Website for invite to pokemon go raid   
포켓몬고 raid 초대 웹사이트

## How to use
1. Install Docker   
- https://www.docker.com/
2. move to directory and type docker compose commend
```
docker-compose up -d --build

or

docker compose up -d --build
```
3. check spring-boot container
```
docker logs -f spring-poke
```
4. enter this url  
- http://localhost:8888/

## tech stack
- Spring-Boot
- JPA
- MySQL
- Redis
- Thymeleaf
- Vanilla JS
- Docker


## service
- 초대글 작성
- 참여댓글 작성
- 글 작성자가 댓글 채택시 팀원끼리 비공개채팅방 생성(웹소켓 기반 채팅방)
- 개인정보(닉네임, 친구코드) 변경
- 게시글 필터
- 레이드 종료 후 팀원간 좋아요 싫어요

## 구현내용
- 전세계 포켓몬고 유저를 이용대상으로 잡았기에 모든 기능은 최소한의 영어 외에는 이미지로 언어 없이 직관적으로 이해할 수 있도록 구현
- 게시글 작성은 레이드 초대 기능이며 대상 포켓몬, 시작 시간, 종료 시간, 참여인원 등 초대에 관한 내용만 입력 가능
- 댓글 작성은 레이드 참여 기능이며 참여인원만 입력 가능
- 게시글 작성자는 댓글 중에서 원하는 참여자를 선택하여 채택 가능하며, 채택시 그들만의 비공개 채팅방 생성
- 채팅방에서는 문자는 입력할 수 없고, 제공된 이모티콘만 입력가능(언어 없이 소통가능)
- 레이드 종료 후 게시글 작성자가 종료버튼을 누르면 채팅방 삭제
- 게시글과 댓글은 합쳐서 1개만 작성가능(동시에 여러 레이드 참여가 불가능하기 때문)
- 본인이 글을 삭제하거나, 채택되지 않거나, 레이드가 종료되면 다시 게시글과 댓글 작성 가능
- 게시글 필터 기능으로 레이드를 종류별로 확인 가능
- 내가 쓴 글(댓글) 보기 기능 구현
- 개인정보(닉네임, 친구코드) 수정 기능 구현
- 레이드 종료 후 팀원평가로 좋아요 싫어요 기능 구현