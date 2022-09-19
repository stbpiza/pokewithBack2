

//sendAjax() : ajax 연결 (POST/GET)
function sendAjax(url, method, data, callback){
	var httpReq = new XMLHttpRequest();
	
	httpReq.open(method, url, true);
	console.log('good');
	
	httpReq.setRequestHeader('Access-Control-Allow-Headers', '*');
	httpReq.setRequestHeader('Content-type', 'application/json');
	httpReq.setRequestHeader('Access-Control-Allow-Origin', '*');
	console.log('ok');
	
	httpReq.onreadystatechange = function () {
	    console.log('들어옴1');
	    if (httpReq.readyState === 4 && httpReq.status === 200) {
	      console.log('들어옴2');
	      console.log(httpReq.responseText);
	      callback(httpReq);
	    }
	};
	
	if(data != null){
	  console.log("POST방식");
	  httpReq.send(data);
	} else {
	  console.log("GET방식");
	  httpReq.send();
	}
}

//showMyPost() : myPost 게시글을 보는 함수
function showMyPost(resultData){

	if(resultData.p_id == null){
	  let currentDiv = document.querySelector("#my-box");
	  let endPage = document.createElement("div");
	  endPage.setAttribute("class", "card-body endBody");
	
	  endStr = '<p class="endStr">Nothing!</p>';
	
	  endPage.innerHTML = endStr;
	
	  currentDiv.appendChild(endPage);
	} else {
		let num = resultData.p_id;
		let chat = resultData.chat;
		console.log(num);
		console.log(typeof(chat));
		
		let startDiv = document.createElement("div");
		startDiv.setAttribute("class", "card shadow");
		startDiv.setAttribute("id", "result-box");
		
		let nickDiv = document.createElement("div");
		nickDiv.setAttribute("class", "card-header py-3 d-flex flex-row align-items-center justify-content-between");
		
		let nickName = '';
		nickName += '<p class="m-0 text-gray"><b>'+resultData.nickname1
		            + '</b><i class="fa fa-thumbs-up updown" aria-hidden="true" style="font-size:10px"></i>' + resultData.u_like
		            + ' <i class="fa fa-thumbs-down" aria-hidden="true" style="font-size:10px"></i>' + resultData.u_hate + '</p>'
		nickDiv.innerHTML = nickName;
		startDiv.appendChild(nickDiv);
		
		let endBtn = document.createElement("button");
		endBtn.setAttribute("class", "endCheck");
		endBtn.setAttribute("Onclick", 'endPost('+num+', "'+chat+'")');
		nickDiv.appendChild(endBtn);
		
		let endBtnText = document.createTextNode("end");
		endBtn.appendChild(endBtnText);
		
		if(resultData.userId == resultData.suserId){
			endBtn.style.display = 'block';
		} else {
			endBtn.style.display = 'none';
		}
		
		if(resultData.p_end == null || resultData.p_end == "2") {
		  let currentDiv = document.querySelector("#my-box");
		  let endPage = document.createElement("div");
		  endPage.setAttribute("class", "card-body endBody");
		
		  endStr = '<p class="endStr">Nothing!</p>';
		
		  endPage.innerHTML = endStr;
		
		  currentDiv.appendChild(endPage);
		} else {
		
		  let cardDiv = document.createElement("div");
		  cardDiv.setAttribute("class", "myCard card-body cardBody"+num);
		  startDiv.appendChild(cardDiv);
		
		  if(resultData.raidLevel == '1') {
		      str = "<img src = '/static/img/1.png' style='width:50px'>"
		  } else if(resultData.raidLevel == '3') {
		      str = "<img src = '/static/img/3.png' style='width:50px'>"
		  } else if(resultData.raidLevel == '5') {
		      str = "<img src = '/static/img/5.png' style='width:50px'>"
		  } else {
		      str = "<img src = '/static/img/mega.png' style='width:50px'>"
		  }
		  
		  cardDiv.innerHTML += '<input type="hidden" id="postId" name="postId" value="'+ resultData.p_id +'">';
		  cardDiv.innerHTML += '<p> Pokemon : <img src="/static/img/pokemon/150.png" width="150px" /></p>';
		  //cardDiv.innerHTML += '<p> Pokemon : <img src="/static/img/pokemon/'+resultData.pokemon+'.png" width="150px" /></p>';
		  cardDiv.innerHTML += '<p> Level of Raid : ' + str + '</p>';
		  cardDiv.innerHTML += '<p> Start Time of Raid : ' + resultData.startTime+'</p>';
		  cardDiv.innerHTML += '<p> End Time of Raid : ' + resultData.endTime+'</p>';
		  cardDiv.innerHTML += '<p> Required Player Level : ' + resultData.minLevel+'</p>';
		  cardDiv.innerHTML += '<p> Premium Pass : <img src="/static/img/3_premium.png" class="remote1"> / <img src="/static/img/2_premium.png" class="remote2"> <span id="npass">' + resultData.nPass+'</span></p>';
		  cardDiv.innerHTML += '<p> Remote Pass : <img src="/static/img/1_remote.png" class="remote1"> <span id="rpass">' + resultData.rPass + '</span></p>';
		
		  let commentDiv3 = document.createElement('div');
		  commentDiv3.setAttribute('class', 'commentBox');
		  commentDiv3.setAttribute('id', 'commentBox');
		
		  let commentA = document.createElement('button');
		  commentA.setAttribute('id', 'comment'+num)
		  commentA.setAttribute('class', 'hide-link');
		  commentA.setAttribute('onclick', 'allCommentAjax('+num+')');
		
		
		  let commentText = document.createTextNode('comment');
		  commentA.appendChild(commentText);
		
		  let arrowDown = document.createElement("i");
		  arrowDown.setAttribute("class", "fa fa-sort-down");
		
		  commentA.appendChild(arrowDown);
		
		  commentDiv3.appendChild(commentA);
		
		  let divide = document.createElement('div');
		  divide.setAttribute('class', 'dropdown-divider');
		
		  commentDiv3.appendChild(divide);
		  cardDiv.appendChild(commentDiv3);
		
		  let currentDiv = document.querySelector("#my-box");
		
		  let chattingDiv = document.createElement("div");
		  chattingDiv.setAttribute("class", "chattingDiv");
		  currentDiv.appendChild(chattingDiv);
		
		  //enter
		  let chattingLink = document.createElement("a");
		  chattingLink.href = '/rooms/' + resultData.chat;
		  chattingLink.className = 'chattingLink';
		  chattingLinkText = document.createTextNode("Start Chatting!");
		  chattingLink.appendChild(chattingLinkText);
		  chattingDiv.appendChild(chattingLink);  
		  currentDiv.appendChild(startDiv);
		
		  if(chat == null){
		    //null일 때
		    chattingDiv.style.display = 'none';
		  }else{
		    //null이 아닐 때
		    chattingDiv.style.display = 'block';
		  }
		}	
	}
}
    

