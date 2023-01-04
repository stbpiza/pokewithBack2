
//sendAjax() : ajax 연결 (POST/GET)
function sendAjax(url, method, data, callback){
  const httpReq = new XMLHttpRequest();

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
        // console.log(httpReq.responseText);
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


//allPostHtml() : 전체/필터 게시글 출력 함수
function allPostHtml(requiredData, n){

  let num = requiredData.raidDtoList[n].raidId;
  console.log(num);
    
  let startDiv = document.createElement("div");
  startDiv.setAttribute("id", "result-box");
  startDiv.setAttribute("class", "card shadow mb-4");
  
  if(requiredData.raidDtoList[n].raidState === "DONE"){
    startDiv.setAttribute("class", "ending card shadow mb-4");
  } else if (requiredData.raidDtoList[n].raidState === "DOING") {
    startDiv.setAttribute("class", "raiding card shadow mb-4");
  }

  let nickDiv = document.createElement("div");
  nickDiv.setAttribute("class", "card-header py-3 d-flex flex-row align-items-center justify-content-between");

  let nickName = '';
  nickName += '<p class="m-0 text-gray"><b>'+requiredData.raidDtoList[n].nickname1
              + '</b><i class="fa fa-thumbs-up updown" aria-hidden="true" style="font-size:10px"></i>' + requiredData.raidDtoList[n].likeCount
              + ' <i class="fa fa-thumbs-down" aria-hidden="true" style="font-size:10px"></i>' + requiredData.raidDtoList[n].hateCount + '</p>'
  nickDiv.innerHTML = nickName;

  startDiv.appendChild(nickDiv);

  let cardDiv = document.createElement("div");
  cardDiv.setAttribute("class", "card-body cardBody"+num);
  startDiv.appendChild(cardDiv);

  if(requiredData.raidDtoList[n].raidType === 'ONE') {
      str = "<img src = '/static/img/1.png' style='width:50px'>"
  } else if(requiredData.raidDtoList[n].raidType === 'THREE') {
      str = "<img src = '/static/img/3.png' style='width:50px'>"
  } else if(requiredData.raidDtoList[n].raidType === 'FIVE') {
      str = "<img src = '/static/img/5.png' style='width:50px'>"
  } else {
      str = "<img src = '/static/img/mega.png' style='width:50px'>"
  }
  
  cardDiv.innerHTML += '<input type="hidden" id="postId" name="postId" value="'+ requiredData.raidDtoList[n].raidId +'">';
  // cardDiv.innerHTML += '<p> Pokemon : <img src="/static/img/pokemon/150.png" width="150px" /></p>';
  cardDiv.innerHTML += '<p> Pokemon : <img src="/static/img/pokemon/'+requiredData.raidDtoList[n].pokemon+'.png" width="150px" /></p>';
  cardDiv.innerHTML += '<p> Level of Raid : ' + str + '</p>';
  cardDiv.innerHTML += '<p> Start Time of Raid : ' + requiredData.raidDtoList[n].startTime+'</p>';
  cardDiv.innerHTML += '<p> End Time of Raid : ' + requiredData.raidDtoList[n].endTime+'</p>';
  cardDiv.innerHTML += '<p> Required Player Level : ' + requiredData.raidDtoList[n].requiredLevel+'</p>';
  cardDiv.innerHTML += '<p> Premium Pass : <img src="/static/img/3_premium.png" style="width:60px"> / <img src="/static/img/2_premium.png" style="width:50px">' + requiredData.raidDtoList[n].normalPass+'</p>';
  cardDiv.innerHTML += '<p> Remote Pass : <img src="/static/img/1_remote.png" style="width:60px"> ' + requiredData.raidDtoList[n].remotePass + '</p>';


  if(requiredData.raidDtoList[n].raidState ==="INVITE"){
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
  }
  
  let postbox = document.querySelector("#post-box");
  postbox.appendChild(startDiv);

}


//allPost() : 모든 포스트/필터된 포스트를 출력하기 전 거치는 ajax
function allPostAjax(selectOption, switchOption) {

    let state = "";
    let type = "";
    if (switchOption) {
        state = "INVITE"
    }
    if (typeof selectOption === 'string') {
        type = selectOption;
    }
    removeAllPost();
    const url = `/api/raid?size=50&state=${state}&type=${type}`;

    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        console.log(JSON.stringify(data));
        for (let i = 0; i < data.raidDtoList.length; i++) {
          allPostHtml(data, i);
        }
      })


};


