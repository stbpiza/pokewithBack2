const fLoginBtn = document.querySelector(".f-login-btn");
const loginBtn = document.querySelector(".login-btn");
const errMsg = document.querySelector(".err-msg");

const inputEmail = document.querySelector(".email");
const inputPassword = document.querySelector(".password");

let loginInput = {
  email: "",
  password: ""
};

function handleLogin() {
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

  loginInput.email = sendEmail;
  loginInput.password = sendPassword;
  loginRequest();
}

function loginRequest() {
  let inputData = loginInput;
  let jsonData = JSON.stringify(inputData);
  const url = "/api/login";

  fetch(url, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: jsonData,
    })
      .then((response) => {
        if (response.status == 200) {
          alert("login success")
          window.location.href = "/";
        } else if (response.status == 400) {
          alert("check your email or password")
        }
  })
}

function fLoginRequest() {
  fLoginBtn.setAttribute(
    "href",
    "https://www.facebook.com/v2.5/dialog/oauth?client_id=630118604601342&response_type=code&scope=email&redirect_uri=https%3A%2F%2F192.168.1.136%3A8443%2FfacebookSignInCallback"
  );
}

fLoginBtn.addEventListener("click", fLoginRequest);
loginBtn.addEventListener("click", handleLogin);