//allPost() : myPost 게시글을 출력하기 전 거치는 ajax
/* 세션이 도입되면 하드 코딩했던 부분 지움 */
function myPostAjax() {
	let url = '/api/mypost';
	
	sendAjax(url, 'GET', null, function(res){
	  console.log(res.response);
	  var result = JSON.parse(res.response);
	  
	  showMyPost(result);
	});
};



//mypost 페이지 들어왔을 시 바로 showMyPost() 함수가 호출되도록 함
if (window.addEventListener)
      window.addEventListener("load", myPostAjax, false);
else if (window.attachEvent)
    window.attachEvent("onload", myPostAjax);
else window.onload = myPostAjax;


//allComment(num) : myPost 게시글의 모든 댓글을 보는 함수
function allComment(resultData) {
	
	if(resultData.data.length == 0){
	  let myCard = document.querySelector(".myCard");
	  
	  let nullDiv = document.createElement("div");
      nullDiv.setAttribute("class", "nullDiv");
	  nullDiv.innerHTML = 'No comments yet.';
      myCard.appendChild(nullDiv);
 
      let arrowUp = document.querySelector(".hide-link");
	  arrowUp.setAttribute('onclick', 'nullComment()');
	  arrowUp.innerHTML = 'comment <i class="fa fa-sort-up"></i>';	  
	} else {
	  let num = resultData.data[0].p_id;
	  let currentDiv = document.querySelector(".cardBody"+num);
	
	  let checkNum = document.createElement('div');
	  checkNum.setAttribute('id', 'checkNum');	
	  checkNum.innerHTML = 'Selectable number of accounts';
	  currentDiv.appendChild(checkNum);
	
	  let nickDiv2 = document.createElement('div');
	  nickDiv2.setAttribute('id', 'nickDiv2');
	  currentDiv.appendChild(nickDiv2);	
	
	  let startDiv = document.createElement("div");
	  startDiv.setAttribute("class", "card-body commentBody"+num);
	  currentDiv.appendChild(startDiv);
	
	  arr = [];
	  for(let i = 0; i<resultData.data.length; i++){
	    if(num == resultData.data[i].p_id && resultData.data[i].c_end == '0'){
	      let commentId = resultData.data[i].c_id;
	      let commentW = document.createElement("div");
	      commentW.setAttribute("class", "commentWrap comment"+commentId);
 		   startDiv.appendChild(commentW);
	
	      let commentTextDiv = document.createElement("div");
          commentW.appendChild(commentTextDiv);
           
          let commentP = document.createElement("p");
          commentP.setAttribute("class", "commentP");
          commentTextDiv.appendChild(commentP);
			

			let commentInput = document.createElement("input");
			commentInput.setAttribute("type", "checkbox");
			commentInput.setAttribute("id", "checkUser"+resultData.data[i].c_id);
			commentInput.setAttribute("class", "checkUser");
			commentInput.setAttribute("name", resultData.data[i].c_id);
			commentInput.setAttribute("value", resultData.data[i].checkNum);
			commentInput.setAttribute("onclick", "checkUser(this, "+resultData.data[i].p_id+")");
			commentP.appendChild(commentInput);

			let commentPText = document.createTextNode(resultData.data[i].nickname1);
			commentP.appendChild(commentPText);
		
			let commentDiv = document.createElement("div");
			commentDiv.setAttribute("class", "commentDiv");
			commentW.appendChild(commentDiv);
		
			let flexDiv = document.createElement("div");
			flexDiv.setAttribute("class", "d-flex align-items-center justify-content-between");
			commentDiv.appendChild(flexDiv);
		
			let remoteResult = document.createElement("div");
			remoteResult.setAttribute("id", "remoteResult");
			flexDiv.appendChild(remoteResult);
		
			let remoteImg = document.createElement("img");
			remoteImg.setAttribute("class", "remote1");
			remoteImg.setAttribute("src", "/static/img/1_remote.png");
			remoteResult.appendChild(remoteImg);
		
			let spanDiv = document.createElement("span");
			spanDiv.setAttribute("class", "commentLength");
			spanDiv.setAttribute("id", "commentLength"+commentId);
			spanDiv.innerHTML = resultData.data[i].checkNum.length;
			remoteResult.appendChild(spanDiv);

			let deleteIcon = document.createElement("i");
			deleteIcon.setAttribute("class", "fas fa-times delete");
			deleteIcon.setAttribute("onClick", "deleteComment("+resultData.data[i].c_id+", "+resultData.data[i].p_id+")");
			flexDiv.appendChild(deleteIcon);
		
			if(resultData.data[i].userId === resultData.suserId){
				deleteIcon.style.display = 'block';
			} else {
				deleteIcon.style.display = 'none';
			}
				   	    
	    } else {
			if(resultData.data[i].c_end == '1'){
				nickDiv2.innerHTML += "<h5 class='nick1'>"+resultData.data[i].nickname1+"'s</5>";
				showNick(resultData.data[i]);			
			}
			checkNum.style.display = 'none'; 
		}
      }
	
	  
	  let comSub = document.createElement("div");
	  comSub.setAttribute("class", "comSub");
	  startDiv.appendChild(comSub);
	
	  let arrowUp = document.getElementById("comment"+num);
	  arrowUp.setAttribute('onclick', 'hideComment('+num+')');
	  arrowUp.innerHTML = 'comment <i class="fa fa-sort-up"></i>';
		
	  let commentBox = document.querySelector(".commentBody"+num);
	  commentBox.style.display = 'block';  	
   }
}

