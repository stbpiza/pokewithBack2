let userInfoInput = {
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
};

const sendBtn = document.querySelector(".send-btn");
const userInfo = document.querySelector(".user-info");
const profile = document.querySelector(".profile-name");
const sidebarList = document.querySelectorAll(".sidebar-list");

//BINDING SAVE BUTTON CLICK EVENT
function saveUserInfo() {
  const pointer = this.parentNode.previousSibling;
  const name = document.querySelectorAll(".nickName");
  const code = document.querySelectorAll(".friendCode");

  const nameRgx = RegExp(/^[A-Za-z0-9_\-]{5,20}$/);
  const codeRgx = RegExp(/^[0-9_\-]{12}$/);

  for (let i = 0; i < pointer.childNodes.length; i++) {
    if (i % 2 == 1) {
      pointer.childNodes[i].classList.add("d-none");
      pointer.nextSibling.childNodes[1].classList.add("d-none");
    } else {
      pointer.childNodes[i].classList.remove("d-none");
      pointer.nextSibling.childNodes[0].classList.remove("d-none");
    }
  }

  //CHECK INPUT VALUES WITH REGULAR EXPRESSTION
  const errMsg = pointer.parentNode.nextSibling;
  const selected = pointer.childNodes;
  const chkName = nameRgx.test(selected[1].value);
  if (!chkName) {
    errMsg.innerText = `Check your Nickname`;
    selected[0].classList.add("err-display");
    selected[1].value = selected[0].innerText;
  } else {
    errMsg.innerText = "";
    selected[0].innerText = selected[1].value;
    const chkCode = codeRgx.test(selected[3].value);
    if (!chkCode) {
      errMsg.innerText = `Check your Friendcode`;
      selected[3].value = selected[2].innerText;
    } else {
      errMsg.innerText = "";
      selected[2].innerText = selected[3].value;
    }
  }

  userInfoInput.nickname1 = name[0].innerText;
  userInfoInput.nickname2 = name[1].innerText;
  userInfoInput.nickname3 = name[2].innerText;
  userInfoInput.nickname4 = name[3].innerText;
  userInfoInput.nickname5 = name[4].innerText;

  userInfoInput.friendCode1 = code[0].innerText;
  userInfoInput.friendCode2 = code[1].innerText;
  userInfoInput.friendCode3 = code[2].innerText;
  userInfoInput.friendCode4 = code[3].innerText;
  userInfoInput.friendCode5 = code[4].innerText;
}
//BINDING EDIT BUTTON CLICK EVENT
function editUserInfo() {
  const pointer = this.parentNode.previousSibling;
  for (let i = 0; i < pointer.childNodes.length; i++) {
    if (i % 2 == 1) {
      pointer.childNodes[i].classList.remove("d-none");
      pointer.nextSibling.childNodes[1].classList.remove("d-none");
    } else {
      pointer.childNodes[i].classList.add("d-none");
      pointer.nextSibling.childNodes[0].classList.add("d-none");
    }
  }
}

