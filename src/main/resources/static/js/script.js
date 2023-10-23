const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

const getSingleContact = (id) => {
  let url = "http://localhost:9090/smartcontactmanager/singlecontact/" + id;
  let loader = document.getElementById("loader");

  loader.style.visibility = "visible";
  let p = fetch(url)
    .then((res) => {
      console.log(res.status);
      return res.json();
    })
    .then((value) => {
      let h1 = document.getElementById("contact-name");
      let loader = document.getElementById("loader");
      let d = document.getElementById("image");
      loader.style.visibility = "hidden";
      d.src =
        "http://localhost:9090/smartcontactmanager/images/" +
        value.contactImage;

      h1.innerHTML = `${value.contactName}`;
    });
};