//allPost() : myPost 게시글의 댓글을 출력하기 전 거치는 ajax
function allCommentAjax(num) {

	let url = '/comment/mypost/'+num;
	
	const postId = num;
	
	sendAjax(url, 'GET', null, function(res){
	  console.log(res.response);
	  var result = JSON.parse(res.response);   
	  allComment(result);
	});
};


//전역 변수 comment_id_names, c_id를 배열로 담는 변수다.
let comment_id_names = [];

//checkUser() : 같이할 유저를 체크 확인하는 함수
/* 음수로 넘어가면 더이상 체크할 수 없게끔 한다. 
선택할 때마다 실시간으로 잔여 계정 수가 바뀐다. 
MVC 패턴으로 찢을 때 다시 최솟값 체크하는 부분을 수정해야 한다.*/
function checkUser(here, num) {
	let checkedUser = document.getElementsByClassName("checkUser");
	console.log('check user : '+checkedUser);

	let sum = 0;
	let count = checkedUser.length;
	let temp=[];
	for(let i=0; i < count; i++ ){
	  if( checkedUser[i].checked == true ){
	    sum += parseInt(checkedUser[i].value.length);
	    temp.push(checkedUser[i].name);
	    checkSubmit(num);
	  }
	}
	comment_id_names = temp;
	checkedUser.value = sum;
	
	console.log(sum);

	let checkNum = document.querySelector("#checkNum");
	
	let rPass = parseInt(document.getElementById("rpass").innerHTML);
	let nPass = parseInt(document.getElementById("npass").innerHTML);

	let leftNum1 = 20 - ( nPass +  rPass + sum);
	let leftNum2 =  10 - ( rPass + sum);
	let leftNum3 = 5 - sum;
	let leftNum = Math.min(leftNum1, leftNum2, leftNum3);

	console.log("최솟값 " + leftNum);
	
	if(leftNum >= 0) {
	  checkNum.innerHTML = 'Selectable number of accounts : ' + leftNum;
	} else {
	  alert("Can't select anymore");
	  for(let i=0; i < count; i++ ){
	    checkedUser[i].checked = false;
	  }
	  checkNum.innerHTML = 'Please select again';
	}
}