//removeAllPost() : 필터링할 때 기존에 있던 포스트들 다 숨기는 기능
function removeAllPost(){
  let card = document.querySelectorAll(".card");
  for(let i = 0; i < card.length; i++){
    card[i].style.display = 'none';
  }
}


//윈도우가 로드될 때 allPost()를 실행시키기 위한 함수
if (window.addEventListener)
        window.addEventListener("load", allPostAjax, false);
else if (window.attachEvent)
      window.attachEvent("onload", allPostAjax);
else window.onload = allPostAjax;


//post 버튼에 onClick 함수 binding
let postBtn = document.getElementById('post-btn');
postBtn.addEventListener('click',createPost);


//createPost() : 새로운 post 탬플릿 생성 함수
function createPost(){

  let modalDiv = document.querySelector("#exampleModalCenter");

  let modalBox = document.createElement("div");
  modalBox.setAttribute("class", "modal-dialog modal-dialog-centered");
  modalBox.setAttribute("role", "document");
  modalDiv.appendChild(modalBox);

  let modalContent = document.createElement("div");
  modalContent.setAttribute("class", "modal-content");
  modalBox.appendChild(modalContent);

  let modalHeader = document.createElement("div");
  modalHeader.setAttribute("class", "modal-header");
  modalContent.appendChild( modalHeader);

  modalHeaderStr = '';
  modalHeaderStr += "<h5 class='modal-title id='exampleModalLongTitle'>New Post</h5>";
  modalHeaderStr += "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>";
  modalHeader.innerHTML =  modalHeaderStr;

  let modalBody = document.createElement("div");
  modalBody.setAttribute("class", "modal-body");
  modalContent.appendChild(modalBody);

  let modalBodyStr = '';
  modalBodyStr += "<p>pokemon : <input type='text' id='pokemon' class='form-control' onkeyup='setTimeout(pokemon, 500)' name='pokemon' placeholder='pokemon of number'><div id='pokemonImg'> </div> </p>";
  modalBodyStr += `<p>Level of Raid : <select name="searchYear" id="raidLevel" class='form-control' name="raidLevel">
                      <option value="ONE">1</option>
                      <option value="THREE">3</option>
                      <option value="FIVE">5</option>
                      <option value="MEGA">mega</option>
                      </select></p>`;
  modalBodyStr += "<p>Start Time of Raid : <input type='datetime-local' id='startTime' class='form-control' name='startTime'></p>";
  modalBodyStr += "<p>End Time of Raid : <input type='datetime-local' id='endTime' class='form-control' name='endTime'></p>";
  modalBodyStr += "<p>Required Player Level : <input type='number' max='50' id='minLevel' class='form-control' name='minLevel'></p>";
  modalBodyStr += "<p>Premium Pass : <input type='number' id='nPass' class='form-control' name='nPass'></p>";
  modalBodyStr += "<p>Remote Pass : <input type='number' id='rPass' class='form-control' name='rPass'></p>";
  modalBody.innerHTML = modalBodyStr;

  document.getElementById("startTime").value = new Date().toISOString().slice(0, 16);
  document.getElementById("endTime").value = new Date().toISOString().slice(0, 16);

  let modalFooter = document.createElement("div");
  modalFooter.setAttribute("class", "modal-footer");
  modalContent.appendChild(modalFooter);

  let modalSubmit = document.createElement("button");
  modalSubmit.setAttribute("id", "submit-btn");
  modalSubmit.setAttribute("class", "border-0 btn-info comment-btn");
  modalSubmit.setAttribute("value", "submit");
  modalSubmit.addEventListener("click", addPost);

  let modalSubmitText = document.createTextNode('Submit');
  modalSubmit.appendChild(modalSubmitText);
  modalFooter.appendChild(modalSubmit);

  let modalClose = document.createElement("button");
  modalClose.setAttribute("class", "border-0 btn-secondary comment-btn");
  modalClose.setAttribute("data-dismiss", "modal");

  let modalCloseText = document.createTextNode('Close');
  modalClose.appendChild(modalCloseText);
  modalFooter.appendChild(modalClose);
}

