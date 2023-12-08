const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

const ip = "10.68.0.118";
const getSingleContactImage = (id) => {
  let loader = document.getElementById("loader");
  loader.style.display = "block";
  let url = `http://${ip}:9090/smartcontactmanager/singlecontact/${id}`;

  loader.style.visibility = "visible";
  fetch(url)
    .then((res) => {
      console.log(res.status);
      return res.json();
    })
    .then((value) => {
      let d = document.getElementById("image");
      loader.style.display = "none";
      d.src =
        `http://${ip}:9090/smartcontactmanager/images/` + value.contactImage;
    });
};

const getSingleContact = (id) => {
  let loader = document.getElementById("loader");
  loader.style.display = "block";
  let url = `http://${ip}:9090/smartcontactmanager/singlecontact/${id}`;

  fetch(url)
    .then((res) => {
      return res.json();
    })
    .then((value) => {
      Swal.fire({
        html: `
        <div class="d-flex justify-content-center">
        <div class="card my-card" style="width: 30rem">
          <div class="container text-center">
            <h2>Contact Details</h2>
          </div>
          <div class="container text-center mt-2">
          <img
          id = "contactImageId"
          class="rounded-circle"
          alt="Cinque Terre"
          width="200px"
          height="200px"
        />
          </div>
          <div class="card-body">
            <div class="container">
              <div class="details">
                <p>Name : ${value.contactName} </p>
                <p>Nick Name : ${value.contactNickname}</p>
                <p>Email : ${value.contactEmail}</p>
                <p>Phone Number : ${value.contactPhoneNumber}</p>
                <p>Alternate Number : ${value.contactAlternatePhoneNumber}</p>
                <p>Working As : ${value.contactWork}</p>
               
              </div>
            </div>
          </div>
        </div>
      </div>
        `,
        showCloseButton: true,
        focusConfirm: false,
      });
      let d = document.getElementById("contactImageId");
      d.src =
        `http://${ip}:9090/smartcontactmanager/images/` + value.contactImage;
    });
};

const deleteContact = (id) => {
  let url = `http://${ip}:9090/smartcontactmanager/delete-contact/${id}`;

  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    console.log(result);
    if (result.isConfirmed) {
      fetch(url, { method: "DELETE" })
        .then((res) => {
          return res.text();
        })
        .then((value) => {
          if (value === "Deleted Successfully") {
            Swal.fire(
              "Deleted!",
              "Your contact has been deleted.",
              "success"
            ).then(() => {
              window.location = "0";
            });
          }
        });
    }
  });
};

const search = () => {
  console.log("searching");
  let query = $("#search-input").val();
  if (query == "") {
    $(".search-result").hide();
  } else {
    //search
    $(".search-result").show();

    //sending request to server
    let url = "http://localhost:9090/smartcontactmanager/search/" + query;
    fetch(url)
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        let txt = `<div class = list-group>`;
        data.forEach((e) => {
          txt += `<a href = '#' onclick = 'getSingleContact(${e.contactId})' class = 'list-group-item list-group-action'> ${e.contactName}</a>`;
        });
        txt += `</diV>`;
        $(".search-result").html(txt);
        $(".search-result").show();
      });
  }
};

// const form = document.getElementById("forgot-form");

// let closebtn = document.getElementById("modalCloseBtn");

// closebtn.addEventListener("click", () => {
//   closebtn.setAttribute("data-dismiss", "modal");
//   form.reset();
// });

