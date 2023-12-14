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