function pokemon(){
	let pokeNum = document.getElementById("pokemon").value;
	let pokeDiv = document.getElementById("pokemonImg");
	pokeDiv.style.display = 'block';
	if(pokeNum === ""){
		pokeDiv.style.backgroundImage = "none";
	}else {	
		pokeDiv.style.backgroundImage = "url('/static/img/pokemon/"+pokeNum+".png')";
	}
	
}


//addPost() : 기존 게시글에 새 게시글의 데이터를 더하는 함수
/* 포스트를 생성하게 되면 바로 mypost 화면으로 리다이렉트 된다. */
function addPost(){
  const addData = {
    pokemon : document.getElementById("pokemon").value,
    raidType : document.getElementById("raidLevel").value,
    startTime : document.getElementById("startTime").value,
    endTime : document.getElementById("endTime").value,
    requiredLevel : document.getElementById("minLevel").value,
    normalPass : document.getElementById("nPass").value,
    remotePass : document.getElementById("rPass").value,
  }



  const strObject = JSON.stringify(addData);
  console.log("보내는 데이터 : "+strObject);

  const url = '/api/raid';

  fetch(url, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: strObject,
    })
      .then((response) => {
        if (response.status === 200) {
          alert("Post Success!");
          window.location.replace("/mypost");
        } else if (response.status === 400) {
          alert("Post fail")
        } else if (response.status === 409) {
          alert("You have already registered post or comment.");
          window.location.replace("/mypost");
        }
  });

  setTimeout("location.reload()", 1000);

  // 밑 부분은 앞으로 서버랑 연결할 때 필요 없는 부분
  // postData.push(addData);

  // console.log(postData.push(addData));
  // console.log(postData);

  // allPostHtml(addData, postData.length-1);
}