// form.addEventListener("submit", (event) => {
//   event.preventDefault();
//   let submitButton = document.getElementById("submit");
//   submitButton.style.visibility = "hidden";
//   const email = document.getElementById("email").value;
//   localStorage.setItem("email", email);
//   //add css to loader
//   let loadder = document.getElementById("forgot-loading");
//   loadder.style.visibility = "visible";
//   const formData = new FormData(form);
//   let forgotModal = document.getElementById("exampleModal");
//   //send data to server
//   fetch("http://localhost:9090/smartcontactmanager/forgot-form", {
//     method: "POST",
//     body: formData,
//   })
//     .then(function (res) {
//       return res.json();
//     })
//     .then(function (data) {
//       //user not found
//       if (data.message === "User not found") {
//         showUserNotFound();
//       } else if (data.message === "Otp send to your email") {
//         //user found with this email
//         showUserFound(data, forgotModal, form, loadder);
//       } else {
//         Swal.fire(data.message);
//       }
//     })
//     .catch(function (error) {
//       console.log(error);
//     });
// });

// const showUserNotFound = () => {
//   Swal.fire({
//     icon: "error",
//     title: "Oops...",
//     text: "User not found try again",
//     allowOutsideClick: false,
//   }).then(() => {
//     localStorage.removeItem("email");
//     location.reload();
//   });
// };

// const showUserFound = (data, forgotModal, form, loadder) => {
//   Swal.fire({
//     icon: "success",
//     text: data.message,
//     allowOutsideClick: false,
//   }).then(() => {
//     //load new form for otp
//     forgotModal.style.display = "none";
//     // Remove the modal backdrop by finding and removing the class
//     var modalBackdrops = document.getElementsByClassName("modal-backdrop");
//     while (modalBackdrops[0]) {
//       modalBackdrops[0].parentNode.removeChild(modalBackdrops[0]);
//     }
//     form.reset();
//     loadder.style.visibility = "hidden";
//     openOtp();
//   });
// };

// const openOtp = async () => {
//   const { value: otp } = await Swal.fire({
//     input: "text",
//     inputLabel: "Enter otp ",
//     allowOutsideClick: false,
//     inputValidator: (value) => {
//       if (!value) {
//         return "Enter otp ";
//       }
//     },
//   });
//   console.log("OTp = ", otp);
//   sendOtpToServerForVerify(otp);
// };

// const sendOtpToServerForVerify = (otp) => {
//   let url = "http://localhost:9090/smartcontactmanager/verify-otp-new/" + otp;
//   console.log(url);
//   fetch(url, {
//     method: "POST",
//   })
//     .then((res) => {
//       if (res.status == 200) return res.json();
//       else return { message: "Bad Request" };
//     })
//     .then((res) => {
//       if (res.message === "true") {
//         openNewPasswordForm();
//       } else if (res.message === "Bad Request") {
//         Swal.fire("Internal Server Error");
//       } else {
//         Swal.fire("Otp not match!Reenter otp").then(() => {
//           openOtp();
//         });
//       }
//     })
//     .catch((er) => {
//       console.log(er);
//     });
// };

// const openNewPasswordForm = async () => {
//   const { value: password } = await Swal.fire({
//     title: "Change Your Password",
//     input: "password",
//     allowOutsideClick: false,
//   });
//   let email = localStorage.getItem("email");
//   let url = `http://localhost:9090/smartcontactmanager/change-password/${email}/${password}`;
//   fetch(url, {
//     method: "POST",
//   })
//     .then((res) => {
//       return res.json();
//     })
//     .then((res) => {
//       if (res.message === "Password Changed Successfully ") {
//         Swal.fire({
//           title: "Done",
//           text: "Password Changed Successfully",
//           icon: "success",
//         });
//       } else {
//         Swal.fire({
//           icon: "error",
//           title: "Oops...",
//           text: "Something went wrong!",
//         });
//       }
//     });

//   localStorage.removeItem("email");
// };

window.addEventListener("load", (e) => {
  let url = "http://localhost:9090/smartcontactmanager/test/t1";
  fetch(url, {
    method: "Get",
  })
    .then((res) => {
      console.log(res);
      return res.json();
    })
    .then((res) => {
      console.log("Second then");
      console.log("Second thern res = ", res);
    })
    .catch((er) => {
      console.log(er);
    });
});
