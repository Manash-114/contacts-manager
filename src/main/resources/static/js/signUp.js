// let v = document.getElementById("verify").innerHTML;
// let email = document.getElementById("email").innerHTML;
// let spinner = document.getElementById("spinner");
// if (v == 0) {
//   Swal.fire({
//     icon: "error",
//     title: "Oops...",
//     allowOutsideClick: false,
//     text: "Verify Your email to continue!",
//   }).then(() => {
//     spinner.style.visibility = "visible";
//     sendOtpToMail();
//   });
// }

// const sendOtpToMail = () => {
//   let url = `http://localhost:9090/smartcontactmanager/signup-otp/${email}`;
//   console.log("Email = ", email);
//   fetch(url, {
//     method: "POST",
//   })
//     .then((res) => {
//       return res.json();
//     })
//     .then((res) => {
//       spinner.style.visibility = "hidden";
//       if (res.message === "true") {
//         Swal.fire({
//           icon: "success",
//           text: "An Otp send to your email",
//           allowOutsideClick: false,
//         }).then(() => {
//           //load new form for otp
//           openOtp();
//         });
//       } else {
//         Swal.fire({
//           icon: "error",
//           text: "Not A valid email",
//           allowOutsideClick: false,
//         }).then(() => {
//           spinner.style.visibility = "hidden";
//         });
//       }
//     });
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
//   let url =
//     "http://localhost:9090/smartcontactmanager/verify-signup-otp/" + otp;
//   fetch(url, {
//     method: "POST",
//   })
//     .then((res) => {
//       if (res.status == 200) return res.json();
//       else return { message: "Bad Request" };
//     })
//     .then((res) => {
//       if (res.message === "true") {
//         //otp match // show success message
//         //update data to db
//         updateEmailVerify();
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

// const updateEmailVerify = () => {
//   let url = `http://localhost:9090/smartcontactmanager/user/is-verify/${email}`;
//   fetch(url, {
//     method: "POST",
//   })
//     .then((res) => {
//       console.log("After update table", res);
//       console.log("status text = ", res.statusText);
//     })
//     .then(() => {
//       console.log("second then");
//     })
//     .catch((er) => {
//       console.log(er);
//     });
// };