//allComment() : 특정 포스트의 모든 댓글을 출력하는 함수
function allComment(resultData, num) {

  let currentDiv = document.querySelector(".cardBody"+num);

  let startDiv = document.createElement("div");
  startDiv.setAttribute("class", "card-body commentBody"+num);

  for(let i = 0; i<resultData.raidCommentDtoList.length; i++){
    // if(num === resultData.raidCommentDtoList[i].raidId){
      let commentId = resultData.raidCommentDtoList[i].raidCommentId;
      let commentW = document.createElement("div");
      commentW.setAttribute("class", "commentWrap comment"+commentId);

      let commentText = '';
      let account = 0;
      if (resultData.raidCommentDtoList[i].account1) account++;
      if (resultData.raidCommentDtoList[i].account2) account++;
      if (resultData.raidCommentDtoList[i].account3) account++;
      if (resultData.raidCommentDtoList[i].account4) account++;
      if (resultData.raidCommentDtoList[i].account5) account++;
      commentText += '<div> <p class="commentP">' + resultData.raidCommentDtoList[i].nickname1;
      commentText += '</p> <div style="float: right; width:60%;"><img src="/static/img/1_remote.png" style="width:60px; margin-right:20px">' + account + "</div></div>";
      commentW.innerHTML = commentText;

      startDiv.appendChild(commentW);
    // }
  }

  let commentForm = document.createElement("div");

  console.log("user "+ userInfo.nickname1);

  let nickname1 = userInfo.nickname1;
  let nickname2 = userInfo.nickname2;
  let nickname3 = userInfo.nickname3;
  let nickname4 = userInfo.nickname4;
  let nickname5 = userInfo.nickname5;
  if(nickname1 === ''){

  } else {
    commentForm.setAttribute('class', 'comment-wrap wrap'+ num);
    startDiv.appendChild(commentForm);
    let str = "";
    str += "<p class='checked'><input type='checkbox' name='nickname" + num + "' value='account1'> 1. " + nickname1 + "</p>";
    if (nickname2 !== null) {
      str += "<p class='checked'><input type='checkbox' name='nickname" + num + "' value='account2'> 2. " + nickname2 + "</p>";
    }
    if (nickname3 !== null) {
      str += "<p class='checked'><input type='checkbox' name='nickname" + num + "' value='account3'> 3. " + nickname3 + "</p>";
    }
    if (nickname4 !== null) {
      str += "<p class='checked'><input type='checkbox' name='nickname" + num + "' value='account4'> 4. " + nickname4 + "</p>";
    }
    if (nickname5 !== null) {
      str += "<p class='checked'><input type='checkbox' name='nickname" + num + "' value='account5'> 5. " + nickname5 + "</p></br>";
    }

    commentForm.innerHTML = str;

    commentSubmit = document.createElement('input');
    commentSubmit.setAttribute('class', 'comment-submit');
    commentSubmit.setAttribute('type', 'submit');
    commentSubmit.setAttribute('value', 'submit');
    commentSubmit.setAttribute('onclick', 'commitComment(' + num + ')');

    commentForm.appendChild(commentSubmit);
  }

    currentDiv.appendChild(startDiv);

    let arrowUp = document.getElementById("comment" + num);
    arrowUp.setAttribute('onclick', 'hideComment(' + num + ')');

    arrowUp.innerHTML = 'comment <i class="fa fa-sort-up"></i>';

    let commentBox = document.querySelector(".commentBody" + num);
    commentBox.style.display = 'block';

}


//allCommentView() : 특정 포스트의 모든 댓글을 출력하기 전 거치는 ajax
function allCommentAjax(num) {
  const url = '/api/comment/'+num;

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      console.log(JSON.stringify(data));
      allComment(data, num);
  });
};


//hideComment() : 특정 포스트의 모든 댓글을 숨기고 보여주는 함수.
function hideComment(num) {
  let commentBox = document.querySelector(".commentBody"+num);
  let arrowDown = document.getElementById("comment"+num);

  if(commentBox.style.display === 'block'){
    commentBox.style.display = 'none';
    arrowDown.innerHTML = 'comment <i class="fa fa-sort-down"></i>';
  } else {
    commentBox.style.display = 'block';
    arrowDown.innerHTML = 'comment <i class="fa fa-sort-up"></i>';
  }
}


//commitComment() : 특정 포스트에 댓글 생성
/* 댓글을 생성하게 되면 바로 mypost 화면으로 리다이렉트 된다. */
function commitComment(num) {
  
  let check_count = document.getElementsByName("nickname"+num).length;

  console.log(check_count);

  const commitData = {
    raidId : num,
    account1 : document.getElementsByName("nickname"+num)[0].checked,
    account2 : false,
    account3 : false,
    account4 : false,
    account5 : false
  }

  if (document.getElementsByName("nickname"+num)[1]) {
    commitData.account2 = document.getElementsByName("nickname"+num)[1].checked;
  }
  if (document.getElementsByName("nickname"+num)[2]) {
    commitData.account3 = document.getElementsByName("nickname"+num)[2].checked;
  }
  if (document.getElementsByName("nickname"+num)[3]) {
    commitData.account4 = document.getElementsByName("nickname"+num)[3].checked;
  }
  if (document.getElementsByName("nickname"+num)[4]) {
    commitData.account5 = document.getElementsByName("nickname"+num)[4].checked;
  }

  const jsonData = JSON.stringify(commitData);

  const url = '/api/comment';

  fetch(url, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: jsonData,
    })
      .then((response) => {
        if (response.status === 200) {
          alert("Comment Success!");
          window.location.replace("/mypost");
        } else if (response.status === 409) {
          alert("You have already registered post or comment.");
          window.location.replace("/mypost");
        } else {
          alert("Comment fail.");
        }
      })
}