//checkSubmit() : 유저를 체크하면 submit 버튼이 생기는 함수
function checkSubmit(num){
	let comSub = document.querySelector(".comSub");
	comSub.innerHTML = '<input type="button" value="submit" class="check-submit" onclick="sendCheck('+num+')">';
}

//showCheck() : 서버 쪽에 데이터를 보내주고 새로고침하는 함수
function sendCheck(p_id) {

	console.log(p_id)
	console.log('arr length = '+comment_id_names);
	console.log(typeof(comment_id_names));
	console.log(comment_id_names);

	if(confirm("Did you check the user's nickname well?")){
	    
	  const sendData = {
	    p_id : p_id,
	    c_id : comment_id_names,
	    chat : Math.random().toString(36).substr(2,11)
	  }
	
	  const strObject = JSON.stringify(sendData);
	  
	  console.log(strObject);
	
	  var url = '/mypost';
	  sendAjax(url, 'PUT', strObject);
	  
	  setTimeout("location.reload()", 1000);
	} else {
	  alert('You canceled it.');
	}
}

//showNick() : 새로고침 하고 난 뒤 comment를 누르면 고른 사람의 친구코드와 닉네임이 보이게 하는 함수
function showNick(result){

	console.log(result+", "+ arr);
	
	    for(let j = 0; j< result.checkNum.length; j++){
	      let onePersonCheckNum= result.checkNum[j];
	      console.log(onePersonCheckNum);
	      switch(onePersonCheckNum){
	        case '1' :
	          // 만일 onePersonCheckNum이 1이라면 그에 해당하는 nickname과 friendcode를 함수에 넣는다. 
	          showNickMaker(result.nickname1, result.friendCode1);
	          break;
	        case '2' : 
	          showNickMaker(result.nickname2, result.friendCode2);
	          break;
	        case '3' : 
	          showNickMaker(result.nickname3, result.friendCode3);
	          break;
	        case '4' : 
	          showNickMaker(result.nickname4, result.friendCode4); 
	          break;
	        case '5' : 
	          showNickMaker(result.nickname5, result.friendCode5);
	          break;
	      }
	    }
}

//showNickMaker() : 그 사람의 nickname과 friendCode를 담아 view로 보여주는 함수
function showNickMaker(nickname, friendCode){
	console.log('showNickMaker : ' + nickname, friendCode );
	
	let nickDiv2 = document.querySelector("#nickDiv2");
	
	let resultDiv = document.createElement("div");
	resultDiv.setAttribute("class", "checkResult");
	
	let onePersonInfo = '';
	onePersonInfo += 'nickname : ' + nickname + '</br> friendCode :' + friendCode+'</br>';
	
	//commentBox 안의 내용물을 변경
	resultDiv.innerHTML = onePersonInfo;
	
	nickDiv2.appendChild(resultDiv);
}


