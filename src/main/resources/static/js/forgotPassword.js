const form = document.getElementById("forgot-form");

let closebtn = document.getElementById("modalCloseBtn");

closebtn.addEventListener("click", () => {
  closebtn.setAttribute("data-dismiss", "modal");
  form.reset();
});

form.addEventListener("submit", (event) => {
  event.preventDefault();
  let submitButton = document.getElementById("submit");
  submitButton.style.visibility = "hidden";
  const email = document.getElementById("email").value;
  localStorage.setItem("email", email);
  //add css to loader
  let loadder = document.getElementById("forgot-loading");
  loadder.style.visibility = "visible";
  const formData = new FormData(form);
  let forgotModal = document.getElementById("exampleModal");
  //send data to server
  fetch("http://10.68.0.118:9090/smartcontactmanager/forgot-form", {
    method: "POST",
    body: formData,
  })
    .then(function (res) {
      return res.json();
    })
    .then(function (data) {
      //user not found
      if (data.message === "User not found") {
        showUserNotFound();
      } else if (data.message === "Otp send to your email") {
        //user found with this email
        showUserFound(data, forgotModal, form, loadder);
      } else {
        Swal.fire(data.message);
      }
    })
    .catch(function (error) {
      console.log(error);
    });
});

const showUserNotFound = () => {
  Swal.fire({
    icon: "error",
    title: "Oops...",
    text: "User not found try again",
    allowOutsideClick: false,
  }).then(() => {
    localStorage.removeItem("email");
    location.reload();
  });
};

const showUserFound = (data, forgotModal, form, loadder) => {
  Swal.fire({
    icon: "success",
    text: data.message,
    allowOutsideClick: false,
  }).then(() => {
    //load new form for otp
    forgotModal.style.display = "none";
    // Remove the modal backdrop by finding and removing the class
    var modalBackdrops = document.getElementsByClassName("modal-backdrop");
    while (modalBackdrops[0]) {
      modalBackdrops[0].parentNode.removeChild(modalBackdrops[0]);
    }
    form.reset();
    loadder.style.visibility = "hidden";
    openOtp();
  });
};

const openOtp = async () => {
  const { value: otp } = await Swal.fire({
    input: "text",
    inputLabel: "Enter otp ",
    allowOutsideClick: false,
    inputValidator: (value) => {
      if (!value) {
        return "Enter otp ";
      }
    },
  });
  console.log("OTp = ", otp);
  sendOtpToServerForVerify(otp);
};

const sendOtpToServerForVerify = (otp) => {
  let url = "http://10.68.0.118:9090/smartcontactmanager/verify-otp-new/" + otp;
  console.log(url);
  fetch(url, {
    method: "POST",
  })
    .then((res) => {
      if (res.status == 200) return res.json();
      else return { message: "Bad Request" };
    })
    .then((res) => {
      if (res.message === "true") {
        openNewPasswordForm();
      } else if (res.message === "Bad Request") {
        Swal.fire("Internal Server Error");
      } else {
        Swal.fire("Otp not match!Reenter otp").then(() => {
          openOtp();
        });
      }
    })
    .catch((er) => {
      console.log(er);
    });
};

const openNewPasswordForm = async () => {
  const { value: password } = await Swal.fire({
    title: "Change Your Password",
    input: "password",
    allowOutsideClick: false,
  });
  let email = localStorage.getItem("email");
  let url = `http://10.68.0.118:9090/smartcontactmanager/change-password/${email}/${password}`;
  fetch(url, {
    method: "POST",
  })
    .then((res) => {
      return res.json();
    })
    .then((res) => {
      if (res.message === "Password Changed Successfully ") {
        Swal.fire({
          title: "Done",
          text: "Password Changed Successfully",
          icon: "success",
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Something went wrong!",
        });
      }
    });

  localStorage.removeItem("email");
};