//makeFilteringButton() : 메인 페이지에 filtering할 select 버튼 생성
function makeFilteringButton(){
  let filterBox = document.querySelector(".filter");

  let select = document.createElement("select");
  select.setAttribute("id", "filterSelect");
  select.setAttribute("onchange", "filterOptionCheck()");
  filterBox.appendChild(select);

  let selectTotal = document.createElement("option");
  selectTotal.setAttribute("value", "");
  let selectTotalText = document.createTextNode("total");
  selectTotal.appendChild(selectTotalText);

  let selectOne = document.createElement("option");
  selectOne.setAttribute("value", "ONE");
  let selectOneText = document.createTextNode("1");
  selectOne.appendChild(selectOneText);

  let selectTwo = document.createElement("option");
  selectTwo.setAttribute("value", "THREE");
  let selectTwoText = document.createTextNode("3");
  selectTwo.appendChild(selectTwoText);

  let selectThree = document.createElement("option");
  selectThree.setAttribute("value", "FIVE");
  let selectThreeText = document.createTextNode("5");
  selectThree.appendChild(selectThreeText);

  let selectFour = document.createElement("option");
  selectFour.setAttribute("value", "MEGA");
  let selectFourText = document.createTextNode("mega");
  selectFour.appendChild(selectFourText);

  document.getElementById("filterSelect").appendChild(selectTotal);
  document.getElementById("filterSelect").appendChild(selectOne);
  document.getElementById("filterSelect").appendChild(selectTwo);
  document.getElementById("filterSelect").appendChild(selectThree);
  document.getElementById("filterSelect").appendChild(selectFour);
}

function makeFilterSwich(){
  let switchBox = document.getElementById("switchDiv");

  let switchInput = document.createElement("input");
  switchInput.setAttribute("type", "checkbox");
  switchInput.setAttribute("class", "custom-control-input");
  switchInput.setAttribute("id", "customSwitch1");
  switchInput.setAttribute("onchange", "filterOptionCheck()");
  switchBox.appendChild(switchInput);

  let switchLabel = document.createElement("label");
  switchLabel.setAttribute("class", "custom-control-label");
  switchLabel.setAttribute("for", "customSwitch1");
  switchBox.appendChild(switchLabel);
}

//filterOptionCheck() : 필터링할 value 대로 ajax를 걸어주는 함수
function filterOptionCheck(){
  var selectOption = document.getElementById("filterSelect").value;
  var switchOption = document.getElementById("customSwitch1").checked;
  return allPostAjax(selectOption, switchOption);

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

//윈도우가 로드될 때 mypageFetch()를 실행시키기 위한 함수
if (window.addEventListener)
	  window.addEventListener("load", mypageFetch, false);
else if (window.attachEvent)
    window.attachEvent("onload", mypageFetch);
else window.onload = mypageFetch;
// End of paint sidebar

//윈도우가 로드될 때 makeFilteringButton()를 실행시키기 위한 함수
if (window.addEventListener)
        window.addEventListener("load", makeFilteringButton, false);
else if (window.attachEvent)
      window.attachEvent("onload", makeFilteringButton);
else window.onload = makeFilteringButton;

//윈도우가 로드될 때 makeFilteringButton()를 실행시키기 위한 함수
if (window.addEventListener)
        window.addEventListener("load", makeFilterSwich, false);
else if (window.attachEvent)
      window.attachEvent("onload", makeFilterSwich);
else window.onload = makeFilterSwich;


