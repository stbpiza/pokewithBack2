//INITIALIZE VARIABLES
const registerBtn = document.querySelector(".register-btn");
const clearBtn = document.querySelector(".clear-btn");
const errMsg = document.querySelector(".err-msg");

const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");
const inputPassword2 = document.querySelector(".password2");
const inputName = document.querySelector(".nickname1");
const inputCode = document.querySelector(".friendCode1");
// console.log(inputEmail, inputPassword, inputPassword2, inputName, inputCode);

//DUMMY DATA FROM GET REQUEST
const userInfoData = {
  // userId: "1668589466621909",
  nickname1: "",
  friendCode1: "",
};

//DUMMY DATA FOR POST REQUEST
let userInfoInput = {
  email: "",
  password: "",
  nickname1: "",
  friendCode1: "",
};

//BINDING CLEAR BUTTTON EVENT
function clearInput(event) {
  event.preventDefault();
  inputEmail.classList.remove("err-display");
  inputEmail.value = "";
  inputPassword.classList.remove("err-display");
  inputPassword.value = "";
  inputPassword2.classList.remove("err-display");
  inputPassword2.value = "";
  inputName.classList.remove("err-display");
  inputName.value = "";
  inputCode.classList.remove("err-display");
  inputCode.value = "";
  errMsg.innerText = "";
}

//BINDING HANDLING REGISTER EVENT
function handleRegister() {
  errMsg.innerText = "";

  const sendEmail = inputEmail.value;
  const emailRgx = RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
  const email = emailRgx.test(sendEmail);
  if (email == false) {
    errMsg.innerText = "Email should be email address form.";
    inputEmail.classList.add("err-display");
    return "";
  } else {
    inputEmail.classList.remove("err-display");
  }

  const sendPassword = inputPassword.value;
  const passwordRgx = RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/);
  const password = passwordRgx.test(sendPassword);
  if (password == false) {
    errMsg.innerText = "Password should be contain at least one alphabet, number, special character. And 8~20 character.";
    inputPassword.classList.add("err-display");
    return "";
  } else {
    inputPassword.classList.remove("err-display");
  }

  const sendPassword2 = inputPassword2.value;
  if (sendPassword != sendPassword2) {
    errMsg.innerText = "Password and Password again should be same.";
    inputPassword.classList.add("err-display");
    inputPassword2.classList.add("err-display");
    return "";
  } else {
    inputPassword.classList.remove("err-display");
    inputPassword2.classList.remove("err-display");
  }

  // CHECK INPUT NICKNAME VALUE FROM REGULAR EXPRESSION
  const sendName = inputName.value;
  const nameRgx = RegExp(/^[A-Za-z\d]{2,20}$/);
  const name = nameRgx.test(sendName);
  if (name == false) {
    errMsg.innerText = "Nickname should be 2~20 character.";
    inputName.classList.add("err-display");
    return "";
  } else {
    inputName.classList.remove("err-display");
  }

  // CHECK INPUT FRIENDCODE VALUE FROM REGULAR EXPRESSION
  const sendCode = inputCode.value;
  const codeRgx = RegExp(/^[\d]{4}[-][\d]{4}[-][\d]{4}[-][\d]{4}$/);
  const code = codeRgx.test(sendCode);
  if (code == false) {
    errMsg.innerText = "Please insert 12-digit number.(0000-0000-0000-0000)";
    inputCode.classList.add("err-display");
    return "";
  } else {
    inputCode.classList.remove("err-display");
  }
  userInfoInput.email = sendEmail;
  userInfoInput.password = sendPassword;
  userInfoInput.nickname1 = sendName;
  userInfoInput.friendCode1 = sendCode;
  postUserInfo();
}

// ADDITIONAL USER INFORMATION FOR POST REQUEST
//POST USER INFORMATION

function postUserInfo() {
  let inputData = userInfoInput;
  let jsonData = JSON.stringify(inputData);
  const url = "/api/signup";

  fetch(url, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: jsonData,
    })
      .then((response) => {
        if (response.status === 200) {
             alert("signup success")
          window.location.href = "/";
        } else if (response.status === 400) {
           alert("signup fail")
        }
  })
}

//BINDING SINGLE EVENT LISTENER FOR EACH BUTTONS
registerBtn.addEventListener("click", handleRegister);
clearBtn.addEventListener("click", clearInput);