//INITIALIZE VARIABLES
const registerBtn = document.querySelector(".register-btn");
const clearBtn = document.querySelector(".clear-btn");
const errMsg = document.querySelector(".err-msg");

const inputName = document.querySelector(".nickname1");
const inputCode = document.querySelector(".friendCode1");
console.log(inputName, inputCode);

//DUMMY DATA FROM GET REQUEST
const userInfoData = {
  // userId: "1668589466621909",
  nickname1: "",
  friendCode1: "",
};

//DUMMY DATA FOR POST REQUEST
let userInfoInput = {
  // userId: "1668589466621909",
  nickname1: "",
  friendCode1: "",
};

//BINDING CLEAR BUTTTON EVENT
function clearInput(event) {
  event.preventDefault();
  inputName.classList.remove("err-display");
  inputName.value = "";
  inputCode.classList.remove("err-display");
  inputCode.value = "";
  errMsg.innerText = "";
}

//BINDING HANDLING REGISTER EVENT
function handleRegister() {
  const sendName = inputName.value;
  // CHECK INPUT NICKNAME VALUE FROM REGULAR EXPRESSION
  const nameRgx = RegExp(/^[가-힣A-Za-z0-9_\-]{3,20}$/);
  const name = nameRgx.test(sendName);
  if (name == false) {
    errMsg.innerText = "Nickname should be contains at least 3 charecter.";
    inputName.classList.add("err-display");
  } else {
    inputName.classList.remove("err-display");
    const sendCode = inputCode.value;
    // CHECK INPUT FRIENDCODE VALUE FROM REGULAR EXPRESSION
    const codeRgx = RegExp(/^[0-9_\-]{12}$/);
    const code = codeRgx.test(sendCode);
    if (code == false) {
      errMsg.innerText = "Please insert 12-digit number.";
      inputCode.classList.add("err-display");
    } else {
      inputCode.classList.remove("err-display");
      userInfoInput.nickname1 = sendName;
      userInfoInput.friendCode1 = sendCode;
      postUserInfo();
    }
  }
}
// ADDITIONAL USER INFORMATION FOR POST REQUEST

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

//POST USER INFORMATION

function postUserInfo() {
  let inputData = userInfoInput;
  let jsonData = JSON.stringify(inputData);
  const url = "/signup";

  sendAjax(url, "POST", jsonData, function (res) {
    console.log("POST DATA: ", jsonData);
    console.log(res.response);
    if (res.response == 1) {
      alert("Your information has been registered. 😉");
      window.location.href = "/";
    } else {
      alert("Faild to register. 😣");
    }
  });
}

//BINDING SINGLE EVENT LISTENER FOR EACH BUTTONS
registerBtn.addEventListener("click", handleRegister);
clearBtn.addEventListener("click", clearInput);