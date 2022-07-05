const loginBtn = document.querySelector(".login-btn");

function loginRequest() {
  loginBtn.setAttribute(
    "href",
    "https://www.facebook.com/v2.5/dialog/oauth?client_id=630118604601342&response_type=code&scope=email&redirect_uri=https%3A%2F%2F192.168.1.136%3A8443%2FfacebookSignInCallback"
  );
}
loginBtn.addEventListener("click", loginRequest);