//DISPLAY USER INFORMATION FROM GET REQUEST
function paintUserInfo(data) {
  let userInfoGet = data;

  for (let i = 1; i < 6; i++) {
    const idList = document.createElement("li");
    idList.setAttribute("class", "d-flex justify-content-around align-items-center user-info-list");
    const errList = document.createElement("li");
    errList.setAttribute("class", "d-flex justify-content-around align-items-center user-info-list err-msg");

    const numberSpan = document.createElement("span");

    const infoDiv = document.createElement("div");
    const nameSpan = document.createElement("span");
    const nameInput = document.createElement("input");
    const codeSpan = document.createElement("span");
    const codeInput = document.createElement("input");

    const btnDiv = document.createElement("div");
    const editBtn = document.createElement("button");
    const saveBtn = document.createElement("button");

    numberSpan.setAttribute(`id`, `idNumber${i}`);
    numberSpan.innerText = `#${i}`;

    nameSpan.setAttribute(`id`, `nickname${i}`);
    nameSpan.setAttribute(`class`, `nickName form-control rounded-pill border-0 user-info`);
    nameInput.setAttribute(`class`, `input-nickName d-none form-control rounded-pill user-info`);
    nameInput.setAttribute(`id`, `NICKNAME${i}`);

    codeSpan.setAttribute(`id`, `friendCode${i}`);
    codeSpan.setAttribute(`class`, `friendCode form-control rounded-pill border-0 user-info`);
    codeInput.setAttribute(`class`, `input-friendCode d-none form-control rounded-pill user-info`);
    codeInput.setAttribute(`maxlength`, `12`);
    codeInput.setAttribute(`id`, `FRIENDCODE${i}`);

    infoDiv.appendChild(nameSpan);
    infoDiv.appendChild(nameInput);
    infoDiv.appendChild(codeSpan);
    infoDiv.appendChild(codeInput);
    infoDiv.setAttribute(`class`, `d-flex flex-column justify-content-between align-items-center`);

    btnDiv.appendChild(editBtn);
    editBtn.setAttribute(`class`, `edit-btn btn btn-default btn-sm`);
    editBtn.innerHTML = `<i class="fas fa-pen"></i>`;

    editBtn.addEventListener("click", editUserInfo);

    btnDiv.appendChild(saveBtn);
    saveBtn.setAttribute(`class`, `del-btn btn btn-default btn-sm d-none`);
    saveBtn.innerHTML = `<i class="fas fa-check"></i>`;

    saveBtn.addEventListener("click", saveUserInfo);

    btnDiv.setAttribute(`class`, `d-flex flex-column justify-content-between align-items-center`);

    idList.appendChild(numberSpan);
    idList.appendChild(infoDiv);
    idList.appendChild(btnDiv);

    userInfo.appendChild(idList);
    userInfo.appendChild(errList);
  }

  let name = document.querySelectorAll(".nickName");
  let code = document.querySelectorAll(".friendCode");
  let inputNickName = document.querySelectorAll(".input-nickName");
  let inputFriendCode = document.querySelectorAll(".input-friendCode");

  name[0].innerText = userInfoGet.nickname1;
  name[1].innerText = userInfoGet.nickname2;
  name[2].innerText = userInfoGet.nickname3;
  name[3].innerText = userInfoGet.nickname4;
  name[4].innerText = userInfoGet.nickname5;

  code[0].innerText = userInfoGet.friendCode1;
  code[1].innerText = userInfoGet.friendCode2;
  code[2].innerText = userInfoGet.friendCode3;
  code[3].innerText = userInfoGet.friendCode4;
  code[4].innerText = userInfoGet.friendCode5;

  inputNickName[0].value = userInfoGet.nickname1;
  inputNickName[1].value = userInfoGet.nickname2;
  inputNickName[2].value = userInfoGet.nickname3;
  inputNickName[3].value = userInfoGet.nickname4;
  inputNickName[4].value = userInfoGet.nickname5;

  inputFriendCode[0].value = userInfoGet.friendCode1;
  inputFriendCode[1].value = userInfoGet.friendCode2;
  inputFriendCode[2].value = userInfoGet.friendCode3;
  inputFriendCode[3].value = userInfoGet.friendCode4;
  inputFriendCode[4].value = userInfoGet.friendCode5;

  //PAINT SIDEBAR USER INFORMATION
  sidebarList[0].innerText = userInfoGet.nickname1;
	if(userInfoGet.nickname2 == "") {
		sidebarList[1].innerText = "Empty";
	} else {
  	sidebarList[1].innerText = userInfoGet.nickname2;		
	}
	if(userInfoGet.nickname3 == "") {
		sidebarList[2].innerText = "Empty";
	} else {
  	sidebarList[2].innerText = userInfoGet.nickname3;		
	}
	if(userInfoGet.nickname4 == "") {
		sidebarList[3].innerText = "Empty";
	} else {
  	sidebarList[3].innerText = userInfoGet.nickname4;		
	}
	if(userInfoGet.nickname5 == "") {
		sidebarList[4].innerText = "Empty";
	} else {
  	sidebarList[4].innerText = userInfoGet.nickname5;		
	}

}

// LOAD DATA FROM GET REQUEST
function loadUserInfo(data) {
  const userInfoGet = data;
  profile.innerText = userInfoGet.nickname1;
  profile.setAttribute("style", "margin-top: 10px");
  paintUserInfo(data);
}

//BINDING SAVE BUTTON EVENT
sendBtn.addEventListener("click", postUserInfo);

//AJAX REQUEST
function sendAjax(url, method, data, callback) {
  const httpReq = new XMLHttpRequest();
  httpReq.open(method, url, true);

  httpReq.setRequestHeader("Access-Control-Allow-Headers", "*");
  httpReq.setRequestHeader("Content-type", "application/json");
  httpReq.setRequestHeader("Access-Control-Allow-Origin", "*");

  httpReq.onreadystatechange = function () {
    if (httpReq.readyState === 4 && httpReq.status === 200) {
      callback(httpReq);
    }
  };

  if (data != null) {
    httpReq.send(data);
  } else {
    httpReq.send();
  }
}

//GET USER INFORMATION
function getUserInfo() {
  const url = "/mypage";

  sendAjax(url, "GET", null, function (res) {
    let result = JSON.parse(res.response);
	console.log("mypage data : " + result);
    loadUserInfo(result);
  });
}

//POST USER INFORMATION
function postUserInfo() {
  let inputData = userInfoInput;
  let jsonData = JSON.stringify(inputData);
  const url = "/mypage";

  sendAjax(url, "POST", jsonData, function (res) {
    console.log("POST DATA: ", jsonData);
 	 console.log(res.response);
    if (res.response == 1) {
      alert("Your information has been updated. 😉");
    } else {
      alert("Faild to update. 😣");
    }
  });
}

// USING AJAX CALL
window.addEventListener("load", getUserInfo);

// CHECK FUNCTION ON LOCAL TEST
// window.addEventListener("load", loadUserInfo);