//hideComment() : 모든 댓글을 숨기고 보여주는 함수.
function hideComment(num) {
	let checkNum = document.querySelector("#checkNum");
	let commentBox = document.querySelector(".commentBody"+num);
	let arrowDown = document.getElementById("comment"+num);
	
	if(commentBox.style.display == 'block'){
	    checkNum.style.display = 'none';
	    commentBox.style.display = 'none';
	    arrowDown.innerHTML = 'comment <i class="fa fa-sort-down"></i>';
	} else {
	    checkNum.style.display = 'block';
	    commentBox.style.display = 'block';
	    arrowDown.innerHTML = 'comment <i class="fa fa-sort-up"></i>';
	}
}


//hideComment() : 모든 댓글을 숨기고 보여주는 함수.
function nullComment() {
	let nullDiv = document.querySelector(".nullDiv");
	let arrowDown = document.querySelector(".hide-link");
	
	if(nullDiv.style.display == 'block'){
	    nullDiv.style.display = 'none';
	    arrowDown.innerHTML = 'comment <i class="fa fa-sort-down"></i>';
	} else {
	    nullDiv.style.display = 'block';
	    arrowDown.innerHTML = 'comment <i class="fa fa-sort-up"></i>';
	}
}



//endPost() : 레이드 종료 버튼.
/* session에 있는 nickname1과 mypost에 떠있는 포스트 작성자의 nickname1이
같을 경우에만 end 버튼 활성화 */
function endPost(num, chat) {

	if(confirm('Are you sure you want to end the raid?')){
	
	  const addData = { p_id : num, chat : chat };
	  const strObject = JSON.stringify(addData);
	  console.log(strObject);
	
	  var url = '/mypost/end';
	  sendAjax(url, 'POST', strObject);

	  setTimeout("location.reload()", 1000);

	} else {
	  alert('You canceled it');
	}
}

//deleteComment() : 댓글 지우는 기능
/* session에 있는 nickname1과 mypost에 떠있는 포스트 작성자의 nickname1이
같을 경우에만 X 버튼 활성화 */
function deleteComment(c_id, p_id){
	if(confirm("Do you really want to erase it?")){
	  const addData = { c_id : c_id }
	  const strObject = JSON.stringify(addData);
	  console.log(strObject);
	
	  var url = '/mypost/mycomment';
	  sendAjax(url, 'DELETE', strObject);
	  setTimeout("location.reload()", 1000);
	}
}

// Init paint sidebar
function mypageFetch() {
	const url = "/api/mypage";

	fetch(url)
		.then((response) => response.json())
		.then((data) => {
			userInfo = data;
			loadSidebar(data);
		});
}

let userInfo = {
	nickname1: "",
	friendCode1: "",
	nickname2: "",
	friendCode2: "",
	nickname3: "",
	friendCode3: "",
	nickname4: "",
	friendCode4: "",
	nickname5: "",
	friendCode5: "",
}

const sidebarList = document.querySelectorAll(".sidebar-list");
function loadSidebar(data){
	const userInfoGet = data;
	sidebarList[0].innerText = userInfoGet.nickname1;
	if(userInfoGet.nickname2 === "") {
		sidebarList[1].innerText = "Empty";
	} else {
		sidebarList[1].innerText = userInfoGet.nickname2;
	}
	if(userInfoGet.nickname3 === "") {
		sidebarList[2].innerText = "Empty";
	} else {
		sidebarList[2].innerText = userInfoGet.nickname3;
	}
	if(userInfoGet.nickname4 === "") {
		sidebarList[3].innerText = "Empty";
	} else {
		sidebarList[3].innerText = userInfoGet.nickname4;
	}
	if(userInfoGet.nickname5 === "") {
		sidebarList[4].innerText = "Empty";
	} else {
		sidebarList[4].innerText = userInfoGet.nickname5;
	}
}

if (window.addEventListener)
	  window.addEventListener("load", mypageFetch, false);
else if (window.attachEvent)
    window.attachEvent("onload", mypageFetch);

else window.onload = mypageFetch;

// End of paint sidebar